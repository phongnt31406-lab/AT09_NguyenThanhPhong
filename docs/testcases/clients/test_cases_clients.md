# Test Cases — Clients & Contacts (tổng 59 TC · 2 part)

| Thông tin | Nội dung |
|---|---|
| Nguồn requirement | [requirements_clients.md](../../requirements/clients/requirements_clients.md) |
| Quy trình | FULL RBT (6 bước) — skill skills-rbt-manual-testing |
| Tổng số TC | 59 |
| Phiên bản | 1.2 — cập nhật 2026-11-10 theo phản hồi PO/BA (bản cũ lưu ở archive/) |
| Định dạng TC ID | RISE_CLIENT_TC_NNN |

## Đánh giá rủi ro theo nhóm chức năng

| Nhóm | Risk Level (cao nhất) | Số TC |
|---|---|---|
| Danh sách | Medium | 8 |
| Thêm client | High | 17 |
| Sửa client | High | 5 |
| Xoá client | High | 5 |
| Chi tiết client | Medium | 1 |
| Contacts | High | 23 |

## Bản đồ tài liệu

| File | Nhóm chức năng | Số TC | TC ID range | REQ bao phủ |
|---|---|---|---|---|
| [Part 1](parts/part_01_client_crud.md) | Quản lý Client (danh sách/thêm/sửa/xoá) | 36 | RISE_CLIENT_TC_001 – RISE_CLIENT_TC_036 | REQ-CLIENT-01 → 23 |
| [Part 2](parts/part_02_contacts.md) | Contacts của Client | 23 | RISE_CLIENT_TC_037 – RISE_CLIENT_TC_059 | REQ-CLIENT-24 → 37 (+14) |

## Bảng Đối Soát Coverage (toàn module)

Ký hiệu: P = có TC positive · N = negative · B = boundary.

