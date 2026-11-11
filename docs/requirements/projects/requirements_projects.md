# Requirements — Projects (Add project)

| Thông tin | Nội dung |
|---|---|
| Module | Projects (Add project) |
| Prefix | PRJ |
| URL | https://rise.anhtester.com/projects/all_projects |
| Nguồn | UI recon trên hệ thống thật (Playwright + Chrome, headless) — tài khoản admin@example.com |
| Ngày khảo sát | 2026-11-08 |
| Tổng số REQ | 14 |
| Dải mã đã dùng | REQ-PRJ-01 → REQ-PRJ-14 · AMB-01 → AMB-05 · RISK-01 → RISK-03 |
| Mã kế tiếp | Đợt phân tích sau bắt đầu từ REQ-PRJ-15 · AMB-06 · RISK-04 — KHÔNG đánh lại từ 01 |

## Tổng quan

Tạo dự án (Project) gắn với một Client đã tồn tại. Project là dữ liệu cha để tạo Task.

**Trong phạm vi:**
- Danh sách Projects (bảng, search)
- Modal Add project — chọn Client đã tạo
- Hiển thị project trong Client detail và Project detail
- Modal Edit project (quan sát để đối chiếu)

**Ngoài phạm vi:**
- Import projects, Manage labels
- Các tab Milestones, Gantt, Timesheets... của project
- Xoá project
- Ràng buộc Deadline ≥ Start date — site demo không validate, PO chốt bỏ qua (AMB-01)
- Price không phải số / số âm — chỉ test happy case (AMB-02)
- Độ dài tối đa (max length) Title
- Phân quyền các role khác Admin (AMB-03)

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-PRJ-01 | Danh sách Projects | Trang /projects/all_projects hiển thị bảng #project-table. | Cột: ID, Title, Client, Price, Start date, Deadline, Progress, Status; nút Manage labels, Import projects, Add project; bộ lọc Label, Start date, Deadline, Status; ô Search. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-02 | Modal Add project | Click "Add project" mở modal "Add project". | Trường theo thứ tự: Title, Project type, Client, Description, Start date, Deadline, Price, Labels; nút Close, Save & continue, Save. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-03 | Title bắt buộc | Bỏ trống Title khi Save. | "This field is required." dưới Title; modal không đóng. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-04 | Project type | Select2 Project type: Client Project (mặc định) / Internal Project. | Chọn Internal Project → trường Client bị ẩn; chọn lại Client Project → Client hiện lại. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-05 | Client mặc định | Client là select bắt buộc nhưng KHÔNG có option rỗng — mặc định chọn sẵn client đầu tiên của danh sách. | Mở modal → Client đã có giá trị (lúc recon là "DEV D"), không có option trống. Quy ước test: luôn chọn tường minh client do chính test tạo, không dùng giá trị mặc định (AMB-04). | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-PRJ-06 | Chọn Client đã tạo | Dropdown Client có ô tìm kiếm; client vừa tạo ở module Clients xuất hiện và chọn được. | Gõ tên client → còn đúng 1 option → click → client_id = ID client vừa tạo. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-07 | Chọn ngày bằng datepicker | Start date và Deadline chọn bằng cách click ngày trên datepicker (đúng thao tác người dùng), định dạng YYYY-MM-DD. Gõ tay ngày vào ô không được lưu (AMB-05). | Click ô → lịch tháng hiện tại, tuần bắt đầu Mo, ngày hôm nay được tô; click ngày 10 → "2026-11-10". | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-PRJ-08 | Không ràng buộc Deadline ≥ Start date | Hệ thống KHÔNG chặn Deadline sớm hơn Start date. | Start 2026-11-10, Deadline 2026-11-05 → lưu thành công. Site demo không validate — PO chốt không kiểm thử (AMB-01). | ⚪ Out-of-scope | 2026-11-08 | Kiểm chứng thực tế |
| REQ-PRJ-09 | Price — happy case | Price nhập số hợp lệ được lưu. (Ghi nhận: hệ thống không validate số — "abc" vẫn lưu và hiển thị "-"; ngoài phạm vi test theo AMB-02.) | Price 1500 → lưu thành công cùng project. | 🟡 Changed | 2026-11-08 | Kiểm chứng thực tế |
| REQ-PRJ-10 | Lưu project thành công | Save với Title + Client hợp lệ. | POST /projects/save success; modal đóng; bảng có dòng mới: Title, Client đúng, Status "Open", Progress 0. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-11 | Project trong Client detail | Project gắn client hiển thị ở tab Projects của Client detail. | Tab Projects liệt kê project; thẻ "Projects" của client tăng 1. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-12 | Trang chi tiết project | Click Title → /projects/view/{id}. | Tiêu đề = Title; 14 tab (Overview, Tasks List, Tasks Kanban, Milestones, Gantt, Notes, Files, Comments, Customer feedback, Timesheets, Invoices, Payments, Expenses, Contracts); Overview hiển thị Start date, Deadline, Client, Project members (người tạo là member). | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-13 | Modal Edit project | Icon Edit mở "Edit project" với dữ liệu hiện tại. | Có thêm trường Status (Open) so với Add; nút Close, Save. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-PRJ-14 | Save & continue | Nút "Save & continue" có trên modal Add project. | Chỉ quan sát, chưa kiểm chứng hành vi sau khi click. | 🟢 Active | — | UI thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

