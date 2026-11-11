# Requirements — Clients & Contacts

| Thông tin | Nội dung |
|---|---|
| Module | Clients & Contacts |
| Prefix | CLIENT |
| URL | https://rise.anhtester.com/clients |
| Nguồn | UI recon trên hệ thống thật (Playwright + Chrome, headless) — tài khoản admin@example.com |
| Ngày khảo sát | 2026-11-08 |
| Tổng số REQ | 37 |
| Dải mã đã dùng | REQ-CLIENT-01 → REQ-CLIENT-37 · AMB-01 → AMB-05 · RISK-01 → RISK-06 |
| Mã kế tiếp | Đợt phân tích sau bắt đầu từ REQ-CLIENT-38 · AMB-06 · RISK-07 — KHÔNG đánh lại từ 01 |

## Tổng quan

Module quản lý khách hàng (Client) của RISE CRM: xem danh sách, thêm, sửa, xoá client và thêm người liên hệ (Contact) cho từng client. Client là dữ liệu gốc để tạo Project.

**Trong phạm vi:**
- Danh sách Clients (tab Clients): bảng, tìm kiếm, phân trang
- Thêm / Sửa / Xoá client
- Trang chi tiết client — tab Contacts
- Thêm Contact cho client (modal Add contact, Add multiple contacts)

**Ngoài phạm vi:**
- Import clients, Manage labels, Excel/Print
- Các tab khác của Client detail (Invoices, Payments...)
- Sửa/Xoá contact, Send invitation
- Việc gửi email login details cho contact — site demo không gửi được email, chỉ giả định có chức năng (AMB-04)
- Độ dài tối đa (max length) các trường text — PO chốt bỏ qua (AMB-05)
- Phân quyền các role khác Admin (AMB-03)

## Bản đồ tài liệu

| File | Story | REQ bao phủ |
|---|---|---|
| [stories/story_01_client_crud.md](stories/story_01_client_crud.md) | STORY-CLIENT-01 | REQ-CLIENT-01 → REQ-CLIENT-23 |
| [stories/story_02_contacts.md](stories/story_02_contacts.md) | STORY-CLIENT-02 | REQ-CLIENT-24 → REQ-CLIENT-37 |

## Phân rã Epic / Story

| Story ID | Tên Story | REQ bao phủ | Số REQ | AMB / RISK liên quan | Ghi chú phạm vi |
|---|---|---|---|---|---|
| STORY-CLIENT-01 | Quản lý Client (danh sách · thêm · sửa · xoá) | REQ-CLIENT-01 → REQ-CLIENT-23 | 23 | AMB-01, 02, 03 · RISK-01, 02, 03 | Danh sách, thêm, sửa, xoá, chi tiết client |
| STORY-CLIENT-02 | Contacts của Client | REQ-CLIENT-24 → REQ-CLIENT-37 | 14 | AMB-04, 05 · RISK-04, 05, 06 | Tab Contacts + modal Add contact |

**Tổng: 2 Story / 37 REQ — mọi REQ thuộc đúng một Story, không mồ côi, không trùng.**

**Hạng mục cấp Epic (không gán Story):** Ma trận phân quyền, ma trận trạng thái, Ambiguity/Risk, NFR, Danh mục Evidence — vì cắt ngang cả 2 Story.

**Thứ tự triển khai đề xuất:** STORY-CLIENT-01 (tạo dữ liệu gốc) → STORY-CLIENT-02 (cần client đã tồn tại). Không Story nào BLOCKED; AMB-01/02 đã được PO chốt là business rule (REQ-CLIENT-09, REQ-CLIENT-22).

## Ma trận phân quyền

Out-of-scope (PO 2026-11-08) — automation chỉ chạy với 1 role Admin được cung cấp (admin@example.com). Admin thực hiện được: xem danh sách, Add, Edit, Delete client, Add contact.

## Ma trận trạng thái

Không áp dụng — Client không có status flow.

## Điểm mơ hồ & Rủi ro

### Ambiguities

