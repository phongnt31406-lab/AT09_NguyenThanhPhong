# Test Cases — E2E Flow (tổng 6 TC)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_e2e_flow.md](../../requirements/e2e_flow/requirements_e2e_flow.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 6 |
| Phiên bản | 1.2 — cập nhật 2026-11-10 theo phản hồi PO/BA (bản cũ lưu ở archive/) |
| Định dạng TC ID | RISE_E2E_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Full flow | High | 2 |
| Liên kết | High | 4 |

## Danh sách Test Cases

| TC ID | REQ ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data | Automatable | Auto Type | Tags |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| RISE_E2E_TC_001 | REQ-E2E-05, REQ-E2E-01, REQ-E2E-02, REQ-E2E-03, REQ-E2E-04, REQ-LOGIN-02, REQ-CLIENT-13, REQ-PRJ-10, REQ-TASK-14 | E2E Flow / Full flow | High | Full flow: Login → Add Client → Add Project → Add Task | Chưa đăng nhập; trình duyệt sạch | 1. Mở /signin, nhập admin@example.com / 123456, click "Sign in"<br>2. Vào Clients → tab Clients → "Add client"<br>3. Company name: AT09_Client_<ts> → Save<br>4. Vào Projects → "Add project"<br>5. Title: AT09_Project_<ts>; Client: gõ AT09_Client_<ts> → click chọn; Start date: click ô → click chọn ngày 10 trên datepicker; Deadline: click ô → bấm ">" sang tháng 12 → click ngày 31 → Save<br>6. Vào Tasks → "Add task"<br>7. Title: AT09_Task_<ts>; Related to: Project; Project: gõ AT09_Project_<ts> → click chọn; Assign to: Admin Example; Start date: click ô → click chọn ngày 20 trên datepicker; Deadline: click ô → click chọn ngày 25 trên datepicker → Save<br>8. Click bộ lọc đang hiển thị "Admin Example" → chọn "- Team member -" để bỏ lọc, rồi Search AT09_Task_<ts> ở bảng Tasks | 1. Vào Dashboard<br>3. Client tạo thành công, search thấy 1 dòng<br>5. Project tạo thành công, cột Client = AT09_Client_<ts><br>7. Task tạo thành công<br>8. Related to = AT09_Project_<ts>, Assigned to = Admin Example, Status To do | High | Timestamp chung: <ts> | Yes | UI | @Smoke @CriticalPath @E2E |
| RISE_E2E_TC_002 | REQ-E2E-01 | E2E Flow / Liên kết | High | Client vừa tạo có ngay trong dropdown Client của Add project (không reload) | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080) | 1. Tạo client AT09_Client_<ts>_b<br>2. Không reload, vào Projects → "Add project"<br>3. Mở dropdown Client, gõ AT09_Client_<ts>_b | 3. Có đúng 1 kết quả | High | Client: AT09_Client_<ts>_b | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_003 | REQ-E2E-02 | E2E Flow / Liên kết | High | Project vừa tạo có trong dropdown Project của Add task | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client AT09_Client_<ts> rồi project AT09_Project_<ts> cho client đó | 1. Vào Tasks → "Add task"<br>2. Related to: Project<br>3. Mở dropdown Project, gõ AT09_Project_<ts> | 3. Có đúng 1 kết quả | High | Project: AT09_Project_<ts> | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_004 | REQ-E2E-03 | E2E Flow / Liên kết | High | Kiểm tra liên kết Client ↔ Project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client AT09_Client_<ts> rồi project AT09_Project_<ts> cho client đó (không dựa vào TC khác) | 1. Mở Client detail của AT09_Client_<ts><br>2. Click tab Projects | 1. Thẻ "Projects" = 1<br>2. Có dòng AT09_Project_<ts> | High | — | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_005 | REQ-E2E-04 | E2E Flow / Liên kết | High | Kiểm tra liên kết Project ↔ Task | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); Arrange: TC tự tạo client → project AT09_Project_<ts> → task AT09_Task_<ts> (không dựa vào TC khác) | 1. Mở project AT09_Project_<ts> → tab Tasks List<br>2. Click Title task AT09_Task_<ts> | 1. Có dòng AT09_Task_<ts><br>2. Modal Task info hiển thị "Project: AT09_Project_<ts>" | High | — | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_006 | REQ-E2E-05, REQ-CLIENT-14, REQ-CLIENT-36 | E2E Flow / Full flow | Medium | Full flow có thêm Contact cho client | Chưa đăng nhập | 1. Đăng nhập admin<br>2. Add client AT09_Client_<ts>_c → "Save & continue"<br>3. Nhập contact Lan Do, email at09_contact_<ts>@example.com, click vào ô Password rồi nhập Test@123, bỏ tick "Email login details", click "Save & add more" rồi đóng modal<br>4. Add project cho client này (chọn client bằng search, ngày chọn trên datepicker)<br>5. Add task cho project này<br>6. Mở Client detail | 3. Contact được lưu<br>6. Tab Contacts có "Lan Do" (Primary contact); tab Projects có project; project có task | Medium | Timestamp: <ts> | Partial | UI | @E2E @NeedsVerify |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-E2E-01 | Client mới dùng được cho Project | 2 | RISE_E2E_TC_001, RISE_E2E_TC_002 | P+N ✅ |
| REQ-E2E-02 | Project mới dùng được cho Task | 2 | RISE_E2E_TC_001, RISE_E2E_TC_003 | P ✅ |
| REQ-E2E-03 | Liên kết Client ↔ Project | 2 | RISE_E2E_TC_001, RISE_E2E_TC_004 | P ✅ |
| REQ-E2E-04 | Liên kết Project ↔ Task | 2 | RISE_E2E_TC_001, RISE_E2E_TC_005 | P ✅ |
| REQ-E2E-05 | Luồng liên tục trong 1 phiên | 2 | RISE_E2E_TC_001, RISE_E2E_TC_006 | P ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| (dùng chung evidence của các module) | clients_list_search_result, project_add_modal_client_dropdown_search, task_add_modal_project_dropdown_search, client_view_projects_tab, project_view_tasks_list_tab | RISE_E2E_TC_001, RISE_E2E_TC_002, RISE_E2E_TC_006 | ✅ full-page |

