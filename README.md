# RISE CRM — Automation (Selenium + Java + Maven + TestNG)

Automation test cho [RISE CRM demo](https://rise.anhtester.com): Login · Clients & Contacts · Projects · Tasks · luồng E2E.
Manual test case và requirements nằm trong [`docs/`](docs/testcases/README.md) (129 TC, bản 1.2).

| Hạng mục | Công nghệ |
|---|---|
| Ngôn ngữ / build | Java 17 · Maven 3.9 |
| Web driver | Selenium 4.51 (Selenium Manager tự tải driver) |
| Test runner | TestNG 7.12 — parallel theo method |
| Report | Allure 2.35 (tên test Tiếng Việt, step Arrange/Act/Assert, ảnh + video mọi test) |
| Log | Log4j2 → console + `reports/logs/rise-automation.log` |
| Video | Chrome DevTools screencast theo từng browser → MP4 (JCodec) |
| Test data | Excel (Apache POI) — mỗi module 1 file, mỗi nhóm dữ liệu 1 sheet |
| Cấu hình | `src/test/resources/configs/*.properties` |

## 1. Chuẩn bị

1. Cài JDK 17, Maven 3.9+, Google Chrome (hoặc Edge), Allure CLI 2.x (để xem report).
2. Tạo file tài khoản từ mẫu:
   ```bash
   cp src/test/resources/configs/credentials.properties.example src/test/resources/configs/credentials.properties
   ```
   rồi điền `admin.email`, `admin.password`. File này đã nằm trong `.gitignore`.
   Có thể thay bằng biến môi trường `ADMIN_EMAIL` / `ADMIN_PASSWORD`.

## 2. Chạy test

```bash
mvn clean test                                        # toàn bộ project (testng.xml)
mvn clean test -DsuiteXmlFile=suites/smoke.xml        # chỉ group smoke
mvn clean test -DsuiteXmlFile=suites/login.xml        # chỉ module Login
mvn clean test -Dheadless=true -Dthread.count=5       # ghi đè cấu hình từ dòng lệnh
mvn clean test -Dbrowser=edge -Dvideo.mode=failed
```

`mvn clean` xoá kết quả lần chạy trước trong `reports/` (giữ lại `reports/logs/`).

## 3. Xem report

```bash
allure serve reports/allure-results                                                  # mở ngay trên trình duyệt
allure generate reports/allure-results -o reports/allure-report --clean               # HTML nhiều file
allure generate reports/allure-results -o reports/allure-report-single --single-file --clean   # 1 file, tiện gửi
```

Mỗi test trong Allure có: tên Tiếng Việt · Description · Severity · Tags · `testId` (label + link ở mục Links) ·
Test Body gồm `Arrange:` / `Act:` / `Assert:` và sub-step của Page Object · ảnh `trang_thai_cuoi_cua_test`
(PASS) hoặc `trang_thai_khi_that_bai` (FAIL) · video `video_ghi_lai_test`. Mật khẩu không được ghi vào report.

## 4. Cấu hình — `src/test/resources/configs/config.properties`

| Key | Mặc định | Ý nghĩa |
|---|---|---|
| `base.url` | `https://rise.anhtester.com` | URL hệ thống |
| `browser` | `chrome` | `chrome` · `edge` · `firefox` (video chỉ chrome/edge) |
| `headless` | `false` | `true` khi đã PASS ổn định trên UI |
| `window.width` / `window.height` | `1920` / `1080` | Viewport |
| `timeout.explicit` / `timeout.page.load` | `15` / `60` | Giây |
| `parallel.mode` / `thread.count` / `dataprovider.thread.count` | `methods` / `3` / `3` | Áp cho mọi suite qua `SuiteConfigListener` |
| `testdata.dir` | `src/test/resources/testdata` | Thư mục file Excel |
| `screenshot.fail.save.disk` | `true` | Lưu thêm ảnh test FAIL vào `reports/screenshots/` |
| `video.mode` | `all` | `all` · `failed` · `off` |
| `video.fps` · `video.max.width` · `video.max.height` · `video.quality` · `video.max.seconds` | `5` · `1280` · `720` · `60` · `300` | Chất lượng video |

Thứ tự ưu tiên: `-Dkey=value` → biến môi trường (`KEY_NAME`, dấu `.` thành `_`) → file properties.

## 5. Test data Excel — `src/test/resources/testdata/`

| File | Sheet | Dùng cho |
|---|---|---|
| `Login.xlsx` | `LoginValid`, `LoginInvalid` | RISE_LOGIN_TC_001 → 005 (đã chạy) |
| `Clients.xlsx` | `Client`, `ClientEdit`, `ClientDelete`, `Contact` | Module Clients & Contacts |
| `Projects.xlsx` | `Project` | Module Projects |
| `Tasks.xlsx` | `Task` | Module Tasks |
| `E2E.xlsx` | `FullFlow` | Luồng Login → Client → Project → Task |

Quy ước (xem sheet `Huong_dan` trong mỗi file):
- Dòng 1 là header. Cột `TC_ID` = mã manual test case, `Run` = `Y`/`N`, `Title` = tên hiển thị trong Allure.
- `<ts>` được thay bằng `yyyyMMddHHmmss_RRR` lúc chạy (cùng giá trị trong 1 dòng) → dữ liệu unique, truy vết được.
- `${key}` lấy từ file cấu hình, VD `${admin.email}` — không ghi tài khoản vào Excel.
- Thêm dòng mới vào sheet `LoginInvalid` là có thêm 1 test, không cần sửa code.

## 6. Cấu trúc thư mục

```text
Final_AT09/
├── pom.xml · testng.xml            # build + suite toàn project
├── suites/                         # smoke.xml, login.xml
├── src/main/java/com/rise/
│   ├── annotations/                # @TestCaseId (label testId + link), @Tags
│   ├── config/                     # ConfigReader
│   ├── constants/                  # FrameworkConstants (đường dẫn reports/, tên attachment)
│   ├── drivers/                    # DriverFactory, DriverManager (ThreadLocal)
│   ├── listeners/                  # SuiteConfigListener, TestListener, ReportListener
│   ├── pages/                      # BasePage + Page Object theo module
│   │   ├── login/ · dashboard/     # đã có
│   │   └── clients/ · projects/ · tasks/ · components/   # phạm vi trong package-info.java
│   ├── utils/                      # ExcelReader, TestDataRow, DataGenerator, ScreenshotUtil, AllureManager
│   └── video/                      # VideoRecorder (CDP screencast → MP4), VideoManager
├── src/main/resources/log4j2.xml
├── src/test/java/com/rise/
│   ├── base/BaseTest.java
│   ├── dataproviders/              # TestDataFiles, LoginDataProvider
│   └── tests/
│       ├── login/LoginTest.java    # đã có
│       └── clients/ · projects/ · tasks/ · e2e/
├── src/test/resources/
│   ├── configs/                    # config.properties, credentials.properties(.example)
│   ├── testdata/                   # *.xlsx
│   └── allure.properties
├── docs/                           # requirements + manual test cases
└── reports/                        # (git-ignored) allure-results, allure-report, logs, screenshots, videos, surefire
```

## 7. Quy ước viết test mới

- Page class `XxxPage` trong `pages/<module>/`, locator `private final By` khai báo đầu class, method nghiệp vụ gắn `@Step`.
- Test class `XxxTest extends BaseTest` trong `tests/<module>/`, method `testXxx()`; mỗi test gắn `@Test(description = "<tên Tiếng Việt>", groups = ...)`,
  `@TestCaseId`, `@Severity`, `@Tags`, `@Description`; thân test chia `Allure.step("Arrange: …")` / `"Act: …"` / `"Assert: …"`.
- Mỗi test tự tạo dữ liệu của nó ở bước Arrange (độc lập dữ liệu) — không dùng dữ liệu có sẵn trên site demo.
- Chỉ dùng explicit wait trong `BasePage`; không `Thread.sleep`, không `System.out` (dùng logger).
- Assertion luôn có message mô tả kỳ vọng.

## 8. Lưu ý kỹ thuật (từ khảo sát UI)

- Dropdown là Select2 (không dùng được `Select` của Selenium); nhấn ESC khi dropdown mở sẽ đóng cả modal.
- Ô Password của Contact readonly tới khi được click → click trước rồi mới nhập; bỏ tick "Email login details to this user".
- Ngày phải click chọn trên datepicker (gõ tay không được lưu).
- Bảng Tasks lọc mặc định theo "Admin Example" → chọn "- Team member -" trước khi search.
- Chrome 156 mới hơn bản CDP Selenium 4.51 hỗ trợ (v155) — Selenium tự dùng bản gần nhất, video vẫn hoạt động.
