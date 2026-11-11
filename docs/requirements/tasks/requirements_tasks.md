# Requirements — Tasks (Add task)

| Thông tin | Nội dung |
|---|---|
| Module | Tasks (Add task) |
| Prefix | TASK |
| URL | https://rise.anhtester.com/tasks/all_tasks |
| Nguồn | UI recon trên hệ thống thật (Playwright + Chrome, headless) — tài khoản admin@example.com |
| Ngày khảo sát | 2026-11-08 |
| Tổng số REQ | 16 |
| Dải mã đã dùng | REQ-TASK-01 → REQ-TASK-16 · AMB-01 → AMB-03 · RISK-01 → RISK-04 |
| Mã kế tiếp | Đợt phân tích sau bắt đầu từ REQ-TASK-17 · AMB-04 · RISK-05 — KHÔNG đánh lại từ 01 |

## Tổng quan

Tạo công việc (Task) gắn với Project đã tạo. Task có trạng thái To do / In progress / Done.

**Trong phạm vi:**
- Danh sách Tasks (List)
- Modal Add task — Related to = Project, chọn Project đã tạo
- Hiển thị task trong project và modal Task info

**Ngoài phạm vi:**
- Kanban, Gantt, Add multiple tasks, Import tasks
- Sửa/xoá task, đổi trạng thái, checklist, sub task, timer
- Upload file, ghi âm
- Phân quyền các role khác Admin (AMB-02)
- Dọn dữ liệu test sau khi chạy (site demo — PO 2026-11-08)

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-TASK-01 | Danh sách Tasks | Trang /tasks/all_tasks, tab List, bảng #task-table. | Cột: ID, Title, Start date, Deadline, Milestone, Related to, Assigned to, Collaborators, Status; nút Manage labels, Import tasks, Add multiple tasks, Add task. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-02 | Bộ lọc mặc định theo người được giao | Bộ lọc Team member mặc định là user đăng nhập (business rule). Muốn thấy mọi task: click bộ lọc "Admin Example" → chọn "- Team member -". | Mở trang → filter hiển thị "Admin Example", bảng "1-10 / 11"; chọn "- Team member -" → bảng tự tải lại, "1-10 / 34" (kiểm chứng 2026-11-08). Options lọc: - Team member -, Admin Example, Anh Tester Admin, Project Manager. | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-TASK-03 | Modal Add task | Click "Add task" mở modal "Add task". | Trường: Title, Description, Related to, Points, Assign to, Collaborators, Status, Priority, Labels, Start date, Deadline; nút Upload File, micro, Close, Save & show, Save. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-04 | Title bắt buộc | Bỏ trống Title khi Save. | "This field is required." dưới Title. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-05 | Related to | Select2 Related to, mặc định "-". | Options: -, Project, Client, Contract, Estimate, Expense, Invoice, Lead, Order, Proposal, Subscription, Ticket. Chọn Project → hiện trường Project và Milestone. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-06 | Project bắt buộc khi Related to = Project | Không chọn Project mà Save. | "This field is required." dưới Project. Ghi nhận: nếu đã Save khi chưa chọn Project rồi mới chọn, dòng lỗi vẫn còn hiển thị (tái hiện 2026-11-08) — có thể do lỗi site demo, PO chốt không kiểm thử luồng này, chỉ test happy case (AMB-01). | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-TASK-07 | Chọn Project đã tạo | Dropdown Project có ô tìm kiếm; project vừa tạo xuất hiện. | Gõ Title project → còn 1 option → chọn → project_id = ID project. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-08 | Assign to / Collaborators theo thành viên project | Sau khi chọn Project, Assign to bị reset rỗng và danh sách chỉ gồm thành viên project (business rule — PO 2026-11-08). | Assign to options: "-", "Admin Example"; Collaborators: "Admin Example"; Milestone: "-". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-09 | Points | Select Points mặc định "1 Point". | Options: 1 Point, 2 Points, 3 Points, 4 Points, 5 Points. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-10 | Status | Select2 Status mặc định "To do". | Options: To do, In progress, Done. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-11 | Priority | Select2 Priority, mặc định trống. | Options: -, Minor, Major, Critical, Blocker. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-12 | Deadline ≥ Start date | Deadline sớm hơn Start date bị chặn ở client. | Start 2026-11-20, Deadline 2026-11-15 → "Deadline must be equal or greater than Start date."; Deadline = Start được phép. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-13 | Định dạng ngày | Start date/Deadline placeholder "YYYY-MM-DD", chọn qua datepicker. | Chọn ngày 20 → "2026-11-20". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-14 | Lưu task thành công | Save với Title + Project hợp lệ. | POST /tasks/save success; modal đóng; bảng có dòng mới: Title, Start, Deadline, Related to = Title project, Assigned to = Admin Example, Status "To do". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-15 | Task trong project | Task hiển thị ở tab "Tasks List" của Project detail. | Tab liệt kê task với đúng Start/Deadline/Assigned to/Status. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-TASK-16 | Xem chi tiết task | Click Title task mở modal "Task info #<id>". | Hiển thị Title, Description, "Project: <title>", Start date, Deadline, Priority, Collaborators, trạng thái; nút Clone task, Edit task, Close. | 🟢 Active | — | Kiểm chứng thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

