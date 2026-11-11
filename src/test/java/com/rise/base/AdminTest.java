package com.rise.base;

import com.rise.config.ConfigReader;
import com.rise.pages.dashboard.DashboardPage;
import com.rise.pages.login.LoginPage;
import io.qameta.allure.Allure;
import org.testng.annotations.BeforeMethod;

/**
 * Test cần đăng nhập Admin: đăng nhập ở Set up (sau khi BaseTest mở browser) — tiền điều kiện "Đã đăng nhập admin".
 */
public abstract class AdminTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginAsAdmin() {
        Allure.step("Đăng nhập tài khoản Admin", () -> {
            new LoginPage(driver()).open().login(ConfigReader.get("admin.email"), ConfigReader.get("admin.password"));
            if (!new DashboardPage(driver()).isLoaded()) {
                throw new IllegalStateException("Không đăng nhập được bằng tài khoản Admin trong configs/credentials.properties");
            }
        });
    }
}
