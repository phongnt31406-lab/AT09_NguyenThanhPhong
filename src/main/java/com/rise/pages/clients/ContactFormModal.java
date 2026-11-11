package com.rise.pages.clients;

import com.rise.pages.BasePage;
import com.rise.pages.components.Modal;
import com.rise.pages.components.Select2;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Modal "Add contact" / "Add multiple contacts" trong #ajaxModal. Locator verify trên DOM thật (2026-11-11).
 * Ô Password có readonly (chống autofill) và chỉ gỡ khi được focus → luôn click vào ô trước khi nhập.
 */
public class ContactFormModal extends BasePage {

    private static final String MODAL_ID = "ajaxModal";

    private final By title = By.id("ajaxModalTitle");
    private final By firstNameInput = By.cssSelector("#ajaxModal #first_name");
    private final By lastNameInput = By.cssSelector("#ajaxModal #last_name");
    private final By emailInput = By.cssSelector("#ajaxModal #email");
    private final By phoneInput = By.cssSelector("#ajaxModal #phone");
    private final By jobTitleInput = By.cssSelector("#ajaxModal #job_title");
    private final By passwordInput = By.id("login_password");
    private final By generatePasswordButton = By.id("generate_password");
    private final By showHidePasswordButton = By.id("show_hide_password");
    private final By canAccessEverythingCheckbox = By.id("can_access_everything");
    private final By canAccessOnlySection = By.cssSelector("#ajaxModal .specific_permission_section");
    private final By canAccessOnlySelect = By.id("s2id_can_access_only");
    private final By emailLoginDetailsCheckbox = By.id("email_login_details");
    private final By alertMessage = By.cssSelector("#ajaxModal .app-alert-message");
    private final By footerButtons = By.cssSelector("#ajaxModal form .modal-footer button");
    private final By saveButton = By.cssSelector("#ajaxModal form .modal-footer button[type='submit']");
    private final By saveAndAddMoreButton = By.id("save-and-add-button");
    private final By closeButton = By.cssSelector("#ajaxModal form .modal-footer button[data-bs-dismiss='modal']");

    private final Modal modal;

    public ContactFormModal(WebDriver driver) {
        super(driver);
        this.modal = new Modal(driver, MODAL_ID);
    }

    public ContactFormModal waitOpen() {
        waitVisible(firstNameInput);
        modal.waitFullyOpen();
        return this;
    }

    public String getTitleText() {
        return getText(title);
    }

    public List<String> getFooterButtonNames() {
        return getVisibleTexts(footerButtons);
    }

    @Step("Nhập First name \"{firstName}\", Last name \"{lastName}\"")
    public ContactFormModal enterName(String firstName, String lastName) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        return this;
    }

    @Step("Nhập Email: \"{email}\"")
    public ContactFormModal enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    @Step("Nhập Phone \"{phone}\", Job Title \"{jobTitle}\"")
    public ContactFormModal enterPhoneAndJobTitle(String phone, String jobTitle) {
        type(phoneInput, phone);
        type(jobTitleInput, jobTitle);
        return this;
    }

    @Step("Chọn Gender: {gender}")
    public ContactFormModal selectGender(String gender) {
        click(By.id("gender_" + gender.toLowerCase()));
        return this;
    }

    public boolean isAnyGenderSelected() {
        return driver.findElements(By.cssSelector("#ajaxModal input[name='gender']")).stream().anyMatch(r -> r.isSelected());
    }

    /** Click vào ô Password (gỡ readonly) rồi mới nhập — không dùng @Step để mật khẩu không lọt vào report. */
    public ContactFormModal enterPassword(String password) {
        Allure.step("Click vào ô Password, xoá và nhập mật khẩu (giá trị được ẩn)", () -> {
            click(passwordInput);
            wait.until(d -> getAttribute(passwordInput, "readonly").isEmpty());
            typeSecret(passwordInput, password);
        });
        return this;
    }

    @Step("Click vào ô Password")
    public ContactFormModal focusPassword() {
        click(passwordInput);
        return this;
    }

    public boolean isPasswordReadonly() {
        return driver.findElement(passwordInput).getDomAttribute("readonly") != null;
    }

    public String getPasswordValue() {
        return getValue(passwordInput);
    }

    public String getPasswordFieldType() {
        return getAttribute(passwordInput, "type");
    }

    @Step("Click \"Generate\"")
    public ContactFormModal clickGeneratePassword() {
        click(generatePasswordButton);
        return this;
    }

    @Step("Click nút mắt hiện/ẩn mật khẩu")
    public ContactFormModal toggleShowPassword() {
        String before = getPasswordFieldType();
        click(showHidePasswordButton);
        wait.until(d -> !getPasswordFieldType().equals(before));
        return this;
    }

    @Step("Đặt \"Can access everything\" = {checked}")
    public ContactFormModal setCanAccessEverything(boolean checked) {
        setCheckbox(canAccessEverythingCheckbox, checked);
        wait.until(d -> isCanAccessOnlyVisible() != checked);
        return this;
    }

    public boolean isCanAccessEverythingChecked() {
        return driver.findElement(canAccessEverythingCheckbox).isSelected();
    }

    /** Theo trạng thái DOM (getClientRects) vì vùng này có thể nằm ngoài vùng cuộn của modal. */
    public boolean isCanAccessOnlyVisible() {
        return Boolean.TRUE.equals(js().executeScript("return arguments[0].getClientRects().length > 0;",
                driver.findElement(canAccessOnlySection)));
    }

    public List<String> getCanAccessOnly() {
        return new Select2(driver, canAccessOnlySelect).getSelected();
    }

    @Step("Đặt \"Email login details to this user\" = {checked}")
    public ContactFormModal setEmailLoginDetails(boolean checked) {
        setCheckbox(emailLoginDetailsCheckbox, checked);
        return this;
    }

    public boolean isEmailLoginDetailsChecked() {
        return driver.findElement(emailLoginDetailsCheckbox).isSelected();
    }

    public String getFirstName() {
        return getValue(firstNameInput);
    }

    public String getLastName() {
        return getValue(lastNameInput);
    }

    public String getEmail() {
        return getValue(emailInput);
    }

    @Step("Click \"Save\" và chờ modal đóng")
    public void save() {
        click(saveButton);
        modal.waitFullyClosed();
    }

    @Step("Click \"Save\" (kỳ vọng bị chặn)")
    public ContactFormModal clickSaveExpectingError() {
        click(saveButton);
        return this;
    }

    @Step("Click \"Save & add more\"")
    public ContactFormModal saveAndAddMore() {
        String email = getEmail();
        click(saveAndAddMoreButton);
        wait.until(d -> !getEmail().equals(email));
        return this;
    }

    @Step("Click \"Close\"")
    public void close() {
        click(closeButton);
        modal.waitFullyClosed();
    }

    /** Thông báo lỗi validate dưới trường (first_name, last_name, email, login_password). */
    public String getFieldError(String fieldId) {
        return getText(By.id(fieldId + "-error"));
    }

    public List<String> getAllFieldErrors() {
        return getVisibleTexts(By.cssSelector("#ajaxModal span[id$='-error']"));
    }

    public String getAlertMessage() {
        return getText(alertMessage);
    }

    public boolean isOpen() {
        return modal.isOpen();
    }
}
