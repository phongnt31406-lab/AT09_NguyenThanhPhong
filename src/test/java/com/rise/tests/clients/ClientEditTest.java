package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.TestDataFiles;
import com.rise.pages.clients.ClientFormModal;
import com.rise.pages.clients.ClientsPage;
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

import java.util.List;

/**
 * Sửa client — RISE_CLIENT_TC_026 → 030. Mỗi test tự tạo client rồi sửa đúng client đó. Dữ liệu: Clients.xlsx / ClientEdit.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Sửa client")
public class ClientEditTest extends ClientsBaseTest {

    private TestDataRow row(String tcId) {
        return ExcelReader.readRow(TestDataFiles.CLIENTS, TestDataFiles.CLIENT_EDIT, tcId);
    }

    @Test(description = "Modal Edit client hiển thị dữ liệu hiện tại", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_026")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Click icon Edit của client vừa tạo: modal \"Edit client\" điền sẵn Company name, Phone, City và chỉ có nút Close, Save.")
    public void testEditModalShowsCurrentData() {
        TestDataRow data = row("RISE_CLIENT_TC_026");
        String name = data.get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client có Phone + City", () -> createClient(name, data.get("Phone"), data.get("City")));
        ClientFormModal modal = Allure.step("Act: Search client và click icon Edit", () -> page.search(name).clickEdit(name));
        Allure.step("Assert: Modal Edit hiển thị đúng dữ liệu hiện tại", () -> {
            Assert.assertEquals(modal.getTitleText(), "Edit client", "Tiêu đề modal không đúng");
            Assert.assertEquals(modal.getCompanyName(), name, "Company name không đúng");
            Assert.assertEquals(modal.getPhone(), data.get("Phone"), "Phone không đúng");
            Assert.assertEquals(modal.getCity(), data.get("City"), "City không đúng");
            Assert.assertEquals(modal.getFooterButtonNames(), List.of("Close", "Save"), "Modal Edit chỉ có nút Close và Save");
        });
    }

    @Test(description = "Sửa Name, Phone, City và lưu", groups = {"smoke", "regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_027")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({"smoke", "regression", "clients"})
    @Description("Sửa Company name, Phone, City rồi Save: modal đóng và bảng hiển thị giá trị mới.")
    public void testEditClient() {
        TestDataRow data = row("RISE_CLIENT_TC_027");
        String name = data.get("CompanyName");
        String newName = data.get("NewCompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        Allure.step("Act: Mở Edit, đổi Name / Phone / City và Save", () -> {
            page.search(name).clickEdit(name)
                    .enterCompanyName(newName)
                    .enterPhone(data.get("NewPhone"))
                    .enterCity(data.get("NewCity"))
                    .save();
        });
        Allure.step("Assert: Bảng hiển thị Name và Phone mới", () -> {
            page.search(newName);
            Assert.assertEquals(page.countRowsNamed(newName), 1, "Phải tìm thấy client với tên mới " + newName);
            Assert.assertEquals(page.getCellOfClient(newName, "Phone"), data.get("NewPhone"), "Phone mới không đúng");
            Assert.assertEquals(page.clickEdit(newName).getCity(), data.get("NewCity"), "City mới không đúng");
        });
    }

    @Test(description = "Không cho lưu khi xoá trắng Company name lúc sửa", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_028")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients", "validation"})
    @Description("Xoá trắng Company name trong modal Edit rồi Save: báo \"This field is required.\", modal không đóng, tên cũ giữ nguyên.")
    public void testEditClientWithEmptyName() {
        TestDataRow data = row("RISE_CLIENT_TC_028");
        String name = data.get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        ClientFormModal modal = Allure.step("Act: Mở Edit, xoá trắng Company name và Save", () ->
                page.search(name).clickEdit(name).enterCompanyName("").clickSaveExpectingError());
        Allure.step("Assert: Báo lỗi bắt buộc, modal không đóng, tên cũ giữ nguyên", () -> {
            Assert.assertEquals(modal.getCompanyNameError(), data.get("ExpectedMessage"), "Thông báo lỗi không đúng");
            Assert.assertTrue(modal.isOpen(), "Modal phải giữ nguyên khi lưu thất bại");
            modal.close();
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Tên cũ phải được giữ nguyên");
        });
    }

    @Test(description = "Đổi Type từ Organization sang Person khi sửa", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_029")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Trong modal Edit chọn Type = Person: nhãn đổi thành \"Name\"; Save rồi mở lại Edit thì Type = Person được giữ.")
    public void testChangeTypeToPerson() {
        TestDataRow data = row("RISE_CLIENT_TC_029");
        String name = data.get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client Organization \"" + name + "\"", () -> createClient(name));
        ClientFormModal modal = Allure.step("Act: Mở Edit và chọn Type = Person", () -> page.search(name).clickEdit(name).selectType(data.get("NewType")));
        Allure.step("Assert: Nhãn đổi thành \"Name\"", () -> {
            Assert.assertEquals(modal.getCompanyNameLabel(), "Name", "Nhãn phải đổi thành Name khi chọn Person");
        });
        Allure.step("Act: Save", () -> {
            modal.save();
        });
        Allure.step("Assert: Mở lại Edit thấy Type = Person", () -> {
            Assert.assertEquals(page.search(name).clickEdit(name).getSelectedType(), "Person", "Type Person phải được lưu");
        });
    }

    @Test(description = "Đóng modal Edit không lưu thay đổi", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_030")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Đổi Phone trong modal Edit rồi click Close: modal đóng, cột Phone giữ giá trị cũ.")
    public void testCloseEditModalWithoutSaving() {
        TestDataRow data = row("RISE_CLIENT_TC_030");
        String name = data.get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client có Phone \"" + data.get("Phone") + "\"", () -> createClient(name, data.get("Phone"), ""));
        Allure.step("Act: Mở Edit, đổi Phone thành \"" + data.get("NewPhone") + "\" và click Close", () -> {
            page.search(name).clickEdit(name).enterPhone(data.get("NewPhone")).close();
        });
        Allure.step("Assert: Cột Phone vẫn là giá trị cũ", () -> {
            page.search(name);
            Assert.assertEquals(page.getCellOfClient(name, "Phone"), data.get("Phone"), "Phone không được thay đổi khi bấm Close");
        });
    }
}
