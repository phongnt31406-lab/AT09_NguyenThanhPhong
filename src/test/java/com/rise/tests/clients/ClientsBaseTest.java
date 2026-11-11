package com.rise.tests.clients;

import com.rise.base.AdminTest;
import com.rise.pages.clients.ClientDetailPage;
import com.rise.pages.clients.ClientsPage;
import com.rise.utils.DataGenerator;
import io.qameta.allure.Allure;

/**
 * Hàm Arrange dùng chung cho module Clients & Contacts — mỗi test tự tạo dữ liệu của chính nó (độc lập dữ liệu).
 */
public abstract class ClientsBaseTest extends AdminTest {

    protected static final String OWNER_DEFAULT = "Admin Example";

    protected ClientsPage clientsPage() {
        return new ClientsPage(driver());
    }

    /** Tên client unique + traceable, VD AT09_Client_20261111093015_482. */
    protected String newClientName() {
        return DataGenerator.uniqueName("AT09_Client");
    }

    /** Tạo client chỉ với Company name rồi ở lại tab Clients. */
    protected ClientsPage createClient(String name) {
        return createClient(name, "", "");
    }

    /** Tạo client với Phone / City (bỏ trống thì không nhập) rồi ở lại tab Clients. */
    protected ClientsPage createClient(String name, String phone, String city) {
        ClientsPage page = clientsPage();
        Allure.step("Tạo client \"" + name + "\"", () -> {
            page.open().openClientsTab();
            var modal = page.clickAddClient().enterCompanyName(name);
            if (!phone.isEmpty()) {
                modal.enterPhone(phone);
            }
            if (!city.isEmpty()) {
                modal.enterCity(city);
            }
            modal.save();
        });
        return page;
    }

    /** Tạo client rồi mở trang chi tiết của client đó (tab Contacts). */
    protected ClientDetailPage createClientAndOpenDetail(String name) {
        ClientsPage page = createClient(name);
        page.search(name);
        return page.openClientDetail(name);
    }

    /** Thêm contact (bỏ tick gửi email login) và chờ contact hiển thị trong bảng. */
    protected void addContact(ClientDetailPage detail, String firstName, String lastName, String email, String password) {
        Allure.step("Thêm contact \"" + firstName + " " + lastName + "\"", () -> {
            detail.clickAddContact()
                    .enterName(firstName, lastName)
                    .enterEmail(email)
                    .enterPassword(password)
                    .setEmailLoginDetails(false)
                    .save();
            detail.waitContactRowByEmail(email);
        });
    }
}
