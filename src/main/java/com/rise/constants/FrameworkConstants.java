package com.rise.constants;

import com.rise.config.ConfigReader;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Đường dẫn và hằng số dùng chung. Mọi output khi chạy test nằm trong thư mục reports/.
 */
public final class FrameworkConstants {

    public static final Path REPORTS_DIR = Paths.get(ConfigReader.get("reports.dir", "reports"));
    public static final Path ALLURE_RESULTS_DIR = REPORTS_DIR.resolve("allure-results");
    public static final Path SCREENSHOTS_DIR = REPORTS_DIR.resolve("screenshots");
    public static final Path VIDEOS_DIR = REPORTS_DIR.resolve("videos");
    public static final Path TESTDATA_DIR = Paths.get(ConfigReader.get("testdata.dir"));

    public static final String SCREENSHOT_PASSED = "trang_thai_cuoi_cua_test";
    public static final String SCREENSHOT_FAILED = "trang_thai_khi_that_bai";
    public static final String VIDEO_ATTACHMENT = "video_ghi_lai_test";

    public static final String COLUMN_TC_ID = "TC_ID";
    public static final String COLUMN_RUN = "Run";
    public static final String COLUMN_TITLE = "Title";

    private FrameworkConstants() {
    }
}
