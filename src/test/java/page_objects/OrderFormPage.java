package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderFormPage {
    private WebDriver driver;

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
    private By orderButton = By.xpath("//button[text()='Заказать']");

    // Кнопка подтверждения заказа во всплывающем окне
    private By confirmOrderButton = By.xpath("//button[text()='Да']");

    // Модальное окно с сообщением об успешном создании заказа
    private By orderSuccessModal = By.xpath("//div[contains(@class, 'Order_Modal')]");

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillCustomerInfo(String name, String surname, String address, String metro, String phone) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(surnameField)).sendKeys(surname);
        wait.until(ExpectedConditions.elementToBeClickable(addressField)).sendKeys(address);

        WebElement metroElement = wait.until(ExpectedConditions.elementToBeClickable(metroField));
        metroElement.click();
        metroElement.sendKeys(metro);

        Thread.sleep(2000);
        WebElement metroStation = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(), '" + metro + "')]")));
        metroStation.click();

        wait.until(ExpectedConditions.elementToBeClickable(phoneField)).sendKeys(phone);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void fillRentalDetails(String date, String period, String color, String comment) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(dateField));
        dateElement.sendKeys(date);

        driver.findElement(By.tagName("body")).click();
        Thread.sleep(1000);

        WebElement rentalPeriodElement = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        rentalPeriodElement.click();

        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='" + period + "']")));
        periodOption.click();

        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGrey).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(commentField)).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public boolean isOrderSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}