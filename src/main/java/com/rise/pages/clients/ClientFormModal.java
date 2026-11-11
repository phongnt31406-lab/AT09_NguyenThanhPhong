package com.rise.pages.clients;

import com.rise.pages.BasePage;
import com.rise.pages.components.Modal;
import com.rise.pages.components.Select2;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Modal "Add client" / "Edit client" trong #ajaxModal. Locator verify trên DOM thật (2026-11-11).
 */
public class ClientFormModal extends BasePage {

    private static final String MODAL_ID = "ajaxModal";

    private final By title = By.id("ajaxModalTitle");
    private final By typeOrganization = By.id("type_organization");
    private final By typePerson = By.id("type_person");
    private final By companyNameLabel = By.cssSelector("#ajaxModal label[for='company_name']");
    private final By companyNameInput = By.cssSelector("#ajaxModal #company_name");
    private final By companyNameError = By.id("company_name-error");
    private final By ownerSelect = By.id("s2id_created_by");
    private final By addressInput = By.cssSelector("#ajaxModal #address");
    private final By cityInput = By.cssSelector("#ajaxModal #city");
    private final By stateInput = By.cssSelector("#ajaxModal #state");
    private final By zipInput = By.cssSelector("#ajaxModal #zip");
    private final By countryInput = By.cssSelector("#ajaxModal #country");
    private final By phoneInput = By.cssSelector("#ajaxModal #phone");
    private final By websiteInput = By.cssSelector("#ajaxModal #website");
    private final By vatNumberInput = By.cssSelector("#ajaxModal #vat_number");
    private final By gstNumberInput = By.cssSelector("#ajaxModal #gst_number");
    private final By clientGroupsSelect = By.id("s2id_group_ids");
    private final By labelsSelect = By.id("s2id_client_labels");
    private final By alertMessage = By.cssSelector("#ajaxModal .app-alert-message");
    private final By footerButtons = By.cssSelector("#ajaxModal form .modal-footer button");
    private final By saveButton = By.cssSelector("#ajaxModal form .modal-footer button[type='submit']");
    private final By saveAndContinueButton = By.id("save-and-continue-button");
    private final By closeButton = By.cssSelector("#ajaxModal form .modal-footer button[data-bs-dismiss='modal']");

    private final Modal modal;

    public ClientFormModal(WebDriver driver) {
        super(driver);
        this.modal = new Modal(driver, MODAL_ID);
    }

    public ClientFormModal waitOpen() {
        waitVisible(companyNameInput);
        modal.waitFullyOpen();
        return this;
    }

    public String getTitleText() {
        return getText(title);
    }

    public List<String> getFooterButtonNames() {
        return getVisibleTexts(footerButtons);
    }

    @Step("Chọn Type: {type}")
    public ClientFormModal selectType(String type) {
        click("Person".equalsIgnoreCase(type) ? typePerson : typeOrganization);
        return this;
    }

    public String getSelectedType() {
        return driver.findElement(typePerson).isSelected() ? "Person" : "Organization";
    }

    public String getCompanyNameLabel() {
        return getText(companyNameLabel);
    }

    @Step("Nhập Company name: \"{name}\"")
    public ClientFormModal enterCompanyName(String name) {
        type(companyNameInput, name);
        return this;
    }

    @Step("Chọn Owner: {owner}")
    public ClientFormModal selectOwner(String owner) {
        owner().select(owner);
        return this;
    }

    public List<String> getOwnerOptions() {
        return owner().getOptions();
    }

    public String getOwner() {
        return owner().getSelected().get(0);
    }

    @Step("Nhập thông tin liên hệ: Address, City, State, Zip, Country")
    public ClientFormModal enterAddress(String address, String city, String state, String zip, String country) {
        type(addressInput, address);
        type(cityInput, city);
        type(stateInput, state);
        type(zipInput, zip);
        type(countryInput, country);
        return this;
    }

    @Step("Nhập Phone: \"{phone}\"")
    public ClientFormModal enterPhone(String phone) {
        type(phoneInput, phone);
        return this;
    }

    @Step("Nhập City: \"{city}\"")
    public ClientFormModal enterCity(String city) {
        type(cityInput, city);
        return this;
    }

    @Step("Nhập Website: \"{website}\"")
    public ClientFormModal enterWebsite(String website) {
        type(websiteInput, website);
        return this;
    }

    @Step("Nhập VAT Number \"{vat}\", GST Number \"{gst}\"")
    public ClientFormModal enterTaxNumbers(String vat, String gst) {
        type(vatNumberInput, vat);
        type(gstNumberInput, gst);
        return this;
    }

    @Step("Chọn Client groups: {group}")
    public ClientFormModal addClientGroup(String group) {
        clientGroups().select(group);
        return this;
    }

    public List<String> getClientGroupOptions() {
        return clientGroups().getOptions();
    }

    public List<String> getClientGroups() {
        return clientGroups().getSelected();
    }

    public List<String> getLabels() {
        return new Select2(driver, labelsSelect).getSelected();
    }

    public String getCompanyName() {
        return getValue(companyNameInput);
    }

    public String getAddress() {
        return getValue(addressInput);
    }

    public String getCity() {
        return getValue(cityInput);
    }

    public String getState() {
        return getValue(stateInput);
    }

    public String getZip() {
        return getValue(zipInput);
    }

    public String getCountry() {
        return getValue(countryInput);
    }

    public String getPhone() {
        return getValue(phoneInput);
    }

    public String getWebsite() {
        return getValue(websiteInput);
    }

    public String getVatNumber() {
        return getValue(vatNumberInput);
    }

    public String getGstNumber() {
        return getValue(gstNumberInput);
    }

    @Step("Click \"Save\" và chờ modal đóng")
    public void save() {
        click(saveButton);
        modal.waitFullyClosed();
    }

    @Step("Click \"Save\" (kỳ vọng bị chặn)")
    public ClientFormModal clickSaveExpectingError() {
        click(saveButton);
        return this;
    }

    @Step("Double click nhanh nút \"Save\"")
    public void doubleClickSave() {
        new Actions(driver).doubleClick(waitClickable(saveButton)).perform();
        modal.waitFullyClosed();
    }

    @Step("Click \"Save & continue\"")
    public ContactFormModal saveAndContinue() {
        click(saveAndContinueButton);
        return new ContactFormModal(driver).waitOpen();
    }

    @Step("Click \"Close\"")
    public void close() {
        click(closeButton);
        modal.waitFullyClosed();
    }

    @Step("Nhấn phím ESC")
    public void pressEscape() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        modal.waitFullyClosed();
    }

    public String getCompanyNameError() {
        return getText(companyNameError);
    }

    public String getAlertMessage() {
        return getText(alertMessage);
    }

    public boolean isOpen() {
        return modal.isOpen();
    }

    private Select2 owner() {
        return new Select2(driver, ownerSelect);
    }

    private Select2 clientGroups() {
        return new Select2(driver, clientGroupsSelect);
    }
}
