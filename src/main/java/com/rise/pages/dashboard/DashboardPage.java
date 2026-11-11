package com.rise.pages.dashboard;

import com.rise.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Dashboard sau khi đăng nhập. Locator đã verify trên DOM thật (2026-11-10).
 */
public class DashboardPage extends BasePage {

    private static final String PATH = "/dashboard";

    private final By loggedInUserName = By.cssSelector("#user-dropdown .user-name");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Step("Chờ Dashboard tải xong")
    public boolean isLoaded() {
        return waitUrlContains(PATH) && isVisible(loggedInUserName);
    }

    @Step("Đọc tên người dùng trên header")
    public String getLoggedInUserName() {
        return getText(loggedInUserName);
    }
}
