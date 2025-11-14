package test_suites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import page_objects.HomePage;
import page_objects.OrderFormPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderFromTopButtonTest {
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

    static Stream<Object[]> orderDataProvider() {
        return Stream.of(
                new Object[]{"Анна", "Иванова", "Ленинский проспект, 85", "Ленинский проспект", "79161234567",
                        "15.12.2025", "сутки", "black", "Позвонить за 15 минут"},
                new Object[]{"Дмитрий", "Смирнов", "ул. Пушкина, 42", "Тверская", "79035558899",
                        "20.12.2025", "пятеро суток", "grey", "Оставить у консьержа"}
        );
    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    public void completeOrderViaTopButton(String name, String surname, String address, String metro,
                                          String phone, String date, String period, String color,
                                          String comment) throws InterruptedException {

        try {
            homePage.clickTopOrderButton();
            orderFormPage.fillCustomerInfo(name, surname, address, metro, phone);
            orderFormPage.fillRentalDetails(date, period, color, comment);
            orderFormPage.confirmOrder();
            assertTrue(orderFormPage.isOrderSuccess());
        } catch (Exception e) {
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}