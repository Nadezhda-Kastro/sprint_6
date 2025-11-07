package test_suites;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page_objects.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FAQTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        homePage.open();
        homePage.acceptCookies();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void checkFAQAccordionFunctionality(int questionIndex) throws InterruptedException {
        homePage.expandQuestion(questionIndex);


        Thread.sleep(1000);

        assertTrue(homePage.isAnswerDisplayed(questionIndex));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}