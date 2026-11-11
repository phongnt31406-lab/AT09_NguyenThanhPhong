# Requirements — Luồng E2E Login → Client → Project → Task

| Thông tin | Nội dung |
|---|---|
| Module | Luồng E2E Login → Client → Project → Task |
| Prefix | E2E |
| URL | https://rise.anhtester.com |
| Nguồn | UI recon trên hệ thống thật (Playwright + Chrome, headless) — tài khoản admin@example.com |
| Ngày khảo sát | 2026-11-08 |
| Tổng số REQ | 5 |
| Dải mã đã dùng | REQ-E2E-01 → REQ-E2E-05 · AMB-01 → AMB-01 · RISK-01 → RISK-01 |
| Mã kế tiếp | Đợt phân tích sau bắt đầu từ REQ-E2E-06 · AMB-02 · RISK-02 — KHÔNG đánh lại từ 01 |

## Tổng quan

Yêu cầu liên module: dữ liệu tạo ở bước trước phải dùng được ở bước sau trong cùng một phiên đăng nhập. Tài liệu chỉ chứa các REQ liên kết; chi tiết từng màn hình xem module tương ứng.

**Trong phạm vi:**
- Login Admin → Add Client (+Contact) → Add Project chọn Client đó → Add Task chọn Project đó
- Kiểm tra liên kết dữ liệu giữa các màn hình

**Ngoài phạm vi:**
- Chi tiết validation từng form (đã có ở module riêng)

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-E2E-01 | Client mới dùng được cho Project | Client vừa tạo xuất hiện ngay trong dropdown Client của Add project. | Không cần reload; search theo tên ra đúng 1 option. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-E2E-02 | Project mới dùng được cho Task | Project vừa tạo (gắn client) xuất hiện trong dropdown Project của Add task (Related to = Project). | Search Title project ra đúng 1 option. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-E2E-03 | Liên kết Client ↔ Project | Project hiển thị ở tab Projects của Client detail, và cột Client ở bảng Projects là tên client. | Thẻ Projects của client = 1. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-E2E-04 | Liên kết Project ↔ Task | Task hiển thị trong Tasks List của project; cột Related to ở bảng Tasks là Title project. | Modal Task info hiển thị "Project: <title>". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-E2E-05 | Luồng liên tục trong 1 phiên | Toàn bộ chuỗi Login → Add Client → Add Project → Add Task thực hiện liên tục không phải đăng nhập lại, không lỗi. | Mọi bước trả success; dữ liệu cuối cùng truy vết được ngược về client. | 🟢 Active | — | Kiểm chứng thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

Không áp dụng — module không có form riêng (dùng form của các module khác).

## Business Rules & Validation Messages

Không áp dụng.

## Luồng xử lý (User Flows)

**Full flow**

1. Đăng nhập admin@example.com / 123456
2. Clients → Add client "AT09_Client_<ts>" → Save
3. Mở client → Add contact
4. Projects → Add project "AT09_Project_<ts>", Client = client vừa tạo → click chọn ngày trên datepicker → Save
5. Tasks → Add task "AT09_Task_<ts>", Related to = Project, Project = project vừa tạo → Save
6. Bỏ lọc "- Team member -", kiểm tra liên kết ở Client detail, Project detail, bảng Tasks

## Ma trận phân quyền

Admin.

## Ma trận trạng thái

Không áp dụng.

## Điểm mơ hồ & Rủi ro

### Ambiguities

| Mã | Câu hỏi | Nguy cơ | Mức độ | Assumption tạm | Trạng thái | Kết luận |
|---|---|---|---|---|---|---|
| AMB-01 | Dữ liệu tạo bởi luồng E2E có cần dọn sau khi chạy không? (Xoá client không xoá project — xem AMB-02 module Clients.) | Rác dữ liệu trên site demo dùng chung. | 🟡 | Teardown xoá theo thứ tự Task → Project → Client. | ✅ Đã chốt | Hiện chưa cần dọn dữ liệu vì là site demo → bỏ TC teardown. |

### Risks

| Mã | Rủi ro | Mô tả | Mitigation |
|---|---|---|---|
| RISK-01 | Chuỗi phụ thuộc dài | Một bước fail làm hỏng các bước sau. | Mỗi bước assert rõ ràng; dữ liệu đặt tên chung 1 timestamp để truy vết. Mỗi TC E2E tự chạy chuỗi của riêng nó, không dựa vào TC khác. |

## Yêu cầu phi chức năng (quan sát được)

Không ghi nhận riêng.

## Danh mục Evidence

Thư mục: `evidence/` (chụp full-page, viewport 1920×1080, headless Chrome).

| Tệp | Màn hình / trạng thái | REQ làm bằng chứng |
|---|---|---|
| (dùng chung evidence của các module) | clients_list_search_result, project_add_modal_client_dropdown_search, task_add_modal_project_dropdown_search, client_view_projects_tab, project_view_tasks_list_tab | REQ-E2E-01 → 05 |

## Nhật ký thay đổi

| Ngày | Nguồn | REQ ảnh hưởng | Loại | Tóm tắt thay đổi | TC cần xử lý |
|---|---|---|---|---|---|
| 2026-11-08 | UI recon | REQ-E2E-01 → REQ-E2E-05 | 🟢 Thêm | Khởi tạo tài liệu từ khảo sát UI thực tế (bản 1.0) | — (viết TC mới) |
| 2026-11-08 | Phản hồi PO/BA | — | 🟡 Sửa | AMB-01: chưa cần dọn dữ liệu. (bản 1.1) | Bỏ TC teardown; TC liên kết không còn phụ thuộc TC_001 |
