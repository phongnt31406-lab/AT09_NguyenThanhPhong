package com.rise.pages.components;

import com.rise.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Bảng DataTables tải dữ liệu bằng AJAX (VD #client-table, #contact-table, #project-table):
 * ô Search, thông tin "1-10 / 340", chọn số dòng/trang, phân trang.
 */
public class DataTable extends BasePage {

    private static final String SEARCH_DONE = "var t = document.getElementById(arguments[0]);"
            + "var p = document.getElementById(arguments[0] + '_processing');"
            + "if (!t || (p && getComputedStyle(p).display !== 'none')) return false;"
            + "var rows = t.querySelectorAll('tbody tr'); var k = arguments[1].toLowerCase();"
            + "if (rows.length === 1 && rows[0].querySelector('.dataTables_empty')) return true;"
            + "return rows.length > 0 && Array.prototype.every.call(rows, function (r) { return r.innerText.toLowerCase().indexOf(k) >= 0; });";
    private static final String INFO_CHANGED = "var i = document.getElementById(arguments[0] + '_info'); return !!i && i.innerText !== arguments[1];";

    private final String tableId;

    public DataTable(WebDriver driver, String tableId) {
        super(driver);
        this.tableId = tableId;
    }

    public void waitLoaded() {
        waitVisible(By.cssSelector("#" + tableId + " tbody tr"));
    }

    /** Gõ từ khoá vào ô Search và chờ bảng tải lại xong (mọi dòng chứa từ khoá hoặc "No record found."). */
    public void search(String keyword) {
        waitLoaded();
        By input = By.cssSelector("input[type='search'][aria-controls='" + tableId + "']");
        WebElement box = waitFirstVisible(input);
        box.clear();
        box.sendKeys(keyword);
        waitForJs(SEARCH_DONE, tableId, keyword);
    }

    public List<String> getHeaders() {
        waitLoaded();
        return driver.findElements(By.cssSelector("#" + tableId + " thead th")).stream().map(BasePage::textOf).toList();
    }

    public List<WebElement> getRows() {
        return driver.findElements(By.cssSelector("#" + tableId + " tbody tr")).stream()
                .filter(r -> r.findElements(By.cssSelector("td.dataTables_empty")).isEmpty()).toList();
    }

    /** Dòng có link/ô text đúng bằng {@code text}. */
    public List<WebElement> getRowsWithLink(String text) {
        return getRows().stream().filter(r -> r.findElements(By.cssSelector("td a")).stream()
                .map(BasePage::textOf).anyMatch(t -> t.equals(text) || t.startsWith(text + " "))).toList();
    }

    public WebElement getRowWithLink(String text) {
        return wait.until(d -> getRowsWithLink(text).stream().findFirst().orElse(null));
    }

    /** Text của ô theo tên cột (header) trong dòng. */
    public String getCell(WebElement row, String header) {
        int index = getHeaders().indexOf(header);
        if (index < 0) {
            throw new IllegalArgumentException("Bảng " + tableId + " không có cột '" + header + "'");
        }
        return textOf(row.findElements(By.tagName("td")).get(index));
    }

    public String getEmptyMessage() {
        return getText(By.cssSelector("#" + tableId + " td.dataTables_empty"));
    }

    public String getInfo() {
        return getText(By.id(tableId + "_info"));
    }

    public List<String> getPageSizeOptions() {
        return pageSizeSelect().getOptions();
    }

    public void setPageSize(String size) {
        String before = getInfo();
        pageSizeSelect().select(size);
        waitForJs(INFO_CHANGED, tableId, before);
    }

    public void goToPage(String page) {
        String before = getInfo();
        By link = By.xpath("//div[@id='" + tableId + "_wrapper']//ul[contains(@class,'pagination')]//a[normalize-space()='" + page + "']");
        click(link);
        waitForJs(INFO_CHANGED, tableId, before);
    }

    public String getActivePage() {
        return getText(By.cssSelector("#" + tableId + "_wrapper .pagination li.active a"));
    }

    private Select2 pageSizeSelect() {
        return new Select2(driver, By.cssSelector("#" + tableId + "_wrapper .dataTables_length .select2-container"));
    }
}
