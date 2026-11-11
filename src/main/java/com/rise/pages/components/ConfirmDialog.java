package com.rise.pages.components;

import com.rise.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Dialog xác nhận xoá dùng chung (#confirmationModal): "Delete?" / Delete / Cancel / nút X.
 */
public class ConfirmDialog extends BasePage {

    private static final String MODAL_ID = "confirmationModal";

    private final By title = By.id("confirmationModalTitle");
    private final By content = By.id("confirmationModalContent");
    private final By deleteButton = By.id("confirmDeleteButton");
    private final By cancelButton = By.cssSelector("#confirmationModal .modal-footer button.btn-default");
    private final By closeIcon = By.cssSelector("#confirmationModal .modal-header .btn-close");
    private final By footerButtons = By.cssSelector("#confirmationModal .modal-footer button");

    private final Modal modal;

    public ConfirmDialog(WebDriver driver) {
        super(driver);
        this.modal = new Modal(driver, MODAL_ID);
    }

    public ConfirmDialog waitOpen() {
        modal.waitFullyOpen();
        return this;
    }

    public String getTitleText() {
        return getText(title);
    }

    public String getMessage() {
        return getText(content);
    }

    public List<String> getButtonTexts() {
        return getVisibleTexts(footerButtons);
    }

    public boolean isDeleteButtonDanger() {
        return getAttribute(deleteButton, "class").contains("btn-danger");
    }

    @Step("Xác nhận \"Delete\"")
    public void confirmDelete() {
        click(deleteButton);
        modal.waitFullyClosed();
    }

    @Step("Bấm \"Cancel\"")
    public void cancel() {
        click(cancelButton);
        modal.waitFullyClosed();
    }

    @Step("Đóng dialog bằng nút X")
    public void closeByX() {
        click(closeIcon);
        modal.waitFullyClosed();
    }

    public boolean isOpen() {
        return modal.isOpen();
    }
}
