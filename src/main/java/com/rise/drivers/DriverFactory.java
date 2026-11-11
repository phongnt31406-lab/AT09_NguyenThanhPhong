package com.rise.drivers;

import com.rise.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

/**
 * Khởi tạo WebDriver theo cấu hình (browser, headless, kích thước cửa sổ). Driver do Selenium Manager tự tải.
 */
public final class DriverFactory {

    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");
        int width = ConfigReader.getInt("window.width");
        int height = ConfigReader.getInt("window.height");

        WebDriver driver = switch (browser) {
            case "chrome" -> new ChromeDriver(chromiumOptions(new ChromeOptions(), headless, width, height));
            case "edge" -> new EdgeDriver(chromiumOptions(new EdgeOptions(), headless, width, height));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless, width, height));
            default -> throw new IllegalArgumentException("Browser không được hỗ trợ: " + browser + " (chrome | edge | firefox)");
        };
        driver.manage().window().setSize(new Dimension(width, height));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("timeout.page.load")));
        LOG.info("Khởi tạo {} (headless={}, {}x{})", browser, headless, width, height);
        return driver;
    }

    private static <T extends ChromiumOptions<T>> T chromiumOptions(T options, boolean headless, int width, int height) {
        options.addArguments("--window-size=" + width + "," + height, "--disable-notifications", "--lang=en-US");
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless, int width, int height) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=" + width, "--height=" + height);
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }
}