| Field (Label) | Loại UI | Required | Ràng buộc (min/max/format/default) | REQ liên quan | Ghi chú (locator) |
|---|---|---|---|---|---|
| Title | Text | Có | required | REQ-TASK-04 | id=title |
| Description | Textarea | Không | Nhận tiếng Việt có dấu | REQ-TASK-03 | id=description |
| Related to | Select2 (single) | Không | Mặc định "-" (general) | REQ-TASK-05 | id=task-context |
| Project | Select2 (ajax, có search) | Có khi Related to = Project | data-rule-required=true | REQ-TASK-06, 07 | id=project_id |
| Points | Select2 | Không | 1–5, mặc định 1 | REQ-TASK-09 | name=points |
| Milestone | Select2 | Không | Theo project | REQ-TASK-08 | id=milestone_id |
| Assign to | Select2 | Không | Mặc định user đăng nhập; reset khi đổi project | REQ-TASK-08 | id=assigned_to |
| Collaborators | Select2 multi | Không | Thành viên project | REQ-TASK-08 | id=collaborators |
| Status | Select2 | Có (mặc định) | To do / In progress / Done | REQ-TASK-10 | id=task_status_id |
| Priority | Select2 | Không | - / Minor / Major / Critical / Blocker | REQ-TASK-11 | id=priority_id |
| Labels | Select2 multi | Không | — | REQ-TASK-03 | id=project_labels |
| Start date | Text + datepicker | Không | YYYY-MM-DD | REQ-TASK-13 | id=start_date |
| Deadline | Text + datepicker | Không | YYYY-MM-DD; ≥ Start date | REQ-TASK-12, 13 | id=deadline |

## Business Rules & Validation Messages

| REQ ID | Rule / Trigger | Thông báo mong đợi (nguyên văn) |
|---|---|---|
| REQ-TASK-04 | Title trống | This field is required. |
| REQ-TASK-06 | Related to = Project, chưa chọn Project | This field is required. |
| REQ-TASK-12 | Deadline < Start date | Deadline must be equal or greater than Start date. |

## Luồng xử lý (User Flows)

**Thêm task cho project đã tạo**

1. (Arrange) Tạo client → project AT09_Project_<ts> cho client đó
2. Vào Tasks
3. Click "Add task"
4. Nhập Title, Description
5. Related to = Project
6. Project: gõ Title project → click chọn
7. Assign to = Admin Example; Status = To do; Priority
8. Click chọn Start date, Deadline (≥ Start) trên datepicker
9. Save
10. Click bộ lọc "Admin Example" → chọn "- Team member -"
11. Search Title → dòng mới có Related to = project

