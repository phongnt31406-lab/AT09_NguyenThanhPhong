package com.rise.utils;

import com.rise.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Chụp màn hình browser hiện tại và lưu ra reports/screenshots khi cần.
 */
public final class ScreenshotUtil {

    private static final Logger LOG = LogManager.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
    }

    public static Optional<byte[]> capture(WebDriver driver) {
        try {
            return Optional.of(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (WebDriverException e) {
            LOG.warn("Không chụp được màn hình: {}", e.getMessage().lines().findFirst().orElse(""));
            return Optional.empty();
        }
    }

    public static Path saveToDisk(byte[] png, String baseName) {
        Path file = FrameworkConstants.SCREENSHOTS_DIR.resolve(FileNames.safe(baseName) + "_" + DataGenerator.uniqueStamp() + ".png");
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, png);
            LOG.info("Đã lưu screenshot: {}", file);
        } catch (IOException e) {
            LOG.warn("Không lưu được screenshot {}: {}", file, e.getMessage());
        }
        return file;
    }
}
