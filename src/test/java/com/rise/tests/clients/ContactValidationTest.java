package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.ClientsDataProvider;
import com.rise.pages.clients.ClientDetailPage;
import com.rise.pages.clients.ContactFormModal;
import com.rise.utils.AllureManager;
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

import java.util.Collections;
import java.util.List;

/**
 * Validation form Add contact — RISE_CLIENT_TC_041 → 050 (data-driven từ Clients.xlsx / ContactValidation) và TC_044.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Contacts")
public class ContactValidationTest extends ClientsBaseTest {

    private static final String REQUIRED = "This field is required.";

    @Test(description = "Không lưu contact khi dữ liệu không hợp lệ", dataProvider = "contactValidation",
            dataProviderClass = ClientsDataProvider.class, groups = {"regression", "clients", "contacts"})
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "contacts", "validation", "data-driven"})
    @Description("Data-driven từ Clients.xlsx / ContactValidation: bỏ trống First name / Last name / Email / Password, email sai định dạng, "
            + "Password 5 ký tự. Hệ thống báo đúng lỗi dưới ô tương ứng và modal không đóng.")
    public void testAddContactWithInvalidData(TestDataRow data) {
        AllureManager.useTestCase(data);
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact", () -> detail.clickAddContact());
        Allure.step("Act: Nhập First \"" + data.get("FirstName") + "\", Last \"" + data.get("LastName") + "\", Email \"" + data.get("Email") + "\", mật khẩu theo dữ liệu và Save", () -> {
            modal.enterName(data.get("FirstName"), data.get("LastName"))
                    .enterEmail(data.get("Email"))
                    .enterPassword(data.get("Password"))
                    .clickSaveExpectingError();
        });
        Allure.step("Assert: Hiển thị \"" + data.get("ExpectedMessage") + "\" dưới " + data.get("ErrorField") + ", modal không đóng", () -> {
            Assert.assertEquals(modal.getFieldError(data.get("ErrorField")), data.get("ExpectedMessage"),
                    "Thông báo lỗi dưới " + data.get("ErrorField") + " không đúng (" + data.tcId() + ")");
            Assert.assertTrue(modal.isOpen(), "Modal phải giữ nguyên khi dữ liệu không hợp lệ");
        });
    }

    @Test(description = "Save khi để trống toàn bộ trường bắt buộc", groups = {"regression", "clients", "contacts"})
    @TestCaseId("RISE_CLIENT_TC_044")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "contacts", "validation"})
    @Description("Không nhập gì rồi Save: hiển thị 3 thông báo \"This field is required.\" dưới First name, Last name, Email.")
    public void testAddContactWithAllRequiredFieldsEmpty() {
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(newClientName()));
        ContactFormModal modal = Allure.step("Arrange: Mở Add contact", () -> detail.clickAddContact());
        Allure.step("Act: Không nhập gì và click Save", () -> {
            modal.clickSaveExpectingError();
        });
        Allure.step("Assert: 3 lỗi bắt buộc dưới First name, Last name, Email", () -> {
            Assert.assertEquals(modal.getFieldError("first_name"), REQUIRED, "Thiếu lỗi dưới First name");
            Assert.assertEquals(modal.getFieldError("last_name"), REQUIRED, "Thiếu lỗi dưới Last name");
            Assert.assertEquals(modal.getFieldError("email"), REQUIRED, "Thiếu lỗi dưới Email");
            List<String> errors = modal.getAllFieldErrors();
            Assert.assertEquals(Collections.frequency(errors, REQUIRED), 3, "Phải có đúng 3 lỗi bắt buộc, thực tế: " + errors);
        });
    }
}
