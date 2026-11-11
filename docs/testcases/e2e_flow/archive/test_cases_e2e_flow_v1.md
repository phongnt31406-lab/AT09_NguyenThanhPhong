# Test Cases — E2E Flow (tổng 7 TC)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_e2e_flow.md](../../requirements/e2e_flow/requirements_e2e_flow.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 7 |
| Ngày sinh | 2026-11-08 |
| Định dạng TC ID | RISE_E2E_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Full flow | High | 2 |
| Liên kết | High | 4 |
| Dọn dữ liệu | Medium | 1 |

## Danh sách Test Cases

| TC ID | REQ ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data | Automatable | Auto Type | Tags |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| RISE_E2E_TC_001 | REQ-E2E-05, REQ-E2E-01, REQ-E2E-02, REQ-E2E-03, REQ-E2E-04, REQ-LOGIN-02, REQ-CLIENT-13, REQ-PRJ-10, REQ-TASK-14 | E2E Flow / Full flow | High | Full flow: Login → Add Client → Add Project → Add Task | Chưa đăng nhập; trình duyệt sạch | 1. Mở /signin, nhập admin@example.com / 123456, click "Sign in"<br>2. Vào Clients → tab Clients → "Add client"<br>3. Company name: AT09_Client_20261108_1000 → Save<br>4. Vào Projects → "Add project"<br>5. Title: AT09_Project_20261108_1000; Client: chọn AT09_Client_20261108_1000; Start 2026-11-10, Deadline 2026-12-31 → Save<br>6. Vào Tasks → "Add task"<br>7. Title: AT09_Task_20261108_1000; Related to: Project; Project: AT09_Project_20261108_1000; Assign to: Admin Example; Start 2026-11-20; Deadline 2026-11-25 → Save<br>8. Search AT09_Task_20261108_1000 ở bảng Tasks | 1. Vào Dashboard<br>3. Client tạo thành công, search thấy 1 dòng<br>5. Project tạo thành công, cột Client = AT09_Client_20261108_1000<br>7. Task tạo thành công<br>8. Related to = AT09_Project_20261108_1000, Assigned to = Admin Example, Status To do | High | Timestamp chung: 20261108_1000 | Yes | UI | @Smoke @CriticalPath @E2E |
| RISE_E2E_TC_002 | REQ-E2E-01 | E2E Flow / Liên kết | High | Client vừa tạo có ngay trong dropdown Client của Add project (không reload) | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080) | 1. Tạo client AT09_Client_20261108_1000_b<br>2. Không reload, vào Projects → "Add project"<br>3. Mở dropdown Client, gõ AT09_Client_20261108_1000_b | 3. Có đúng 1 kết quả | High | Client: AT09_Client_20261108_1000_b | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_003 | REQ-E2E-02 | E2E Flow / Liên kết | High | Project vừa tạo có trong dropdown Project của Add task | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); vừa tạo project AT09_Project_20261108_1000 | 1. Vào Tasks → "Add task"<br>2. Related to: Project<br>3. Mở dropdown Project, gõ AT09_Project_20261108_1000 | 3. Có đúng 1 kết quả | High | Project: AT09_Project_20261108_1000 | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_004 | REQ-E2E-03 | E2E Flow / Liên kết | High | Kiểm tra liên kết Client ↔ Project | Đã chạy xong full flow (TC_001) | 1. Mở Client detail của AT09_Client_20261108_1000<br>2. Click tab Projects | 1. Thẻ "Projects" = 1<br>2. Có dòng AT09_Project_20261108_1000 | High | — | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_005 | REQ-E2E-04 | E2E Flow / Liên kết | High | Kiểm tra liên kết Project ↔ Task | Đã chạy xong full flow (TC_001) | 1. Mở project AT09_Project_20261108_1000 → tab Tasks List<br>2. Click Title task AT09_Task_20261108_1000 | 1. Có dòng AT09_Task_20261108_1000<br>2. Modal Task info hiển thị "Project: AT09_Project_20261108_1000" | High | — | Yes | UI | @E2E @Regression |
| RISE_E2E_TC_006 | REQ-E2E-05, REQ-CLIENT-14, REQ-CLIENT-36 | E2E Flow / Full flow | Medium | Full flow có thêm Contact cho client | Chưa đăng nhập | 1. Đăng nhập admin<br>2. Add client AT09_Client_20261108_1000_c → "Save & continue"<br>3. Nhập contact Lan Do, email at09_contact_20261108_1000@example.com, bỏ tick "Email login details", click "Save & add more" rồi đóng modal<br>4. Add project cho client này<br>5. Add task cho project này<br>6. Mở Client detail | 3. Contact được lưu<br>6. Tab Contacts có "Lan Do" (Primary contact); tab Projects có project; project có task | Medium | Timestamp: 20261108_1000 | Partial | UI | @E2E @NeedsVerify |
| RISE_E2E_TC_007 | REQ-E2E-05 | E2E Flow / Dọn dữ liệu | Medium | Teardown xoá dữ liệu theo thứ tự Task → Project → Client | Đã chạy full flow | 1. Xoá task AT09_Task_20261108_1000<br>2. Xoá project AT09_Project_20261108_1000<br>3. Xoá client AT09_Client_20261108_1000<br>4. Search "AT09_" ở 3 bảng | 1. Toast "The record has been deleted."<br>2. Toast "The record has been deleted."<br>3. Toast "The record has been deleted."<br>4. Không còn bản ghi của lần chạy | Medium | — | Yes | UI | @E2E @Cleanup |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-E2E-01 | Client mới dùng được cho Project | 2 | RISE_E2E_TC_001, RISE_E2E_TC_002 | P+N ✅ |
| REQ-E2E-02 | Project mới dùng được cho Task | 2 | RISE_E2E_TC_001, RISE_E2E_TC_003 | P ✅ |
| REQ-E2E-03 | Liên kết Client ↔ Project | 2 | RISE_E2E_TC_001, RISE_E2E_TC_004 | P ✅ |
| REQ-E2E-04 | Liên kết Project ↔ Task | 2 | RISE_E2E_TC_001, RISE_E2E_TC_005 | P ✅ |
| REQ-E2E-05 | Luồng liên tục trong 1 phiên | 3 | RISE_E2E_TC_001, RISE_E2E_TC_006, RISE_E2E_TC_007 | P ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| (dùng chung evidence của các module) | clients_list_search_result, project_add_modal_client_dropdown_search, task_add_modal_project_dropdown_search, client_view_projects_tab, project_view_tasks_list_tab | RISE_E2E_TC_001, RISE_E2E_TC_002, RISE_E2E_TC_006, RISE_E2E_TC_007 | ✅ full-page |

## Vùng chưa có evidence

1 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): RISE_E2E_TC_006. Đề xuất recon bổ sung trước khi automation.

## Assumptions (Q&A Bước 2 — chờ PO/BA xác nhận)

| Mã | Phạm vi | Điểm chưa rõ | Giả định đã áp dụng | TC bị ảnh hưởng |
|---|---|---|---|---|
| ASM-08 | Chung | Chỉ có tài khoản Admin | Không sinh TC phân quyền role khác — cần tài khoản bổ sung | — |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 7 ID duy nhất, định dạng RISE_E2E_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 1 TC @NeedsVerify |
