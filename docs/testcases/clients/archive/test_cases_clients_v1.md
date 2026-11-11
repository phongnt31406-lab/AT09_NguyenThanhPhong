# Test Cases — Clients & Contacts (tổng 61 TC · 2 part)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_clients.md](../../requirements/clients/requirements_clients.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 61 |
| Ngày sinh | 2026-11-08 |
| Định dạng TC ID | RISE_CLIENT_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Danh sách | Medium | 8 |
| Thêm client | High | 18 |
| Sửa client | High | 5 |
| Xoá client | High | 5 |
| Chi tiết client | Medium | 1 |
| Phi chức năng | Medium | 1 |
| Contacts | High | 23 |

## Bản đồ tài liệu

| File | Nhóm chức năng | Số TC | TC ID range | REQ bao phủ |
|---|---|---|---|---|
| [Part 1](parts/part_01_client_crud.md) | Quản lý Client (danh sách/thêm/sửa/xoá) | 38 | RISE_CLIENT_TC_001 – RISE_CLIENT_TC_038 | REQ-CLIENT-01 → 23 |
| [Part 2](parts/part_02_contacts.md) | Contacts của Client | 23 | RISE_CLIENT_TC_039 – RISE_CLIENT_TC_061 | REQ-CLIENT-24 → 37 (+14) |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-CLIENT-01 | Bố cục trang Clients | 1 | TC_001 | P ✅ |
| REQ-CLIENT-02 | Bảng danh sách client | 1 | TC_002 |  ✅ |
| REQ-CLIENT-03 | Tìm kiếm client | 3 | TC_003, TC_004, TC_005 | N ✅ |
| REQ-CLIENT-04 | Phân trang | 2 | TC_006, TC_007 |  ✅ |
| REQ-CLIENT-05 | Bộ lọc danh sách | 1 | TC_008 |  ✅ |
| REQ-CLIENT-06 | Mở modal Add client | 1 | TC_009 | P ✅ |
| REQ-CLIENT-07 | Loại client | 2 | TC_012, TC_030 | P ✅ |
| REQ-CLIENT-08 | Company name bắt buộc | 2 | TC_013, TC_015 | P+N+B ✅ |
| REQ-CLIENT-09 | Company name chỉ có khoảng trắng | 1 | TC_014 |  ✅ |
| REQ-CLIENT-10 | Owner | 2 | TC_009, TC_019 | P ✅ |
| REQ-CLIENT-11 | Trường thông tin tuỳ chọn | 3 | TC_011, TC_020, TC_021 | P+N ✅ |
| REQ-CLIENT-12 | Client groups & Labels | 2 | TC_011, TC_022 | P ✅ |
| REQ-CLIENT-13 | Lưu client thành công (Save) | 9 | TC_010, TC_011, TC_012, TC_016, TC_017, TC_018, TC_026, TC_038, RISE_E2E_TC_001 | P+N ✅ |
| REQ-CLIENT-14 | Save & continue | 3 | TC_023, TC_060, RISE_E2E_TC_006 | P ✅ |
| REQ-CLIENT-15 | Đóng modal không lưu | 3 | TC_024, TC_025, TC_031 | P+N ✅ |
| REQ-CLIENT-16 | Mở modal Edit client | 2 | TC_027, TC_031 | P+N ✅ |
| REQ-CLIENT-17 | Validate khi sửa | 1 | TC_029 | P+N ✅ |
| REQ-CLIENT-18 | Lưu thay đổi client | 2 | TC_028, TC_030 | P ✅ |
| REQ-CLIENT-19 | Xác nhận xoá | 2 | TC_032, TC_036 | P ✅ |
| REQ-CLIENT-20 | Huỷ xoá | 2 | TC_033, TC_036 | N ✅ |
| REQ-CLIENT-21 | Xoá client | 1 | TC_034 | P ✅ |
| REQ-CLIENT-22 | Xoá client đang có project | 1 | TC_035 |  ✅ |
| REQ-CLIENT-23 | Trang chi tiết client | 1 | TC_037 | P ✅ |
| REQ-CLIENT-24 | Tab Contacts | 1 | TC_039 |  ✅ |
| REQ-CLIENT-25 | Modal Add contact | 1 | TC_040 | P ✅ |
| REQ-CLIENT-26 | First name bắt buộc | 2 | TC_043, TC_046 | N ✅ |
| REQ-CLIENT-27 | Last name bắt buộc | 2 | TC_044, TC_046 | N ✅ |
| REQ-CLIENT-28 | Email contact bắt buộc | 2 | TC_045, TC_046 | N ✅ |
| REQ-CLIENT-29 | Email contact đúng định dạng | 2 | TC_047, TC_048 | N ✅ |
| REQ-CLIENT-30 | Email contact là duy nhất | 1 | TC_049 | N ✅ |
| REQ-CLIENT-31 | Password contact | 3 | TC_050, TC_051, TC_052 | P+N+B ✅ |
| REQ-CLIENT-32 | Password tự sinh & hiện/ẩn | 3 | TC_040, TC_053, TC_054 | P ✅ |
| REQ-CLIENT-33 | Ô Password chống autofill | 1 | TC_055 |  ✅ |
| REQ-CLIENT-34 | Phân quyền contact | 2 | TC_040, TC_056 | P ✅ |
| REQ-CLIENT-35 | Gender | 2 | TC_040, TC_057 | P ✅ |
| REQ-CLIENT-36 | Lưu contact thành công | 6 | TC_041, TC_042, TC_058, TC_059, TC_060, RISE_E2E_TC_006 | P+N ✅ |
| REQ-CLIENT-37 | Primary contact | 3 | TC_041, TC_042, TC_061 | P+N ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| clients_overview_tab_fullpage.png | Trang Clients — tab Overview | TC_001 | ✅ full-page |
| clients_list_tab_fullpage.png | Tab Clients — bảng mặc định | TC_002, TC_006, TC_007, TC_008 | ✅ full-page |
| clients_list_search_result_fullpage.png | Search theo tên | TC_003, TC_004, TC_005, TC_010, TC_011, TC_012, TC_016, TC_017… | ✅ full-page |
| client_add_modal_default_fullpage.png | Modal Add client mặc định | TC_009, TC_011, TC_019, TC_020, TC_021 | ✅ full-page |
| client_add_modal_type_person_fullpage.png | Type = Person | TC_012, TC_030 | ✅ full-page |
| client_add_modal_owner_dropdown_open_fullpage.png | Dropdown Owner mở | TC_009, TC_019 | ✅ full-page |
| client_add_modal_groups_dropdown_open_fullpage.png | Dropdown Client groups mở | TC_011, TC_022 | ✅ full-page |
| client_add_modal_submit_empty_fullpage.png | Save khi trống | TC_013, TC_015 | ✅ full-page |
| client_add_modal_whitespace_name_fullpage.png | Name toàn khoảng trắng | TC_014 | ✅ full-page |
| client_add_modal_filled_fullpage.png | Form đã điền đủ | TC_010, TC_011, TC_012, TC_016, TC_017, TC_018, TC_020, TC_021… | ✅ full-page |
| client_add_success_modal_check_fullpage.png | Lưu thành công — dấu ✓ trong modal | TC_010, TC_011, TC_012, TC_016, TC_017, TC_018, TC_026, TC_038… | ✅ full-page |
| client_after_save_overview_fullpage.png | Trang sau khi lưu (tab Overview) | TC_010, TC_011, TC_012, TC_016, TC_017, TC_018, TC_026, TC_038… | ✅ full-page |
| client_add_save_and_continue_result_fullpage.png | Sau Save & continue → Add multiple contacts | TC_023, TC_060, RISE_E2E_TC_006 | ✅ full-page |
| client_edit_modal_default_fullpage.png | Modal Edit client | TC_027, TC_031 | ✅ full-page |
| client_edit_saved_list_fullpage.png | Bảng sau khi sửa | TC_028, TC_030 | ✅ full-page |
| client_delete_confirm_dialog_fullpage.png | Dialog xác nhận xoá | TC_032, TC_033, TC_036 | ✅ full-page |
| client_delete_success_toast_fullpage.png | Toast xoá thành công | TC_034 | ✅ full-page |
| client_delete_with_project_result_fullpage.png | Xoá client đang có project | TC_035 | ✅ full-page |
| client_view_default_fullpage.png | Client detail — tab Contacts rỗng | TC_037, TC_039 | ✅ full-page |
| contact_add_modal_default_fullpage.png | Modal Add contact mặc định | TC_040, TC_053, TC_054, TC_056, TC_057 | ✅ full-page |
| contact_add_modal_submit_empty_fullpage.png | Save khi trống | TC_043, TC_044, TC_045, TC_046 | ✅ full-page |
| contact_add_modal_invalid_email_fullpage.png | Email sai định dạng | TC_047, TC_048 | ✅ full-page |
| contact_add_modal_duplicate_email_fullpage.png | Email đã tồn tại | TC_049 | ✅ full-page |
| contact_add_modal_password_short_fullpage.png | Password 5 ký tự | TC_050, TC_051, TC_052 | ✅ full-page |
| contact_add_modal_access_everything_checked_fullpage.png | Tick Can access everything | TC_040, TC_056 | ✅ full-page |
| contact_add_modal_filled_fullpage.png | Form contact đã điền | TC_041, TC_042, TC_058, TC_059, TC_060, RISE_E2E_TC_006 | ✅ full-page |
| contact_add_success_fullpage.png | Contact đã lưu — Primary contact | TC_041, TC_042, TC_058, TC_059, TC_060, TC_061, RISE_E2E_TC_006 | ✅ full-page |