| REQ ID | Mô tả ngắn | Số TC | TC IDs | Đủ Positive/Negative/Boundary? |
|---|---|---|---|---|
| REQ-CLIENT-01 | Bố cục trang Clients | 1 | TC_001 | P ✅ |
| REQ-CLIENT-02 | Bảng danh sách client | 1 | TC_002 |  ✅ |
| REQ-CLIENT-03 | Tìm kiếm client | 3 | TC_003, TC_004, TC_005 | N ✅ |
| REQ-CLIENT-04 | Phân trang | 2 | TC_006, TC_007 |  ✅ |
| REQ-CLIENT-05 | Bộ lọc danh sách | 1 | TC_008 | P ✅ |
| REQ-CLIENT-06 | Mở modal Add client | 1 | TC_009 | P ✅ |
| REQ-CLIENT-07 | Loại client | 2 | TC_012, TC_029 | P ✅ |
| REQ-CLIENT-08 | Company name bắt buộc | 1 | TC_013 | P+N ✅ |
| REQ-CLIENT-09 | Company name chỉ có khoảng trắng (business rule) | 1 | TC_014 | N ✅ |
| REQ-CLIENT-10 | Owner | 2 | TC_009, TC_018 | P ✅ |
| REQ-CLIENT-11 | Trường thông tin tuỳ chọn | 3 | TC_011, TC_019, TC_020 | P+N ✅ |
| REQ-CLIENT-12 | Client groups & Labels | 2 | TC_011, TC_021 | P ✅ |
| REQ-CLIENT-13 | Lưu client thành công (Save) | 8 | TC_010, TC_011, TC_012, TC_015, TC_016, TC_017, TC_025, RISE_E2E_TC_001 | P+N ✅ |
| REQ-CLIENT-14 | Save & continue | 3 | TC_022, TC_058, RISE_E2E_TC_006 | P ✅ |
| REQ-CLIENT-15 | Đóng modal không lưu | 3 | TC_023, TC_024, TC_030 | P+N ✅ |
| REQ-CLIENT-16 | Mở modal Edit client | 2 | TC_026, TC_030 | P+N ✅ |
| REQ-CLIENT-17 | Validate khi sửa | 1 | TC_028 | P+N ✅ |
| REQ-CLIENT-18 | Lưu thay đổi client | 2 | TC_027, TC_029 | P ✅ |
| REQ-CLIENT-19 | Xác nhận xoá | 2 | TC_031, TC_035 | P ✅ |
| REQ-CLIENT-20 | Huỷ xoá | 2 | TC_032, TC_035 | P+N ✅ |
| REQ-CLIENT-21 | Xoá client | 1 | TC_033 | P ✅ |
| REQ-CLIENT-22 | Xoá client đang có project (business rule) | 1 | TC_034 | P ✅ |
| REQ-CLIENT-23 | Trang chi tiết client | 1 | TC_036 | P ✅ |
| REQ-CLIENT-24 | Tab Contacts | 1 | TC_037 |  ✅ |
| REQ-CLIENT-25 | Modal Add contact | 1 | TC_038 | P ✅ |
| REQ-CLIENT-26 | First name bắt buộc | 2 | TC_041, TC_044 | N ✅ |
| REQ-CLIENT-27 | Last name bắt buộc | 2 | TC_042, TC_044 | N ✅ |
| REQ-CLIENT-28 | Email contact bắt buộc | 2 | TC_043, TC_044 | N ✅ |
| REQ-CLIENT-29 | Email contact đúng định dạng | 2 | TC_045, TC_046 | N ✅ |
| REQ-CLIENT-30 | Email contact là duy nhất | 1 | TC_047 | N ✅ |
| REQ-CLIENT-31 | Password contact | 3 | TC_048, TC_049, TC_050 | P+N+B ✅ |
| REQ-CLIENT-32 | Password tự sinh & hiện/ẩn | 3 | TC_038, TC_051, TC_052 | P ✅ |
| REQ-CLIENT-33 | Ô Password chống autofill | 1 | TC_053 |  ✅ |
| REQ-CLIENT-34 | Phân quyền contact | 2 | TC_038, TC_054 | P ✅ |
| REQ-CLIENT-35 | Gender | 2 | TC_038, TC_055 | P ✅ |
| REQ-CLIENT-36 | Lưu contact thành công | 6 | TC_039, TC_040, TC_056, TC_057, TC_058, RISE_E2E_TC_006 | P+N ✅ |
| REQ-CLIENT-37 | Primary contact | 3 | TC_039, TC_040, TC_059 | P+N ✅ |

## Bảng Đối Soát Evidence