## Vùng chưa có evidence

1 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): RISE_E2E_TC_006. Đề xuất recon bổ sung trước khi automation.

## Assumptions & quyết định PO/BA

| Mã | Phạm vi | Điểm chưa rõ | Quyết định / giả định áp dụng | TC liên quan | Trạng thái |
|---|---|---|---|---|---|
| ASM-06 | Chung | Cách nhập ngày (Projects AMB-05) | Luôn click ô ngày rồi click chọn ngày trên datepicker. Ngày trong TC là ví dụ cho tháng 11/2026 — chạy tháng khác thì chọn ngày cùng số trong tháng hiện tại và đổi Expected tương ứng | RISE_CLIENT_TC_003, RISE_CLIENT_TC_011, RISE_PRJ_TC_009, RISE_PRJ_TC_011, RISE_PRJ_TC_012, RISE_TASK_TC_012, RISE_TASK_TC_013, RISE_TASK_TC_014, RISE_TASK_TC_016, RISE_E2E_TC_001 | ✅ PO chốt 2026-11-08 |
| ASM-08 | Chung | Phân quyền role khác Admin | Out-of-scope — chỉ chạy với 1 role Admin được cung cấp | — | ✅ PO chốt 2026-11-08 |
| ASM-09 | Chung | Độc lập dữ liệu (Clients RISK-01) | Mỗi TC chỉ thao tác trên dữ liệu do chính nó tạo ở bước Arrange (VD Edit/Delete phải Add trước). <ts> = yyyyMMddHHmmss sinh lúc chạy | Toàn bộ TC | ✅ PO chốt 2026-11-08 |
| ASM-10 | Chung | Dọn dữ liệu sau khi chạy (E2E AMB-01) | Chưa cần dọn — đã bỏ TC teardown | — (đã bỏ) | ✅ PO chốt 2026-11-08 |
| ASM-11 | Chung | Max length các trường text (Clients AMB-05) | Bỏ qua — đã bỏ TC 255 ký tự (Client, Project) | — (đã bỏ) | ✅ PO chốt 2026-11-08 |

## Nhật ký thay đổi

| Ngày | Phiên bản | Thay đổi |
|---|---|---|
| 2026-11-08 | 1.0 | Khởi tạo 7 TC (FULL RBT) |
| 2026-11-08 | 1.1 | Cập nhật theo phản hồi PO/BA, còn 6 TC. Bỏ hoặc đổi tên (mã bản 1.0): TC_007 "Teardown xoá dữ liệu theo thứ tự Task → Project → Client". Mọi TC tự tạo dữ liệu ở bước Arrange; timestamp cố định đổi thành <ts>; mã TC được đánh lại liên tục. |
| 2026-11-10 | 1.2 | Phản hồi PO/BA lần 2, còn 6 TC. Không bỏ TC.  |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 6 ID duy nhất, định dạng RISE_E2E_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ trong phạm vi có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 1 TC @NeedsVerify |
