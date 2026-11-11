package com.rise.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import io.qameta.allure.util.ResultsUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Đính kèm ảnh/video vào Allure và cập nhật metadata cho test data-driven (mỗi dòng Excel = 1 TC).
 */
public final class AllureManager {

    private AllureManager() {
    }

    public static void attachScreenshot(String name, byte[] png) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png), "png");
    }

    public static void attachVideo(String name, Path mp4) {
        try (InputStream in = Files.newInputStream(mp4)) {
            Allure.addAttachment(name, "video/mp4", in, "mp4");
        } catch (IOException e) {
            throw new UncheckedIOException("Không đính kèm được video " + mp4, e);
        }
    }

    /** Đặt tên hiển thị + label testId theo dòng dữ liệu Excel. */
    public static void useTestCase(TestDataRow row) {
        Allure.getLifecycle().updateTestCase(tc -> {
            if (!row.title().isBlank()) {
                tc.setName(row.title());
            }
            tc.getLabels().add(new Label().setName("testId").setValue(row.tcId()));
            tc.getLinks().add(ResultsUtils.createTmsLink(row.tcId()));
        });
    }
}