## Ma trận phân quyền

Out-of-scope (PO 2026-11-08) — automation chỉ chạy với 1 role Admin được cung cấp.

## Ma trận trạng thái

| Trạng thái hiện tại | Hành động cho phép | Trạng thái kế tiếp | Ai được thực hiện |
|---|---|---|---|
| To do (mặc định khi tạo) | Chọn khi tạo task | To do | Admin |
| — | Chọn In progress / Done ngay khi tạo | In progress / Done | Admin |
| Chuyển trạng thái sau khi tạo | Ngoài phạm vi khảo sát | — | — |

## Điểm mơ hồ & Rủi ro

### Ambiguities

| Mã | Câu hỏi | Nguy cơ | Mức độ | Assumption tạm | Trạng thái | Kết luận |
|---|---|---|---|---|---|---|
| AMB-01 | Thông báo "This field is required." dưới Project vẫn hiển thị sau khi đã chọn project (tới lần submit sau) — có phải lỗi UI?<br>Bước tái hiện (kiểm chứng lại 2026-11-08):<br>1. Đăng nhập admin@example.com / 123456<br>2. Vào Tasks → click "Add task" (không phải "Add multiple tasks")<br>3. Nhập Title bất kỳ<br>4. Related to: chọn "Project"<br>5. KHÔNG chọn Project, click "Save" → dòng đỏ "This field is required." hiện dưới Project<br>6. Mở dropdown Project, click chọn một project thật (không chọn "-"), VD "test project deadline and start date"<br>7. Quan sát: ô Project đã có giá trị nhưng dòng đỏ "This field is required." vẫn còn (click sang ô khác cũng không mất)<br>Lưu ý: phải Save trống ở bước 5 trước — nếu chọn Project ngay từ đầu thì không thấy lỗi. | Người dùng hiểu nhầm; assert text lỗi có thể sai. | 🟢 | Không assert việc lỗi tự biến mất trước khi submit. | ⛔ Out-of-scope | PO 2026-11-10: có thể do lỗi của site demo — chỉ test happy case (chọn Project rồi Save thành công). Bỏ TC "Chọn Project sau khi bị báo lỗi bắt buộc rồi lưu thành công"; giữ TC Project bắt buộc (đã kiểm chứng ổn định). |
| AMB-02 | Role nào được tạo task cho project? | Thiếu TC phân quyền. | 🟡 | Chỉ test Admin. | ⛔ Out-of-scope | Chỉ chạy với role Admin được cung cấp. |
| AMB-03 | Bảng Tasks mặc định lọc theo người đăng nhập — task giao cho người khác sẽ không thấy. | Assert sai "không tìm thấy task". | 🟡 | Luôn giao task cho Admin Example hoặc xoá filter trước khi search. | ✅ Đã chốt | Business rule. Trước khi search task: click bộ lọc đang chọn "Admin Example" → chọn "- Team member -" để bỏ lọc, rồi mới search (đã kiểm chứng: 11 → 34 dòng). |

### Risks

| Mã | Rủi ro | Mô tả | Mitigation |
|---|---|---|---|
| RISK-01 | Assign to bị reset khi chọn Project | Business rule: nếu chọn Assign to trước Project thì giá trị mất. | Thứ tự thao tác: Related to → Project → Assign to. |
| RISK-02 | Dropdown Project load ajax (385 option) | Chậm, dễ chọn sai khi tên gần giống. | Gõ Title unique, chờ còn 1 kết quả rồi click chọn. |
| RISK-03 | Phụ thuộc chuỗi dữ liệu Client → Project | Task cần project; project cần client. | Độc lập dữ liệu: mỗi TC tự tạo Client → Project ở bước Arrange. Chưa cần dọn dữ liệu (site demo — PO 2026-11-08). |
| RISK-04 | Bộ lọc mặc định che kết quả search | Bảng Tasks lọc sẵn theo "Admin Example" (AMB-03). | Bỏ lọc bằng cách chọn "- Team member -" trước khi search. |