## Vùng chưa có evidence

11 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): TC_006, TC_007, TC_008, TC_015, TC_016, TC_026, TC_030, TC_038, TC_042, TC_059, TC_060. Đề xuất recon bổ sung trước khi automation.

## Assumptions (Q&A Bước 2 — chờ PO/BA xác nhận)

| Mã | Phạm vi | Điểm chưa rõ | Giả định đã áp dụng | TC bị ảnh hưởng |
|---|---|---|---|---|
| ASM-02 | Clients | Company name toàn khoảng trắng (AMB-01) | Kỳ vọng báo required; hiện trạng lỗi server → @KnownIssue | RISE_CLIENT_TC_014 |
| ASM-03 | Clients | Xoá client có project (AMB-02) | Ghi nhận theo hành vi hiện tại; kỳ vọng nghiệp vụ chờ PO | RISE_CLIENT_TC_035 |
| ASM-04 | Clients | Email login details mặc định tick (AMB-04) | Mọi TC tạo contact đều bỏ tick để không gửi mail | RISE_CLIENT_TC_041 trở đi |
| ASM-08 | Chung | Chỉ có tài khoản Admin | Không sinh TC phân quyền role khác — cần tài khoản bổ sung | — |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 61 ID duy nhất, định dạng RISE_CLIENT_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 11 TC @NeedsVerify |
