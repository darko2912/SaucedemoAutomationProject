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
import static Helpers.Data.*;

public class EndToEndTest extends BaseTest {

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

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void endToEndTest(){
        //Login
        loginUser();
        Assert.assertEquals(driver.getCurrentUrl(), inventoryURL);
        //Add product 1
        String productName = listOfItems().get(0);
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        //Countinue Shopping
        cartPage.clickOnContinueShoppingButton();
        Assert.assertNotEquals(loginURL, inventoryURL);
        //Add product 2
        String productName2 = listOfItems().get(1);
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
        String productName3 = listOfItems().get(2);
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
        inputValidInformation();
        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutPage.overviewTitleIsDisplayed());
        Assert.assertTrue(checkoutPage.overviewIsNotEmpty());
        checkoutPage.clickOnFinishButton();
        Assert.assertTrue(checkoutPage.completeMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.completeMessage.getText(), finishMessage);
        Assert.assertEquals(driver.getCurrentUrl(), finishURL);
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
        Assert.assertEquals(driver.getCurrentUrl(), loginURL);
        Assert.assertFalse(inventoryPage.logoutButtonIsDisplayed());
        Assert.assertTrue(loginPage.loginButton.isDisplayed());
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }
}
