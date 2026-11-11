# Test Cases — Projects (tổng 21 TC)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_projects.md](../../requirements/projects/requirements_projects.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 21 |
| Phiên bản | 1.2 — cập nhật 2026-11-10 theo phản hồi PO/BA (bản cũ lưu ở archive/) |
| Định dạng TC ID | RISE_PRJ_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Danh sách | Low | 1 |
| Thêm project | High | 16 |
| Liên kết | High | 1 |
| Chi tiết project | Medium | 1 |
| Sửa project | Low | 1 |
| Phi chức năng | Medium | 1 |

## Danh sách Test Cases

| TC ID | REQ ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data | Automatable | Auto Type | Tags |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| RISE_PRJ_TC_001 | REQ-PRJ-01 | Projects / Danh sách | Low | Trang Projects hiển thị đúng bảng và nút | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080) | 1. Mở https://rise.anhtester.com/projects/all_projects | 1. Tiêu đề "Projects"; cột ID, Title, Client, Price, Start date, Deadline, Progress, Status; nút Manage labels, Import projects, Add project; bộ lọc Label, Start date, Deadline, Status | Low | — | Yes | UI | @UI |
| RISE_PRJ_TC_002 | REQ-PRJ-02, REQ-PRJ-05 | Projects / Thêm project | Medium | Modal Add project hiển thị đúng mặc định | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project" | 1. Modal "Add project"; thứ tự trường Title, Project type (= Client Project), Client (đã có sẵn một giá trị — không assert tên cụ thể), Description, Start date, Deadline, Price, Labels; nút Close, Save & continue, Save | Medium | — | Yes | UI | @UI |
| RISE_PRJ_TC_003 | REQ-PRJ-03 | Projects / Thêm project | High | Title bắt buộc | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Để trống Title<br>3. Click "Save" | 3. "This field is required." dưới Title; modal không đóng | High | Title: (trống) | Yes | UI | @Regression |
| RISE_PRJ_TC_004 | REQ-PRJ-04 | Projects / Thêm project | Medium | Internal Project ẩn trường Client | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Project type: chọn "Internal Project" | 2. Trường Client biến mất | Medium | Project type: Internal Project | Yes | UI | @Regression |
| RISE_PRJ_TC_005 | REQ-PRJ-04 | Projects / Thêm project | Low | Chọn lại Client Project hiện lại trường Client | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Project type: Internal Project<br>3. Project type: Client Project | 3. Trường Client hiển thị lại | Low | — | Yes | UI | @Regression |
| RISE_PRJ_TC_006 | REQ-PRJ-05 | Projects / Thêm project | Medium | Dropdown Client không có lựa chọn trống — phải chọn client vừa tạo | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Mở dropdown Client<br>3. Gõ: AT09_Client_<ts> → click chọn kết quả | 1. Ô Client đã có sẵn một client (không để trống)<br>2. Danh sách không có option rỗng/placeholder<br>3. Client hiển thị AT09_Client_<ts> thay cho giá trị mặc định | Medium | Client: AT09_Client_<ts> | Yes | UI | @Regression |
| RISE_PRJ_TC_007 | REQ-PRJ-06 | Projects / Thêm project | High | Tìm và chọn client vừa tạo trong dropdown | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Mở dropdown Client<br>3. Gõ: AT09_Client_<ts><br>4. Chọn kết quả | 3. Chỉ còn 1 kết quả AT09_Client_<ts><br>4. Client hiển thị AT09_Client_<ts> | High | Client: AT09_Client_<ts> | Yes | UI | @CriticalPath |
| RISE_PRJ_TC_008 | REQ-PRJ-06 | Projects / Thêm project | Low | Tìm client không tồn tại trong dropdown | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Mở dropdown Client<br>3. Gõ: zzz_no_client | 3. Không có kết quả (Select2 hiển thị "No matches found") ⚠️ chưa kiểm chứng nguyên văn | Low | Search: zzz_no_client | Yes | UI | @Regression @NeedsVerify |
| RISE_PRJ_TC_009 | REQ-PRJ-10, REQ-PRJ-09 | Projects / Thêm project | High | Thêm project hợp lệ với đầy đủ thông tin cho client đã tạo | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Nhập Title: AT09_Project_<ts><br>3. Client: gõ AT09_Client_<ts> → click chọn<br>4. Nhập Description: Dự án kiểm thử AT09<br>5. Start date: click ô → click chọn ngày 10/11/2026 trên datepicker<br>6. Deadline: click ô → bấm ">" sang tháng 12/2026 → click ngày 31<br>7. Nhập Price: 1500<br>8. Click "Save"<br>9. Search AT09_Project_<ts> | 8. Modal đóng<br>9. Dòng mới: Title AT09_Project_<ts>, Client AT09_Client_<ts>, Start 2026-11-10, Deadline 2026-12-31, Status Open; Price có giá trị (không phải "-") | High | Title: AT09_Project_<ts> \| Price: 1500 \| Start: 2026-11-10 \| Deadline: 2026-12-31 | Yes | UI | @Smoke @CriticalPath |
| RISE_PRJ_TC_010 | REQ-PRJ-10 | Projects / Thêm project | High | Thêm project chỉ với Title và Client | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Nhập Title: AT09_Project_<ts><br>3. Client: gõ AT09_Client_<ts> → click chọn<br>4. Click "Save"<br>5. Search AT09_Project_<ts> | 5. Dòng mới, Price "-", Start date "-", Deadline "-", Status Open | High | Title: AT09_Project_<ts> | Yes | UI | @Regression |
| RISE_PRJ_TC_011 | REQ-PRJ-07 | Projects / Thêm project | Medium | Datepicker trả về định dạng YYYY-MM-DD | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Click ô Start date<br>3. Click ngày 10 | 2. Lịch "November 2026", hàng tiêu đề Mo Tu We Th Fr Sa Su, ngày hôm nay được tô<br>3. Ô Start date = "2026-11-10" | Medium | — | Yes | UI | @UI |
| RISE_PRJ_TC_012 | REQ-PRJ-07 | Projects / Thêm project | Medium | Deadline bằng Start date | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: AT09_Project_<ts>; Client: gõ AT09_Client_<ts> → click chọn<br>3. Start date: click ô → click chọn ngày 10 trên datepicker<br>4. Deadline: click ô → click chọn ngày 10 trên datepicker<br>5. Click "Save" | 5. Lưu thành công, Start = Deadline = 2026-11-10 | Medium | Start: 2026-11-10 \| Deadline: 2026-11-10 | Yes | UI | @Boundary |
| RISE_PRJ_TC_013 | REQ-PRJ-09 | Projects / Thêm project | Low | Price số thập phân | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: AT09_Project_<ts>; Client: AT09_Client_<ts><br>3. Nhập Price: 1500.50<br>4. Click "Save" | 4. Cột Price hiển thị "$1,500.50" ⚠️ chưa kiểm chứng định dạng tiền | Low | Price: 1500.50 | Yes | UI | @NeedsVerify |
| RISE_PRJ_TC_014 | REQ-PRJ-10 | Projects / Thêm project | Medium | Title tiếng Việt có dấu và emoji | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: Dự án Kiểm thử 🚀 AT09_<ts>; Client: AT09_Client_<ts><br>3. Click "Save"<br>4. Search "AT09_<ts>" | 4. Title hiển thị đúng nguyên văn | Medium | Title: Dự án Kiểm thử 🚀 AT09_<ts> | Yes | UI | @Localization |
| RISE_PRJ_TC_015 | REQ-PRJ-10 | Projects / Thêm project | High | Title chứa script (XSS) | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: <script>alert(1)</script>AT09_<ts>; Client: AT09_Client_<ts><br>3. Click "Save"<br>4. Search "AT09_<ts>" rồi mở project | 4. Không bật popup; Title hiển thị dạng text ⚠️ chưa kiểm chứng | High | Title: <script>alert(1)</script>AT09_<ts> | Yes | UI | @Security @NeedsVerify |
| RISE_PRJ_TC_016 | REQ-PRJ-11 | Projects / Liên kết | High | Project hiển thị trong Client detail | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client rồi tạo project AT09_Project_<ts> cho client đó | 1. Mở Client detail của AT09_Client_<ts><br>2. Click tab "Projects" | 1. Thẻ "Projects" = 1<br>2. Bảng có dòng AT09_Project_<ts>, Status Open | High | Client: AT09_Client_<ts> | Yes | UI | @Regression @CriticalPath |
| RISE_PRJ_TC_017 | REQ-PRJ-12 | Projects / Chi tiết project | Medium | Trang chi tiết project hiển thị đúng thông tin | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client rồi tạo project AT09_Project_<ts> (Start 2026-11-10, Deadline 2026-12-31 chọn trên datepicker) | 1. Search AT09_Project_<ts> ở /projects/all_projects<br>2. Click Title | 2. URL /projects/view/<id>; tiêu đề AT09_Project_<ts>; tab Overview đang chọn; "Start date: 2026-11-10", "Deadline: 2026-12-31", "Client: AT09_Client_<ts>"; Project members có "Admin Example" | Medium | Project: AT09_Project_<ts> | Yes | UI | @Regression |
| RISE_PRJ_TC_018 | REQ-PRJ-13 | Projects / Sửa project | Low | Modal Edit project có thêm trường Status | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client rồi tạo project AT09_Project_<ts> | 1. Search AT09_Project_<ts><br>2. Click icon Edit | 2. Modal "Edit project" với dữ liệu hiện tại; có trường Status = Open; nút Close, Save | Low | — | Yes | UI | @UI |
| RISE_PRJ_TC_019 | REQ-PRJ-02 | Projects / Thêm project | Low | Close modal Add project không lưu | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: AT09_Project_<ts><br>3. Click "Close"<br>4. Search AT09_Project_<ts> | 4. "No record found." | Low | — | Yes | UI | @Regression |
| RISE_PRJ_TC_020 | REQ-PRJ-14 | Projects / Thêm project | Low | Save & continue trên Add project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: AT09_Project_<ts>; Client: AT09_Client_<ts><br>3. Click "Save & continue" | 3. Project được lưu và chuyển sang bước tiếp theo (nội dung chưa khảo sát) ⚠️ chưa kiểm chứng | Low | — | Partial | UI | @NeedsVerify |
| RISE_PRJ_TC_021 | REQ-PRJ-10 | Projects / Phi chức năng | Medium | Double click Save chỉ tạo 1 project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client "AT09_Client_<ts>" bằng Add client; đang ở /projects/all_projects | 1. Click nút "Add project"<br>2. Title: AT09_Project_<ts>; Client: AT09_Client_<ts><br>3. Double click nhanh "Save"<br>4. Search AT09_Project_<ts> | 4. Chỉ 1 dòng ⚠️ chưa kiểm chứng | Medium | — | Partial | UI | @NeedsVerify |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-PRJ-01 | Danh sách Projects | 1 | TC_001 | P ✅ |
| REQ-PRJ-02 | Modal Add project | 2 | TC_002, TC_019 | P+N ✅ |
| REQ-PRJ-03 | Title bắt buộc | 1 | TC_003 | N ✅ |
| REQ-PRJ-04 | Project type | 2 | TC_004, TC_005 | P ✅ |
| REQ-PRJ-05 | Client mặc định | 2 | TC_002, TC_006 | P+N ✅ |
| REQ-PRJ-06 | Chọn Client đã tạo | 2 | TC_007, TC_008 | P+N ✅ |
| REQ-PRJ-07 | Chọn ngày bằng datepicker | 2 | TC_011, TC_012 | B ✅ |
| REQ-PRJ-08 | Không ràng buộc Deadline ≥ Start date | 0 | — | ⚪ Out-of-scope (không viết TC) |
| REQ-PRJ-09 | Price — happy case | 2 | TC_009, TC_013 | P ✅ |
| REQ-PRJ-10 | Lưu project thành công | 6 | TC_009, TC_010, TC_014, TC_015, TC_021, RISE_E2E_TC_001 | P+N ✅ |
| REQ-PRJ-11 | Project trong Client detail | 1 | TC_016 | P ✅ |
| REQ-PRJ-12 | Trang chi tiết project | 1 | TC_017 | P ✅ |
| REQ-PRJ-13 | Modal Edit project | 1 | TC_018 | P ✅ |
| REQ-PRJ-14 | Save & continue | 1 | TC_020 |  ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| projects_list_default_fullpage.png | Danh sách Projects | TC_001 | ✅ full-page |
| project_add_modal_default_fullpage.png | Modal Add project mặc định | TC_002, TC_006, TC_019 | ✅ full-page |
| project_add_modal_submit_empty_fullpage.png | Save khi trống Title | TC_003 | ✅ full-page |
| project_add_modal_type_internal_fullpage.png | Project type = Internal | TC_004, TC_005 | ✅ full-page |
| project_add_modal_client_dropdown_open_fullpage.png | Dropdown Client mở | TC_002, TC_006 | ✅ full-page |
| project_add_modal_client_dropdown_search_fullpage.png | Search client vừa tạo | TC_007, TC_008 | ✅ full-page |
| project_add_modal_datepicker_open_fullpage.png | Datepicker Start date | TC_011, TC_012 | ✅ full-page |
| project_add_modal_deadline_before_start_fullpage.png | Bảng sau khi lưu Deadline < Start | TC_009, TC_010, TC_014, TC_015, TC_021, RISE_E2E_TC_001 | ✅ full-page |
| project_edit_modal_deadline_picker_fullpage.png | Edit project + datepicker | TC_018 | ✅ full-page |
| project_edit_saved_invalid_dates_list_fullpage.png | Bảng: 2026-11-10 / 2026-11-05, Price "-" | TC_009, TC_013 | ✅ full-page |
| projects_list_search_result_fullpage.png | Search project vừa tạo | TC_009, TC_010, TC_014, TC_015, TC_021, RISE_E2E_TC_001 | ✅ full-page |
| client_view_projects_tab_fullpage.png | Client detail — tab Projects | TC_016 | ✅ full-page |
| project_view_overview_fullpage.png | Project detail — Overview | TC_017 | ✅ full-page |