| Field (Label) | Loại UI | Required | Ràng buộc (min/max/format/default) | REQ liên quan | Ghi chú (locator) |
|---|---|---|---|---|---|
| Title | Text | Có | required; không maxlength | REQ-PRJ-03 | id=title |
| Project type | Select2 (single) | Có | Client Project (mặc định) / Internal Project | REQ-PRJ-04 | id=project-type-dropdown |
| Client | Select2 (single, có search) | Có (khi Client Project) | Không có option rỗng; mặc định client đầu tiên | REQ-PRJ-05, 06 | select[name=client_id] |
| Description | Textarea | Không | — | REQ-PRJ-02 | id=description |
| Start date | Text + datepicker | Không | YYYY-MM-DD; data-convert-date-format | REQ-PRJ-07 | id=start_date |
| Deadline | Text + datepicker | Không | YYYY-MM-DD; KHÔNG so sánh với Start date | REQ-PRJ-07, 08 | id=deadline |
| Price | Text | Không | Không validate số | REQ-PRJ-09 | id=price |
| Labels | Select2 multi | Không | — | REQ-PRJ-02 | id=project_labels |
| Status (chỉ Edit) | Select2 | — | Mặc định Open | REQ-PRJ-13 | status_id |

## Business Rules & Validation Messages

| REQ ID | Rule / Trigger | Thông báo mong đợi (nguyên văn) |
|---|---|---|
| REQ-PRJ-03 | Title trống | This field is required. |

## Luồng xử lý (User Flows)

**Thêm project cho client đã tạo**

1. (Arrange) Tạo client AT09_Client_<ts> ở module Clients
2. Vào Projects
3. Click "Add project"
4. Nhập Title
5. Giữ Project type = Client Project
6. Mở dropdown Client → gõ tên client vừa tạo → click chọn
7. Click ô Start date → click ngày trên datepicker; tương tự với Deadline; nhập Price
8. Save
9. Search Title → dòng mới với Client đúng, Status Open

## Ma trận phân quyền

Out-of-scope (PO 2026-11-08) — automation chỉ chạy với 1 role Admin được cung cấp.

## Ma trận trạng thái

Project có Status (Open — quan sát trong Edit và bảng). Các trạng thái khác (Completed, Hold, Canceled — thấy trên Dashboard) và chuyển trạng thái nằm ngoài phạm vi Add project → Không khảo sát.

## Điểm mơ hồ & Rủi ro

### Ambiguities

| Mã | Câu hỏi | Nguy cơ | Mức độ | Assumption tạm | Trạng thái | Kết luận |
|---|---|---|---|---|---|---|
| AMB-01 | Deadline sớm hơn Start date có hợp lệ không? (Task thì có chặn, Project thì không.) | Không nhất quán giữa 2 module; dữ liệu báo cáo sai. | 🔴 | Coi là BUG; TC kỳ vọng bị chặn → hiện tại FAIL, gắn @KnownIssue. | ⛔ Out-of-scope | Site demo không validate chỗ này, kết quả tuỳ dữ liệu test → bỏ TC. REQ-PRJ-08 giữ lại để ghi nhận hành vi, trạng thái ⚪ Out-of-scope. |
| AMB-02 | Price có phải là số không âm? | Giá trị rác bị lưu thành rỗng mà không báo. | 🟡 | Kỳ vọng chỉ nhận số ≥ 0; TC "abc" gắn @KnownIssue. | ✅ Đã chốt | Chỉ test happy case (Price là số hợp lệ). Bỏ TC Price "abc" và số âm. |
| AMB-03 | Role nào được tạo project? | Thiếu TC phân quyền. | 🟡 | Chỉ test Admin. | ⛔ Out-of-scope | Chỉ chạy với role Admin được cung cấp. |
| AMB-04 | Client mặc định là client đầu tiên — có nên có option trống "- Select client -"? | Người dùng/automation quên chọn → project gắn nhầm "DEV D". | 🟡 | Automation luôn chọn client tường minh và assert lại client sau khi lưu. | ✅ Đã chốt | Đi từ luồng tạo client, rồi add project cho đúng client vừa tạo — không dùng DEV D. TC không assert tên client mặc định. |
| AMB-05 | Ngày gõ tay (không qua datepicker) có được lưu không? Lần tạo đầu gõ "2026-11-10" trực tiếp thì project lưu với ngày rỗng. | TC/automation nhập ngày bằng sendKeys có thể mất dữ liệu. | 🟡 | Chọn ngày qua datepicker (hoặc sendKeys rồi blur + kiểm tra value) — cần recon thêm. | ✅ Đã chốt | Luôn click vào ô ngày rồi click chọn ngày trên datepicker — đó mới là thao tác của người dùng. Không gõ tay ngày. |

