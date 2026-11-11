package com.rise.pages;

import com.rise.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Hàm thao tác dùng chung cho mọi Page Object — chỉ dùng explicit wait (WebDriverWait), không hard sleep.
 */
public abstract class BasePage {

    private static final By TOAST_MESSAGE = By.xpath("//div[contains(@class,'app-alert-message')][not(ancestor::div[contains(@class,'modal')])]");

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("timeout.explicit")));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    protected void openPath(String path) {
        String url = ConfigReader.get("base.url") + path;
        log.info("Mở URL: {}", url);
        driver.get(url);
    }

    /**
     * Chờ phần tử hiển thị. Trang RISE cuộn bằng container overflow:hidden (perfect-scrollbar) nên phần tử nằm
     * ngoài vùng nhìn bị Selenium coi là ẩn → cuộn tới phần tử nếu nó đã có trong DOM nhưng chưa hiển thị.
     */
    protected WebElement waitVisible(By locator) {
        return wait.until(d -> {
            List<WebElement> found = d.findElements(locator);
            if (found.isEmpty()) {
                return null;
            }
            WebElement element = found.get(0);
            if (!element.isDisplayed()) {
                scrollIntoView(element);
            }
            return element.isDisplayed() ? element : null;
        });
    }

    /**
     * Text của phần tử theo DOM (textContent, gộp khoảng trắng) — dùng cho ô bảng có thể nằm ngoài vùng nhìn,
     * vì Selenium getText() trả chuỗi rỗng với phần tử bị coi là ẩn.
     */
    protected static String textOf(WebElement element) {
        String text = element.getDomProperty("textContent");
        return text == null ? "" : text.replaceAll("\\s+", " ").trim();
    }

    protected void scrollIntoView(WebElement element) {
        js().executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected WebElement waitClickable(By locator) {
        WebElement element = waitVisible(locator);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Phần tử đầu tiên đang hiển thị trong số các phần tử khớp locator (bỏ qua bản sao ẩn). */
    protected WebElement waitFirstVisible(By locator) {
        return wait.until(d -> {
            List<WebElement> found = d.findElements(locator);
            for (WebElement element : found) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
            found.forEach(this::scrollIntoView);
            return found.stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
        });
    }

    protected void click(By locator) {
        log.info("Click: {}", locator);
        waitClickable(locator).click();
    }

    protected void clickFirstVisible(By locator) {
        log.info("Click (phần tử hiển thị): {}", locator);
        WebElement element = waitFirstVisible(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(By locator, String text) {
        log.info("Nhập vào {}: \"{}\"", locator, text);
        fill(waitVisible(locator), text);
    }

    /** Nhập dữ liệu nhạy cảm (mật khẩu) — không ghi giá trị ra log. */
    protected void typeSecret(By locator, String text) {
        log.info("Nhập dữ liệu ẩn vào {}", locator);
        fill(waitVisible(locator), text);
    }

    /**
     * ChromeDriver không gõ được ký tự ngoài BMP (emoji) bằng sendKeys → gán value bằng JS và phát sự kiện input/change.
     */
    private void fill(WebElement element, String text) {
        element.clear();
        if (text == null || text.isEmpty()) {
            return;
        }
        if (text.codePoints().anyMatch(cp -> cp > 0xFFFF)) {
            js().executeScript("arguments[0].value = arguments[1];"
                    + "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));"
                    + "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));", element, text);
        } else {
            element.sendKeys(text);
        }
    }

    protected void setCheckbox(By locator, boolean checked) {
        WebElement box = waitVisible(locator);
        if (box.isSelected() != checked) {
            box.click();
        }
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText().trim();
    }

    protected String getValue(By locator) {
        WebElement element = driver.findElement(locator);
        String value = element.getDomProperty("value");
        return value == null ? "" : value;
    }

    protected String getAttribute(By locator, String name) {
        String value = driver.findElement(locator).getDomAttribute(name);
        return value == null ? "" : value;
    }

    protected List<String> getVisibleTexts(By locator) {
        return driver.findElements(locator).stream().filter(WebElement::isDisplayed).map(e -> e.getText().trim()).toList();
    }

    protected boolean isVisible(By locator) {
        try {
            waitVisible(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Kiểm tra tức thời (không chờ) — dùng khi kỳ vọng phần tử KHÔNG hiển thị. */
    protected boolean isDisplayedNow(By locator) {
        try {
            return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean waitUrlContains(String fragment) {
        try {
            return wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void waitForJs(String condition, Object... args) {
        wait.until(d -> Boolean.TRUE.equals(js().executeScript("return (function(){" + condition + "}).apply(null, arguments);", args)));
    }

    protected JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    /** Toast thông báo góc màn hình (VD "The record has been deleted."). */
    public String getToastMessage() {
        return waitFirstVisible(TOAST_MESSAGE).getText().trim();
    }

    public boolean isBrowserAlertPresent(Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