## Vùng chưa có evidence

5 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): TC_008, TC_013, TC_015, TC_020, TC_021. Đề xuất recon bổ sung trước khi automation.

## Assumptions & quyết định PO/BA

| Mã | Phạm vi | Điểm chưa rõ | Quyết định / giả định áp dụng | TC liên quan | Trạng thái |
|---|---|---|---|---|---|
| ASM-05 | Projects | Deadline < Start date (AMB-01), Price chữ / số âm (AMB-02) | Out-of-scope / chỉ happy case — đã bỏ 3 TC | — (đã bỏ) | ✅ PO chốt 2026-11-08 |
| ASM-06 | Chung | Cách nhập ngày (Projects AMB-05) | Luôn click ô ngày rồi click chọn ngày trên datepicker. Ngày trong TC là ví dụ cho tháng 11/2026 — chạy tháng khác thì chọn ngày cùng số trong tháng hiện tại và đổi Expected tương ứng | RISE_CLIENT_TC_003, RISE_CLIENT_TC_011, RISE_PRJ_TC_009, RISE_PRJ_TC_011, RISE_PRJ_TC_012, RISE_TASK_TC_012, RISE_TASK_TC_013, RISE_TASK_TC_014, RISE_TASK_TC_016, RISE_E2E_TC_001 | ✅ PO chốt 2026-11-08 |
| ASM-08 | Chung | Phân quyền role khác Admin | Out-of-scope — chỉ chạy với 1 role Admin được cung cấp | — | ✅ PO chốt 2026-11-08 |
| ASM-09 | Chung | Độc lập dữ liệu (Clients RISK-01) | Mỗi TC chỉ thao tác trên dữ liệu do chính nó tạo ở bước Arrange (VD Edit/Delete phải Add trước). <ts> = yyyyMMddHHmmss sinh lúc chạy | Toàn bộ TC | ✅ PO chốt 2026-11-08 |
| ASM-10 | Chung | Dọn dữ liệu sau khi chạy (E2E AMB-01) | Chưa cần dọn — đã bỏ TC teardown | — (đã bỏ) | ✅ PO chốt 2026-11-08 |
| ASM-11 | Chung | Max length các trường text (Clients AMB-05) | Bỏ qua — đã bỏ TC 255 ký tự (Client, Project) | — (đã bỏ) | ✅ PO chốt 2026-11-08 |

## Nhật ký thay đổi

| Ngày | Phiên bản | Thay đổi |
|---|---|---|
| 2026-11-08 | 1.0 | Khởi tạo 25 TC (FULL RBT) |
| 2026-11-08 | 1.1 | Cập nhật theo phản hồi PO/BA, còn 21 TC. Bỏ hoặc đổi tên (mã bản 1.0): TC_006 "Client mặc định là client đầu tiên, không có lựa chọn trống"; TC_013 "Deadline sớm hơn Start date"; TC_014 "Price nhập chữ"; TC_016 "Price số âm"; TC_019 "Title dài 255 ký tự". Mới hoặc đổi tên (mã bản 1.1): TC_006 "Dropdown Client không có lựa chọn trống — phải chọn client vừa tạo". Mọi TC tự tạo dữ liệu ở bước Arrange; timestamp cố định đổi thành <ts>; mã TC được đánh lại liên tục. |
| 2026-11-10 | 1.2 | Phản hồi PO/BA lần 2, còn 21 TC. Không bỏ TC.  |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 21 ID duy nhất, định dạng RISE_PRJ_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ trong phạm vi có ≥1 TC (REQ ⚪ Out-of-scope không cần TC) |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 5 TC @NeedsVerify |
