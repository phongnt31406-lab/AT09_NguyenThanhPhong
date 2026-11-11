package com.rise.utils;

import com.rise.constants.FrameworkConstants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Một dòng dữ liệu Excel (tên cột → giá trị đã thay token).
 * toString chỉ in TC_ID để report không lộ mật khẩu khi dùng làm tham số DataProvider.
 */
public final class TestDataRow {

    private final Map<String, String> values;

    public TestDataRow(Map<String, String> values) {
        this.values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
    }

    public String get(String column) {
        if (!values.containsKey(column)) {
            throw new IllegalArgumentException("Không có cột '" + column + "' trong dữ liệu " + tcId() + ". Các cột: " + values.keySet());
        }
        return values.get(column);
    }

    public String tcId() {
        return values.getOrDefault(FrameworkConstants.COLUMN_TC_ID, "");
    }

    public String title() {
        return values.getOrDefault(FrameworkConstants.COLUMN_TITLE, "");
    }

    @Override
    public String toString() {
        return tcId();
    }
}