| Ảnh evidence | Màn hình / trạng thái | TC dựa vào | Đầy đủ? |
|---|---|---|---|
| clients_overview_tab_fullpage.png | Trang Clients — tab Overview | TC_001 | ✅ full-page |
| clients_list_tab_fullpage.png | Tab Clients — bảng mặc định | TC_002, TC_006, TC_007, TC_008 | ✅ full-page |
| clients_list_search_result_fullpage.png | Search theo tên | TC_003, TC_004, TC_005, TC_010, TC_011, TC_012, TC_015, TC_016… | ✅ full-page |
| client_add_modal_default_fullpage.png | Modal Add client mặc định | TC_009, TC_011, TC_018, TC_019, TC_020 | ✅ full-page |
| client_add_modal_type_person_fullpage.png | Type = Person | TC_012, TC_029 | ✅ full-page |
| client_add_modal_owner_dropdown_open_fullpage.png | Dropdown Owner mở | TC_009, TC_018 | ✅ full-page |
| client_add_modal_groups_dropdown_open_fullpage.png | Dropdown Client groups mở | TC_011, TC_021 | ✅ full-page |
| client_add_modal_submit_empty_fullpage.png | Save khi trống | TC_013 | ✅ full-page |
| client_add_modal_whitespace_name_fullpage.png | Name toàn khoảng trắng | TC_014 | ✅ full-page |
| client_add_modal_filled_fullpage.png | Form đã điền đủ | TC_010, TC_011, TC_012, TC_015, TC_016, TC_017, TC_019, TC_020… | ✅ full-page |
| client_add_success_modal_check_fullpage.png | Lưu thành công — dấu ✓ trong modal | TC_010, TC_011, TC_012, TC_015, TC_016, TC_017, TC_025, RISE_E2E_TC_001 | ✅ full-page |
| client_after_save_overview_fullpage.png | Trang sau khi lưu (tab Overview) | TC_010, TC_011, TC_012, TC_015, TC_016, TC_017, TC_025, RISE_E2E_TC_001 | ✅ full-page |
| client_add_save_and_continue_result_fullpage.png | Sau Save & continue → Add multiple contacts | TC_022, TC_058, RISE_E2E_TC_006 | ✅ full-page |
| client_edit_modal_default_fullpage.png | Modal Edit client | TC_026, TC_030 | ✅ full-page |
| client_edit_saved_list_fullpage.png | Bảng sau khi sửa | TC_027, TC_029 | ✅ full-page |
| client_delete_confirm_dialog_fullpage.png | Dialog xác nhận xoá | TC_031, TC_032, TC_035 | ✅ full-page |
| client_delete_success_toast_fullpage.png | Toast xoá thành công | TC_033 | ✅ full-page |
| client_delete_with_project_result_fullpage.png | Xoá client đang có project | TC_034 | ✅ full-page |
| client_view_default_fullpage.png | Client detail — tab Contacts rỗng | TC_036, TC_037 | ✅ full-page |
| contact_add_modal_default_fullpage.png | Modal Add contact mặc định | TC_038, TC_051, TC_052, TC_054, TC_055 | ✅ full-page |
| contact_add_modal_submit_empty_fullpage.png | Save khi trống | TC_041, TC_042, TC_043, TC_044 | ✅ full-page |
| contact_add_modal_invalid_email_fullpage.png | Email sai định dạng | TC_045, TC_046 | ✅ full-page |
| contact_add_modal_duplicate_email_fullpage.png | Email đã tồn tại | TC_047 | ✅ full-page |
| contact_add_modal_password_short_fullpage.png | Password 5 ký tự | TC_048, TC_049, TC_050 | ✅ full-page |
| contact_add_modal_access_everything_checked_fullpage.png | Tick Can access everything | TC_038, TC_054 | ✅ full-page |
| contact_add_modal_filled_fullpage.png | Form contact đã điền | TC_039, TC_040, TC_056, TC_057, TC_058, RISE_E2E_TC_006 | ✅ full-page |
| contact_add_success_fullpage.png | Contact đã lưu — Primary contact | TC_039, TC_040, TC_056, TC_057, TC_058, TC_059, RISE_E2E_TC_006 | ✅ full-page |

## Vùng chưa có evidence

9 TC gắn `@NeedsVerify` (hành vi chưa được thao tác khi recon — Expected Result đánh dấu ⚠️): TC_006, TC_007, TC_008, TC_015, TC_025, TC_029, TC_040, TC_057, TC_058. Đề xuất recon bổ sung trước khi automation.

## Assumptions & quyết định PO/BA

