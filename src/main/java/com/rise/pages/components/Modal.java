package com.rise.pages.components;

import com.rise.pages.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Modal Bootstrap của RISE (#ajaxModal, #confirmationModal) — modal không bị gỡ khỏi DOM, chỉ bật/tắt class "show".
 * Bootstrap bỏ qua thao tác đóng khi modal đang chạy animation, nên phải chờ mở hoàn toàn trước khi thao tác.
 */
public class Modal extends BasePage {

    private static final String FULLY_OPEN = "var m = document.getElementById(arguments[0]); if (!m) return false;"
            + "var d = m.querySelector('.modal-dialog');"
            + "return m.classList.contains('show') && getComputedStyle(m).opacity === '1'"
            + " && (!d || getComputedStyle(d).transform === 'none');";
    private static final String FULLY_CLOSED = "var m = document.getElementById(arguments[0]);"
            + "return (!m || getComputedStyle(m).display === 'none')"
            + " && !document.querySelector('.modal-backdrop') && !document.body.classList.contains('modal-open');";

    private final String modalId;

    public Modal(WebDriver driver, String modalId) {
        super(driver);
        this.modalId = modalId;
    }

    public void waitFullyOpen() {
        waitForJs(FULLY_OPEN, modalId);
    }

    public void waitFullyClosed() {
        waitForJs(FULLY_CLOSED, modalId);
    }

    public boolean isOpen() {
        return Boolean.TRUE.equals(js().executeScript("var m = document.getElementById(arguments[0]); return !!m && m.classList.contains('show');", modalId));
    }
}
