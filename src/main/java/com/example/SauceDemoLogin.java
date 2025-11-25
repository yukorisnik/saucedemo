package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SauceDemoLogin {
    public static void main(String[] args) {
        
            
        // Kör Chrome headless eller med UI. Ta bort "--headless" för att se webbläsaren.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // aktivera om du vill köra utan UI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.saucedemo.com/");

            // Hitta inputfälten och knappen
            WebElement usernameInput = driver.findElement(By.id("user-name"));
            WebElement passwordInput = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("login-button"));

            usernameInput.sendKeys("standard_user");
            passwordInput.sendKeys("secret_sauce");
            loginButton.click();

            // Enkel verifiering: kontrollera att vi är på inventory-sidan
            Thread.sleep(3000); // kort paus
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/inventory.html")) {
                System.out.println("  Login lyckades!  ");
            } else {
                System.out.println("Login misslyckades!! " + currentUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Stäng webbläsaren
            driver.quit();
        }
    }
}
