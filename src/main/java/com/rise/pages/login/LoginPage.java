package com.rise.pages.login;

import com.rise.pages.BasePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Trang đăng nhập /signin. Locator đã verify trên DOM thật (2026-11-10).
 */
public class LoginPage extends BasePage {

    private static final String PATH = "/signin";

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By signInButton = By.cssSelector("#signin-form button[type='submit']");
    private final By emailError = By.id("email-error");
    private final By passwordError = By.id("password-error");
    private final By authFailedAlert = By.cssSelector("div.alert.alert-danger[role='alert']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Mở trang đăng nhập")
    public LoginPage open() {
        openPath(PATH);
        waitVisible(emailInput);
        return this;
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    @Step("Nhập Email \"{email}\"")
    public LoginPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    /** Không dùng @Step để mật khẩu không bị ghi vào tham số của Allure result. */
    public LoginPage enterPassword(String password) {
        Allure.step("Nhập mật khẩu (giá trị được ẩn)", () -> {
            typeSecret(passwordInput, password);
        });
        return this;
    }

    @Step("Click nút \"Sign in\"")
    public void clickSignIn() {
        click(signInButton);
    }

    /**
     * Thông báo lỗi theo vị trí hiển thị: email | password | alert (alert đỏ phía trên form).
     */
    @Step("Đọc thông báo lỗi tại \"{field}\"")
    public String getErrorMessage(String field) {
        return switch (field.toLowerCase()) {
            case "email" -> getText(emailError);
            case "password" -> getText(passwordError);
            case "alert" -> getText(authFailedAlert);
            default -> throw new IllegalArgumentException("ErrorField không hợp lệ: " + field + " (email | password | alert)");
        };
    }

    public boolean isOnSignInPage() {
        return getCurrentUrl().contains(PATH);
    }
}
