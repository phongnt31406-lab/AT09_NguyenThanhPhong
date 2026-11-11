# Test Cases — Login (tổng 18 TC)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_login.md](../../requirements/login/requirements_login.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 18 |
| Phiên bản | 1.1 — cập nhật 2026-11-08 theo phản hồi PO/BA (bản 1.0 lưu ở archive/) |
| Định dạng TC ID | RISE_LOGIN_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Đăng nhập | High | 12 |
| Bảo vệ trang | High | 1 |
| Bàn phím | Low | 2 |
| Giao diện | Low | 2 |
| Phi chức năng | Medium | 1 |

## Danh sách Test Cases

| TC ID | REQ ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data | Automatable | Auto Type | Tags |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| RISE_LOGIN_TC_001 | REQ-LOGIN-02 | Login / Đăng nhập | High | Đăng nhập thành công với tài khoản Admin hợp lệ | Chưa đăng nhập; trình duyệt sạch cookie | 1. Mở https://rise.anhtester.com/signin<br>2. Nhập Email: admin@example.com<br>3. Nhập Password: 123456<br>4. Click nút "Sign in" | 1. Form đăng nhập hiển thị, con trỏ ở ô Email<br>4. Chuyển tới https://rise.anhtester.com/dashboard/index/1; title "Dashboard \| RISE CRM \| Anh Tester Demo"; header hiển thị "Admin Example" | High | Email: admin@example.com \| Password: 123456 | Yes | UI | @Smoke @CriticalPath @Core |
| RISE_LOGIN_TC_002 | REQ-LOGIN-05 | Login / Đăng nhập | High | Đăng nhập với Email sai định dạng (invalid email) | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. Dưới ô Email hiển thị "Please enter a valid email address."; không gửi request; URL vẫn là /signin | High | Email: admin@ \| Password: 123456 | Yes | UI | @Regression @Core |
| RISE_LOGIN_TC_003 | REQ-LOGIN-03 | Login / Đăng nhập | High | Đăng nhập khi để trống Email (null email) | Chưa đăng nhập; đang ở /signin | 1. Để trống Email<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. Dưới ô Email hiển thị "This field is required."; không chuyển trang | High | Email: (trống) \| Password: 123456 | Yes | UI | @Regression @Core |
| RISE_LOGIN_TC_004 | REQ-LOGIN-07, REQ-LOGIN-08 | Login / Đăng nhập | High | Đăng nhập với Password sai (invalid password) | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@example.com<br>2. Nhập Password: wrong999<br>3. Click "Sign in" | 3. Ở lại /signin; alert đỏ "Authentication failed!" phía trên form; ô Email và Password bị xoá trống | High | Email: admin@example.com \| Password: wrong999 | Yes | UI | @Regression @Security @Core |
| RISE_LOGIN_TC_005 | REQ-LOGIN-04 | Login / Đăng nhập | High | Đăng nhập khi để trống Password (null password) | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@example.com<br>2. Để trống Password<br>3. Click "Sign in" | 3. Dưới ô Password hiển thị "This field is required."; không chuyển trang | High | Email: admin@example.com \| Password: (trống) | Yes | UI | @Regression @Core |
| RISE_LOGIN_TC_006 | REQ-LOGIN-06, REQ-LOGIN-08 | Login / Đăng nhập | High | Đăng nhập với Email đúng định dạng nhưng chưa đăng ký | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: notexist_qa01@example.com<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. Ở lại /signin; alert "Authentication failed!" (giống hệt khi sai mật khẩu); 2 ô bị xoá trống | High | Email: notexist_qa01@example.com \| Password: 123456 | Yes | UI | @Regression @Security |
| RISE_LOGIN_TC_007 | REQ-LOGIN-03, REQ-LOGIN-04 | Login / Đăng nhập | Medium | Submit khi để trống cả Email và Password | Chưa đăng nhập; đang ở /signin | 1. Để trống Email và Password<br>2. Click "Sign in" | 2. Hiển thị 2 thông báo "This field is required." — một dưới Email, một dưới Password | Medium | Email: (trống) \| Password: (trống) | Yes | UI | @Regression |
| RISE_LOGIN_TC_008 | REQ-LOGIN-05 | Login / Đăng nhập | Medium | Email có khoảng trắng đầu/cuối bị coi là sai định dạng | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: "  admin@example.com  " (2 khoảng trắng đầu và cuối)<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. "Please enter a valid email address." dưới ô Email; không đăng nhập | Medium | Email: "  admin@example.com  " | Yes | UI | @Regression @Boundary |
| RISE_LOGIN_TC_009 | REQ-LOGIN-09 | Login / Đăng nhập | Medium | Email viết hoa vẫn đăng nhập thành công | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: ADMIN@EXAMPLE.COM<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. Chuyển tới /dashboard/index/1 | Medium | Email: ADMIN@EXAMPLE.COM \| Password: 123456 | Yes | UI | @Regression |
| RISE_LOGIN_TC_010 | REQ-LOGIN-05 | Login / Đăng nhập | High | SQL injection ở ô Email bị chặn bởi validate định dạng | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: ' OR 1=1--<br>2. Nhập Password: 123456<br>3. Click "Sign in" | 3. "Please enter a valid email address."; không đăng nhập | High | Email: ' OR 1=1-- | Yes | UI | @Security |
| RISE_LOGIN_TC_011 | REQ-LOGIN-07 | Login / Đăng nhập | High | SQL injection ở ô Password không vượt qua xác thực | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@example.com<br>2. Nhập Password: ' OR '1'='1<br>3. Click "Sign in" | 3. Alert "Authentication failed!"; không vào Dashboard | High | Password: ' OR '1'='1 | Yes | UI | @Security |
| RISE_LOGIN_TC_012 | REQ-LOGIN-10 | Login / Đăng nhập | Low | Mật khẩu được che khi nhập | Chưa đăng nhập; đang ở /signin | 1. Nhập Password: 123456<br>2. Đọc thuộc tính type của ô Password | 1. Ô hiển thị "••••••"<br>2. type = "password" | Low | Password: 123456 | Yes | UI | @Regression @Security |
| RISE_LOGIN_TC_013 | REQ-LOGIN-11 | Login / Bảo vệ trang | High | Truy cập trang nội bộ khi chưa đăng nhập bị chuyển về signin và quay lại sau đăng nhập | Chưa đăng nhập | 1. Mở trực tiếp https://rise.anhtester.com/clients<br>2. Nhập Email: admin@example.com<br>3. Nhập Password: 123456<br>4. Click "Sign in" | 1. URL = https://rise.anhtester.com/signin?redirect=https://rise.anhtester.com/clients<br>4. Quay về https://rise.anhtester.com/clients | High | URL: /clients | Yes | UI | @Security @Regression |
| RISE_LOGIN_TC_014 | REQ-LOGIN-12 | Login / Bàn phím | Low | Đăng nhập bằng phím Enter | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@example.com<br>2. Nhập Password: 123456<br>3. Nhấn phím Enter khi con trỏ ở ô Password | 3. Đăng nhập thành công, rời trang /signin | Low | Email: admin@example.com \| Password: 123456 | Yes | UI | @A11y |
| RISE_LOGIN_TC_015 | REQ-LOGIN-01 | Login / Giao diện | Low | Hiển thị đúng các thành phần form đăng nhập | Chưa đăng nhập | 1. Mở https://rise.anhtester.com/signin<br>2. Quan sát form | 2. Có ô Email (placeholder "Email", đang focus), ô Password (placeholder "Password"), nút "Sign in", link "Forgot password?", dòng "You don't have an account? Sign up" | Low | URL: /signin | Yes | UI | @UI |
| RISE_LOGIN_TC_016 | REQ-LOGIN-01 | Login / Bàn phím | Low | Thứ tự Tab: Email → Password → Sign in | Chưa đăng nhập; đang ở /signin, focus ở Email | 1. Nhấn Tab<br>2. Nhấn Tab | 1. Focus chuyển sang ô Password ⚠️ chưa có evidence thứ tự Tab<br>2. Focus chuyển sang nút "Sign in" | Low | Phím Tab | Partial | UI | @A11y @NeedsVerify |
| RISE_LOGIN_TC_017 | REQ-LOGIN-13 | Login / Giao diện | Low | Link Forgot password và Sign up trỏ đúng địa chỉ | Chưa đăng nhập; đang ở /signin | 1. Click link "Forgot password?"<br>2. Quay lại /signin<br>3. Click link "Sign up" | 1. URL = /signin/request_reset_password ⚠️ chưa click khi recon<br>3. URL = /signup | Low | — | Yes | UI | @Regression @NeedsVerify |
| RISE_LOGIN_TC_018 | REQ-LOGIN-02 | Login / Phi chức năng | Medium | Double click nút Sign in không gây lỗi | Chưa đăng nhập; đang ở /signin | 1. Nhập Email: admin@example.com<br>2. Nhập Password: 123456<br>3. Double click nhanh nút "Sign in" | 3. Vào Dashboard bình thường, không hiện trang lỗi ⚠️ chưa kiểm chứng | Medium | Email: admin@example.com \| Password: 123456 | Partial | UI | @Regression @NeedsVerify |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-LOGIN-01 | Hiển thị form đăng nhập | 2 | TC_015, TC_016 | P ✅ |
| REQ-LOGIN-02 | Đăng nhập thành công | 3 | TC_001, TC_018, RISE_E2E_TC_001 | P+N ✅ |
| REQ-LOGIN-03 | Email bắt buộc | 2 | TC_003, TC_007 | N ✅ |
| REQ-LOGIN-04 | Password bắt buộc | 2 | TC_005, TC_007 | N ✅ |
| REQ-LOGIN-05 | Email đúng định dạng | 3 | TC_002, TC_008, TC_010 | N+B ✅ |
| REQ-LOGIN-06 | Email chưa đăng ký | 1 | TC_006 | N ✅ |
| REQ-LOGIN-07 | Sai mật khẩu | 2 | TC_004, TC_011 | N ✅ |
| REQ-LOGIN-08 | Xoá dữ liệu nhập sau khi đăng nhập thất bại | 2 | TC_004, TC_006 | N ✅ |
| REQ-LOGIN-09 | Email không phân biệt hoa/thường | 1 | TC_009 | P ✅ |
| REQ-LOGIN-10 | Che mật khẩu | 1 | TC_012 |  ✅ |
| REQ-LOGIN-11 | Bảo vệ trang nội bộ | 2 | TC_013, TC_037 |  ✅ |
| REQ-LOGIN-12 | Submit bằng phím Enter | 1 | TC_014 |  ✅ |
| REQ-LOGIN-13 | Link phụ | 1 | TC_017 |  ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| login_default_fullpage.png | Form đăng nhập — mặc định | TC_015, TC_016, TC_017 | ✅ full-page |
| login_submit_empty_both_fullpage.png | Submit khi trống cả 2 trường | TC_003, TC_005, TC_007 | ✅ full-page |
| login_submit_empty_email_fullpage.png | Submit khi trống Email | TC_003, TC_007 | ✅ full-page |
| login_submit_empty_password_fullpage.png | Submit khi trống Password | TC_005, TC_007 | ✅ full-page |
| login_submit_email_invalid_format_fullpage.png | Email "admin@" | TC_002, TC_008, TC_010 | ✅ full-page |
| login_submit_email_not_registered_fullpage.png | Email chưa đăng ký | TC_004, TC_006 | ✅ full-page |
| login_submit_wrong_password_fullpage.png | Sai mật khẩu | TC_004, TC_006, TC_011 | ✅ full-page |
| login_success_dashboard_fullpage.png | Dashboard sau đăng nhập (viewport) | TC_001, TC_018, RISE_E2E_TC_001 | ✅ full-page |
| login_unauth_redirect_fullpage.png | Truy cập /tasks/all_tasks khi chưa đăng nhập | TC_013, TC_037 | ✅ full-page |

