package com.rise.pages.clients;

import com.rise.pages.BasePage;
import com.rise.pages.components.ConfirmDialog;
import com.rise.pages.components.DataTable;
import com.rise.pages.components.Select2;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Trang /clients — tab Overview / Clients / Contacts, bảng #client-table. Locator verify trên DOM thật (2026-11-11).
 */
public class ClientsPage extends BasePage {

    private static final String PATH = "/clients";
    private static final String TABLE_ID = "client-table";

    private final By tabs = By.cssSelector("a[data-bs-toggle='tab'][data-bs-target]");
    private final By activeTab = By.cssSelector("a[data-bs-toggle='tab'][data-bs-target].active");
    private final By clientsTab = By.cssSelector("a[data-bs-target='#clients_list']");
    private final By titleButtons = By.cssSelector(".title-button-group a.btn");
    private final By addClientButton = By.cssSelector(".title-button-group a[title='Add client']");
    private final By editIcon = By.cssSelector("a.edit[title='Edit client']");
    private final By deleteIcon = By.cssSelector("a.delete[title='Delete client']");
    private final By editModalField = By.cssSelector("#ajaxModal #company_name");
    private final By confirmDeleteButton = By.id("confirmDeleteButton");

    private final DataTable table;

    public ClientsPage(WebDriver driver) {
        super(driver);
        this.table = new DataTable(driver, TABLE_ID);
    }

    @Step("Mở trang Clients")
    public ClientsPage open() {
        openPath(PATH);
        waitVisible(clientsTab);
        return this;
    }

    @Step("Mở tab \"Clients\"")
    public ClientsPage openClientsTab() {
        click(clientsTab);
        table.waitLoaded();
        return this;
    }

    public List<String> getTabNames() {
        return getVisibleTexts(tabs);
    }

    public String getActiveTabName() {
        return getText(activeTab);
    }

    public List<String> getTitleButtonNames() {
        return getVisibleTexts(titleButtons);
    }

    @Step("Click \"Add client\"")
    public ClientFormModal clickAddClient() {
        click(addClientButton);
        return new ClientFormModal(driver).waitOpen();
    }

    @Step("Tìm client \"{keyword}\"")
    public ClientsPage search(String keyword) {
        table.search(keyword);
        return this;
    }

    public DataTable table() {
        return table;
    }

    public List<String> getTableHeaders() {
        return table.getHeaders();
    }

    public int countRowsNamed(String name) {
        return table.getRowsWithLink(name).size();
    }

    public String getCellOfClient(String name, String column) {
        return table.getCell(table.getRowWithLink(name), column);
    }

    /** Tên client (text link) của mọi dòng đang hiển thị. */
    public List<String> getClientNames() {
        return table.getRows().stream()
                .map(r -> textOf(r.findElement(By.cssSelector("td a[href*='/clients/view/']")))).toList();
    }

    /** true nếu có thẻ script bị chèn vào bảng (dữ liệu không được escape). */
    public boolean hasInjectedScriptInTable() {
        return !driver.findElements(By.cssSelector("#client-table tbody script")).isEmpty();
    }

    public List<String> getColumnValues(String column) {
        return table.getRows().stream().map(r -> table.getCell(r, column)).toList();
    }

    @Step("Click icon Edit của client \"{name}\"")
    public ClientFormModal clickEdit(String name) {
        clickInRow(name, editIcon, editModalField);
        return new ClientFormModal(driver).waitOpen();
    }

    @Step("Click icon Delete của client \"{name}\"")
    public ConfirmDialog clickDelete(String name) {
        clickInRow(name, deleteIcon, confirmDeleteButton);
        return new ConfirmDialog(driver).waitOpen();
    }

    @Step("Mở chi tiết client \"{name}\"")
    public ClientDetailPage openClientDetail(String name) {
        WebElement link = table.getRowWithLink(name).findElement(By.xpath(".//td/a[contains(@href,'/clients/view/')]"));
        link.click();
        return new ClientDetailPage(driver).waitLoaded();
    }

    @Step("Lọc theo \"{placeholder}\" = \"{value}\"")
    public ClientsPage filterBy(String placeholder, String value) {
        String before = table.getInfo();
        new Select2(driver, filterContainer(placeholder)).select(value);
        waitForJs("var i = document.getElementById(arguments[0]); return !!i && i.innerText !== arguments[1];", TABLE_ID + "_info", before);
        return this;
    }

    public List<String> getFilterOptions(String placeholder) {
        return new Select2(driver, filterContainer(placeholder)).getOptions();
    }

    private By filterContainer(String placeholder) {
        return By.xpath("//div[@id='clients_list']//div[contains(@class,'select2-container')][.//span[contains(@class,'select2-chosen') and normalize-space()='" + placeholder + "']]");
    }

    /**
     * Click icon trong dòng của client cho tới khi {@code opened} hiển thị. Ngay sau khi lưu, bảng render lại dòng vừa sửa
     * nên cú click đầu có thể rơi vào dòng cũ sắp bị thay → lấy lại dòng và click lại (chờ tối đa timeout.explicit).
     */
    private void clickInRow(String name, By icon, By opened) {
        wait.until(d -> {
            if (isDisplayedNow(opened)) {
                return true;
            }
            List<WebElement> rows = table.getRowsWithLink(name);
            if (rows.isEmpty()) {
                return false;
            }
            WebElement target = rows.get(0).findElement(icon);
            scrollIntoView(target);
            try {
                target.click();
                new WebDriverWait(d, Duration.ofSeconds(3)).until(x -> isDisplayedNow(opened));
                return true;
            } catch (ElementClickInterceptedException | TimeoutException e) {
                return false;
            }
        });
    }
}
