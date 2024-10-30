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

public class EndToEndTest extends BaseTest {

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

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void endToEndTest(){
        //Login
        String validUsername = excelReader.getStringData("Login", 1,0);
        String validPassword = excelReader.getStringData("Login", 1,1);
        String loginURL = driver.getCurrentUrl();
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();
        Assert.assertNotEquals(loginURL, "https://www.saucedemo.com/inventory.html");
        //Add product 1
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        //Countinue Shopping
        cartPage.clickOnContinueShoppingButton();
        Assert.assertNotEquals(loginURL, "https://www.saucedemo.com/inventory.html");
        //Add product 2
        String productName2 = "Sauce Labs Fleece Jacket";
        inventoryPage.clickOnProduct(productName2);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName2);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "2");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName2));
        //Countinue Shopping
        cartPage.clickOnContinueShoppingButton();
        //Add product 3
        String productName3 = "Sauce Labs Bolt T-Shirt";
        inventoryPage.clickOnProduct(productName3);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName3);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "3");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName3));
        //Remove product 1
        cartPage.selectSpecifiedProductFromCart(productName);
        itemPage.clickOnRemoveButton();
        itemPage.clickOnCartIcon();
        Assert.assertEquals(itemPage.cartBadge.getText(), "2");
        //Checkout
        cartPage.clickOnCheckoutButton();
        String validFirstName = excelReader.getStringData("CheckoutPage",1,0);
        String validLastName = excelReader.getStringData("CheckoutPage", 1,1);
        String validPostalCode = String.valueOf(excelReader.getIntegerData("CheckoutPage", 1,2));
        checkoutPage.inputFirstName(validFirstName);
        checkoutPage.inputLastName(validLastName);
        checkoutPage.inputPostalCode(validPostalCode);
        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutPage.overviewTitleIsDisplayed());
        Assert.assertTrue(checkoutPage.overviewIsNotEmpty());
        checkoutPage.clickOnFinishButton();
        Assert.assertTrue(checkoutPage.completeMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.completeMessage.getText(), "Thank you for your order!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html");
        checkoutPage.clickOnBackHomeButton();
        Assert.assertFalse(inventoryPage.isNotEmptyCart());
        Assert.assertTrue(inventoryPage.removeButton.isEmpty());
        scrollToElement(inventoryPage.cartIcon);
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
        cartPage.clickOnHamburgerButton();
        cartPage.clickOnAllItemsButton();
        inventoryPage.clickOnHamburgerButton();
        inventoryPage.clickOnLogoutButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
        Assert.assertTrue(loginPage.loginButton.isDisplayed());
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }
}
