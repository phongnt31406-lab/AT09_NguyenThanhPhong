# Requirements — Login (Admin)

| Thông tin | Nội dung |
|---|---|
| Module | Login (Admin) |
| Prefix | LOGIN |
| URL | https://rise.anhtester.com/signin |
| Nguồn | UI recon trên hệ thống thật (Playwright + Chrome, headless) — tài khoản admin@example.com |
| Ngày khảo sát | 2026-11-08 |
| Tổng số REQ | 13 |
| Dải mã đã dùng | REQ-LOGIN-01 → REQ-LOGIN-13 · AMB-01 → AMB-03 · RISK-01 → RISK-02 |
| Mã kế tiếp | Đợt phân tích sau bắt đầu từ REQ-LOGIN-14 · AMB-04 · RISK-03 — KHÔNG đánh lại từ 01 |

## Tổng quan

Trang đăng nhập của RISE CRM. Người dùng nhập Email + Password để truy cập khu vực quản trị. Đây là cổng vào bắt buộc cho mọi luồng Clients / Projects / Tasks.

**Trong phạm vi:**
- Form đăng nhập (Email, Password, Sign in)
- Validation phía client và thông báo lỗi phía server
- Chuyển hướng sau đăng nhập và khi truy cập trang nội bộ lúc chưa đăng nhập

**Ngoài phạm vi:**
- Luồng Forgot password, Sign up (chỉ ghi nhận link)
- Khoá tài khoản / CAPTCHA sau nhiều lần đăng nhập sai — PO chốt out-of-scope (AMB-01)
- Quy tắc độ dài / độ phức tạp mật khẩu ở màn hình đăng nhập — PO chốt out-of-scope (AMB-02)
- Thời gian hết hạn session — out-of-scope vì mỗi TC automation chạy độc lập, tự đăng nhập (AMB-03)
- Phân quyền các role khác Admin — chỉ chạy với 1 role được cung cấp
- Đăng xuất

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-LOGIN-01 | Hiển thị form đăng nhập | Trang /signin hiển thị form #signin-form gồm Email (placeholder "Email", autofocus), Password (type=password, placeholder "Password"), nút "Sign in", link "Forgot password?" và "Sign up". | Mở /signin → thấy đủ 5 thành phần; con trỏ nằm sẵn ở ô Email. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-02 | Đăng nhập thành công | Nhập đúng email + password của Admin → vào Dashboard. | admin@example.com / 123456 → chuyển tới /dashboard/index/1, title "Dashboard \| RISE CRM \| Anh Tester Demo", header hiển thị "Admin Example". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-03 | Email bắt buộc | Bỏ trống Email khi submit → chặn ở client, không gửi request. | Hiển thị "This field is required." ngay dưới ô Email; URL vẫn là /signin. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-04 | Password bắt buộc | Bỏ trống Password khi submit → chặn ở client. | Hiển thị "This field is required." ngay dưới ô Password. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-05 | Email đúng định dạng | Email sai định dạng (VD "admin@") hoặc có khoảng trắng đầu/cuối bị chặn ở client. | Hiển thị "Please enter a valid email address." dưới ô Email; không gửi request. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-06 | Email chưa đăng ký | Email đúng định dạng nhưng không tồn tại → server từ chối. | Ở lại /signin, hiển thị alert đỏ "Authentication failed!" phía trên form. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-07 | Sai mật khẩu | Email đúng, password sai → server từ chối với cùng thông báo (không tiết lộ trường nào sai). | Hiển thị "Authentication failed!" — giống hệt trường hợp email chưa đăng ký. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-08 | Xoá dữ liệu nhập sau khi đăng nhập thất bại | Sau lỗi "Authentication failed!" trang được tải lại, ô Email và Password đều trống. | Sau REQ-LOGIN-06/07: Email = "", Password = "". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-09 | Email không phân biệt hoa/thường | Email viết hoa vẫn đăng nhập được. | ADMIN@EXAMPLE.COM / 123456 → vào Dashboard. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-10 | Che mật khẩu | Ô Password hiển thị dạng ký tự che. | Thuộc tính type="password"; nhập "123456" hiển thị "••••••". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-11 | Bảo vệ trang nội bộ | Chưa đăng nhập mà mở URL nội bộ → chuyển về trang đăng nhập kèm tham số redirect; đăng nhập xong quay lại đúng trang. | /clients → /signin?redirect=https://rise.anhtester.com/clients; đăng nhập → quay về URL ban đầu. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-12 | Submit bằng phím Enter | Nhấn Enter trong ô Password tương đương click "Sign in". | Enter → đăng nhập thành công. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-LOGIN-13 | Link phụ | Link "Forgot password?" trỏ tới /signin/request_reset_password; "Sign up" trỏ tới /signup. | Đọc href từ DOM (chưa click). | 🟢 Active | — | UI thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

| Field (Label) | Loại UI | Required | Ràng buộc (min/max/format/default) | REQ liên quan | Ghi chú (locator) |
|---|---|---|---|---|---|
| Email | Text input (type=text) | Có | data-rule-required, data-rule-email; không có maxlength; autofocus | REQ-LOGIN-03, 05, 06, 09 | id=email, name=email |
| Password | Password input | Có | data-rule-required; không có minlength/maxlength ở client | REQ-LOGIN-04, 07, 10 | id=password, name=password |
| Sign in | Button submit | — | POST /signin/authenticate → 302 | REQ-LOGIN-02 | form#signin-form button[type=submit] |

## Business Rules & Validation Messages

