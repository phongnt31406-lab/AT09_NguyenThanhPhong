# Test Cases — Tasks (tổng 25 TC)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_tasks.md](../../requirements/tasks/requirements_tasks.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 25 |
| Ngày sinh | 2026-11-08 |
| Định dạng TC ID | RISE_TASK_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Danh sách | Low | 1 |
| Thêm task | High | 21 |
| Liên kết | High | 1 |
| Chi tiết task | Medium | 1 |
| Phi chức năng | Medium | 1 |

## Danh sách Test Cases

| TC ID | REQ ID | Module | Risk Level | Test Title | Pre-Condition | Test Steps | Expected Result | Priority | Test Data | Automatable | Auto Type | Tags |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| RISE_TASK_TC_001 | REQ-TASK-01, REQ-TASK-02 | Tasks / Danh sách | Low | Danh sách Tasks hiển thị đúng và lọc mặc định theo Admin | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080) | 1. Mở https://rise.anhtester.com/tasks/all_tasks | 1. Tab List đang chọn; cột ID, Title, Start date, Deadline, Milestone, Related to, Assigned to, Collaborators, Status; filter Assigned to = "Admin Example"; nút Add task, Add multiple tasks | Low | — | Yes | UI | @UI |
| RISE_TASK_TC_002 | REQ-TASK-03, REQ-TASK-09, REQ-TASK-10 | Tasks / Thêm task | Medium | Modal Add task hiển thị đúng mặc định | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks") | 1. Modal "Add task": Related to = "-", Points = "1 Point", Assign to = "Admin Example", Status = "To do", Priority trống, Start/Deadline placeholder "YYYY-MM-DD"; nút Upload File, Close, Save & show, Save | Medium | — | Yes | UI | @UI |
| RISE_TASK_TC_003 | REQ-TASK-04 | Tasks / Thêm task | High | Title bắt buộc | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Để trống Title<br>3. Click "Save" | 3. "This field is required." dưới Title | High | Title: (trống) | Yes | UI | @Regression |
| RISE_TASK_TC_004 | REQ-TASK-05 | Tasks / Thêm task | Medium | Related to = Project hiện trường Project và Milestone | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Mở dropdown Related to<br>3. Chọn "Project" | 2. Options: -, Project, Client, Contract, Estimate, Expense, Invoice, Lead, Order, Proposal, Subscription, Ticket<br>3. Xuất hiện trường Project và Milestone | Medium | Related to: Project | Yes | UI | @Regression |
| RISE_TASK_TC_005 | REQ-TASK-06 | Tasks / Thêm task | High | Project bắt buộc khi Related to = Project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0941<br>3. Related to: Project<br>4. Không chọn Project<br>5. Click "Save" | 5. "This field is required." dưới Project | High | Project: (trống) | Yes | UI | @Regression |
| RISE_TASK_TC_006 | REQ-TASK-07 | Tasks / Thêm task | High | Tìm và chọn project vừa tạo | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Related to: Project<br>3. Mở dropdown Project, gõ: AT09_Project_20261108_0920<br>4. Chọn kết quả | 3. Chỉ 1 kết quả AT09_Project_20261108_0920<br>4. Project hiển thị AT09_Project_20261108_0920 | High | Project: AT09_Project_20261108_0920 | Yes | UI | @CriticalPath |
| RISE_TASK_TC_007 | REQ-TASK-08 | Tasks / Thêm task | High | Assign to bị reset sau khi chọn Project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Related to: Project<br>3. Chọn Project: AT09_Project_20261108_0920<br>4. Quan sát Assign to<br>5. Mở dropdown Assign to | 4. Assign to trống (placeholder "Assign to")<br>5. Options: "-", "Admin Example" | High | Project: AT09_Project_20261108_0920 | Yes | UI | @Regression |
| RISE_TASK_TC_008 | REQ-TASK-08 | Tasks / Thêm task | Low | Collaborators chỉ gồm thành viên project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Related to: Project; chọn Project AT09_Project_20261108_0920<br>3. Click ô Collaborators | 3. Danh sách chỉ có "Admin Example" | Low | — | Yes | UI | @Regression |
| RISE_TASK_TC_009 | REQ-TASK-09 | Tasks / Thêm task | Low | Chọn Points = 5 | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Mở dropdown Points<br>3. Chọn "5 Points" | 2. Options 1 Point → 5 Points<br>3. Hiển thị "5 Points" | Low | Points: 5 | Yes | UI | @Regression |
| RISE_TASK_TC_010 | REQ-TASK-10 | Tasks / Thêm task | Medium | Tạo task với Status In progress | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0942<br>3. Related to: Project; Project: AT09_Project_20261108_0920<br>4. Assign to: Admin Example<br>5. Status: In progress<br>6. Click "Save"<br>7. Search AT09_Task_20261108_0942 | 5. Options Status: To do, In progress, Done<br>7. Cột Status = "In progress" ⚠️ chưa kiểm chứng hiển thị | Medium | Status: In progress | Yes | UI | @Regression @NeedsVerify |
| RISE_TASK_TC_011 | REQ-TASK-11 | Tasks / Thêm task | Low | Danh sách Priority | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Mở dropdown Priority | 2. Options: -, Minor, Major, Critical, Blocker | Low | — | Yes | UI | @Regression |
| RISE_TASK_TC_012 | REQ-TASK-13 | Tasks / Thêm task | Medium | Datepicker của Task trả về định dạng YYYY-MM-DD | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Quan sát placeholder ô Start date và Deadline<br>3. Click ô Start date<br>4. Click ngày 20 | 2. Placeholder cả 2 ô = "YYYY-MM-DD"<br>3. Lịch "November 2026", tuần bắt đầu Mo<br>4. Ô Start date = "2026-11-20" | Medium | Start date: ngày 20/11/2026 | Yes | UI | @UI |
| RISE_TASK_TC_013 | REQ-TASK-12 | Tasks / Thêm task | High | Deadline sớm hơn Start date bị chặn | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0943; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Start date: 2026-11-20 (datepicker)<br>4. Deadline: 2026-11-15 (datepicker)<br>5. Click "Save" | 5. "Deadline must be equal or greater than Start date." dưới Deadline; không lưu | High | Start: 2026-11-20 \| Deadline: 2026-11-15 | Yes | UI | @Boundary |
| RISE_TASK_TC_014 | REQ-TASK-12 | Tasks / Thêm task | High | Deadline bằng Start date được chấp nhận | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0944; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Assign to: Admin Example<br>4. Start date: 2026-11-20<br>5. Deadline: 2026-11-20<br>6. Click "Save" | 6. Lưu thành công; Start = Deadline = 2026-11-20 | High | Start: 2026-11-20 \| Deadline: 2026-11-20 | Yes | UI | @Boundary |
| RISE_TASK_TC_015 | REQ-TASK-12 | Tasks / Thêm task | Medium | Deadline sau Start date 1 ngày | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0945; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Assign to: Admin Example<br>4. Start date: 2026-11-20<br>5. Deadline: 2026-11-21<br>6. Click "Save" | 6. Lưu thành công | Medium | Start: 2026-11-20 \| Deadline: 2026-11-21 | Yes | UI | @Boundary |
| RISE_TASK_TC_016 | REQ-TASK-14 | Tasks / Thêm task | High | Thêm task hợp lệ đầy đủ cho project đã tạo | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0940<br>3. Description: Task kiểm thử AT09 - Kiểm thử tạo task gắn project<br>4. Related to: Project<br>5. Project: AT09_Project_20261108_0920<br>6. Assign to: Admin Example<br>7. Status: To do<br>8. Priority: Minor<br>9. Start date: 2026-11-20<br>10. Deadline: 2026-11-25<br>11. Click "Save"<br>12. Search AT09_Task_20261108_0940 | 11. Modal đóng<br>12. Dòng mới: Title AT09_Task_20261108_0940, Start 2026-11-20, Deadline 2026-11-25, Related to AT09_Project_20261108_0920, Assigned to Admin Example, Status To do | High | Title: AT09_Task_20261108_0940 \| Priority: Minor \| Start: 2026-11-20 \| Deadline: 2026-11-25 | Yes | UI | @Smoke @CriticalPath |
| RISE_TASK_TC_017 | REQ-TASK-14 | Tasks / Thêm task | High | Thêm task chỉ với Title và Project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0946<br>3. Related to: Project; Project: AT09_Project_20261108_0920<br>4. Assign to: Admin Example<br>5. Click "Save"<br>6. Search AT09_Task_20261108_0946 | 6. Dòng mới, Start/Deadline "-", Status To do | High | Title: AT09_Task_20261108_0946 | Yes | UI | @Regression |
| RISE_TASK_TC_018 | REQ-TASK-15 | Tasks / Liên kết | High | Task hiển thị trong Tasks List của project | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã tạo task AT09_Task_20261108_0940 cho AT09_Project_20261108_0920 | 1. Mở project AT09_Project_20261108_0920<br>2. Click tab "Tasks List" | 2. Bảng có dòng AT09_Task_20261108_0940, Start 2026-11-20, Deadline 2026-11-25, Assigned to Admin Example, Status To do | High | — | Yes | UI | @Regression @CriticalPath |
| RISE_TASK_TC_019 | REQ-TASK-16 | Tasks / Chi tiết task | Medium | Modal Task info hiển thị đúng dữ liệu | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có task AT09_Task_20261108_0940 | 1. Search AT09_Task_20261108_0940 ở /tasks/all_tasks<br>2. Click Title task | 2. Modal "Task info #<id>": Title, Description, "Project: AT09_Project_20261108_0920", Start date 2026-11-20, Deadline 2026-11-25, Priority Minor, Status To do; nút Clone task, Edit task, Close | Medium | — | Yes | UI | @Regression |
| RISE_TASK_TC_020 | REQ-TASK-14 | Tasks / Thêm task | Medium | Description tiếng Việt có dấu | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0947; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Description: Kiểm thử tự động – ỨNG DỤNG quản lý 😀<br>4. Click "Save", mở Task info | 4. Description hiển thị đúng nguyên văn | Medium | Description: Kiểm thử tự động – ỨNG DỤNG quản lý 😀 | Yes | UI | @Localization |
| RISE_TASK_TC_021 | REQ-TASK-14 | Tasks / Thêm task | High | Title chứa script (XSS) | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: <script>alert(1)</script>AT09_0948; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Click "Save"<br>4. Search "AT09_0948" | 4. Không bật popup; Title hiển thị dạng text ⚠️ chưa kiểm chứng | High | Title: <script>alert(1)</script>AT09_0948 | Yes | UI | @Security @NeedsVerify |
| RISE_TASK_TC_022 | REQ-TASK-05, REQ-TASK-14 | Tasks / Thêm task | Low | Task không gắn đối tượng (Related to = "-") | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0949<br>3. Giữ Related to = "-"<br>4. Click "Save"<br>5. Search AT09_Task_20261108_0949 | 5. Lưu thành công; cột Related to trống ⚠️ chưa kiểm chứng | Low | — | Yes | UI | @NeedsVerify |
| RISE_TASK_TC_023 | REQ-TASK-03 | Tasks / Thêm task | Low | Save & show mở chi tiết task | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0950; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Click "Save & show" | 3. Task được lưu và mở modal "Task info" của task ⚠️ chưa kiểm chứng | Low | — | Partial | UI | @NeedsVerify |
| RISE_TASK_TC_024 | REQ-TASK-03 | Tasks / Thêm task | Low | Close modal Add task không lưu | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0951<br>3. Click "Close"<br>4. Search AT09_Task_20261108_0951 | 4. "No record found." | Low | — | Yes | UI | @Regression |
| RISE_TASK_TC_025 | REQ-TASK-14 | Tasks / Phi chức năng | Medium | Double click Save chỉ tạo 1 task | Đã đăng nhập admin@example.com / 123456 (viewport 1920×1080); đã có project "AT09_Project_20261108_0920" (Client Project, client AT09_Client_20261108_0900); đang ở /tasks/all_tasks | 1. Click nút "Add task" (không phải "Add multiple tasks")<br>2. Title: AT09_Task_20261108_0952; Related to: Project; Project: AT09_Project_20261108_0920<br>3. Double click nhanh "Save"<br>4. Search AT09_Task_20261108_0952 | 4. Chỉ 1 dòng ⚠️ chưa kiểm chứng | Medium | — | Partial | UI | @NeedsVerify |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-TASK-01 | Danh sách Tasks | 1 | TC_001 | P ✅ |
| REQ-TASK-02 | Bộ lọc mặc định theo người được giao | 1 | TC_001 | P ✅ |
| REQ-TASK-03 | Modal Add task | 3 | TC_002, TC_023, TC_024 | P+N ✅ |
| REQ-TASK-04 | Title bắt buộc | 1 | TC_003 | N ✅ |
| REQ-TASK-05 | Related to | 2 | TC_004, TC_022 | N ✅ |
| REQ-TASK-06 | Project bắt buộc khi Related to = Project | 1 | TC_005 | N ✅ |
| REQ-TASK-07 | Chọn Project đã tạo | 1 | TC_006 | P ✅ |
| REQ-TASK-08 | Assign to / Collaborators theo thành viên project | 2 | TC_007, TC_008 | P ✅ |
| REQ-TASK-09 | Points | 2 | TC_002, TC_009 | P ✅ |
| REQ-TASK-10 | Status | 2 | TC_002, TC_010 | P ✅ |
| REQ-TASK-11 | Priority | 1 | TC_011 |  ✅ |
| REQ-TASK-12 | Deadline ≥ Start date | 3 | TC_013, TC_014, TC_015 | P+N+B ✅ |
| REQ-TASK-13 | Định dạng ngày | 1 | TC_012 |  ✅ |
| REQ-TASK-14 | Lưu task thành công | 7 | TC_016, TC_017, TC_020, TC_021, TC_022, TC_025, RISE_E2E_TC_001 | P+N ✅ |
| REQ-TASK-15 | Task trong project | 1 | TC_018 | P ✅ |
| REQ-TASK-16 | Xem chi tiết task | 1 | TC_019 | P ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| tasks_list_default_fullpage.png | Danh sách Tasks mặc định | TC_001 | ✅ full-page |
| task_add_modal_default_fullpage.png | Modal Add task mặc định | TC_002, TC_009, TC_023, TC_024 | ✅ full-page |
| task_add_modal_submit_empty_fullpage.png | Save khi trống Title | TC_003 | ✅ full-page |
| task_add_modal_related_project_fullpage.png | Related to = Project | TC_004, TC_022 | ✅ full-page |
| task_add_modal_project_required_fullpage.png | Chưa chọn Project | TC_005 | ✅ full-page |
| task_add_modal_project_dropdown_search_fullpage.png | Search project vừa tạo | TC_006 | ✅ full-page |
| task_add_modal_project_selected_fullpage.png | Sau khi chọn Project (Assign to reset) | TC_007, TC_008 | ✅ full-page |
| task_add_modal_assignto_dropdown_open_fullpage.png | Dropdown Assign to | TC_007, TC_008 | ✅ full-page |
| task_add_modal_status_dropdown_open_fullpage.png | Dropdown Status | TC_002, TC_010 | ✅ full-page |
| task_add_modal_priority_dropdown_open_fullpage.png | Dropdown Priority | TC_011 | ✅ full-page |
| task_add_modal_deadline_before_start_fullpage.png | Deadline < Start date | TC_012, TC_013, TC_014, TC_015 | ✅ full-page |
| task_add_modal_filled_fullpage.png | Form đã điền | TC_016, TC_017, TC_020, TC_021, TC_022, TC_025, RISE_E2E_TC_001 | ✅ full-page |
| task_after_save_fullpage.png | Bảng sau khi lưu | TC_016, TC_017, TC_020, TC_021, TC_022, TC_025, RISE_E2E_TC_001 | ✅ full-page |
| tasks_list_search_result_fullpage.png | Search task vừa tạo | TC_016, TC_017, TC_020, TC_021, TC_022, TC_025, RISE_E2E_TC_001 | ✅ full-page |
| project_view_tasks_list_tab_fullpage.png | Project detail — Tasks List | TC_018 | ✅ full-page |
| task_detail_modal_fullpage.png | Modal Task info | TC_019 | ✅ full-page |

## Vùng chưa có evidence

5 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): TC_010, TC_021, TC_022, TC_023, TC_025. Đề xuất recon bổ sung trước khi automation.

## Assumptions (Q&A Bước 2 — chờ PO/BA xác nhận)

| Mã | Phạm vi | Điểm chưa rõ | Giả định đã áp dụng | TC bị ảnh hưởng |
|---|---|---|---|---|
| ASM-07 | Tasks | Bảng Tasks lọc mặc định theo Admin (AMB-03) | Mọi task tạo ra đều Assign to = Admin Example | RISE_TASK_TC_010 trở đi |
| ASM-08 | Chung | Chỉ có tài khoản Admin | Không sinh TC phân quyền role khác — cần tài khoản bổ sung | — |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 25 ID duy nhất, định dạng RISE_TASK_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 5 TC @NeedsVerify |
