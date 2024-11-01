package Tests;

import Base.BaseTest;
import Base.ExcelReader;
import Base.RetryAnalyzer;
import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static Helpers.URLs.*;
import static Helpers.Data.*;

public class CartPageTest extends BaseTest {

    public CartPageTest(){
        PageFactory.initElements(driver,this);
    }

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
    public void userCanCountinueShoppingWhenIsLocatedInTheCart(){
        loginUser();
        String productName = listOfItems().get(0);
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        cartPage.clickOnContinueShoppingButton();
        String productName2 = listOfItems().get(1);
        inventoryPage.clickOnProduct(productName2);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName2);
        itemPage.clickOnAddButton();

        Assert.assertEquals(itemPage.cartBadge.getText(), "2");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        Assert.assertTrue(cartPage.productIsInTheCart(productName2));
    }

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCanReturnToTheProductPage(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        cartPage.clickOnHamburgerButton();
        cartPage.clickOnAllItemsButton();

        Assert.assertEquals(driver.getCurrentUrl(), inventoryURL);
        Assert.assertTrue(inventoryPage.productsAreVisible());
    }

    @Test(priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void userCanRemoveProductFromCart(){
        loginUser();
        int numberOfAddedProducts = 1;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(inventoryPage.cartBadge.getText(), String.valueOf(numberOfAddedProducts));
        inventoryPage.clickOnCartIcon();
        Assert.assertFalse(cartPage.cartIsEmpty());
        cartPage.clickOnRemoveButton(numberOfAddedProducts);

        Assert.assertTrue(cartPage.cartIsEmpty());
        Assert.assertFalse(inventoryPage.isNotEmptyCart());
    }

    @Test(priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitLinkedinProfileOfSaucedemo(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.linkedinIcon);
        cartPage.clickOnLinkedinIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), linkedinURL);
    }

    @Test(priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitFacebookProfileOfSaucedemo(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.facebookIcon);
        cartPage.clickOnFacebookIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), facebookURL);
    }

    @Test(priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void userCanVisitTwitterProfileOfSaucedemo(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.twitterIcon);
        cartPage.clickOnTwitterIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), twitterURL);
    }

    @Test(priority = 70, retryAnalyzer = RetryAnalyzer.class)
    public void userCanRedirectedToAboutPage(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        cartPage.clickOnHamburgerButton();
        cartPage.clickOnAboutButton();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), aboutURL);
        Assert.assertEquals(driver.getTitle(), titleAboutPage);
    }

    @Test(priority = 80, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotProceedTheOrderWithEmptyCart(){
        loginUser();
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
        cartPage.clickOnCheckoutButton();

        Assert.assertEquals(driver.getCurrentUrl(), cartURL);
        Assert.assertFalse(cartPage.cartIsEmpty());
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }
}
