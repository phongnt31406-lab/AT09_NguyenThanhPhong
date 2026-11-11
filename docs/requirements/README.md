# Danh mục Requirements — RISE CRM (rise.anhtester.com)

> Điểm vào cấp hệ thống. Đọc file này trước khi gán REQ ID mới.

## 1. Bảng danh mục module

| Module | Prefix | Tài liệu | REQ đã dùng | Mã kế tiếp | AMB treo | Story | Cập nhật |
|---|---|---|---|---|---|---|---|
| Login (Admin) | LOGIN | [requirements_login.md](login/requirements_login.md) | REQ-LOGIN-01 → REQ-LOGIN-13 (13) | REQ-LOGIN-14 | 0 | — | 2026-11-08 |
| Clients & Contacts | CLIENT | [requirements_clients.md](clients/requirements_clients.md) | REQ-CLIENT-01 → REQ-CLIENT-37 (37) | REQ-CLIENT-38 | 0 | 2 | 2026-11-08 |
| Projects (Add project) | PRJ | [requirements_projects.md](projects/requirements_projects.md) | REQ-PRJ-01 → REQ-PRJ-14 (14) | REQ-PRJ-15 | 0 | — | 2026-11-08 |
| Tasks (Add task) | TASK | [requirements_tasks.md](tasks/requirements_tasks.md) | REQ-TASK-01 → REQ-TASK-16 (16) | REQ-TASK-17 | 0 | — | 2026-11-08 |
| Luồng E2E Login → Client → Project → Task | E2E | [requirements_e2e_flow.md](e2e_flow/requirements_e2e_flow.md) | REQ-E2E-01 → REQ-E2E-05 (5) | REQ-E2E-06 | 0 | — | 2026-11-08 |

**Prefix đã chiếm:** `LOGIN`, `CLIENT`, `PRJ`, `TASK`, `E2E` — module mới phải chọn prefix khác.

## 2. Trạng thái REQ toàn hệ thống

| Module | 🟢 Active | 🟡 Changed | 🔴 Deprecated | ⚪ Out-of-scope |
|---|---|---|---|---|
| Login (Admin) | 13 | 0 | 0 | 0 |
| Clients & Contacts | 35 | 2 | 0 | 0 |
| Projects (Add project) | 10 | 3 | 0 | 1 |
| Tasks (Add task) | 14 | 2 | 0 | 0 |
| Luồng E2E Login → Client → Project → Task | 5 | 0 | 0 | 0 |

## 3. Ambiguity còn treo

Không còn AMB nào chờ trả lời (đã chốt toàn bộ ngày 2026-11-10).

## 4. Cấu trúc thư mục chuẩn

```
docs/requirements/
├── README.md
└── <module>/
    ├── requirements_<module>.md   ← INDEX, tên bất biến
    ├── evidence/*.png
    └── stories/story_NN_<slug>.md ← chỉ khi tách
```

## 5. Quy trình sử dụng

| Tình huống | Workflow | Ghi vào đâu |
|---|---|---|
| Khảo sát module từ website | /generate_requirements_from_website | docs/requirements/<module>/ |
| Phân tích ticket/tài liệu | /analyze_requirement_document | docs/requirements/<module>/analysis/ |
| Sinh test case | /generate_manual_testcases_rbt | docs/testcases/<module>/ |

## 6. Nhật ký danh mục

| Ngày | Thay đổi |
|---|---|
| 2026-11-08 | Khởi tạo danh mục; thêm 5 module LOGIN, CLIENT, PRJ, TASK, E2E (UI recon) |
| 2026-11-08 | Bản 1.1: cập nhật theo phản hồi PO/BA — chốt AMB, đánh dấu REQ 🟡 Changed / ⚪ Out-of-scope, thêm 2 evidence Tasks |
| 2026-11-10 | Bản 1.2: Login AMB-03, Tasks AMB-01 → out-of-scope; không còn AMB treo |