| REQ ID | Rule / Trigger | Thông báo mong đợi (nguyên văn) |
|---|---|---|
| REQ-LOGIN-03 | Email trống | This field is required. |
| REQ-LOGIN-04 | Password trống | This field is required. |
| REQ-LOGIN-05 | Email sai định dạng / có khoảng trắng đầu-cuối | Please enter a valid email address. |
| REQ-LOGIN-06 | Email chưa đăng ký | Authentication failed! |
| REQ-LOGIN-07 | Sai mật khẩu | Authentication failed! |

## Luồng xử lý (User Flows)

**Đăng nhập Admin**

1. Mở https://rise.anhtester.com/signin
2. Nhập Email
3. Nhập Password
4. Click "Sign in" (hoặc Enter)
5. Hệ thống xác thực → chuyển tới Dashboard (hoặc URL trong ?redirect=)

## Ma trận phân quyền

Out-of-scope (PO 2026-11-08) — automation chỉ chạy với 1 role Admin được cung cấp (admin@example.com).

## Ma trận trạng thái

Không áp dụng.

## Điểm mơ hồ & Rủi ro

### Ambiguities

| Mã | Câu hỏi | Nguy cơ | Mức độ | Assumption tạm | Trạng thái | Kết luận |
|---|---|---|---|---|---|---|
| AMB-01 | Có khoá tài khoản / CAPTCHA sau N lần đăng nhập sai không? | Automation chạy lặp nhiều lần sai có thể làm khoá tài khoản admin dùng chung. | 🟡 | Không có khoá (đã thử 3 lần sai liên tiếp vẫn đăng nhập được). | ⛔ Out-of-scope | Không viết TC khoá tài khoản / CAPTCHA. |
| AMB-02 | Password có quy tắc độ dài/độ phức tạp ở màn hình đăng nhập không? | Không biết biên để thiết kế BVA. | 🟢 | Không có — chỉ kiểm tra required. | ⛔ Out-of-scope | Không viết TC độ dài / độ phức tạp mật khẩu. |
| AMB-03 | Thời gian hết hạn session là bao lâu? | Test dài có thể bị đẩy về /signin giữa chừng. | 🟡 | Không xác định; test E2E thực hiện trong 1 phiên liên tục. | ⛔ Out-of-scope | PO 2026-11-10: out-of-scope vì mỗi TC automation chạy độc lập (tự đăng nhập ở đầu test). Bỏ TC "Session hết hạn khi đang điền form Add client". |

### Risks

| Mã | Rủi ro | Mô tả | Mitigation |
|---|---|---|---|
| RISK-01 | Tài khoản dùng chung trên site demo | Nhiều người cùng dùng admin@example.com; mật khẩu có thể bị đổi. | Đọc credential từ config; kiểm tra đăng nhập ở @BeforeSuite, fail sớm nếu sai. |
| RISK-02 | Thông báo lỗi là alert sau reload | Alert "Authentication failed!" xuất hiện sau redirect 302 → dễ flaky nếu assert quá sớm. | Explicit wait cho .alert-danger visible thay vì sleep. |

## Yêu cầu phi chức năng (quan sát được)

- Trang hiển thị tốt ở viewport 1920×1080 (đã chụp evidence).
- Form submit dạng POST + redirect 302 (không phải AJAX).

## Danh mục Evidence

Thư mục: `evidence/` (chụp full-page, viewport 1920×1080, headless Chrome).

| Tệp | Màn hình / trạng thái | REQ làm bằng chứng |
|---|---|---|
| login_default_fullpage.png | Form đăng nhập — mặc định | REQ-LOGIN-01, 13 |
| login_submit_empty_both_fullpage.png | Submit khi trống cả 2 trường | REQ-LOGIN-03, 04 |
| login_submit_empty_email_fullpage.png | Submit khi trống Email | REQ-LOGIN-03 |
| login_submit_empty_password_fullpage.png | Submit khi trống Password | REQ-LOGIN-04 |
| login_submit_email_invalid_format_fullpage.png | Email "admin@" | REQ-LOGIN-05 |
| login_submit_email_not_registered_fullpage.png | Email chưa đăng ký | REQ-LOGIN-06, 08 |
| login_submit_wrong_password_fullpage.png | Sai mật khẩu | REQ-LOGIN-07, 08 |
| login_success_dashboard_fullpage.png | Dashboard sau đăng nhập (viewport) | REQ-LOGIN-02 |
| login_unauth_redirect_fullpage.png | Truy cập /tasks/all_tasks khi chưa đăng nhập | REQ-LOGIN-11 |

## Nhật ký thay đổi

| Ngày | Nguồn | REQ ảnh hưởng | Loại | Tóm tắt thay đổi | TC cần xử lý |
|---|---|---|---|---|---|
| 2026-11-08 | UI recon | REQ-LOGIN-01 → REQ-LOGIN-13 | 🟢 Thêm | Khởi tạo tài liệu từ khảo sát UI thực tế (bản 1.0) | — (viết TC mới) |
| 2026-11-08 | Phản hồi PO/BA | — (chỉ AMB) | 🟡 Sửa | AMB-01, AMB-02 → out-of-scope; phân quyền role khác → out-of-scope; AMB-03 (session) còn chờ trả lời. (bản 1.1) | Không đổi TC |
| 2026-11-10 | Phản hồi PO/BA lần 2 | — | 🟡 Sửa | AMB-03 (session) → out-of-scope vì mỗi TC automation chạy độc lập. (bản 1.2) | Bỏ TC Session hết hạn (module Clients) |
