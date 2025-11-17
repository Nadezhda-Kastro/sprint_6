package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Заголовок главной страницы
    private By header = By.className("Home_Header__iJKdX");

    // Кнопка "Заказать" в верхней части страницы
    private By orderButtonTop = By.className("Button_Button__ra12g");

    // Кнопка "Заказать" в нижней части страницы
    private By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']");

    // Кнопка принятия cookies
    private By cookieButton = By.id("rcc-confirm-button");

    // Раздел с часто задаваемыми вопросами
    private By questionsSection = By.className("Home_FAQ__3uVm4");

    // Кнопки вопросов в аккордеоне
    private By questionButtons = By.xpath("//div[@data-accordion-component='AccordionItemButton']");

    // Панели с ответами на вопросы
    private By answerPanels = By.xpath("//div[@data-accordion-component='AccordionItemPanel']");

    public HomePage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Открытие главной страницы приложения
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Принятие cookies для продолжения работы с сайтом
    public void acceptCookies() {
        try {
            WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookie.click();
        } catch (Exception e) {
            System.out.println("Куки-баннер не найден");
        }
    }

    // Нажатие на верхнюю кнопку "Заказать"
    public void clickTopOrderButton() {
        List<WebElement> orderButtons = driver.findElements(orderButtonTop);
        if (!orderButtons.isEmpty()) {
            orderButtons.get(0).click();
        }
    }

    // Нажатие на нижнюю кнопку "Заказать"
    public void clickBottomOrderButton() {

        List<WebElement> orderButtons = driver.findElements(By.xpath("//button[text()='Заказать']"));
        WebElement bottomButton = orderButtons.get(orderButtons.size() - 1);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bottomButton);
        wait.until(ExpectedConditions.elementToBeClickable(bottomButton));
        bottomButton.click();
    }

    // Раскрытие вопроса по индексу для просмотра ответа
    public void expandQuestion(int questionIndex) {
        List<WebElement> questionElements = driver.findElements(questionButtons);
        if (questionIndex < questionElements.size()) {
            WebElement question = questionElements.get(questionIndex);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                    question
            );
            wait.until(ExpectedConditions.elementToBeClickable(question));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
        }
    }

    // Получение текста ответа на вопрос по индексу
    public String getAnswerText(int questionIndex) {
        List<WebElement> answerElements = driver.findElements(answerPanels);
        if (questionIndex < answerElements.size()) {
            return answerElements.get(questionIndex).getText();
        }
        return "Ответ не найден";
    }

    // Проверка отображения ответа на вопрос
    public boolean isAnswerDisplayed(int questionIndex) {
        try {
            List<WebElement> answerElements = driver.findElements(answerPanels);
            return questionIndex < answerElements.size() && answerElements.get(questionIndex).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Получение общего количества вопросов на странице
    public int getQuestionsCount() {
        List<WebElement> questionElements = driver.findElements(questionButtons);
        return questionElements.size();
    }

    // Скролл страницы к указанному элементу
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}