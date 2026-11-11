package com.rise.tests.login;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.base.BaseTest;
import com.rise.dataproviders.LoginDataProvider;
import com.rise.dataproviders.TestDataFiles;
import com.rise.pages.dashboard.DashboardPage;
import com.rise.pages.login.LoginPage;
import com.rise.utils.AllureManager;
import com.rise.utils.ExcelReader;
import com.rise.utils.TestDataRow;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login Admin Page — 5 case @Core (RISE_LOGIN_TC_001 → 005). Dữ liệu: Login.xlsx.
 */
@Epic("RISE CRM")
@Feature("Login Admin Page")
@Story("Đăng nhập")
public class LoginTest extends BaseTest {

    @Test(description = "Đăng nhập thành công với tài khoản Admin hợp lệ", groups = {"smoke", "regression", "login"})
    @TestCaseId("RISE_LOGIN_TC_001")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({"smoke", "regression", "login", "core"})
    @Description("Kiểm tra Admin đăng nhập bằng email và mật khẩu hợp lệ thì được chuyển vào Dashboard và header hiển thị đúng tên người dùng.")
    public void testLoginWithValidCredentials() {
        TestDataRow data = ExcelReader.readRow(TestDataFiles.LOGIN, TestDataFiles.LOGIN_VALID, "RISE_LOGIN_TC_001");
        LoginPage loginPage = new LoginPage(driver());
        DashboardPage dashboardPage = new DashboardPage(driver());

        Allure.step("Arrange: Mở trang đăng nhập", () -> {
            loginPage.open();
        });
        Allure.step("Act: Đăng nhập bằng tài khoản Admin", () -> {
            loginPage.login(data.get("Email"), data.get("Password"));
        });
        Allure.step("Assert: Hệ thống chuyển vào Dashboard và hiển thị tên Admin", () -> {
            Assert.assertTrue(dashboardPage.isLoaded(),
                    "Sau khi đăng nhập phải chuyển tới Dashboard, URL hiện tại: " + dashboardPage.getCurrentUrl());
            Assert.assertTrue(dashboardPage.getCurrentUrl().contains(data.get("ExpectedUrl")),
                    "URL phải chứa " + data.get("ExpectedUrl"));
            Assert.assertEquals(dashboardPage.getLoggedInUserName(), data.get("ExpectedUser"),
                    "Header phải hiển thị tên người dùng đã đăng nhập");
        });
    }

    @Test(description = "Đăng nhập thất bại với dữ liệu không hợp lệ",
            dataProvider = "loginInvalid", dataProviderClass = LoginDataProvider.class,
            groups = {"regression", "login"})
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "login", "core", "data-driven"})
    @Description("Data-driven từ Login.xlsx / sheet LoginInvalid: email sai định dạng, email trống, sai mật khẩu, mật khẩu trống. "
            + "Hệ thống phải hiển thị đúng thông báo lỗi tại vị trí tương ứng và người dùng vẫn ở trang đăng nhập.")
    public void testLoginWithInvalidData(TestDataRow data) {
        AllureManager.useTestCase(data);
        LoginPage loginPage = new LoginPage(driver());

        Allure.step("Arrange: Mở trang đăng nhập", () -> {
            loginPage.open();
        });
        Allure.step("Act: Đăng nhập với Email \"" + data.get("Email") + "\" và mật khẩu theo dữ liệu " + data.tcId(), () -> {
            loginPage.login(data.get("Email"), data.get("Password"));
        });
        Allure.step("Assert: Hiển thị \"" + data.get("ExpectedMessage") + "\" tại " + data.get("ErrorField") + " và vẫn ở trang đăng nhập", () -> {
            Assert.assertEquals(loginPage.getErrorMessage(data.get("ErrorField")), data.get("ExpectedMessage"),
                    "Thông báo lỗi tại '" + data.get("ErrorField") + "' không đúng (" + data.tcId() + ")");
            Assert.assertTrue(loginPage.isOnSignInPage(),
                    "Đăng nhập thất bại phải ở lại trang /signin, URL hiện tại: " + loginPage.getCurrentUrl());
        });
    }
}
