package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Base.RetryAnalyzer;
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

import static Helpers.URLs.*;

public class InventoryPageTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.navigate().to(loginURL);

        excelReader = new ExcelReader("Test Data.xlsx");
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        itemPage = new ItemPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
    }
    @Test (priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void sortItemAtoZ(){
        loginUser();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameAtoZ();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Test.allTheThings() T-Shirt (Red)");
    }

    @Test(priority = 20,retryAnalyzer = RetryAnalyzer.class)
    public void sortItemZtoA(){
        loginUser();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortNameZtoA();
        Assert.assertEquals(inventoryPage.productsName.get(0).getText(), "Test.allTheThings() T-Shirt (Red)");
        Assert.assertEquals(inventoryPage.productsName.get(inventoryPage.productsName.size()-1).getText(), "Sauce Labs Backpack");
    }

    @Test(priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void sortPriceHighToLow(){
        loginUser();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceHighToLow();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$49.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$7.99");
    }

    @Test(priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void sortPriceLowToHigh(){
        loginUser();
        inventoryPage.clickOnSortDropdown();
        inventoryPage.clickOnSortPriceLowToHigh();
        Assert.assertEquals(inventoryPage.priceOfProducts.get(0).getText(), "$7.99");
        Assert.assertEquals(inventoryPage.priceOfProducts.get(inventoryPage.priceOfProducts.size()-1).getText(), "$49.99");
    }

    @Test (priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void userCanAddProductToTheCart(){
        loginUser();
        int numberOfAddedProducts = 1;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);

        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));
        inventoryPage.clickOnCartIcon();
        Assert.assertFalse(cartPage.cartIsEmpty());
    }

    @Test(priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void userCanOpenAndCloseProduct(){
        loginUser();
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        Assert.assertTrue(itemPage.imageCanBeSeen());
        itemPage.clickOnBackToProductsButton();
        Assert.assertTrue(inventoryPage.productsAreVisible());
        Assert.assertEquals(driver.getCurrentUrl(), inventoryURL);
    }

    @Test(priority = 70, retryAnalyzer = RetryAnalyzer.class)
    public void userCanAddSpecifiedProductToTheCart(){
        loginUser();
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
    }

    @Test(priority = 80, retryAnalyzer = RetryAnalyzer.class)
    public void userCanRemoveProductFromCartWithoutEnteringIt(){
        loginUser();
        int numberOfAddedProducts = 3;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));

        inventoryPage.clickOnRemoveButton(numberOfAddedProducts);
        Assert.assertFalse(inventoryPage.isNotEmptyCart());
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Test(priority = 90, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitLinkedinProfileOfSaucedemo(){
        loginUser();
        scrollToElement(inventoryPage.linkedinIcon);
        inventoryPage.clickOnLinkedinIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), linkedinURL);
    }

    @Test(priority = 100, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitFacebookProfileOfSaucedemo(){
        loginUser();
        scrollToElement(inventoryPage.facebookIcon);
        inventoryPage.clickOnFacebookIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), facebookURL);
    }

    @Test(priority = 110, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitTwitterProfileOfSaucedemo(){
        loginUser();
        scrollToElement(inventoryPage.twitterIcon);
        inventoryPage.clickOnTwitterIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), twitterURL);
    }

    @Test(priority = 120, retryAnalyzer = RetryAnalyzer.class)
    public void resetAppState(){
        loginUser();
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

    @Test(priority = 130, retryAnalyzer = RetryAnalyzer.class)
    public void userCanRedirectedToAboutPage(){
        loginUser();
        inventoryPage.clickOnHamburgerButton();
        inventoryPage.clickOnAboutButton();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), aboutURL);
        Assert.assertEquals(driver.getTitle(), "Sauce Labs: Cross Browser Testing, Selenium Testing & Mobile Testing");
    }

    @Test(priority = 140, retryAnalyzer = RetryAnalyzer.class)
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