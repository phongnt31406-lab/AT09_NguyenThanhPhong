package com.rise.video;

import com.rise.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.json.Json;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Quay video cho 1 browser bằng Chrome DevTools screencast (mỗi driver 1 video riêng → chạy được khi parallel/headless),
 * sau đó ghép frame thành MP4 (H.264) bằng JCodec.
 */
public final class VideoRecorder {

    private static final Logger LOG = LogManager.getLogger(VideoRecorder.class);

    private record Frame(long receivedNanos, byte[] jpeg) {
    }

    private final DevTools devTools;
    private final List<Frame> frames = new ArrayList<>();
    private final ExecutorService ackExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "video-ack");
        t.setDaemon(true);
        return t;
    });
    private long startNanos;
    private long stopNanos;

    private VideoRecorder(DevTools devTools) {
        this.devTools = devTools;
    }

    /** Trả về null nếu browser không hỗ trợ DevTools (VD Firefox). */
    public static VideoRecorder startFor(WebDriver driver) {
        if (!(driver instanceof HasDevTools hasDevTools)) {
            LOG.warn("Browser {} không hỗ trợ DevTools — bỏ qua quay video", driver.getClass().getSimpleName());
            return null;
        }
        VideoRecorder recorder = new VideoRecorder(hasDevTools.getDevTools());
        recorder.start();
        return recorder;
    }

    private void start() {
        devTools.createSessionIfThereIsNotOne();
        devTools.addListener(new Event<Map<String, Object>>("Page.screencastFrame", input -> input.read(Json.MAP_TYPE)), this::onFrame);
        devTools.send(new Command<Void>("Page.enable", Map.of()));
        devTools.send(new Command<Void>("Page.startScreencast", Map.of(
                "format", "jpeg",
                "quality", ConfigReader.getInt("video.quality"),
                "maxWidth", ConfigReader.getInt("video.max.width"),
                "maxHeight", ConfigReader.getInt("video.max.height"),
                "everyNthFrame", 1)));
        startNanos = System.nanoTime();
    }

    private void onFrame(Map<String, Object> event) {
        byte[] jpeg = Base64.getDecoder().decode((String) event.get("data"));
        synchronized (frames) {
            frames.add(new Frame(System.nanoTime(), jpeg));
        }
        Object sessionId = event.get("sessionId");
        ackExecutor.submit(() -> devTools.send(new Command<Void>("Page.screencastFrameAck", Map.of("sessionId", sessionId))));
    }

    /** Dừng quay; gọi trước khi đóng browser. */
    public void stop() {
        stopNanos = System.nanoTime();
        try {
            devTools.send(new Command<Void>("Page.stopScreencast", Map.of()));
        } catch (RuntimeException e) {
            LOG.warn("Không dừng được screencast: {}", e.getMessage().lines().findFirst().orElse(""));
        } finally {
            ackExecutor.shutdown();
            awaitAcks();
            devTools.clearListeners();
        }
    }

    /** Ghép frame thành file MP4. Trả về false nếu không có frame nào. */
    public boolean saveTo(Path mp4) throws IOException {
        List<Frame> snapshot;
        synchronized (frames) {
            snapshot = new ArrayList<>(frames);
        }
        if (snapshot.isEmpty()) {
            return false;
        }
        int fps = ConfigReader.getInt("video.fps");
        long stepNanos = TimeUnit.SECONDS.toNanos(1) / fps;
        long endNanos = Math.min(stopNanos, startNanos + TimeUnit.SECONDS.toNanos(ConfigReader.getInt("video.max.seconds")));

        Files.createDirectories(mp4.getParent());
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(mp4.toFile(), fps);
        BufferedImage first = ImageIO.read(new ByteArrayInputStream(snapshot.get(0).jpeg()));
        int width = first.getWidth() & ~1;
        int height = first.getHeight() & ~1;
        int index = 0;
        BufferedImage current = normalize(first, width, height);
        for (long t = startNanos; t <= endNanos; t += stepNanos) {
            int next = index;
            while (next + 1 < snapshot.size() && snapshot.get(next + 1).receivedNanos() <= t) {
                next++;
            }
            if (next != index) {
                index = next;
                current = normalize(ImageIO.read(new ByteArrayInputStream(snapshot.get(index).jpeg())), width, height);
            }
            encoder.encodeImage(current);
        }
        encoder.finish();
        LOG.info("Đã lưu video {} ({} frame gốc)", mp4, snapshot.size());
        return true;
    }

    private void awaitAcks() {
        try {
            ackExecutor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static BufferedImage normalize(BufferedImage source, int width, int height) {
        BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return target;
    }
}
