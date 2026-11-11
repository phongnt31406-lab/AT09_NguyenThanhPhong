package com.rise.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Đọc cấu hình từ classpath: configs/config.properties và configs/credentials.properties.
 * Thứ tự ưu tiên: System property (-Dkey=value) → biến môi trường (KEY_NAME) → file properties.
 */
public final class ConfigReader {

    private static final Logger LOG = LogManager.getLogger(ConfigReader.class);
    private static final String[] CONFIG_FILES = {"configs/config.properties", "configs/credentials.properties"};
    private static final Properties PROPERTIES = loadAll();

    private ConfigReader() {
    }

    private static Properties loadAll() {
        Properties properties = new Properties();
        for (String file : CONFIG_FILES) {
            try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(file)) {
                if (in == null) {
                    LOG.warn("Không tìm thấy file cấu hình trên classpath: {}", file);
                    continue;
                }
                properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
                LOG.info("Đã nạp cấu hình: {}", file);
            } catch (IOException e) {
                throw new UncheckedIOException("Không đọc được file cấu hình " + file, e);
            }
        }
        return properties;
    }

    public static String get(String key) {
        String value = lookup(key);
        if (value == null) {
            throw new IllegalStateException("Thiếu cấu hình '" + key + "' — khai báo trong configs/*.properties, "
                    + "biến môi trường " + toEnvName(key) + " hoặc -D" + key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = lookup(key);
        return value == null ? defaultValue : value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static String lookup(String key) {
        String system = System.getProperty(key);
        if (system != null && !system.isBlank()) {
            return system.trim();
        }
        String env = System.getenv(toEnvName(key));
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        String file = PROPERTIES.getProperty(key);
        return file == null || file.isBlank() ? null : file.trim();
    }

    private static String toEnvName(String key) {
        return key.toUpperCase().replace('.', '_');
    }
}
