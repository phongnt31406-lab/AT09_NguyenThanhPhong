# Danh mục Test Cases — RISE CRM

## 1. Bảng danh mục module

| Module | Prefix TC ID | Tài liệu | Số TC | Số part | REQ bao phủ | Cập nhật |
|---|---|---|---|---|---|---|
| Login | RISE_LOGIN_TC_ | [test_cases_login.md](login/test_cases_login.md) | 18 | 1 | 13/13 | 2026-11-08 |
| Clients & Contacts | RISE_CLIENT_TC_ | [test_cases_clients.md](clients/test_cases_clients.md) | 59 | 2 | 37/37 | 2026-11-08 |
| Projects | RISE_PRJ_TC_ | [test_cases_projects.md](projects/test_cases_projects.md) | 21 | 1 | 13/13 (+1 ⚪ OOS) | 2026-11-08 |
| Tasks | RISE_TASK_TC_ | [test_cases_tasks.md](tasks/test_cases_tasks.md) | 25 | 1 | 16/16 | 2026-11-08 |
| E2E Flow | RISE_E2E_TC_ | [test_cases_e2e_flow.md](e2e_flow/test_cases_e2e_flow.md) | 6 | 1 | 5/5 | 2026-11-08 |

**Prefix TC đã chiếm:** `RISE_LOGIN_TC_`, `RISE_CLIENT_TC_`, `RISE_PRJ_TC_`, `RISE_TASK_TC_`, `RISE_E2E_TC_`

## 2. Độ phủ so với requirements

| Module | Tổng REQ | REQ có TC | Tổng TC | @Smoke | @KnownIssue | @NeedsVerify |
|---|---|---|---|---|---|---|
| Login | 13 | 13 | 18 | 1 | 0 | 3 |
| Clients & Contacts | 37 | 37 | 59 | 4 | 0 | 9 |
| Projects | 14 | 13 (+1 OOS) | 21 | 1 | 0 | 5 |
| Tasks | 16 | 16 | 25 | 1 | 0 | 5 |
| E2E Flow | 5 | 5 | 6 | 1 | 0 | 1 |

## 3. Cấu trúc thư mục chuẩn

```
docs/testcases/
├── README.md
└── <module>/
    ├── test_cases_<module>.md    ← INDEX, tên bất biến
    ├── parts/part_NN_<slug>.md   ← khi >40 TC
    └── archive/test_cases_<module>_vN.md ← phiên bản cũ
```

## 4. Kết quả thực thi

Chưa có — kết quả sẽ lưu ở `docs/executions/`.

## 5. Quy trình sử dụng

| Tình huống | Workflow |
|---|---|
| Sinh TC từ requirements | /generate_manual_testcases_rbt |
| Chuyển TC thành automation | /generate_automation_from_testcases |

## 6. Nhật ký danh mục

| Ngày | Thay đổi |
|---|---|
| 2026-11-08 | Khởi tạo danh mục; thêm 5 module (FULL RBT) — bản 1.0 |
| 2026-11-08 | Bản 1.1: cập nhật theo phản hồi PO/BA |
| 2026-11-10 | Bản 1.2: bỏ 2 TC theo phản hồi lần 2; bản 1.0 và 1.1 lưu ở <module>/archive/ |
