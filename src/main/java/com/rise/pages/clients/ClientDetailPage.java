package com.rise.pages.clients;

import com.rise.pages.BasePage;
import com.rise.pages.components.DataTable;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Trang chi tiết client /clients/view/{id} — tab mặc định Contacts (#contact-table). Locator verify trên DOM thật (2026-11-11).
 */
public class ClientDetailPage extends BasePage {

    private static final String CONTACT_TABLE_ID = "contact-table";

    private final By heading = By.cssSelector(".page-title h1");
    private final By summaryCards = By.cssSelector(".card");
    private final By contactsTab = By.cssSelector("a[data-bs-target='#client-contacts']");
    private final By projectsTab = By.cssSelector("a[data-bs-target='#client-projects']");
    private final By addContactButton = By.cssSelector("a[title='Add contact']");
    private final By sendInvitationButton = By.cssSelector("a[title='Send invitation']");
    private final By primaryBadge = By.cssSelector("span.badge");

    private final DataTable contacts;

    public ClientDetailPage(WebDriver driver) {
        super(driver);
        this.contacts = new DataTable(driver, CONTACT_TABLE_ID);
    }

    public ClientDetailPage waitLoaded() {
        waitUrlContains("/clients/view/");
        waitVisible(heading);
        contacts.waitLoaded();
        return this;
    }

    public String getHeading() {
        return getText(heading);
    }

    /** Text của 4 thẻ tổng quan, VD "0 Projects", "$0.00 Total invoiced". */
    public List<String> getSummaryCardTexts() {
        return driver.findElements(summaryCards).stream().filter(WebElement::isDisplayed)
                .map(c -> c.getText().replaceAll("\\s+", " ").trim())
                .filter(t -> t.matches(".*(Projects|Total invoiced|Payments|Due)$")).toList();
    }

    public boolean isContactsTabActive() {
        return getAttribute(contactsTab, "class").contains("active");
    }

    public List<String> getContactTableHeaders() {
        return contacts.getHeaders();
    }

    public String getContactTableEmptyMessage() {
        return contacts.getEmptyMessage();
    }

    public boolean isAddContactButtonVisible() {
        return isVisible(addContactButton);
    }

    public boolean isSendInvitationButtonVisible() {
        return isVisible(sendInvitationButton);
    }

    @Step("Click \"Add contact\"")
    public ContactFormModal clickAddContact() {
        click(addContactButton);
        return new ContactFormModal(driver).waitOpen();
    }

    /** Chờ dòng contact có tên hiển thị {@code fullName} xuất hiện trong bảng. */
    public WebElement waitContactRow(String fullName) {
        return contacts.getRowWithLink(fullName);
    }

    public String getContactCell(String fullName, String column) {
        return contacts.getCell(waitContactRow(fullName), column);
    }

    /** Tên contact đúng như hiển thị (không gồm badge). */
    public String getContactDisplayName(String fullName) {
        return textOf(waitContactRow(fullName).findElement(By.cssSelector("td a"))).replace("Primary contact", "").trim();
    }

    public boolean isPrimaryContact(String fullName) {
        return waitContactRow(fullName).findElements(primaryBadge).stream().anyMatch(b -> textOf(b).equals("Primary contact"));
    }

    /** Dòng contact theo email (dùng khi tên có thể bị hệ thống làm sạch, VD dữ liệu XSS). */
    public WebElement waitContactRowByEmail(String email) {
        return wait.until(d -> contacts.getRows().stream()
                .filter(r -> textOf(r).contains(email)).findFirst().orElse(null));
    }

    public String getContactNameByEmail(String email) {
        return textOf(waitContactRowByEmail(email).findElement(By.cssSelector("td a"))).replace("Primary contact", "").trim();
    }

    /** true nếu có thẻ img mang thuộc tính onerror bị chèn vào bảng contact (dữ liệu không được escape). */
    public boolean hasInjectedImageInContacts() {
        return !driver.findElements(By.cssSelector("#contact-table tbody img[onerror]")).isEmpty();
    }

    @Step("Mở tab \"Projects\" của client")
    public ClientDetailPage openProjectsTab() {
        click(projectsTab);
        return this;
    }
}