### Risks

| Mã | Rủi ro | Mô tả | Mitigation |
|---|---|---|---|
| RISK-01 | Dropdown Client 340+ option | Mở dropdown chậm; tên client trùng nhau. | Luôn gõ tên unique vào ô search của Select2 rồi click chọn. |
| RISK-02 | Ngày nhập không được commit | Gõ tay ngày không được lưu (AMB-05). | Click chọn ngày trên datepicker; nếu ngày ở tháng khác thì bấm mũi tên chuyển tháng; assert lại ngày trong bảng sau khi lưu. |
| RISK-03 | Phụ thuộc dữ liệu Client | Add project cần client tồn tại; client bị người khác xoá → test fail. | Độc lập dữ liệu: mỗi TC tự tạo client ở bước Arrange rồi tạo project cho chính client đó. |

## Yêu cầu phi chức năng (quan sát được)

- Modal hiển thị đủ trường không cần cuộn ở 1920×1080.

## Danh mục Evidence

Thư mục: `evidence/` (chụp full-page, viewport 1920×1080, headless Chrome).

| Tệp | Màn hình / trạng thái | REQ làm bằng chứng |
|---|---|---|
| projects_list_default_fullpage.png | Danh sách Projects | REQ-PRJ-01 |
| project_add_modal_default_fullpage.png | Modal Add project mặc định | REQ-PRJ-02, 05 |
| project_add_modal_submit_empty_fullpage.png | Save khi trống Title | REQ-PRJ-03 |
| project_add_modal_type_internal_fullpage.png | Project type = Internal | REQ-PRJ-04 |
| project_add_modal_client_dropdown_open_fullpage.png | Dropdown Client mở | REQ-PRJ-05 |
| project_add_modal_client_dropdown_search_fullpage.png | Search client vừa tạo | REQ-PRJ-06 |
| project_add_modal_datepicker_open_fullpage.png | Datepicker Start date | REQ-PRJ-07 |
| project_add_modal_deadline_before_start_fullpage.png | Bảng sau khi lưu Deadline < Start | REQ-PRJ-08, 10 |
| project_edit_modal_deadline_picker_fullpage.png | Edit project + datepicker | REQ-PRJ-13 |
| project_edit_saved_invalid_dates_list_fullpage.png | Bảng: 2026-11-10 / 2026-11-05, Price "-" | REQ-PRJ-08, 09 |
| projects_list_search_result_fullpage.png | Search project vừa tạo | REQ-PRJ-10 |
| client_view_projects_tab_fullpage.png | Client detail — tab Projects | REQ-PRJ-11 |
| project_view_overview_fullpage.png | Project detail — Overview | REQ-PRJ-12 |

## Nhật ký thay đổi

| Ngày | Nguồn | REQ ảnh hưởng | Loại | Tóm tắt thay đổi | TC cần xử lý |
|---|---|---|---|---|---|
| 2026-11-08 | UI recon | REQ-PRJ-01 → REQ-PRJ-14 | 🟢 Thêm | Khởi tạo tài liệu từ khảo sát UI thực tế (bản 1.0) | — (viết TC mới) |
| 2026-11-08 | Phản hồi PO/BA | REQ-PRJ-05, 07, 08, 09 | 🟡 Sửa | REQ-PRJ-08 → Out-of-scope (AMB-01); REQ-PRJ-09 chỉ happy case (AMB-02); REQ-PRJ-05 không dùng client mặc định (AMB-04); REQ-PRJ-07 bắt buộc click chọn ngày trên datepicker (AMB-05); AMB-03 out-of-scope. (bản 1.1) | Bỏ TC Deadline < Start, Price "abc", Price âm, Title 255 ký tự; sửa TC modal mặc định, client mặc định; mọi bước nhập ngày dùng datepicker |
