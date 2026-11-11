package com.rise.pages.components;

import com.rise.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Dropdown Select2 v3 (thay cho thẻ select gốc — không dùng được lớp Select của Selenium).
 * Đóng dropdown bằng cách click lớp mask; KHÔNG dùng ESC vì ESC đóng luôn modal chứa dropdown.
 */
public class Select2 extends BasePage {

    private static final By DROP = By.cssSelector(".select2-drop-active");
    private static final By OPTIONS = By.cssSelector(".select2-drop-active li.select2-result-selectable");
    private static final By SEARCH_INPUT = By.cssSelector(".select2-drop-active input.select2-input");
    private static final By MASK = By.id("select2-drop-mask");

    private final By container;

    public Select2(WebDriver driver, By container) {
        super(driver);
        this.container = container;
    }

    public void open() {
        WebElement box = waitVisible(container);
        List<WebElement> single = box.findElements(By.cssSelector("a.select2-choice"));
        WebElement trigger = single.isEmpty() ? box.findElement(By.cssSelector("input.select2-input")) : single.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(trigger)).click();
        waitDropOpen();
    }

    /** Không dùng waitVisible (có cuộn trang) — Select2 tự đóng dropdown khi trang bị cuộn. */
    private void waitDropOpen() {
        wait.until(d -> isDisplayedNow(DROP) && !d.findElements(OPTIONS).isEmpty());
    }

    /**
     * Đóng dropdown như khi người dùng click ra ngoài: lớp mask của Select2 có opacity 0 nên Selenium coi là ẩn
     * và không click được → phát sự kiện click lên mask bằng JS (đúng cơ chế Select2 dùng để đóng).
     */
    public void close() {
        if (!driver.findElements(MASK).isEmpty()) {
            js().executeScript("document.getElementById('select2-drop-mask').click();");
        }
        wait.until(d -> !isDisplayedNow(DROP));
    }

    /** Danh sách lựa chọn đang hiển thị (mở rồi đóng dropdown). */
    public List<String> getOptions() {
        open();
        List<String> options = driver.findElements(OPTIONS).stream().map(BasePage::textOf).filter(t -> !t.isEmpty()).toList();
        close();
        return options;
    }

    /** Chọn đúng option có text = optionText; nếu dropdown có ô tìm kiếm thì gõ để lọc trước. */
    public void select(String optionText) {
        open();
        if (isDisplayedNow(SEARCH_INPUT) && isSingle()) {
            driver.findElement(SEARCH_INPUT).sendKeys(optionText);
        }
        WebElement option = wait.until(d -> d.findElements(OPTIONS).stream()
                .filter(o -> textOf(o).equals(optionText)).findFirst().orElse(null));
        option.click();
        wait.until(d -> !isDisplayedNow(DROP));
    }

    /** Text đang chọn (single) hoặc các tag đã chọn (multi). */
    public List<String> getSelected() {
        WebElement box = waitVisible(container);
        List<WebElement> chosen = box.findElements(By.cssSelector(".select2-chosen"));
        if (!chosen.isEmpty()) {
            return List.of(chosen.get(0).getText().trim());
        }
        return box.findElements(By.cssSelector(".select2-search-choice div")).stream().map(e -> e.getText().trim()).toList();
    }

    private boolean isSingle() {
        return !driver.findElement(container).findElements(By.cssSelector("a.select2-choice")).isEmpty();
    }
}
