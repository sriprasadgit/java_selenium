package com.selenium_learning.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.List;


public class Controller extends SuiteSetup {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver and open the URL
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(baseURL);
    }

    @Parameters({"username", "password"})
    @Test(priority = 1)
    public void loginAndVerify(String username, String password) {
        // Step 1: Verify Title
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");

        // Step 2: Login with parameters from TestNG.xml
        login(username, password);

       
        // Verify Title after login
        
        //String expectedLoggedInTitle = "Swag Labs";
        //String actualLoggedInTitle = driver.getTitle();
        //Assert.assertEquals(actualLoggedInTitle, expectedLoggedInTitle, "Title does not match for parameterized login");
    }

    private void login(String username, String password) {
        // Helper method for login
        WebElement userElement = driver.findElement(By.id("user-name"));
        WebElement passwordElement = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        userElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButton.click();
        // Step 3: Verify user navigated to the right page and check title
        
        try {
            // Check if error label is present (login unsuccessful)
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h3[@data-test='error']")));

            // If no exception is thrown, login is unsuccessful
            System.out.println("Login unsuccessful as expeted for username: " + username + ", password: " + password);
            
	        } catch (TimeoutException e) {
	            // Exception is caught if error label is not present (login successful)
	            // Verify user navigated to the right page and check title
	        	
	            WebElement productsLabel = driver.findElement(By.xpath("//div[contains(@class, 'logo') and text() = 'Swag Labs']"));
	            if (productsLabel.isDisplayed()) {
	                System.out.println("Login successful for username: " + username + ", password: " + password);
	            } else {
	                // Login successful, but products label is not displayed
	                System.out.println("Login successful, but products label is not displayed for username: " + username + ", password: " + password);
	            }
	
	            // Verify Title after login
	            String expectedLoggedInTitle = "Swag Labs";
	            String actualLoggedInTitle = driver.getTitle();
	            Assert.assertEquals(actualLoggedInTitle, expectedLoggedInTitle, "Title does not match for parameterized login");
	        }
	        
       }

    @AfterMethod
    public void tearDown() {
        // Close the WebDriver instance
        if (driver != null) {
            driver.quit();
        }
    }
}
