# STORY-CLIENT-01 — Quản lý Client (danh sách · thêm · sửa · xoá)

← [Về index requirements_clients.md](../requirements_clients.md)

| Thông tin | Nội dung |
|---|---|
| Story | STORY-CLIENT-01 |
| REQ bao phủ | REQ-CLIENT-01 → REQ-CLIENT-23 |
| AMB / RISK tham chiếu | AMB-01, 02, 03 · RISK-01, 02, 03 (xem index) |

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-CLIENT-01 | Bố cục trang Clients | Trang /clients có 3 tab Overview / Clients / Contacts và 3 nút Manage labels, Import clients, Add client. | Mở /clients → tab mặc định là Overview; thấy đủ 3 tab + 3 nút. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-02 | Bảng danh sách client | Tab Clients hiển thị bảng #client-table. | Cột theo thứ tự: ID, Name, Primary contact, Phone, Client groups, Labels, Projects, Total invoiced, Payment Received, Due, (thao tác Edit/Delete). | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-03 | Tìm kiếm client | Ô Search lọc bảng theo từ khoá. | Nhập tên client → chỉ còn dòng khớp; từ khoá không khớp → "No record found." | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-04 | Phân trang | Bảng có chọn số dòng/trang và điều hướng trang. | Page size: 10 (mặc định), 25, 50, 100; thông tin dạng "1-10 / 340". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-05 | Bộ lọc danh sách | Có các bộ lọc Quick filters, Owner, Client groups, Label kèm nút áp dụng/xoá lọc. | Hiển thị 4 dropdown lọc (chỉ quan sát, chưa kiểm chứng kết quả lọc). | 🟢 Active | — | UI thực tế |
| REQ-CLIENT-06 | Mở modal Add client | Click "Add client" mở modal tiêu đề "Add client". | Modal có nút Close, "Save & continue", "Save"; Type mặc định Organization; Owner mặc định user đang đăng nhập. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-07 | Loại client | Radio Type: Organization (mặc định) / Person. | Chọn Person → nhãn "Company name" đổi thành "Name"; các trường khác giữ nguyên. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-08 | Company name bắt buộc | Bỏ trống Company name khi Save. | Hiển thị "This field is required." dưới trường; modal không đóng. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-09 | Company name chỉ có khoảng trắng (business rule) | Hệ thống không tự trim khoảng trắng; Company name toàn khoảng trắng bị server từ chối. | Hiện alert đỏ trong modal: "Oops, something went wrong!"; modal không đóng; không tạo client. PO chốt đây là validation theo business rule (AMB-01). | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-CLIENT-10 | Owner | Dropdown Owner (bắt buộc) mặc định là user đăng nhập. | Mặc định "Admin Example" (value=2); options: Anh Tester Admin, Admin Example, Project Manager. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-11 | Trường thông tin tuỳ chọn | Address (textarea), City, State, Zip, Country, Phone, Website, VAT Number, GST Number là tuỳ chọn. | DOM không có rule required/format/maxlength cho các trường này; bỏ trống vẫn lưu được. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-12 | Client groups & Labels | Hai trường multi-select tuỳ chọn. | Client groups có options: VIP, Gold, Silver; Labels chọn từ danh sách nhãn. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-13 | Lưu client thành công (Save) | Click Save với dữ liệu hợp lệ. | POST /clients/save trả success; modal hiện dấu ✓ rồi đóng; client mới có ID mới, xuất hiện trong bảng với đúng Name/Phone. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-14 | Save & continue | Lưu client và chuyển ngay sang thêm contact. | Client được lưu; modal chuyển thành "Add multiple contacts" với nút "Save & add more". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-15 | Đóng modal không lưu | Nút Close, nút X, phím ESC đóng modal. | Modal đóng, không tạo bản ghi. (ESC: đã quan sát modal đóng khi nhấn ESC.) | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-16 | Mở modal Edit client | Icon Edit (title "Edit client") trên mỗi dòng. | Modal "Edit client" điền sẵn dữ liệu hiện tại; chỉ có nút Close và Save (không có Save & continue). | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-17 | Validate khi sửa | Xoá trắng Company name rồi Save. | "This field is required."; không lưu. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-18 | Lưu thay đổi client | Sửa Name/Phone/City rồi Save. | Bảng cập nhật ngay giá trị mới (VD Name có hậu tố "_Edited", Phone 0987654321). | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-19 | Xác nhận xoá | Icon Delete (title "Delete client") mở dialog xác nhận. | Dialog tiêu đề "Delete?", nội dung "Are you sure? You won't be able to undo this action!", nút "Delete" và "Cancel". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-20 | Huỷ xoá | Click Cancel trong dialog. | Dialog đóng, client vẫn còn trong bảng. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-21 | Xoá client | Click Delete trong dialog. | POST /clients/delete success; toast "The record has been deleted."; dòng biến mất (search lại → "No record found."). | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-22 | Xoá client đang có project (business rule) | Được phép xoá client đã có project, không cảnh báo. | Client bị xoá; project liên quan vẫn tồn tại và vẫn hiển thị tên client cũ. PO chốt là business rule (AMB-02). | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-CLIENT-23 | Trang chi tiết client | Click tên client → /clients/view/{id}. | Tiêu đề "Client details - <tên>"; 4 thẻ Projects / Total invoiced / Payments / Due; tab mặc định Contacts; 18 tab. | 🟢 Active | — | Kiểm chứng thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

