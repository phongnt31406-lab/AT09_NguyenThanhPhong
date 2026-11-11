package com.rise.pages.projects;

import com.rise.pages.BasePage;
import com.rise.pages.components.Modal;
import com.rise.pages.components.Select2;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Modal "Add project" trong #ajaxModal. Ô Client là select[name=client_id] không có id riêng →
 * locate container Select2 nằm ngay trước thẻ select. Locator verify trên DOM thật (2026-11-11).
 */
public class ProjectFormModal extends BasePage {

    private final By titleInput = By.cssSelector("#ajaxModal #title");
    private final By clientSelect = By.xpath("//div[@id='ajaxModal']//select[@name='client_id']/preceding-sibling::div[contains(@class,'select2-container')][1]");
    private final By saveButton = By.cssSelector("#ajaxModal form .modal-footer button[type='submit']");

    private final Modal modal;

    public ProjectFormModal(WebDriver driver) {
        super(driver);
        this.modal = new Modal(driver, "ajaxModal");
    }

    public ProjectFormModal waitOpen() {
        waitVisible(titleInput);
        modal.waitFullyOpen();
        return this;
    }

    @Step("Nhập Title: \"{title}\"")
    public ProjectFormModal enterTitle(String title) {
        type(titleInput, title);
        return this;
    }

    @Step("Chọn Client: \"{clientName}\"")
    public ProjectFormModal selectClient(String clientName) {
        new Select2(driver, clientSelect).select(clientName);
        return this;
    }

    public String getSelectedClient() {
        return new Select2(driver, clientSelect).getSelected().get(0);
    }

    @Step("Click \"Save\" và chờ modal đóng")
    public void save() {
        click(saveButton);
        modal.waitFullyClosed();
    }
}
