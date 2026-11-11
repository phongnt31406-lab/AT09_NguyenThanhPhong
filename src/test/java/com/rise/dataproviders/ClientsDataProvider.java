package com.rise.dataproviders;

import com.rise.utils.ExcelReader;
import org.testng.annotations.DataProvider;

/**
 * DataProvider module Clients & Contacts — đọc Clients.xlsx. parallel = true để các dòng chạy song song.
 */
public final class ClientsDataProvider {

    private ClientsDataProvider() {
    }

    @DataProvider(name = "clientValidation", parallel = true)
    public static Object[][] clientValidation() {
        return sheet(TestDataFiles.CLIENT_VALIDATION);
    }

    @DataProvider(name = "clientSearch", parallel = true)
    public static Object[][] clientSearch() {
        return sheet(TestDataFiles.CLIENT_SEARCH);
    }

    @DataProvider(name = "contactValidation", parallel = true)
    public static Object[][] contactValidation() {
        return sheet(TestDataFiles.CONTACT_VALIDATION);
    }

    private static Object[][] sheet(String sheetName) {
        return ExcelReader.toDataProvider(ExcelReader.readSheet(TestDataFiles.CLIENTS, sheetName));
    }
}
