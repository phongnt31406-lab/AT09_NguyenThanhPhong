package com.rise.dataproviders;

import com.rise.utils.ExcelReader;
import org.testng.annotations.DataProvider;

/**
 * DataProvider module Login — đọc Login.xlsx. parallel = true để các dòng dữ liệu chạy song song.
 */
public final class LoginDataProvider {

    private LoginDataProvider() {
    }

    @DataProvider(name = "loginInvalid", parallel = true)
    public static Object[][] loginInvalid() {
        return ExcelReader.toDataProvider(ExcelReader.readSheet(TestDataFiles.LOGIN, TestDataFiles.LOGIN_INVALID));
    }
}
