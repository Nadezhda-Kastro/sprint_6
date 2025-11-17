package test_suites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import page_objects.HomePage;
import page_objects.OrderFormPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFromBottomButtonTest {
    private WebDriver driver;
    private HomePage homePage;
    private OrderFormPage orderFormPage;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        orderFormPage = new OrderFormPage(driver);
        homePage.open();
        homePage.acceptCookies();
    }

    @Test
    public void completeOrderViaBottomButton() {
        homePage.clickBottomOrderButton();
        orderFormPage.fillCustomerInfo(
                "Екатерина",
                "Кузнецова",
                "пр. Вернадского, 105",
                "Проспект Вернадского",
                "79990001122"
        );
        orderFormPage.fillRentalDetails(
                "18.12.2025",
                "двое суток",
                "black",
                "Код домофона 125"
        );
        orderFormPage.confirmOrder();

        boolean success = orderFormPage.isOrderSuccess();
        assertTrue(success, "Заказ должен быть успешно оформлен");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}