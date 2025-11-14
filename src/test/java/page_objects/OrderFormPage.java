package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Поле ввода имени
    private By nameField = By.xpath("//input[@placeholder='* Имя']");

    // Поле ввода фамилии
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле ввода адреса доставки
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле выбора станции метро
    private By metroField = By.xpath("//input[@placeholder='* Станция метро']");

    // Поле ввода номера телефона
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка перехода к следующему шагу оформления заказа
    private By nextButton = By.xpath("//button[text()='Далее']");

    // Поле выбора даты доставки самоката
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // Поле выбора срока аренды самоката
    private By rentalPeriodField = By.className("Dropdown-placeholder");

    // Чекбокс для выбора черного цвета самоката
    private By colorBlack = By.id("black");

    // Чекбокс для выбора серого цвета самоката
    private By colorGrey = By.id("grey");

    // Поле для ввода комментария для курьера
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка подтверждения заказа на второй странице формы
    private By orderButton = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Middle')]");

    // Кнопка подтверждения заказа во всплывающем окне
    private By confirmOrderButton = By.xpath("//button[text()='Да']");

    // Модальное окно с сообщением об успешном создании заказа
    private By orderSuccessModal = By.xpath("//div[contains(@class, 'Order_Modal')]");

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillCustomerInfo(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        WebElement metroElement = driver.findElement(metroField);
        metroElement.click();

        By metroStationOption = By.xpath(".//li[@class='select-search__row']//button");
        wait.until(ExpectedConditions.elementToBeClickable(metroStationOption));
        driver.findElement(metroStationOption).click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillRentalDetails(String date, String period, String color, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));

        WebElement dateElement = driver.findElement(dateField);
        dateElement.sendKeys(date);
        driver.findElement(By.tagName("body")).click();

        driver.findElement(rentalPeriodField).click();
        By periodOption = By.xpath(".//div[contains(text(), '" + period + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption)).click();

        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGrey).click();
        }

        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }

        WebElement orderButtonElement = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderButtonElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderButtonElement);
    }

    public void confirmOrder() {
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButton);
    }

    public boolean isOrderSuccess() {
        try {
            WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
            return successModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}