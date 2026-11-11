package com.rise.utils;

import com.rise.config.ConfigReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sinh dữ liệu unique + traceable và thay token trong dữ liệu Excel:
 * <ul>
 *   <li>{@code <ts>} → yyyyMMddHHmmss_RRR (cùng 1 giá trị cho mọi ô của một dòng)</li>
 *   <li>{@code ${key}} → giá trị cấu hình, VD {@code ${admin.email}}</li>
 * </ul>
 */
public final class DataGenerator {

    public static final String TS_TOKEN = "<ts>";
    private static final Pattern CONFIG_TOKEN = Pattern.compile("\\$\\{([\\w.]+)}");
    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private DataGenerator() {
    }

    /** Dấu thời gian unique kể cả khi nhiều thread chạy cùng giây, VD 20261110153045_482. */
    public static String uniqueStamp() {
        return LocalDateTime.now().format(TS_FORMAT) + "_" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String uniqueName(String prefix) {
        return prefix + "_" + uniqueStamp();
    }

    public static String uniqueEmail(String prefix) {
        return prefix.toLowerCase() + "_" + uniqueStamp() + "@example.com";
    }

    public static String resolve(String raw, String stamp) {
        if (raw == null || raw.isEmpty()) {
            return raw;
        }
        String value = raw.replace(TS_TOKEN, stamp);
        Matcher matcher = CONFIG_TOKEN.matcher(value);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(ConfigReader.get(matcher.group(1))));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
