package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.TestDataFiles;
import com.rise.pages.clients.ClientDetailPage;
import com.rise.pages.clients.ClientsPage;
import com.rise.pages.clients.ContactFormModal;
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
import java.util.List;

/**
 * Contacts của client — RISE_CLIENT_TC_038 → 059 (trừ nhóm validation ở ContactValidationTest). Dữ liệu: Clients.xlsx / Contact.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Contacts")
public class ContactTest extends ClientsBaseTest {

    private TestDataRow row(String tcId) {
        return ExcelReader.readRow(TestDataFiles.CLIENTS, TestDataFiles.CONTACT, tcId);
    }

    private String fullName(TestDataRow data) {
        return data.get("FirstName") + " " + data.get("LastName");
    }

    private void addExisting(ClientDetailPage detail, TestDataRow data) {
        addContact(detail, data.get("ExistingFirstName"), data.get("ExistingLastName"), data.get("ExistingEmail"), data.get("Password"));
    }

    private void fillContact(ContactFormModal modal, TestDataRow data) {
        modal.enterName(data.get("FirstName"), data.get("LastName")).enterEmail(data.get("Email"));
        if (!data.get("Phone").isEmpty() || !data.get("JobTitle").isEmpty()) {
            modal.enterPhoneAndJobTitle(data.get("Phone"), data.get("JobTitle"));
        }
        if (!data.get("Gender").isEmpty()) {
            modal.selectGender(data.get("Gender"));
        }
        modal.enterPassword(data.get("Password")).setEmailLoginDetails(false);
    }

    @Test(description = "Modal Add contact hiển thị đúng mặc định", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_038")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts", "ui"})
    @Description("Modal Add contact: tên/email trống, Gender chưa chọn, Password sinh sẵn 8 ký tự bị che, Can access everything bỏ tick, Can access only = Projects, Email login details đã tick (chỉ kiểm tra trạng thái — AMB-04), nút Close và Save.")
    public void testAddContactModalDefaults() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Act: Click \"Add contact\"", () -> detail.clickAddContact());
        Allure.step("Assert: Giá trị mặc định của modal đúng", () -> {
            Assert.assertEquals(modal.getTitleText(), "Add contact", "Tiêu đề modal không đúng");
            Assert.assertEquals(modal.getFirstName(), "", "First name mặc định phải trống");
            Assert.assertEquals(modal.getLastName(), "", "Last name mặc định phải trống");
            Assert.assertEquals(modal.getEmail(), "", "Email mặc định phải trống");
            Assert.assertFalse(modal.isAnyGenderSelected(), "Gender mặc định chưa được chọn");
            Assert.assertEquals(modal.getPasswordValue().length(), 8, "Password phải được sinh sẵn 8 ký tự");
            Assert.assertEquals(modal.getPasswordFieldType(), "password", "Password phải bị che");
            Assert.assertFalse(modal.isCanAccessEverythingChecked(), "Can access everything mặc định bỏ tick");
            Assert.assertEquals(modal.getCanAccessOnly(), List.of("Projects"), "Can access only mặc định là Projects");
            Assert.assertTrue(modal.isEmailLoginDetailsChecked(), "Email login details to this user mặc định đã tick");
            Assert.assertEquals(modal.getFooterButtonNames(), List.of("Close", "Save"), "Các nút của modal không đúng");
        });
    }

    @Test(description = "Thêm contact đầu tiên → trở thành Primary contact", groups = {"smoke", "regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_039")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({"smoke", "regression", "contacts", "critical-path"})
    @Description("Thêm contact đầu tiên cho client mới (click ô Password trước khi nhập, bỏ tick gửi email): contact hiển thị kèm badge Primary contact với đúng Job Title, Email, Phone.")
    public void testAddFirstContactBecomesPrimary() {
        TestDataRow data = row("RISE_CLIENT_TC_039");
        String name = fullName(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(data.get("ClientName")));
        Allure.step("Act: Thêm contact \"" + name + "\" và Save", () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
        });
        Allure.step("Assert: Contact là Primary contact với đúng thông tin", () -> {
            Assert.assertTrue(detail.isPrimaryContact(name), "Contact đầu tiên phải có badge Primary contact");
            Assert.assertEquals(detail.getContactCell(name, "Job Title"), data.get("JobTitle"), "Job Title không đúng");
            Assert.assertEquals(detail.getContactCell(name, "Email"), data.get("Email"), "Email không đúng");
            Assert.assertEquals(detail.getContactCell(name, "Phone"), data.get("Phone"), "Phone không đúng");
        });
    }

    @Test(description = "Contact thứ hai không phải Primary contact", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_040")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts"})
    @Description("Client đã có Primary contact: thêm contact thứ hai thì contact mới không có badge, contact đầu tiên vẫn là Primary.")
    public void testSecondContactIsNotPrimary() {
        TestDataRow data = row("RISE_CLIENT_TC_040");
        String first = data.get("ExistingFirstName") + " " + data.get("ExistingLastName");
        String second = fullName(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và contact đầu tiên \"" + first + "\"", () -> {
            ClientDetailPage d = createClientAndOpenDetail(data.get("ClientName"));
            addExisting(d, data);
            return d;
        });
        Allure.step("Act: Thêm contact thứ hai \"" + second + "\"", () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
            detail.waitContactRowByEmail(data.get("Email"));
        });
        Allure.step("Assert: Contact thứ hai không phải Primary, contact đầu tiên vẫn là Primary", () -> {
            Assert.assertFalse(detail.isPrimaryContact(second), "Contact thứ hai không được có badge Primary contact");
            Assert.assertTrue(detail.isPrimaryContact(first), "Contact đầu tiên phải vẫn là Primary contact");
        });
    }

    @Test(description = "Email đã được đăng ký", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_047")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "contacts", "validation"})
    @Description("Thêm contact với email trùng contact đã có: alert đỏ trong modal báo email đã đăng ký, modal giữ nguyên dữ liệu đã nhập.")
    public void testDuplicateContactEmail() {
        TestDataRow data = row("RISE_CLIENT_TC_047");
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và contact có email " + data.get("ExistingEmail"), () -> {
            ClientDetailPage d = createClientAndOpenDetail(data.get("ClientName"));
            addExisting(d, data);
            return d;
        });
        ContactFormModal modal = Allure.step("Act: Thêm contact khác dùng lại email đó và Save", () -> {
            ContactFormModal m = detail.clickAddContact();
            fillContact(m, data);
            return m.clickSaveExpectingError();
        });
        Allure.step("Assert: Báo email đã đăng ký, modal giữ nguyên dữ liệu", () -> {
            Assert.assertEquals(modal.getAlertMessage(), data.get("ExpectedMessage"), "Thông báo email trùng không đúng");
            Assert.assertTrue(modal.isOpen(), "Modal phải giữ nguyên khi lưu thất bại");
            Assert.assertEquals(modal.getFirstName(), data.get("FirstName"), "First name đã nhập phải được giữ nguyên");
            Assert.assertEquals(modal.getEmail(), data.get("Email"), "Email đã nhập phải được giữ nguyên");
        });
    }

    @Test(description = "Password đúng 6 ký tự (min) được chấp nhận", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_049")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "contacts", "boundary"})
    @Description("Password đúng 6 ký tự (biên tối thiểu) được chấp nhận và contact được lưu.")
    public void testPasswordMinLengthAccepted() {
        TestDataRow data = row("RISE_CLIENT_TC_049");
        String name = fullName(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(data.get("ClientName")));
        Allure.step("Act: Thêm contact với Password 6 ký tự", () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
        });
        Allure.step("Assert: Contact \"" + name + "\" xuất hiện", () -> {
            Assert.assertEquals(detail.getContactDisplayName(name), name, "Contact phải được lưu với Password 6 ký tự");
        });
    }

    @Test(description = "Nút Generate sinh mật khẩu mới", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_051")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "contacts"})
    @Description("Hiện mật khẩu bằng nút mắt, ghi lại giá trị, click Generate thì mật khẩu đổi sang chuỗi mới dài 8 ký tự.")
    public void testGeneratePassword() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact và hiện mật khẩu", () -> detail.clickAddContact().toggleShowPassword());
        String before = modal.getPasswordValue();
        Allure.step("Act: Click \"Generate\"", () -> {
            modal.clickGeneratePassword();
        });
        Allure.step("Assert: Mật khẩu mới khác mật khẩu cũ và dài 8 ký tự", () -> {
            String after = modal.getPasswordValue();
            Assert.assertNotEquals(after, before, "Generate phải sinh mật khẩu mới");
            Assert.assertEquals(after.length(), 8, "Mật khẩu sinh ra phải dài 8 ký tự");
        });
    }

    @Test(description = "Nút mắt hiện/ẩn mật khẩu", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_052")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "contacts"})
    @Description("Click nút mắt: ô Password chuyển type=\"text\"; click lần nữa: quay lại type=\"password\".")
    public void testShowHidePassword() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact", () -> detail.clickAddContact());
        Allure.step("Act: Click nút mắt", () -> {
            modal.toggleShowPassword();
        });
        Allure.step("Assert: Mật khẩu hiển thị dạng text", () -> {
            Assert.assertEquals(modal.getPasswordFieldType(), "text", "Sau khi click nút mắt, Password phải hiện dạng text");
        });
        Allure.step("Act: Click nút mắt lần nữa", () -> {
            modal.toggleShowPassword();
        });
        Allure.step("Assert: Mật khẩu bị che lại", () -> {
            Assert.assertEquals(modal.getPasswordFieldType(), "password", "Click lần 2 phải che lại mật khẩu");
        });
    }

    @Test(description = "Ô Password chỉ nhập được sau khi focus", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_053")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts"})
    @Description("Ô Password có readonly (chống autofill) cho tới khi được click; sau khi click thì hết readonly và nhập được.")
    public void testPasswordReadonlyUntilFocus() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact", () -> detail.clickAddContact());
        Allure.step("Assert: Ô Password đang readonly", () -> {
            Assert.assertTrue(modal.isPasswordReadonly(), "Ô Password phải readonly khi chưa focus");
        });
        Allure.step("Act: Click vào ô Password", () -> {
            modal.focusPassword();
        });
        Allure.step("Assert: Hết readonly và nhập được ký tự", () -> {
            Assert.assertFalse(modal.isPasswordReadonly(), "Ô Password phải hết readonly sau khi focus");
            modal.enterPassword("Abc@1234");
            Assert.assertEquals(modal.getPasswordValue().length(), 8, "Phải nhập được mật khẩu sau khi focus");
        });
    }

    @Test(description = "Tick \"Can access everything\" ẩn \"Can access only\"", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_054")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts"})
    @Description("Tick Can access everything thì dòng Can access only bị ẩn; bỏ tick thì hiện lại.")
    public void testCanAccessEverythingTogglesSection() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact", () -> detail.clickAddContact());
        Allure.step("Act: Tick \"Can access everything\"", () -> {
            modal.setCanAccessEverything(true);
        });
        Allure.step("Assert: Dòng \"Can access only\" bị ẩn", () -> {
            Assert.assertFalse(modal.isCanAccessOnlyVisible(), "Can access only phải bị ẩn khi tick Can access everything");
        });
        Allure.step("Act: Bỏ tick \"Can access everything\"", () -> {
            modal.setCanAccessEverything(false);
        });
        Allure.step("Assert: Dòng \"Can access only\" hiện lại", () -> {
            Assert.assertTrue(modal.isCanAccessOnlyVisible(), "Can access only phải hiện lại khi bỏ tick");
        });
    }

    @Test(description = "Lưu contact có chọn Gender Female", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_055")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "contacts"})
    @Description("Chọn Gender = Female khi thêm contact thì lưu thành công.")
    public void testAddContactWithGender() {
        TestDataRow data = row("RISE_CLIENT_TC_055");
        String name = fullName(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(data.get("ClientName")));
        Allure.step("Act: Thêm contact Gender = " + data.get("Gender"), () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
        });
        Allure.step("Assert: Contact được lưu", () -> {
            Assert.assertEquals(detail.getContactDisplayName(name), name, "Contact có Gender phải được lưu");
        });
    }

    @Test(description = "Tên contact tiếng Việt có dấu", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_056")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts", "localization"})
    @Description("Tên contact tiếng Việt có dấu được lưu và hiển thị đúng.")
    public void testVietnameseContactName() {
        TestDataRow data = row("RISE_CLIENT_TC_056");
        String name = fullName(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(data.get("ClientName")));
        Allure.step("Act: Thêm contact \"" + name + "\"", () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
        });
        Allure.step("Assert: Tên hiển thị đúng dấu", () -> {
            Assert.assertEquals(detail.getContactNameByEmail(data.get("Email")), name, "Tên tiếng Việt hiển thị không đúng");
        });
    }

    @Test(description = "First name chứa script (XSS)", groups = {"regression", "clients", "contacts", "security"})
    @TestCaseId("RISE_CLIENT_TC_057")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "contacts", "security"})
    @Description("First name chứa <img src=x onerror=alert(1)>: lưu xong không bật popup alert và không chèn thẻ img vào bảng contact.")
    public void testContactNameWithScript() {
        TestDataRow data = row("RISE_CLIENT_TC_057");
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(data.get("ClientName")));
        Allure.step("Act: Thêm contact có First name chứa script", () -> {
            ContactFormModal modal = detail.clickAddContact();
            fillContact(modal, data);
            modal.save();
            detail.waitContactRowByEmail(data.get("Email"));
        });
        Allure.step("Assert: Không bật popup alert, tên hiển thị dạng text", () -> {
            Assert.assertFalse(detail.isBrowserAlertPresent(Duration.ofSeconds(2)), "Không được bật popup alert từ dữ liệu contact");
            Assert.assertFalse(detail.hasInjectedImageInContacts(), "Không được chèn thẻ img vào bảng contact");
            Assert.assertTrue(detail.getContactNameByEmail(data.get("Email")).contains(data.get("LastName")), "Contact phải hiển thị trong bảng");
        });
    }

    @Test(description = "Thêm contact ngay sau Save & continue", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_058")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts"})
    @Description("Save & continue → nhập contact → Save & add more: form được làm trống để nhập tiếp; mở client thấy contact là Primary contact.")
    public void testAddContactAfterSaveAndContinue() {
        TestDataRow data = row("RISE_CLIENT_TC_058");
        String client = data.get("ClientName");
        String name = fullName(data);
        ClientsPage page = clientsPage();
        ContactFormModal modal = Allure.step("Arrange: Add client \"" + client + "\" bằng Save & continue", () ->
                page.open().openClientsTab().clickAddClient().enterCompanyName(client).saveAndContinue());
        Allure.step("Act: Nhập contact \"" + name + "\" và click \"Save & add more\"", () -> {
            fillContact(modal, data);
            modal.saveAndAddMore();
        });
        Allure.step("Assert: Form contact được làm trống để nhập tiếp", () -> {
            Assert.assertEquals(modal.getFirstName(), "", "First name phải được làm trống sau Save & add more");
            Assert.assertEquals(modal.getEmail(), "", "Email phải được làm trống sau Save & add more");
        });
        Allure.step("Assert: Đóng modal, mở client thấy contact là Primary contact", () -> {
            modal.close();
            ClientDetailPage detail = page.openClientsTab().search(client).openClientDetail(client);
            Assert.assertTrue(detail.isPrimaryContact(name), "Contact thêm qua Save & add more phải là Primary contact");
        });
    }

    @Test(description = "Primary contact hiển thị ở bảng Clients", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_059")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts"})
    @Description("Client đã có contact đầu tiên: cột Primary contact ở bảng Clients hiển thị tên contact đó.")
    public void testPrimaryContactShownInClientList() {
        TestDataRow data = row("RISE_CLIENT_TC_059");
        String client = data.get("ClientName");
        String contact = data.get("ExistingFirstName") + " " + data.get("ExistingLastName");
        Allure.step("Arrange: Tạo client và contact đầu tiên \"" + contact + "\"", () -> {
            addExisting(createClientAndOpenDetail(client), data);
        });
        ClientsPage page = Allure.step("Act: Mở tab Clients và search \"" + client + "\"", () -> clientsPage().open().openClientsTab().search(client));
        Allure.step("Assert: Cột Primary contact hiển thị \"" + contact + "\"", () -> {
            Assert.assertEquals(page.getCellOfClient(client, "Primary contact"), contact, "Cột Primary contact không đúng");
        });
    }
}
