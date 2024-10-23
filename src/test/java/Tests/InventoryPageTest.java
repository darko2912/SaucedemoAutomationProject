package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class InventoryPageTest extends BaseTest {

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

    @Test(priority = 10)
    public void userCanLogout() {
        for (int i = 1; i < excelReader.getLastRow("InventoryPage"); i++) {
            String validUsername = excelReader.getStringData("InventoryPage", i, 0);
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
    }

    @Test(priority = 20)
    public void userCannotLoginWithEmptyFieldAfterLoggingOut() {
        for (int i = 1; i < excelReader.getLastRow("InventoryPage"); i++) {
            String validUsername = excelReader.getStringData("InventoryPage", i, 0);
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
    }

    @Test(priority = 30)
    public void theSortDropdownListWorks(){
        loginStandard_user();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameZtoA();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Test.allTheThings() T-Shirt (Red)");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Sauce Labs Backpack");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameAtoZ();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Test.allTheThings() T-Shirt (Red)");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceHighToLow();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$49.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$7.99");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceLowToHigh();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$7.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$49.99");
    }

    @Test (priority = 40)
    public void userCanAddProductToTheCart(){
        loginStandard_user();
        int numberOfAddedProducts = 1;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);

        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));
        inventoryPage.clickOnCartIcon();
        Assert.assertFalse(cartPage.cartIsEmpty());
    }

    @Test(priority = 50)
    public void userCanOpenAndCloseProduct(){
        loginStandard_user();
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        Assert.assertTrue(itemPage.imageCanBeSeen());
        itemPage.clickOnBackToProductsButton();
        Assert.assertTrue(inventoryPage.productsAreVisible());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @Test(priority = 60)
    public void userCanAddSpecifiedProductToTheCart(){
        loginStandard_user();
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
    }

    @Test(priority = 70)
    public void userCanRemoveProductFromCartWithoutEnteringIt(){
        loginStandard_user();
        int numberOfAddedProducts = 3;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));

        inventoryPage.clickOnRemoveButton(numberOfAddedProducts);
        Assert.assertFalse(inventoryPage.isNotEmptyCart());
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Test(priority = 80)
    public void userCanVisitLinkedinProfileOfSaucedemo(){
        loginStandard_user();
        scrollToElement(inventoryPage.linkedinIcon);
        inventoryPage.clickOnLinkedinIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.linkedin.com/company/sauce-labs/");
    }

    @Test(priority = 90)
    public void userCanVisitFacebookProfileOfSaucedemo(){
        loginStandard_user();
        scrollToElement(inventoryPage.facebookIcon);
        inventoryPage.clickOnFacebookIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/saucelabs");
    }

    @Test(priority = 100)
    public void userCanVisitTwitterProfileOfSaucedemo(){
        loginStandard_user();
        scrollToElement(inventoryPage.twitterIcon);
        inventoryPage.clickOnTwitterIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://x.com/saucelabs");
    }

    @Test(priority = 110)
    public void resetAppState(){
        loginStandard_user();
        int numberOfAddedProducts = 1;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));
        inventoryPage.clickOnHamburgerButton();
        inventoryPage.clickOnResetAppState();

        Assert.assertFalse(inventoryPage.isNotEmptyCart());
        Assert.assertTrue(inventoryPage.removeButton.isEmpty());
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Test(priority = 120)
    public void userCanRedirectedToAboutPage(){
        loginStandard_user();
        inventoryPage.clickOnHamburgerButton();
        inventoryPage.clickOnAboutButton();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/");
    }

    @Test(priority = 130)
    public void theSortDropdownListWorksCertainUSer(){
        loginProblem_user();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameZtoA();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Test.allTheThings() T-Shirt (Red)");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Sauce Labs Backpack");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameAtoZ();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Test.allTheThings() T-Shirt (Red)");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceHighToLow();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$49.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$7.99");

        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceLowToHigh();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$7.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$49.99");
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }

}