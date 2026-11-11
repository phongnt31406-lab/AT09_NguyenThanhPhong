package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.pages.clients.ClientDetailPage;
import com.rise.pages.clients.ClientsPage;
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
 * Trang chi tiết client — RISE_CLIENT_TC_036, 037.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Chi tiết client")
public class ClientDetailTest extends ClientsBaseTest {

    private static final List<String> SUMMARY_CARDS = List.of("Projects", "Total invoiced", "Payments", "Due");
    private static final List<String> CONTACT_HEADERS = List.of("", "Name", "Job Title", "Email", "Phone", "Skype", "");

    @Test(description = "Mở trang chi tiết client", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_036")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients"})
    @Description("Click tên client: chuyển tới /clients/view/<id>, tiêu đề \"Client details - <tên>\", 4 thẻ Projects / Total invoiced / Payments / Due, tab Contacts đang chọn.")
    public void testOpenClientDetail() {
        String name = newClientName();
        ClientsPage page = Allure.step("Arrange: Tạo client \"" + name + "\"", () -> createClient(name));
        ClientDetailPage detail = Allure.step("Act: Search và click vào tên client", () -> page.search(name).openClientDetail(name));
        Allure.step("Assert: Trang chi tiết hiển thị đúng", () -> {
            Assert.assertTrue(detail.getCurrentUrl().matches(".*/clients/view/\\d+$"), "URL phải là /clients/view/<id>, thực tế: " + detail.getCurrentUrl());
            Assert.assertEquals(detail.getHeading(), "Client details - " + name, "Tiêu đề trang không đúng");
            List<String> cards = detail.getSummaryCardTexts();
            Assert.assertEquals(cards.size(), SUMMARY_CARDS.size(), "Phải có 4 thẻ tổng quan, thực tế: " + cards);
            for (int i = 0; i < SUMMARY_CARDS.size(); i++) {
                Assert.assertTrue(cards.get(i).endsWith(SUMMARY_CARDS.get(i)), "Thẻ thứ " + (i + 1) + " phải là " + SUMMARY_CARDS.get(i) + ", thực tế: " + cards.get(i));
            }
            Assert.assertTrue(detail.isContactsTabActive(), "Tab Contacts phải đang được chọn");
        });
    }

    @Test(description = "Tab Contacts của client mới chưa có contact", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_037")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "ui"})
    @Description("Client mới tạo: tab Contacts có bảng cột Name, Job Title, Email, Phone, Skype, hiển thị \"No record found.\" và có nút Send invitation, Add contact.")
    public void testEmptyContactsTab() {
        String name = newClientName();
        ClientDetailPage detail = Allure.step("Arrange: Tạo client và mở trang chi tiết", () -> createClientAndOpenDetail(name));
        List<String> headers = Allure.step("Act: Đọc bảng Contacts", () -> detail.getContactTableHeaders());
        Allure.step("Assert: Bảng rỗng đúng cột và có 2 nút chức năng", () -> {
            Assert.assertEquals(headers, CONTACT_HEADERS, "Cột của bảng Contacts không đúng");
            Assert.assertEquals(detail.getContactTableEmptyMessage(), "No record found.", "Client mới phải chưa có contact");
            Assert.assertTrue(detail.isSendInvitationButtonVisible(), "Phải có nút Send invitation");
            Assert.assertTrue(detail.isAddContactButtonVisible(), "Phải có nút Add contact");
        });
    }
}