## Yêu cầu phi chức năng (quan sát được)

- Modal cao, ở 1920×1080 vẫn thấy đủ nút Save.

## Danh mục Evidence

Thư mục: `evidence/` (chụp full-page, viewport 1920×1080, headless Chrome).

| Tệp | Màn hình / trạng thái | REQ làm bằng chứng |
|---|---|---|
| tasks_list_default_fullpage.png | Danh sách Tasks mặc định | REQ-TASK-01, 02 |
| tasks_list_filter_team_member_cleared_fullpage.png | Bỏ lọc: chọn "- Team member -" → 1-10 / 34 (2026-11-08) | REQ-TASK-02 |
| task_add_modal_project_error_after_select_fullpage.png | Đã chọn Project nhưng lỗi "This field is required." vẫn hiển thị (AMB-01, 2026-11-08) | REQ-TASK-06 |
| task_add_modal_default_fullpage.png | Modal Add task mặc định | REQ-TASK-03, 09 |
| task_add_modal_submit_empty_fullpage.png | Save khi trống Title | REQ-TASK-04 |
| task_add_modal_related_project_fullpage.png | Related to = Project | REQ-TASK-05 |
| task_add_modal_project_required_fullpage.png | Chưa chọn Project | REQ-TASK-06 |
| task_add_modal_project_dropdown_search_fullpage.png | Search project vừa tạo | REQ-TASK-07 |
| task_add_modal_project_selected_fullpage.png | Sau khi chọn Project (Assign to reset) | REQ-TASK-08 |
| task_add_modal_assignto_dropdown_open_fullpage.png | Dropdown Assign to | REQ-TASK-08 |
| task_add_modal_status_dropdown_open_fullpage.png | Dropdown Status | REQ-TASK-10 |
| task_add_modal_priority_dropdown_open_fullpage.png | Dropdown Priority | REQ-TASK-11 |
| task_add_modal_deadline_before_start_fullpage.png | Deadline < Start date | REQ-TASK-12, 13 |
| task_add_modal_filled_fullpage.png | Form đã điền | REQ-TASK-14 |
| task_after_save_fullpage.png | Bảng sau khi lưu | REQ-TASK-14 |
| tasks_list_search_result_fullpage.png | Search task vừa tạo | REQ-TASK-14 |
| project_view_tasks_list_tab_fullpage.png | Project detail — Tasks List | REQ-TASK-15 |
| task_detail_modal_fullpage.png | Modal Task info | REQ-TASK-16 |

## Nhật ký thay đổi

| Ngày | Nguồn | REQ ảnh hưởng | Loại | Tóm tắt thay đổi | TC cần xử lý |
|---|---|---|---|---|---|
| 2026-11-08 | UI recon | REQ-TASK-01 → REQ-TASK-16 | 🟢 Thêm | Khởi tạo tài liệu từ khảo sát UI thực tế (bản 1.0) | — (viết TC mới) |
| 2026-11-08 | Phản hồi PO/BA + kiểm chứng lại UI | REQ-TASK-02, REQ-TASK-06 | 🟡 Sửa | AMB-01: tái hiện lại, bổ sung bước tái hiện + evidence; AMB-02 out-of-scope; AMB-03 chốt cách bỏ lọc "- Team member -" (kiểm chứng 11 → 34 dòng); thêm RISK-04; RISK-01 là business rule. (bản 1.1) | Mọi bước search task thêm thao tác bỏ lọc; mọi bước nhập ngày dùng datepicker; thêm TC chọn Project sau khi báo lỗi |
| 2026-11-10 | Phản hồi PO/BA lần 2 | REQ-TASK-06 | 🟡 Sửa | AMB-01 → out-of-scope: hành vi lỗi còn hiển thị có thể do site demo, chỉ test happy case. (bản 1.2) | Bỏ TC Chọn Project sau khi bị báo lỗi |