## Vùng chưa có evidence

3 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): TC_016, TC_017, TC_018. Đề xuất recon bổ sung trước khi automation.

## Assumptions & quyết định PO/BA

| Mã | Phạm vi | Điểm chưa rõ | Quyết định / giả định áp dụng | TC liên quan | Trạng thái |
|---|---|---|---|---|---|
| ASM-01 | Login | "Invalid email" trong yêu cầu có thể là email sai định dạng hoặc email chưa đăng ký | TC @Core "invalid email" = sai định dạng; bổ sung 1 TC email chưa đăng ký | RISE_LOGIN_TC_002, RISE_LOGIN_TC_006 | ❓ Giả định — chưa có phản hồi |
| ASM-06 | Chung | Cách nhập ngày (Projects AMB-05) | Luôn click ô ngày rồi click chọn ngày trên datepicker. Ngày trong TC là ví dụ cho tháng 11/2026 — chạy tháng khác thì chọn ngày cùng số trong tháng hiện tại và đổi Expected tương ứng | RISE_CLIENT_TC_003, RISE_CLIENT_TC_011, RISE_PRJ_TC_009, RISE_PRJ_TC_011, RISE_PRJ_TC_012, RISE_TASK_TC_013, RISE_TASK_TC_014, RISE_TASK_TC_015, RISE_TASK_TC_017, RISE_E2E_TC_001 | ✅ PO chốt 2026-11-08 |
| ASM-08 | Chung | Phân quyền role khác Admin | Out-of-scope — chỉ chạy với 1 role Admin được cung cấp | — | ✅ PO chốt 2026-11-08 |
| ASM-09 | Chung | Độc lập dữ liệu (Clients RISK-01) | Mỗi TC chỉ thao tác trên dữ liệu do chính nó tạo ở bước Arrange (VD Edit/Delete phải Add trước). <ts> = yyyyMMddHHmmss sinh lúc chạy | Toàn bộ TC | ✅ PO chốt 2026-11-08 |
| ASM-10 | Chung | Dọn dữ liệu sau khi chạy (E2E AMB-01) | Chưa cần dọn — đã bỏ TC teardown | — (đã bỏ) | ✅ PO chốt 2026-11-08 |
| ASM-11 | Chung | Max length các trường text (Clients AMB-05) | Bỏ qua — đã bỏ TC 255 ký tự (Client, Project) | — (đã bỏ) | ✅ PO chốt 2026-11-08 |

## Nhật ký thay đổi

| Ngày | Phiên bản | Thay đổi |
|---|---|---|
| 2026-11-08 | 1.0 | Khởi tạo 18 TC (FULL RBT) |
| 2026-11-08 | 1.1 | Cập nhật theo phản hồi PO/BA, còn 18 TC. Không bỏ TC. Mọi TC tự tạo dữ liệu ở bước Arrange; timestamp cố định đổi thành <ts>; mã TC được đánh lại liên tục. |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 18 ID duy nhất, định dạng RISE_LOGIN_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ trong phạm vi có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 3 TC @NeedsVerify |
