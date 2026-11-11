package com.rise.base;

import com.rise.drivers.DriverFactory;
import com.rise.drivers.DriverManager;
import com.rise.video.VideoManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Mỗi test method có browser riêng (ThreadLocal) → chạy parallel độc lập.
 * Ảnh + video cuối test do ReportListener đính kèm ngay sau thân test, trước khi đóng browser ở đây.
 */
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpBrowser() {
        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        VideoManager.start(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        DriverManager.quitDriver();
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }
}
