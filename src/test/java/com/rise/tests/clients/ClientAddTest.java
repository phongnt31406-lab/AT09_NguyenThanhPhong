package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.ClientsDataProvider;
import com.rise.dataproviders.TestDataFiles;
import com.rise.pages.clients.ClientFormModal;
import com.rise.pages.clients.ClientsPage;
import com.rise.pages.clients.ContactFormModal;
import com.rise.utils.AllureManager;
import com.rise.utils.ExcelReader;
import com.rise.utils.TestDataRow;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Thêm client — RISE_CLIENT_TC_009 → 025. Dữ liệu: Clients.xlsx / Client, ClientValidation.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Thêm client")
public class ClientAddTest extends ClientsBaseTest {

    private static final List<String> ADD_FOOTER = List.of("Close", "Save & continue", "Save");
    private static final List<String> OWNERS = List.of("Anh Tester Admin", "Admin Example", "Project Manager");
    private static final List<String> CLIENT_GROUPS = List.of("VIP", "Gold", "Silver");

    private TestDataRow row(String tcId) {
        return ExcelReader.readRow(TestDataFiles.CLIENTS, TestDataFiles.CLIENT, tcId);
    }

    private ClientFormModal openAddClient(ClientsPage page) {
        return page.open().openClientsTab().clickAddClient();
    }

