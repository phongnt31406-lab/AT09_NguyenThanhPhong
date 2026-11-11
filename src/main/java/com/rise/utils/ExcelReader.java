package com.rise.utils;

import com.rise.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Đọc test data từ Excel: mỗi module 1 file (Login.xlsx, Clients.xlsx...), mỗi nhóm dữ liệu 1 sheet.
 * Dòng 1 là header; cột TC_ID định danh dòng; cột Run = N để bỏ qua dòng đó.
 */
public final class ExcelReader {

    private static final Logger LOG = LogManager.getLogger(ExcelReader.class);
    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelReader() {
    }

    public static List<TestDataRow> readSheet(String fileName, String sheetName) {
        File file = FrameworkConstants.TESTDATA_DIR.resolve(fileName).toFile();
        try (Workbook workbook = WorkbookFactory.create(file, null, true)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Không có sheet '" + sheetName + "' trong " + file);
            }
            List<String> headers = readHeaders(sheet.getRow(0));
            List<TestDataRow> rows = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isBlank(row, headers.size())) {
                    continue;
                }
                String stamp = DataGenerator.uniqueStamp();
                Map<String, String> values = new LinkedHashMap<>();
                for (int c = 0; c < headers.size(); c++) {
                    values.put(headers.get(c), DataGenerator.resolve(cellText(row.getCell(c)), stamp));
                }
                if (!"N".equalsIgnoreCase(values.getOrDefault(FrameworkConstants.COLUMN_RUN, "Y"))) {
                    rows.add(new TestDataRow(values));
                }
            }
            LOG.info("Đọc {} dòng dữ liệu từ {} / {}", rows.size(), fileName, sheetName);
            return rows;
        } catch (IOException e) {
            throw new UncheckedIOException("Không đọc được file test data " + file, e);
        }
    }

    public static TestDataRow readRow(String fileName, String sheetName, String tcId) {
        return readSheet(fileName, sheetName).stream()
                .filter(r -> r.tcId().equals(tcId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy TC_ID " + tcId + " trong " + fileName + " / " + sheetName));
    }

    public static Object[][] toDataProvider(List<TestDataRow> rows) {
        return rows.stream().map(r -> new Object[]{r}).toArray(Object[][]::new);
    }

    private static List<String> readHeaders(Row header) {
        if (header == null) {
            throw new IllegalArgumentException("Sheet thiếu dòng header");
        }
        List<String> headers = new ArrayList<>();
        for (int c = 0; c < header.getLastCellNum(); c++) {
            String name = cellText(header.getCell(c)).trim();
            if (name.isEmpty()) {
                break;
            }
            headers.add(name);
        }
        return headers;
    }

    private static boolean isBlank(Row row, int columns) {
        for (int c = 0; c < columns; c++) {
            if (!cellText(row.getCell(c)).isBlank()) {
                return false;
            }
        }
        return true;
    }

    private static String cellText(Cell cell) {
        return cell == null ? "" : FORMATTER.formatCellValue(cell);
    }
}
