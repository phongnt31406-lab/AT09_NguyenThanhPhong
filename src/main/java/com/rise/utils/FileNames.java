package com.rise.utils;

/**
 * Chuẩn hoá chuỗi thành tên file an toàn trên Windows/Linux.
 */
public final class FileNames {

    private FileNames() {
    }

    public static String safe(String name) {
        String cleaned = name.replaceAll("[^A-Za-z0-9_.-]+", "_").replaceAll("_+", "_");
        return cleaned.length() > 120 ? cleaned.substring(0, 120) : cleaned;
    }
}
