package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Base.RetryAnalyzer;
import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginPageTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.navigate().to("https://www.saucedemo.com/");

        excelReader = new ExcelReader("Test Data.xlsx");
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        itemPage = new ItemPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
    }

    @Test (priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLogIn(){
        String validUsername = excelReader.getStringData("Login", 1,0);
        String validPassword = excelReader.getStringData("Login", 1,1);
        String loginURL = driver.getCurrentUrl();
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        Assert.assertNotEquals(loginURL, "https://www.saucedemo.com/inventory.html");
        inventoryPage.clickOnHamburgerButton();
        wait.until(ExpectedConditions.visibilityOf(inventoryPage.logoutButton));
        Assert.assertTrue(inventoryPage.logoutButtonIsDisplayed());
        Assert.assertTrue(inventoryPage.productsAreVisible());
    }

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLogout() {
            String validUsername = excelReader.getStringData("InventoryPage", 1, 0);
            String validPassword = excelReader.getStringData("InventoryPage", 1, 1);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(validUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();
            inventoryPage.clickOnHamburgerButton();
            inventoryPage.clickOnLogoutButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.loginButton.isDisplayed());
    }

    @Test(priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLoginWithEmptyFieldAfterLoggingOut() {
            String validUsername = excelReader.getStringData("InventoryPage", 1, 0);
            String validPassword = excelReader.getStringData("InventoryPage", 1, 1);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(validUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();
            inventoryPage.clickOnHamburgerButton();
            inventoryPage.clickOnLogoutButton();
            loginPage.clickOnLoginButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.loginButton.isDisplayed());
            Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username is required");
    }

    @Test (priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLogInWithInvalidUsernameAndPassword(){
        for (int i=1; i<=excelReader.getLastRow("Login"); i++) {
            String invalidUsername = excelReader.getStringData("Login", i, 2);
            String invalidPassword = excelReader.getStringData("Login", i, 2);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(invalidUsername);
            loginPage.inputPassword(invalidPassword);
            loginPage.clickOnLoginButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.errorMessage.isDisplayed());
            Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username and password do not match any user in this service");
        }
    }

    @Test (priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLogInWithEmptyFields(){
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername("");
            loginPage.inputPassword("");
            loginPage.clickOnLoginButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.errorMessage.isDisplayed());
            Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username is required");
    }

    @Test (priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLogInWithInvalidUsername(){
        for (int i=1; i<=excelReader.getLastRow("Login"); i++) {
            String invalidUsername = excelReader.getStringData("Login", i, 2);
            String validPassword = excelReader.getStringData("Login", 1, 1);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(invalidUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.errorMessage.isDisplayed());
            Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username and password do not match any user in this service");
        }
    }

    @Test (priority = 70, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotLogInWithInvalidPassword(){
        for (int i=1; i<=excelReader.getLastRow("Login"); i++) {
            String validUsername = excelReader.getStringData("Login", 1, 0);
            String invalidPassword = excelReader.getStringData("Login", i, 3);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(validUsername);
            loginPage.inputPassword(invalidPassword);
            loginPage.clickOnLoginButton();

            Assert.assertEquals(loginURL, driver.getCurrentUrl());
            Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(loginPage.errorMessage.isDisplayed());
            Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username and password do not match any user in this service");
        }
    }

    @Test (priority = 80, retryAnalyzer = RetryAnalyzer.class)
    public void userWhoIsLockedOutCannotLogIn(){
        String validUsername = excelReader.getStringData("Login", 2,0);
        String validPassword = excelReader.getStringData("Login", 1,1);
        String loginURL = driver.getCurrentUrl();
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();

        Assert.assertEquals(loginURL, driver.getCurrentUrl());
        Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
        Assert.assertTrue(loginPage.errorMessage.isDisplayed());
        Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test (priority = 90, retryAnalyzer = RetryAnalyzer.class)
    public void certainUserCanLogIn(){
        for (int i=3; i<=excelReader.getLastRow("Login"); i++) {
            String validUsername = excelReader.getStringData("Login", i, 0);
            String validPassword = excelReader.getStringData("Login", 1, 1);
            String loginURL = driver.getCurrentUrl();
            loginPage.inputUsername(validUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();

            Assert.assertNotEquals(loginURL, "https://www.saucedemo.com/inventory.html");
            inventoryPage.clickOnHamburgerButton();
            wait.until(ExpectedConditions.visibilityOf(inventoryPage.logoutButton));
            Assert.assertTrue(inventoryPage.logoutButtonIsDisplayed());
            Assert.assertTrue(inventoryPage.productsAreVisible());

            driver.navigate().to("https://www.saucedemo.com/");
        }
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }

}
