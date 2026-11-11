package com.rise.listeners;

import com.rise.config.ConfigReader;
import com.rise.constants.FrameworkConstants;
import com.rise.drivers.DriverManager;
import com.rise.utils.AllureManager;
import com.rise.utils.ScreenshotUtil;
import com.rise.video.VideoManager;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Chạy ngay sau thân test (trước khi Allure đóng test case) để ảnh + video nằm trong Test Body:
 * PASS → ảnh "trang_thai_cuoi_cua_test"; FAIL → ảnh "trang_thai_khi_that_bai" + lưu reports/screenshots.
 */
public class ReportListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (!method.isTestMethod()) {
            return;
        }
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return;
        }
        boolean failed = result.getStatus() == ITestResult.FAILURE;
        String testName = result.getMethod().getMethodName() + suffix(result);
        ScreenshotUtil.capture(driver).ifPresent(png -> {
            AllureManager.attachScreenshot(failed ? FrameworkConstants.SCREENSHOT_FAILED : FrameworkConstants.SCREENSHOT_PASSED, png);
            if (failed && ConfigReader.getBoolean("screenshot.fail.save.disk")) {
                ScreenshotUtil.saveToDisk(png, testName);
            }
        });
        VideoManager.stopAndAttach(testName, failed);
    }

    private static String suffix(ITestResult result) {
        Object[] params = result.getParameters();
        return params.length > 0 ? "_" + params[0] : "";
    }
}