| Mã | Câu hỏi | Nguy cơ | Mức độ | Assumption tạm | Trạng thái | Kết luận |
|---|---|---|---|---|---|---|
| AMB-01 | Company name toàn khoảng trắng nên được trim và báo "This field is required." hay chấp nhận? Hiện server trả "Oops, something went wrong!". | Lỗi 500 lộ ra người dùng; TC không biết assert theo hướng nào. | 🔴 | Coi là BUG: kỳ vọng hiển thị lỗi required, không tạo client. TC gắn @KnownIssue. | ✅ Đã chốt | Business rule: hệ thống không tự trim khoảng trắng, server từ chối với "Oops, something went wrong!". TC assert đúng thông báo này, bỏ @KnownIssue. |
| AMB-02 | Có được phép xoá client đang có project không? Hiện xoá được và project mồ côi vẫn mang tên client. | Mất toàn vẹn dữ liệu, báo cáo sai. | 🔴 | Ghi nhận theo hành vi hiện tại (cho xoá); TC kiểm chứng project còn tồn tại và gắn @KnownIssue. | ✅ Đã chốt | Được phép — business rule. TC kiểm tra xoá thành công và project vẫn còn, bỏ @KnownIssue. |
| AMB-03 | Role nào ngoài Admin được thêm/sửa/xoá client và contact? | Thiếu TC phân quyền. | 🟡 | Chỉ test Admin; cần tài khoản role thấp để bổ sung. | ⛔ Out-of-scope | Chỉ chạy với role Admin được cung cấp. |
| AMB-04 | Checkbox "Email login details to this user" mặc định tick → hệ thống gửi email thật cho contact? | Automation gửi mail tới địa chỉ giả/thật hàng loạt. | 🟡 | Automation bỏ tick checkbox này; không kiểm chứng việc gửi mail. | ✅ Đã chốt | Site demo không gửi được email — chỉ giả định có chức năng, không kiểm tra email. TC tạo contact vẫn bỏ tick checkbox để việc lưu không phụ thuộc mail server của site demo; TC "modal mặc định" chỉ kiểm tra checkbox đang được tick. |
| AMB-05 | Độ dài tối đa của các trường text (Company name, First/Last name, Phone...)? | Không có biên cho BVA. | 🟡 | Không giới hạn ở client; test với chuỗi 255 ký tự để quan sát. | ⛔ Out-of-scope | Bỏ qua các case test max length. |

### Risks

| Mã | Rủi ro | Mô tả | Mitigation |
|---|---|---|---|
| RISK-01 | Dữ liệu demo dùng chung | Bảng có ~340 client do nhiều người tạo, tên trùng (VD nhiều "TCL"). | Độc lập dữ liệu (PO 2026-11-08): mỗi TC chỉ thao tác trên dữ liệu do chính nó tạo ở bước Arrange — VD TC Edit/Delete phải Add client trước rồi sửa/xoá đúng client đó. Không dùng, không sửa dữ liệu có sẵn (DEV D, TCL…). Tên unique AT09_<Loại>_<ts>, <ts> = yyyyMMddHHmmss lúc chạy. |
| RISK-02 | Modal AJAX + animation | Modal hiện dấu ✓ rồi mới đóng; DataTable reload bất đồng bộ. | Chờ modal invisible và chờ bảng có dòng khớp search. |
| RISK-03 | Select2 thay cho select gốc | Owner, Client groups, Labels là Select2 — không dùng Select của Selenium được; ESC có thể đóng cả modal. | Click container .select2-choice → gõ → click li trong .select2-drop-active; không dùng ESC. |
| RISK-04 | Ô Password readonly tới khi focus | Ô có thuộc tính readonly (chống trình duyệt tự điền); chỉ khi được click/focus mới gỡ readonly. Selenium sendKeys vào ô readonly KHÔNG báo lỗi nhưng không nhập được gì → contact lưu với password sinh sẵn, test pass giả hoặc fail khó hiểu. | Click vào ô Password trước (giống người dùng), chờ readonly biến mất, rồi clear + sendKeys; sau đó đọc lại value để chắc đã nhập đúng. |
| RISK-05 | Email trùng giữa các lần chạy | Contact email phải duy nhất toàn hệ thống. | Sinh email at09_contact_<ts>@example.com. |
| RISK-06 | Checkbox gửi email mặc định tick | Nếu giữ tick, mỗi lần Save hệ thống sẽ cố gửi email; site demo không gửi được email nên việc lưu có thể chậm hoặc báo lỗi không liên quan tới chức năng contact. | Bỏ tick "Email login details to this user" trước khi Save (AMB-04); không assert việc gửi email. |

## Yêu cầu phi chức năng (quan sát được)

- Bảng phân trang phía server (DataTable AJAX).
- Thời gian lưu client ~1s trên site demo (quan sát).

