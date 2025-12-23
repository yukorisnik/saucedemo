package com.example;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SauceDemoLoginTest {

    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(SauceDemoLogin.class.getName());

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  // måste köra headless Action i github
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testSuccessfulLogin() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        Thread.sleep(2000);  // Rek med WebDriverWait i riktiga tester

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("inventory.html"),
                "Login misslyckades: hamnade på: " + currentUrl);
        logger.info("Login lyckades, nu på sidan: " + currentUrl);
        System.out.println("Login lyckades!");

    }

    @Test
    void testFailedUserLogin() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        // fel användare matas in
        usernameInput.sendKeys("wrong_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        Thread.sleep(2000);  // Rekommenderat: använd WebDriverWait i riktiga tester

        // Hämta felmeddelandet
        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));

        // Kolla felmeddelandet
        assertTrue(errorMessage.isDisplayed(),
            "Förväntat felmeddelande visades inte vid misslyckad inloggning");

        System.out.println("Misslyckad inloggning testad med fel användare.");
    }
    
    @Test
    void testFailedPassLogin() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        // fel lösen matas in
        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("wrong_password");
        loginButton.click();

        Thread.sleep(2000); 

        // Hämta felmeddelandet
        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));

        // Kolla felmeddelandet
        assertTrue(errorMessage.isDisplayed(),
            "Förväntat felmeddelande visades inte vid misslyckad inloggning");
        System.out.println("Misslyckad inloggning testad med fel lösen.");
    }
}