    @Test(description = "Modal Add client hiển thị đúng mặc định", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_009")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients", "ui"})
    @Description("Modal Add client mở ra với Type = Organization, Company name trống, Owner = người đăng nhập, Client groups/Labels trống và 3 nút Close, Save & continue, Save.")
    public void testAddClientModalDefaults() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở tab Clients", () -> {
            page.open().openClientsTab();
        });
        ClientFormModal modal = Allure.step("Act: Click \"Add client\"", () -> page.clickAddClient());
        Allure.step("Assert: Giá trị mặc định của modal đúng", () -> {
            Assert.assertEquals(modal.getTitleText(), "Add client", "Tiêu đề modal không đúng");
            Assert.assertEquals(modal.getSelectedType(), "Organization", "Type mặc định phải là Organization");
            Assert.assertEquals(modal.getCompanyName(), "", "Company name mặc định phải trống");
            Assert.assertEquals(modal.getOwner(), OWNER_DEFAULT, "Owner mặc định phải là người đang đăng nhập");
            Assert.assertTrue(modal.getClientGroups().isEmpty(), "Client groups mặc định phải trống");
            Assert.assertTrue(modal.getLabels().isEmpty(), "Labels mặc định phải trống");
            Assert.assertEquals(modal.getFooterButtonNames(), ADD_FOOTER, "Các nút của modal không đúng");
        });
    }

    @Test(description = "Thêm client Organization chỉ với trường bắt buộc", groups = {"smoke", "regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_010")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({"smoke", "regression", "clients", "critical-path"})
    @Description("Nhập Company name rồi Save: modal đóng, client mới xuất hiện khi search với Projects = 0, Due = $0.00.")
    public void testAddClientWithRequiredFieldOnly() {
        TestDataRow data = row("RISE_CLIENT_TC_010");
        String name = data.get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name \"" + name + "\" và Save", () -> {
            modal.enterCompanyName(name).save();
        });
        Allure.step("Assert: Client mới xuất hiện với Projects = 0, Due = $0.00", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Phải có đúng 1 client " + name);
            Assert.assertEquals(page.getCellOfClient(name, "Projects"), "0", "Client mới phải có 0 project");
            Assert.assertEquals(page.getCellOfClient(name, "Due"), "$0.00", "Client mới phải có Due = $0.00");
        });
    }

    @Test(description = "Thêm client với đầy đủ thông tin", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_011")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients", "critical-path"})
    @Description("Nhập đủ Company name, Address, City, State, Zip, Country, Phone, Website, VAT, GST, Client groups rồi Save: bảng hiển thị Phone + Client groups và modal Edit hiển thị đúng toàn bộ giá trị.")
    public void testAddClientWithFullInformation() {
        TestDataRow data = row("RISE_CLIENT_TC_011");
        String name = data.get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập đầy đủ thông tin và Save", () -> {
            modal.enterCompanyName(name)
                    .enterAddress(data.get("Address"), data.get("City"), data.get("State"), data.get("Zip"), data.get("Country"))
                    .enterPhone(data.get("Phone"))
                    .enterWebsite(data.get("Website"))
                    .enterTaxNumbers(data.get("VATNumber"), data.get("GSTNumber"))
                    .addClientGroup(data.get("ClientGroups"))
                    .save();
        });
        Allure.step("Assert: Bảng hiển thị đúng Phone và Client groups", () -> {
            page.search(name);
            Assert.assertEquals(page.getCellOfClient(name, "Phone"), data.get("Phone"), "Phone hiển thị không đúng");
            Assert.assertEquals(page.getCellOfClient(name, "Client groups"), data.get("ClientGroups"), "Client groups hiển thị không đúng");
        });
        Allure.step("Assert: Modal Edit hiển thị đúng toàn bộ giá trị đã nhập", () -> {
            ClientFormModal edit = page.clickEdit(name);
            Assert.assertEquals(edit.getCompanyName(), name, "Company name không đúng");
            Assert.assertEquals(edit.getAddress(), data.get("Address"), "Address không đúng");
            Assert.assertEquals(edit.getCity(), data.get("City"), "City không đúng");
            Assert.assertEquals(edit.getState(), data.get("State"), "State không đúng");
            Assert.assertEquals(edit.getZip(), data.get("Zip"), "Zip không đúng");
            Assert.assertEquals(edit.getCountry(), data.get("Country"), "Country không đúng");
            Assert.assertEquals(edit.getPhone(), data.get("Phone"), "Phone không đúng");
            Assert.assertEquals(edit.getWebsite(), data.get("Website"), "Website không đúng");
            Assert.assertEquals(edit.getVatNumber(), data.get("VATNumber"), "VAT Number không đúng");
            Assert.assertEquals(edit.getGstNumber(), data.get("GSTNumber"), "GST Number không đúng");
            Assert.assertEquals(edit.getClientGroups(), List.of(data.get("ClientGroups")), "Client groups không đúng");
        });
    }

    @Test(description = "Thêm client loại Person", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_012")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Chọn Type = Person thì nhãn \"Company name\" đổi thành \"Name\"; Save thì client được tạo và tìm thấy.")
    public void testAddPersonClient() {
        TestDataRow data = row("RISE_CLIENT_TC_012");
        String name = data.get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Chọn Type = Person", () -> {
            modal.selectType(data.get("Type"));
        });
        Allure.step("Assert: Nhãn trường đổi thành \"Name\"", () -> {
            Assert.assertEquals(modal.getCompanyNameLabel(), "Name", "Nhãn phải đổi thành Name khi chọn Person");
        });
        Allure.step("Act: Nhập Name \"" + name + "\" và Save", () -> {
            modal.enterCompanyName(name).save();
        });
        Allure.step("Assert: Client Person được tạo", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Phải tìm thấy client " + name);
        });
    }

    @Test(description = "Không lưu client khi Company name không hợp lệ", dataProvider = "clientValidation",
            dataProviderClass = ClientsDataProvider.class, groups = {"regression", "clients"})
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients", "validation", "data-driven"})
    @Description("Data-driven từ Clients.xlsx / ClientValidation: Company name trống báo lỗi dưới ô; toàn khoảng trắng bị server từ chối (business rule: không tự trim). Modal không đóng.")
    public void testAddClientWithInvalidCompanyName(TestDataRow data) {
        AllureManager.useTestCase(data);
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name \"" + data.get("CompanyName") + "\" và Save", () -> {
            modal.enterCompanyName(data.get("CompanyName")).clickSaveExpectingError();
        });
        Allure.step("Assert: Hiển thị \"" + data.get("ExpectedMessage") + "\" và modal không đóng", () -> {
            String actual = "alert".equals(data.get("ErrorField")) ? modal.getAlertMessage() : modal.getCompanyNameError();
            Assert.assertEquals(actual, data.get("ExpectedMessage"), "Thông báo lỗi không đúng (" + data.tcId() + ")");
            Assert.assertTrue(modal.isOpen(), "Modal phải giữ nguyên khi lưu thất bại");
        });
    }

    @Test(description = "Company name chứa mã script (XSS)", groups = {"regression", "clients", "security"})
    @TestCaseId("RISE_CLIENT_TC_015")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients", "security"})
    @Description("Company name chứa <script>alert(1)</script>: lưu xong, bảng không bật popup alert và không chèn thẻ script vào trang.")
    public void testCompanyNameWithScript() {
        TestDataRow data = row("RISE_CLIENT_TC_015");
        String name = data.get("CompanyName");
        String marker = name.substring(name.indexOf("AT09_XSS_"));
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name chứa script và Save", () -> {
            modal.enterCompanyName(name).save();
        });
        Allure.step("Act: Tìm \"" + marker + "\"", () -> {
            page.search(marker);
        });
        Allure.step("Assert: Không có popup alert, tên hiển thị dạng text", () -> {
            Assert.assertFalse(page.isBrowserAlertPresent(Duration.ofSeconds(2)), "Không được bật popup alert từ dữ liệu client");
            Assert.assertFalse(page.hasInjectedScriptInTable(), "Không được chèn thẻ script vào bảng");
            List<String> names = page.getClientNames();
            Assert.assertTrue(names.stream().anyMatch(n -> n.contains(marker)), "Phải tìm thấy client chứa " + marker + ", thực tế: " + names);
        });
    }

    @Test(description = "Company name tiếng Việt có dấu và emoji", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_016")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients", "localization"})
    @Description("Company name tiếng Việt có dấu kèm emoji được lưu và hiển thị đúng nguyên văn.")
    public void testCompanyNameUnicode() {
        TestDataRow data = row("RISE_CLIENT_TC_016");
        String name = data.get("CompanyName");
        String marker = name.substring(name.indexOf("AT09_"));
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name \"" + name + "\" và Save", () -> {
            modal.enterCompanyName(name).save();
        });
        Allure.step("Assert: Tên hiển thị đúng nguyên văn", () -> {
            page.search(marker);
            Assert.assertEquals(page.getClientNames(), List.of(name), "Tên client hiển thị không đúng nguyên văn");
        });
    }

    @Test(description = "Cho phép trùng tên client đã có", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_017")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Hệ thống không kiểm tra trùng tên: thêm client trùng tên với client vừa tạo vẫn lưu được → có 2 dòng cùng tên.")
    public void testAddDuplicateClientName() {
        String name = row("RISE_CLIENT_TC_017").get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        Allure.step("Act: Thêm client khác trùng tên \"" + name + "\"", () -> {
            page.clickAddClient().enterCompanyName(name).save();
        });
        Allure.step("Assert: Có 2 dòng cùng tên", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 2, "Phải có 2 client cùng tên " + name);
        });
    }

    @Test(description = "Đổi Owner sang người khác", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_018")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Dropdown Owner có Anh Tester Admin, Admin Example, Project Manager; chọn Project Manager rồi lưu thì lọc Owner = Project Manager thấy client.")
    public void testAddClientWithOtherOwner() {
        TestDataRow data = row("RISE_CLIENT_TC_018");
        String name = data.get("CompanyName");
        String owner = data.get("Owner");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Assert: Dropdown Owner có đủ 3 người dùng", () -> {
            Assert.assertEquals(modal.getOwnerOptions(), OWNERS, "Danh sách Owner không đúng");
        });
        Allure.step("Act: Chọn Owner \"" + owner + "\", nhập Company name và Save", () -> {
            modal.selectOwner(owner).enterCompanyName(name).save();
        });
        Allure.step("Assert: Lọc Owner = " + owner + " thấy client vừa tạo", () -> {
            page.filterBy("- Owner -", owner).search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Lọc theo Owner " + owner + " phải thấy client " + name);
        });
    }

    @Test(description = "Phone chứa chữ cái vẫn lưu (không validate)", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_019")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Phone không có rule định dạng: giá trị có chữ cái vẫn lưu và hiển thị nguyên văn.")
    public void testPhoneWithLetters() {
        TestDataRow data = row("RISE_CLIENT_TC_019");
        String name = data.get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name, Phone \"" + data.get("Phone") + "\" và Save", () -> {
            modal.enterCompanyName(name).enterPhone(data.get("Phone")).save();
        });
        Allure.step("Assert: Phone hiển thị nguyên văn", () -> {
            page.search(name);
            Assert.assertEquals(page.getCellOfClient(name, "Phone"), data.get("Phone"), "Phone phải hiển thị nguyên văn");
        });
    }

    @Test(description = "Website không phải URL vẫn lưu (không validate)", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_020")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Website không có rule URL: giá trị \"not a url\" vẫn lưu được và giữ nguyên khi mở lại Edit.")
    public void testWebsiteNotUrl() {
        TestDataRow data = row("RISE_CLIENT_TC_020");
        String name = data.get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name, Website \"" + data.get("Website") + "\" và Save", () -> {
            modal.enterCompanyName(name).enterWebsite(data.get("Website")).save();
        });
        Allure.step("Assert: Client được lưu với Website nguyên văn", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Client phải được lưu");
            Assert.assertEquals(page.clickEdit(name).getWebsite(), data.get("Website"), "Website phải giữ nguyên văn");
        });
    }

    @Test(description = "Chọn nhiều Client groups", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_021")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Dropdown Client groups có VIP, Gold, Silver; chọn VIP và Gold thì cột Client groups hiển thị cả hai.")
    public void testAddClientWithMultipleGroups() {
        TestDataRow data = row("RISE_CLIENT_TC_021");
        String name = data.get("CompanyName");
        List<String> groups = Arrays.stream(data.get("ClientGroups").split(",")).map(String::trim).toList();
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Assert: Dropdown Client groups có VIP, Gold, Silver", () -> {
            Assert.assertEquals(modal.getClientGroupOptions(), CLIENT_GROUPS, "Danh sách Client groups không đúng");
        });
        Allure.step("Act: Nhập Company name, chọn " + groups + " và Save", () -> {
            modal.enterCompanyName(name);
            groups.forEach(modal::addClientGroup);
            modal.save();
        });
        Allure.step("Assert: Cột Client groups hiển thị " + groups, () -> {
            page.search(name);
            String cell = page.getCellOfClient(name, "Client groups");
            groups.forEach(g -> Assert.assertTrue(cell.contains(g), "Client groups phải có " + g + ", thực tế: " + cell));
        });
    }

    @Test(description = "Save & continue chuyển sang thêm contact", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_022")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Click Save & continue: client được lưu và modal chuyển thành \"Add multiple contacts\" với nút \"Save & add more\".")
    public void testSaveAndContinue() {
        String name = row("RISE_CLIENT_TC_022").get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        ContactFormModal contact = Allure.step("Act: Nhập Company name và click \"Save & continue\"", () -> modal.enterCompanyName(name).saveAndContinue());
        Allure.step("Assert: Modal chuyển thành \"Add multiple contacts\" có nút \"Save & add more\"", () -> {
            Assert.assertEquals(contact.getTitleText(), "Add multiple contacts", "Tiêu đề modal sau Save & continue không đúng");
            Assert.assertTrue(contact.getFooterButtonNames().contains("Save & add more"), "Phải có nút Save & add more");
        });
        Allure.step("Assert: Client đã được lưu", () -> {
            contact.close();
            page.openClientsTab().search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Client phải được lưu sau Save & continue");
        });
    }

    @Test(description = "Đóng modal bằng nút Close không lưu dữ liệu", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_023")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Nhập Company name rồi click Close: modal đóng và không tạo client.")
    public void testCloseAddClientModal() {
        String name = row("RISE_CLIENT_TC_023").get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name và click \"Close\"", () -> {
            modal.enterCompanyName(name).close();
        });
        Allure.step("Assert: Không tạo client (\"No record found.\")", () -> {
            page.search(name);
            Assert.assertEquals(page.table().getEmptyMessage(), "No record found.", "Không được tạo client khi bấm Close");
        });
    }

    @Test(description = "Phím ESC đóng modal Add client", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_024")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "a11y"})
    @Description("Nhấn ESC khi modal Add client đang mở thì modal đóng.")
    public void testEscapeClosesModal() {
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhấn phím ESC", () -> {
            modal.pressEscape();
        });
        Allure.step("Assert: Modal đã đóng", () -> {
            Assert.assertFalse(modal.isOpen(), "Modal phải đóng khi nhấn ESC");
        });
    }

    @Test(description = "Double click Save chỉ tạo 1 client", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_025")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Double click nhanh nút Save chỉ tạo đúng 1 client.")
    public void testDoubleClickSave() {
        String name = row("RISE_CLIENT_TC_025").get("CompanyName");
        ClientsPage page = clientsPage();
        ClientFormModal modal = Allure.step("Arrange: Mở modal Add client", () -> openAddClient(page));
        Allure.step("Act: Nhập Company name và double click nút \"Save\"", () -> {
            modal.enterCompanyName(name).doubleClickSave();
        });
        Allure.step("Assert: Chỉ có đúng 1 client", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Double click Save chỉ được tạo 1 client");
        });
    }
}