| Field (Label) | Loại UI | Required | Ràng buộc (min/max/format/default) | REQ liên quan | Ghi chú (locator) |
|---|---|---|---|---|---|
| Type | Radio (Organization / Person) | Có | Mặc định Organization | REQ-CLIENT-07 | name=account_type |
| Company name / Name | Text | Có | data-rule-required; không có maxlength | REQ-CLIENT-08, 09 | id=company_name |
| Owner | Select2 (single) | Có | Mặc định user đăng nhập (value 2) | REQ-CLIENT-10 | id=created_by |
| Address | Textarea | Không | — | REQ-CLIENT-11 | id=address |
| City / State / Zip / Country | Text | Không | Không validate | REQ-CLIENT-11 | id=city, state, zip, country |
| Phone | Text | Không | Không validate định dạng số | REQ-CLIENT-11 | id=phone |
| Website | Text | Không | Không validate URL | REQ-CLIENT-11 | id=website |
| VAT Number / GST Number | Text | Không | — | REQ-CLIENT-11 | id=vat_number, gst_number |
| Client groups | Select2 multi | Không | VIP, Gold, Silver | REQ-CLIENT-12 | id=group_ids |
| Labels | Select2 multi | Không | — | REQ-CLIENT-12 | id=client_labels |

## Business Rules & Validation Messages

| REQ ID | Rule / Trigger | Thông báo mong đợi (nguyên văn) |
|---|---|---|
| REQ-CLIENT-08 | Company name trống (Add) | This field is required. |
| REQ-CLIENT-09 | Company name toàn khoảng trắng | Oops, something went wrong! |
| REQ-CLIENT-17 | Company name trống (Edit) | This field is required. |
| REQ-CLIENT-19 | Click Delete | Delete? — Are you sure? You won't be able to undo this action! |
| REQ-CLIENT-21 | Xoá thành công | The record has been deleted. |
| REQ-CLIENT-03 | Tìm không thấy | No record found. |

## Luồng xử lý (User Flows)

**Thêm client**

1. Vào Clients → tab Clients
2. Click "Add client"
3. Chọn Type, nhập Company name (+ thông tin tuỳ chọn)
4. Click Save
5. Tìm client vừa tạo bằng ô Search → thấy 1 dòng

**Sửa client**

1. (Arrange) Add client AT09_Client_<ts> — chỉ sửa dữ liệu do chính test tạo
2. Search client đó
3. Click icon Edit
4. Sửa trường
5. Save → bảng cập nhật

**Xoá client**

1. (Arrange) Add client AT09_Del_<ts> — chỉ xoá dữ liệu do chính test tạo
2. Search client đó
3. Click icon Delete
4. Dialog "Delete?" → Delete
5. Toast "The record has been deleted." → bảng "No record found."

