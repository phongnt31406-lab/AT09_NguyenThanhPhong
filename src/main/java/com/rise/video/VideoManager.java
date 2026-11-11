package com.rise.video;

import com.rise.config.ConfigReader;
import com.rise.constants.FrameworkConstants;
import com.rise.utils.AllureManager;
import com.rise.utils.DataGenerator;
import com.rise.utils.FileNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Quản lý video theo thread. video.mode: all = giữ mọi video | failed = chỉ test FAIL | off = không quay.
 */
public final class VideoManager {

    private static final Logger LOG = LogManager.getLogger(VideoManager.class);
    private static final ThreadLocal<VideoRecorder> RECORDER = new ThreadLocal<>();

    private VideoManager() {
    }

    public static void start(WebDriver driver) {
        if ("off".equalsIgnoreCase(mode())) {
            return;
        }
        try {
            RECORDER.set(VideoRecorder.startFor(driver));
        } catch (RuntimeException e) {
            LOG.warn("Không bắt đầu quay video được: {}", e.getMessage().lines().findFirst().orElse(""));
        }
    }

    /** Dừng quay; lưu + đính Allure theo video.mode. */
    public static void stopAndAttach(String testName, boolean failed) {
        VideoRecorder recorder = RECORDER.get();
        RECORDER.remove();
        if (recorder == null) {
            return;
        }
        recorder.stop();
        if ("failed".equalsIgnoreCase(mode()) && !failed) {
            return;
        }
        Path mp4 = FrameworkConstants.VIDEOS_DIR.resolve(FileNames.safe(testName) + "_" + DataGenerator.uniqueStamp() + ".mp4");
        try {
            if (recorder.saveTo(mp4)) {
                AllureManager.attachVideo(FrameworkConstants.VIDEO_ATTACHMENT, mp4);
            }
        } catch (IOException | RuntimeException e) {
            LOG.warn("Không lưu được video {}: {}", mp4, e.getMessage());
        }
    }

    private static String mode() {
        return ConfigReader.get("video.mode", "all");
    }
}
