package Tests;

import Base.BaseTest;
import Base.ExcelReader;
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

        driver.navigate().to("https://www.saucedemo.com/");

        excelReader = new ExcelReader("Test Data.xlsx");
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        itemPage = new ItemPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
    }

    @Test (priority = 10)
    public void userCanCountinueShoppingWhenIsLocatedInTheCart(){
        loginStandard_user();
        String productName = "Sauce Labs Bike Light";
        inventoryPage.clickOnProduct(productName);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName);
        itemPage.clickOnAddButton();
        Assert.assertTrue(itemPage.isNotemptyCart());
        Assert.assertEquals(itemPage.cartBadge.getText(), "1");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        cartPage.clickOnContinueShoppingButton();
        String productName2 = "Sauce Labs Backpack";
        inventoryPage.clickOnProduct(productName2);
        Assert.assertEquals(itemPage.nameOfProduct.getText(), productName2);
        itemPage.clickOnAddButton();

        Assert.assertEquals(itemPage.cartBadge.getText(), "2");
        itemPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.productIsInTheCart(productName));
        Assert.assertTrue(cartPage.productIsInTheCart(productName2));
    }

    @Test(priority = 20)
    public void userCanReturnToTheProductPage(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        cartPage.clickOnHamburgerButton();
        cartPage.clickOnAllItemsButton();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(inventoryPage.productsAreVisible());
    }

    @Test(priority = 30)
    public void userCanRemoveProductFromCart(){
        loginStandard_user();
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

    @Test(priority = 40)
    public void userCanVisitLinkedinProfileOfSaucedemo(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.linkedinIcon);
        cartPage.clickOnLinkedinIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.linkedin.com/company/sauce-labs/");
    }

    @Test(priority = 50)
    public void userCanVisitFacebookProfileOfSaucedemo(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.facebookIcon);
        cartPage.clickOnFacebookIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/saucelabs");
    }

    @Test(priority = 60)
    public void userCanVisitTwitterProfileOfSaucedemo(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        scrollToElement(cartPage.twitterIcon);
        cartPage.clickOnTwitterIcon();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://x.com/saucelabs");
    }

    @Test(priority = 70)
    public void userCanRedirectedToAboutPage(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        cartPage.clickOnHamburgerButton();
        cartPage.clickOnAboutButton();
        switchTab(1);
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/");
    }

    @Test(priority = 80)
    public void userCannotProceedTheOrderWithEmptyCart(){
        loginStandard_user();
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
        cartPage.clickOnCheckoutButton();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");
    }

    @AfterMethod
    public void tearDownTest(){
        driver.quit();
    }
}
