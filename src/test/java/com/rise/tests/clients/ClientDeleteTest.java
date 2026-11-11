package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.TestDataFiles;
import com.rise.pages.clients.ClientsPage;
import com.rise.pages.components.ConfirmDialog;
import com.rise.pages.projects.ProjectsPage;
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
 * Xoá client — RISE_CLIENT_TC_031 → 035. Mỗi test tự tạo client rồi xoá đúng client đó. Dữ liệu: Clients.xlsx / ClientDelete.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Xoá client")
public class ClientDeleteTest extends ClientsBaseTest {

    private static final String DIALOG_MESSAGE = "Are you sure? You won't be able to undo this action!";

    private TestDataRow row(String tcId) {
        return ExcelReader.readRow(TestDataFiles.CLIENTS, TestDataFiles.CLIENT_DELETE, tcId);
    }

    @Test(description = "Dialog xác nhận xoá hiển thị đúng nội dung", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_031")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients", "ui"})
    @Description("Click icon Delete: dialog tiêu đề \"Delete?\", nội dung cảnh báo không thể hoàn tác, nút Delete (đỏ) và Cancel.")
    public void testDeleteConfirmationDialog() {
        String name = row("RISE_CLIENT_TC_031").get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        ConfirmDialog dialog = Allure.step("Act: Search client và click icon Delete", () -> page.search(name).clickDelete(name));
        Allure.step("Assert: Dialog hiển thị đúng tiêu đề, nội dung và nút", () -> {
            Assert.assertEquals(dialog.getTitleText(), "Delete?", "Tiêu đề dialog không đúng");
            Assert.assertEquals(dialog.getMessage(), DIALOG_MESSAGE, "Nội dung dialog không đúng");
            Assert.assertEquals(dialog.getButtonTexts(), List.of("Delete", "Cancel"), "Các nút của dialog không đúng");
            Assert.assertTrue(dialog.isDeleteButtonDanger(), "Nút Delete phải có màu đỏ (btn-danger)");
        });
    }

    @Test(description = "Cancel trong dialog không xoá client", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_032")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients"})
    @Description("Click Cancel trong dialog xác nhận: dialog đóng và client vẫn còn.")
    public void testCancelDelete() {
        String name = row("RISE_CLIENT_TC_032").get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        ConfirmDialog dialog = Allure.step("Act: Click icon Delete rồi \"Cancel\"", () -> {
            ConfirmDialog d = page.search(name).clickDelete(name);
            d.cancel();
            return d;
        });
        Allure.step("Assert: Dialog đóng, client vẫn còn", () -> {
            Assert.assertFalse(dialog.isOpen(), "Dialog phải đóng sau khi bấm Cancel");
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Client không được bị xoá khi bấm Cancel");
        });
    }

    @Test(description = "Xoá client thành công", groups = {"smoke", "regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_033")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({"smoke", "regression", "clients"})
    @Description("Client chưa có project: Delete → toast \"The record has been deleted.\" và search lại không còn client.")
    public void testDeleteClient() {
        TestDataRow data = row("RISE_CLIENT_TC_033");
        String name = data.get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        Allure.step("Act: Click icon Delete rồi \"Delete\"", () -> {
            page.search(name).clickDelete(name).confirmDelete();
        });
        Allure.step("Assert: Toast xoá thành công và không còn client", () -> {
            Assert.assertEquals(page.getToastMessage(), data.get("ExpectedToast"), "Toast sau khi xoá không đúng");
            page.search(name);
            Assert.assertEquals(page.table().getEmptyMessage(), "No record found.", "Client phải bị xoá");
        });
    }

    @Test(description = "Xoá client đang có project được phép, project vẫn còn", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_034")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({"regression", "clients", "business-rule"})
    @Description("Business rule (AMB-02): xoá client đang có project không bị chặn; project vẫn tồn tại và cột Client vẫn hiển thị tên client đã xoá.")
    public void testDeleteClientWithProject() {
        TestDataRow data = row("RISE_CLIENT_TC_034");
        String name = data.get("CompanyName");
        String project = data.get("ProjectTitle");
        ProjectsPage projects = new ProjectsPage(driver());
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\" và project \"" + project + "\" cho client đó", () -> {
            createClient(name);
            projects.open().clickAddProject().enterTitle(project).selectClient(name).save();
            return clientsPage().open().openClientsTab();
        });
        Allure.step("Act: Xoá client \"" + name + "\"", () -> {
            page.search(name).clickDelete(name).confirmDelete();
        });
        Allure.step("Assert: Xoá thành công, không có cảnh báo", () -> {
            Assert.assertEquals(page.getToastMessage(), data.get("ExpectedToast"), "Toast sau khi xoá không đúng");
        });
        Allure.step("Assert: Project vẫn tồn tại và vẫn hiển thị tên client đã xoá", () -> {
            projects.open().search(project);
            Assert.assertEquals(projects.countRowsTitled(project), 1, "Project phải vẫn tồn tại sau khi xoá client");
            Assert.assertEquals(projects.getCellOfProject(project, "Client"), name, "Cột Client của project phải giữ tên client đã xoá");
        });
    }

    @Test(description = "Đóng dialog xoá bằng nút X", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_035")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients"})
    @Description("Click nút X ở góc dialog xác nhận: dialog đóng và client vẫn còn.")
    public void testCloseDeleteDialogByX() {
        String name = row("RISE_CLIENT_TC_035").get("CompanyName");
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        Allure.step("Act: Click icon Delete rồi nút X", () -> {
            page.search(name).clickDelete(name).closeByX();
        });
        Allure.step("Assert: Client vẫn còn", () -> {
            page.search(name);
            Assert.assertEquals(page.countRowsNamed(name), 1, "Client không được bị xoá khi đóng dialog");
        });
    }
}