| Mã | Phạm vi | Điểm chưa rõ | Quyết định / giả định áp dụng | TC liên quan | Trạng thái |
|---|---|---|---|---|---|
| ASM-02 | Clients | Company name toàn khoảng trắng (AMB-01) | Business rule: không trim, server báo "Oops, something went wrong!" — TC assert đúng thông báo này | RISE_CLIENT_TC_014 | ✅ PO chốt 2026-11-08 |
| ASM-03 | Clients | Xoá client có project (AMB-02) | Business rule: được phép xoá, project vẫn còn | RISE_CLIENT_TC_034 | ✅ PO chốt 2026-11-08 |
| ASM-04 | Clients | Email login details mặc định tick (AMB-04) | Site demo không gửi được email — không kiểm tra email; TC lưu contact vẫn bỏ tick để không phụ thuộc mail server | RISE_CLIENT_TC_038 | ✅ PO chốt 2026-11-08 |
| ASM-06 | Chung | Cách nhập ngày (Projects AMB-05) | Luôn click ô ngày rồi click chọn ngày trên datepicker. Ngày trong TC là ví dụ cho tháng 11/2026 — chạy tháng khác thì chọn ngày cùng số trong tháng hiện tại và đổi Expected tương ứng | RISE_CLIENT_TC_003, RISE_CLIENT_TC_011, RISE_PRJ_TC_009, RISE_PRJ_TC_011, RISE_PRJ_TC_012, RISE_TASK_TC_012, RISE_TASK_TC_013, RISE_TASK_TC_014, RISE_TASK_TC_016, RISE_E2E_TC_001 | ✅ PO chốt 2026-11-08 |
| ASM-08 | Chung | Phân quyền role khác Admin | Out-of-scope — chỉ chạy với 1 role Admin được cung cấp | — | ✅ PO chốt 2026-11-08 |
| ASM-09 | Chung | Độc lập dữ liệu (Clients RISK-01) | Mỗi TC chỉ thao tác trên dữ liệu do chính nó tạo ở bước Arrange (VD Edit/Delete phải Add trước). <ts> = yyyyMMddHHmmss sinh lúc chạy | Toàn bộ TC | ✅ PO chốt 2026-11-08 |
| ASM-10 | Chung | Dọn dữ liệu sau khi chạy (E2E AMB-01) | Chưa cần dọn — đã bỏ TC teardown | — (đã bỏ) | ✅ PO chốt 2026-11-08 |
| ASM-11 | Chung | Max length các trường text (Clients AMB-05) | Bỏ qua — đã bỏ TC 255 ký tự (Client, Project) | — (đã bỏ) | ✅ PO chốt 2026-11-08 |

## Nhật ký thay đổi

| Ngày | Phiên bản | Thay đổi |
|---|---|---|
| 2026-11-08 | 1.0 | Khởi tạo 61 TC (FULL RBT) |
| 2026-11-08 | 1.1 | Cập nhật theo phản hồi PO/BA, còn 60 TC. Bỏ hoặc đổi tên (mã bản 1.0): TC_014 "Company name chỉ gồm khoảng trắng"; TC_015 "Company name dài 255 ký tự"; TC_035 "Xoá client đang có project". Mới hoặc đổi tên (mã bản 1.1): TC_014 "Company name chỉ gồm khoảng trắng bị server từ chối"; TC_034 "Xoá client đang có project được phép, project vẫn còn". Mọi TC tự tạo dữ liệu ở bước Arrange; timestamp cố định đổi thành <ts>; mã TC được đánh lại liên tục. |
| 2026-11-10 | 1.2 | Phản hồi PO/BA lần 2, còn 59 TC. Bỏ hoặc đổi tên (mã bản 1.1): TC_037 "Session hết hạn khi đang điền form Add client". Mã TC được đánh lại liên tục. |

## Self-Quality Gate (7 tiêu chí)

| # | Tiêu chí | Kết quả |
|---|---|---|
| 1 | Unique TC ID | ✅ 59 ID duy nhất, định dạng RISE_CLIENT_TC_NNN |
| 2 | 1-to-1 Step-Expected | ✅ Steps đánh số, Expected đánh số theo đúng bước có kết quả quan sát được |
| 3 | Concrete Test Data | ✅ Dữ liệu cụ thể (AT09_*_<timestamp>, email, ngày) |
| 4 | Field Validation Coverage | ✅ Mỗi field có ràng buộc đều có ≥1 positive + ≥2 negative/boundary (field tự do: ghi nhận hành vi hiện tại) |
| 5 | Automation Metadata | ✅ 100% có Automatable / Auto Type / Tags |
| 6 | Requirement Coverage | ✅ Mọi REQ trong phạm vi có ≥1 TC |
| 7 | Evidence-verified | ✅ Đã mở 100% ảnh evidence; 9 TC @NeedsVerify |
