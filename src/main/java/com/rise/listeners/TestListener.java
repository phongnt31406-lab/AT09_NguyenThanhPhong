package com.rise.listeners;

import com.rise.config.ConfigReader;
import com.rise.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;

/**
 * Ghi log vòng đời test + tổng kết PASS/FAIL/SKIP, và sinh environment.properties cho Allure.
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ISuite suite) {
        LOG.info("===== Bắt đầu suite: {} =====", suite.getName());
        writeAllureEnvironment();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("▶ Bắt đầu: {}", name(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("✔ PASS: {} ({} ms)", name(result), duration(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("✘ FAIL: {} ({} ms) — {}", name(result), duration(result), String.valueOf(result.getThrowable()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("⤼ SKIP: {} — {}", name(result), String.valueOf(result.getThrowable()));
    }

    @Override
    public void onFinish(ITestContext context) {
        LOG.info("===== Kết quả '{}': PASS {} · FAIL {} · SKIP {} =====", context.getName(),
                context.getPassedTests().size(), context.getFailedTests().size(), context.getSkippedTests().size());
    }

    private static String name(ITestResult result) {
        String params = result.getParameters().length > 0 ? " " + Arrays.toString(result.getParameters()) : "";
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName() + params;
    }

    private static long duration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    private static void writeAllureEnvironment() {
        Properties env = new Properties();
        env.setProperty("Base.URL", ConfigReader.get("base.url"));
        env.setProperty("Browser", ConfigReader.get("browser"));
        env.setProperty("Headless", ConfigReader.get("headless"));
        env.setProperty("Parallel", ConfigReader.get("parallel.mode") + " / " + ConfigReader.get("thread.count") + " threads");
        env.setProperty("Video", ConfigReader.get("video.mode"));
        env.setProperty("Java", System.getProperty("java.version"));
        try {
            Files.createDirectories(FrameworkConstants.ALLURE_RESULTS_DIR);
            try (Writer writer = Files.newBufferedWriter(FrameworkConstants.ALLURE_RESULTS_DIR.resolve("environment.properties"), StandardCharsets.UTF_8)) {
                env.store(writer, "RISE CRM automation");
            }
        } catch (IOException e) {
            LOG.warn("Không ghi được environment.properties cho Allure: {}", e.getMessage());
        }
    }
}
