# Automation Progress — RISE CRM

## Framework (xong 2026-11-10)
- [x] Scaffold Selenium + Java + Maven + TestNG, Log4j2, Allure, video CDP, parallel, properties, Excel
- [x] Login: RISE_LOGIN_TC_001 → 005 (5/5 PASS ổn định)

## Module Clients & Contacts (đang làm)
- [x] Bước 1: Phân tích 59 TC (docs/testcases/clients — part_01 client CRUD 36 TC, part_02 contacts 23 TC)
- [x] Bước 2: Recon DOM thật (danh sách, filter, phân trang, modal Add/Edit, dialog xoá, toast, client detail, modal contact, modal Add project)
- [ ] Bước 3: Thiết kế POM (components Select2/Modal/DataTable/ConfirmDialog + ClientsPage, ClientFormModal, ClientDetailPage, ContactFormModal, ProjectsPage)
- [ ] Bước 4: Test data Clients.xlsx theo sheet
- [ ] Bước 5: Test classes
- [ ] Bước 6: Chạy + auto-heal, PASS 2 lần liên tiếp

## Phát hiện khi recon (ảnh hưởng automation)
- Modal dùng chung #ajaxModal / #confirmationModal, KHÔNG bị gỡ khỏi DOM — chỉ bỏ class show
- Bootstrap bỏ qua thao tác đóng khi modal đang animation → phải chờ opacity = 1 trước khi click
- Coi là "đã đóng" khi: display none + không còn .modal-backdrop + body hết class modal-open
- Footer #ajaxModal có 2 nút Close (1 ẩn trong #ajaxModalOriginalContent) → chỉ dùng nút trong form
- Bộ lọc danh sách (Owner, Client groups) và page size tự reload bảng, không cần nút áp dụng
- Ô Client của Add project là select[name=client_id] không có id → locate container qua select2 liền trước