## Danh mục Evidence

Thư mục: `evidence/` (chụp full-page, viewport 1920×1080, headless Chrome).

| Tệp | Màn hình / trạng thái | REQ làm bằng chứng |
|---|---|---|
| clients_overview_tab_fullpage.png | Trang Clients — tab Overview | REQ-CLIENT-01 |
| clients_list_tab_fullpage.png | Tab Clients — bảng mặc định | REQ-CLIENT-02, 04, 05 |
| clients_list_search_result_fullpage.png | Search theo tên | REQ-CLIENT-03, 13 |
| client_add_modal_default_fullpage.png | Modal Add client mặc định | REQ-CLIENT-06, 10, 11 |
| client_add_modal_type_person_fullpage.png | Type = Person | REQ-CLIENT-07 |
| client_add_modal_owner_dropdown_open_fullpage.png | Dropdown Owner mở | REQ-CLIENT-10 |
| client_add_modal_groups_dropdown_open_fullpage.png | Dropdown Client groups mở | REQ-CLIENT-12 |
| client_add_modal_submit_empty_fullpage.png | Save khi trống | REQ-CLIENT-08 |
| client_add_modal_whitespace_name_fullpage.png | Name toàn khoảng trắng | REQ-CLIENT-09 |
| client_add_modal_filled_fullpage.png | Form đã điền đủ | REQ-CLIENT-11, 13 |
| client_add_success_modal_check_fullpage.png | Lưu thành công — dấu ✓ trong modal | REQ-CLIENT-13 |
| client_after_save_overview_fullpage.png | Trang sau khi lưu (tab Overview) | REQ-CLIENT-13 |
| client_add_save_and_continue_result_fullpage.png | Sau Save & continue → Add multiple contacts | REQ-CLIENT-14 |
| client_edit_modal_default_fullpage.png | Modal Edit client | REQ-CLIENT-16 |
| client_edit_saved_list_fullpage.png | Bảng sau khi sửa | REQ-CLIENT-18 |
| client_delete_confirm_dialog_fullpage.png | Dialog xác nhận xoá | REQ-CLIENT-19, 20 |
| client_delete_success_toast_fullpage.png | Toast xoá thành công | REQ-CLIENT-21 |
| client_delete_with_project_result_fullpage.png | Xoá client đang có project | REQ-CLIENT-22 |
| client_view_default_fullpage.png | Client detail — tab Contacts rỗng | REQ-CLIENT-23, 24 |
| contact_add_modal_default_fullpage.png | Modal Add contact mặc định | REQ-CLIENT-25, 32, 34, 35 |
| contact_add_modal_submit_empty_fullpage.png | Save khi trống | REQ-CLIENT-26, 27, 28 |
| contact_add_modal_invalid_email_fullpage.png | Email sai định dạng | REQ-CLIENT-29 |
| contact_add_modal_duplicate_email_fullpage.png | Email đã tồn tại | REQ-CLIENT-30 |
| contact_add_modal_password_short_fullpage.png | Password 5 ký tự | REQ-CLIENT-31 |
| contact_add_modal_access_everything_checked_fullpage.png | Tick Can access everything | REQ-CLIENT-34 |
| contact_add_modal_filled_fullpage.png | Form contact đã điền | REQ-CLIENT-36 |
| contact_add_success_fullpage.png | Contact đã lưu — Primary contact | REQ-CLIENT-36, 37 |

## Nhật ký thay đổi

| Ngày | Nguồn | REQ ảnh hưởng | Loại | Tóm tắt thay đổi | TC cần xử lý |
|---|---|---|---|---|---|
| 2026-11-08 | UI recon | REQ-CLIENT-01 → REQ-CLIENT-37 | 🟢 Thêm | Khởi tạo tài liệu từ khảo sát UI thực tế (bản 1.0) | — (viết TC mới) |
| 2026-11-08 | Phản hồi PO/BA | REQ-CLIENT-09, REQ-CLIENT-22 | 🟡 Sửa | AMB-01, AMB-02 chốt là business rule; AMB-03, AMB-05 out-of-scope; AMB-04 không kiểm tra email. Thêm quy tắc độc lập dữ liệu (RISK-01), giải thích chi tiết RISK-04, thêm RISK-06. (bản 1.1) | Sửa TC Company name khoảng trắng, Xoá client có project; bỏ TC 255 ký tự; mọi TC tự tạo dữ liệu |
