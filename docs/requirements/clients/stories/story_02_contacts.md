# STORY-CLIENT-02 — Contacts của Client

← [Về index requirements_clients.md](../requirements_clients.md)

| Thông tin | Nội dung |
|---|---|
| Story | STORY-CLIENT-02 |
| REQ bao phủ | REQ-CLIENT-24 → REQ-CLIENT-37 |
| AMB / RISK tham chiếu | AMB-04, 05 · RISK-04, 05, 06 (xem index) |

## Yêu cầu chức năng

> Trạng thái: 🟢 Active · 🟡 Changed (sửa theo phản hồi PO/BA) · ⚪ Out-of-scope (giữ để ghi nhận hành vi, không viết TC).

| REQ ID | Tên yêu cầu | Mô tả | Acceptance Criteria | Trạng thái | Cập nhật lần cuối | Nguồn |
|---|---|---|---|---|---|---|
| REQ-CLIENT-24 | Tab Contacts | Tab Contacts của client detail hiển thị bảng contact. | Cột: Name, Job Title, Email, Phone, Skype; nút "Send invitation", "Add contact"; rỗng → "No record found." | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-25 | Modal Add contact | Click "Add contact" mở modal "Add contact". | Trường: First name, Last name, Email, Phone, Skype, Job Title, Gender, Password (+Generate, nút mắt), Permissions, checkbox "Email login details to this user"; nút Close, Save. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-26 | First name bắt buộc | Bỏ trống First name. | "This field is required." dưới First name. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-27 | Last name bắt buộc | Bỏ trống Last name. | "This field is required." dưới Last name. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-28 | Email contact bắt buộc | Bỏ trống Email. | "This field is required." dưới Email. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-29 | Email contact đúng định dạng | Email sai định dạng (VD "an.nguyen@"). | "Please enter a valid email address." | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-30 | Email contact là duy nhất | Email đã dùng cho user/contact khác → server từ chối. | Alert đỏ trong modal: "The email address you have entered is already registered."; modal giữ nguyên dữ liệu. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-31 | Password contact | Password bắt buộc, tối thiểu 6 ký tự. | Trống → "This field is required."; 5 ký tự → "Please enter at least 6 characters." | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-32 | Password tự sinh & hiện/ẩn | Mở modal đã có sẵn password 8 ký tự; nút Generate sinh password mới; nút mắt chuyển type password ↔ text. | Generate → giá trị mới 8 ký tự (VD "lAuL.X4N"); click mắt → type="text". | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-33 | Ô Password chống autofill | Ô Password có readonly cho tới khi được focus (onfocus gỡ readonly). | Gõ phím khi chưa focus không có tác dụng; click vào ô rồi mới nhập được. (Lưu ý cho automation.) | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-34 | Phân quyền contact | Permissions: checkbox "Can access everything" (mặc định bỏ tick) và multi-select "Can access only" (mặc định "Projects"). | Tick "Can access everything" → ẩn "Can access only". Ghi chú: "Note: The primary contact will get full permission." | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-35 | Gender | Radio Male / Female / Other, không chọn sẵn, tuỳ chọn. | Lưu được khi không chọn Gender. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-36 | Lưu contact thành công | Save với dữ liệu hợp lệ. | POST /clients/save_contact success; modal đóng; contact xuất hiện trong tab Contacts đúng Name/Job Title/Email/Phone. | 🟢 Active | — | Kiểm chứng thực tế |
| REQ-CLIENT-37 | Primary contact | Contact đầu tiên của client tự động là Primary contact. | Badge "Primary contact" cạnh tên; cột Primary contact ở bảng Clients hiển thị tên contact. | 🟢 Active | — | Kiểm chứng thực tế |

## Đặc tả trường dữ liệu (Field Specifications)

| Field (Label) | Loại UI | Required | Ràng buộc (min/max/format/default) | REQ liên quan | Ghi chú (locator) |
|---|---|---|---|---|---|
| First name | Text | Có | required | REQ-CLIENT-26 | id=first_name |
| Last name | Text | Có | required | REQ-CLIENT-27 | id=last_name |
| Email | Text | Có | required + email; duy nhất (server) | REQ-CLIENT-28, 29, 30 | id=email |
| Phone / Skype / Job Title | Text | Không | — | REQ-CLIENT-25 | id=phone, skype, job_title |
| Gender | Radio | Không | Male / Female / Other, không chọn sẵn | REQ-CLIENT-35 | name=gender |
| Password | Password | Có | required, minlength 6; sinh sẵn 8 ký tự; readonly tới khi focus | REQ-CLIENT-31, 32, 33 | id=login_password |
| Can access everything | Checkbox | Không | Mặc định bỏ tick | REQ-CLIENT-34 | id=can_access_everything |
| Can access only | Select2 multi | Không | Mặc định "Projects" | REQ-CLIENT-34 | id=can_access_only |
| Email login details to this user | Checkbox | Không | Mặc định TICK | REQ-CLIENT-25 | id=email_login_details |

## Business Rules & Validation Messages

| REQ ID | Rule / Trigger | Thông báo mong đợi (nguyên văn) |
|---|---|---|
| REQ-CLIENT-26/27/28 | First/Last name/Email trống | This field is required. |
| REQ-CLIENT-29 | Email sai định dạng | Please enter a valid email address. |
| REQ-CLIENT-30 | Email đã tồn tại | The email address you have entered is already registered. |
| REQ-CLIENT-31 | Password < 6 ký tự | Please enter at least 6 characters. |
| REQ-CLIENT-31 | Password trống | This field is required. |

## Luồng xử lý (User Flows)

**Thêm contact**

1. Mở client detail (click tên client)
2. Tab Contacts → "Add contact"
3. Nhập First name, Last name, Email (unique), Password (click ô trước khi nhập)
4. Bỏ tick "Email login details to this user"
5. Save → contact xuất hiện, contact đầu tiên có badge Primary contact

