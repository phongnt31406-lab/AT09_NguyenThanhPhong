package com.rise.tests.clients;

import com.rise.annotations.Tags;
import com.rise.annotations.TestCaseId;
import com.rise.dataproviders.ClientsDataProvider;
import com.rise.pages.clients.ClientsPage;
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

import java.util.List;

/**
 * Danh sách Clients — RISE_CLIENT_TC_001 → 008.
 */
@Epic("RISE CRM")
@Feature("Clients & Contacts")
@Story("Danh sách Clients")
public class ClientListTest extends ClientsBaseTest {

    private static final List<String> TABS = List.of("Overview", "Clients", "Contacts");
    private static final List<String> TITLE_BUTTONS = List.of("Manage labels", "Import clients", "Add client");
    private static final List<String> TABLE_HEADERS = List.of("ID", "Name", "Primary contact", "Phone", "Client groups",
            "Labels", "Projects", "Total invoiced", "Payment Received", "Due", "");
    private static final List<String> PAGE_SIZES = List.of("10", "25", "50", "100");

    @Test(description = "Trang Clients hiển thị đủ tab và nút chức năng", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_001")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "ui"})
    @Description("Mở /clients: có 3 tab Overview (đang chọn) / Clients / Contacts và 3 nút Manage labels, Import clients, Add client.")
    public void testClientsPageLayout() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở trang Clients", () -> {
            page.open();
        });
        List<String> tabs = Allure.step("Act: Đọc các tab và nút chức năng", () -> page.getTabNames());
        Allure.step("Assert: Hiển thị đủ 3 tab, tab Overview đang chọn và 3 nút chức năng", () -> {
            Assert.assertEquals(tabs, TABS, "Các tab của trang Clients không đúng");
            Assert.assertEquals(page.getActiveTabName(), "Overview", "Tab mặc định phải là Overview");
            Assert.assertEquals(page.getTitleButtonNames(), TITLE_BUTTONS, "Các nút chức năng không đúng");
        });
    }

    @Test(description = "Bảng Clients có đúng các cột theo thứ tự", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_002")
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients", "ui"})
    @Description("Tab Clients hiển thị bảng với cột theo thứ tự: ID, Name, Primary contact, Phone, Client groups, Labels, Projects, Total invoiced, Payment Received, Due, cột thao tác.")
    public void testClientTableColumns() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở trang Clients", () -> {
            page.open();
        });
        Allure.step("Act: Click tab \"Clients\"", () -> {
            page.openClientsTab();
        });
        Allure.step("Assert: Header bảng đúng thứ tự cột", () -> {
            Assert.assertEquals(page.getTableHeaders(), TABLE_HEADERS, "Cột của bảng Clients không đúng thứ tự");
        });
    }

    @Test(description = "Tìm kiếm client", dataProvider = "clientSearch", dataProviderClass = ClientsDataProvider.class,
            groups = {"regression", "clients"})
    @Severity(SeverityLevel.NORMAL)
    @Tags({"regression", "clients", "search", "data-driven"})
    @Description("Data-driven từ Clients.xlsx / ClientSearch: tìm theo tên đầy đủ, một phần tên và từ khoá không có kết quả.")
    public void testSearchClient(TestDataRow data) {
        AllureManager.useTestCase(data);
        String clientName = data.get("CompanyName");
        String keyword = data.get("SearchKeyword");
        ClientsPage page = clientsPage();

        Allure.step("Arrange: " + (clientName.isEmpty() ? "Mở tab Clients" : "Tạo client \"" + clientName + "\" và ở tab Clients"), () -> {
            if (clientName.isEmpty()) {
                page.open().openClientsTab();
            } else {
                createClient(clientName);
            }
        });
        Allure.step("Act: Nhập vào ô Search \"" + keyword + "\"", () -> {
            page.search(keyword);
        });
        Allure.step("Assert: Kết quả tìm kiếm đúng", () -> {
            if (!data.get("ExpectedMessage").isEmpty()) {
                Assert.assertEquals(page.table().getEmptyMessage(), data.get("ExpectedMessage"), "Thông báo khi không có kết quả không đúng");
            } else {
                List<String> names = page.getClientNames();
                Assert.assertTrue(names.contains(clientName), "Kết quả phải có client " + clientName + ", thực tế: " + names);
                Assert.assertTrue(names.stream().allMatch(n -> n.contains(keyword)), "Mọi dòng phải chứa từ khoá " + keyword + ", thực tế: " + names);
            }
            if (!data.get("ExpectedInfo").isEmpty()) {
                Assert.assertEquals(page.table().getInfo(), data.get("ExpectedInfo"), "Thông tin phân trang không đúng");
            }
        });
    }

    @Test(description = "Đổi số dòng mỗi trang sang 25", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_006")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "pagination"})
    @Description("Dropdown số dòng/trang có 10, 25, 50, 100; chọn 25 thì bảng hiển thị 25 dòng và thông tin dạng \"1-25 / <tổng>\".")
    public void testChangePageSize() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở tab Clients", () -> {
            page.open().openClientsTab();
        });
        Allure.step("Act + Assert: Dropdown page size có 10, 25, 50, 100", () -> {
            Assert.assertEquals(page.table().getPageSizeOptions(), PAGE_SIZES, "Các lựa chọn số dòng/trang không đúng");
        });
        Allure.step("Act: Chọn 25 dòng mỗi trang", () -> {
            page.table().setPageSize("25");
        });
        Allure.step("Assert: Bảng hiển thị 25 dòng, info \"1-25 / <tổng>\"", () -> {
            Assert.assertEquals(page.table().getRows().size(), 25, "Bảng phải hiển thị 25 dòng");
            Assert.assertTrue(page.table().getInfo().startsWith("1-25 / "), "Info phải dạng 1-25 / <tổng>, thực tế: " + page.table().getInfo());
        });
    }

    @Test(description = "Chuyển sang trang 2", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_007")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "pagination"})
    @Description("Click nút trang 2 thì thông tin chuyển thành \"11-20 / <tổng>\" và nút 2 được tô.")
    public void testGoToSecondPage() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở tab Clients", () -> {
            page.open().openClientsTab();
        });
        Allure.step("Act: Click nút trang \"2\"", () -> {
            page.table().goToPage("2");
        });
        Allure.step("Assert: Info \"11-20 / <tổng>\" và trang 2 đang được chọn", () -> {
            Assert.assertTrue(page.table().getInfo().startsWith("11-20 / "), "Info phải dạng 11-20 / <tổng>, thực tế: " + page.table().getInfo());
            Assert.assertEquals(page.table().getActivePage(), "2", "Nút trang 2 phải được tô");
        });
    }

    @Test(description = "Lọc theo Client groups = VIP", groups = {"regression", "clients"})
    @TestCaseId("RISE_CLIENT_TC_008")
    @Severity(SeverityLevel.MINOR)
    @Tags({"regression", "clients", "filter"})
    @Description("Chọn bộ lọc Client groups = VIP thì bảng tự tải lại và mọi dòng có cột Client groups chứa VIP.")
    public void testFilterByClientGroup() {
        ClientsPage page = clientsPage();
        Allure.step("Arrange: Mở tab Clients", () -> {
            page.open().openClientsTab();
        });
        Allure.step("Act: Chọn bộ lọc \"- Client groups -\" = VIP", () -> {
            page.filterBy("- Client groups -", "VIP");
        });
        Allure.step("Assert: Mọi dòng có Client groups chứa \"VIP\"", () -> {
            List<String> groups = page.getColumnValues("Client groups");
            Assert.assertFalse(groups.isEmpty(), "Phải có ít nhất 1 client thuộc nhóm VIP");
            Assert.assertTrue(groups.stream().allMatch(g -> g.contains("VIP")), "Mọi dòng phải thuộc nhóm VIP, thực tế: " + groups);
        });
    }
}
