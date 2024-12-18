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

import static Helpers.Data.*;
import static Helpers.URLs.*;

public class CheckoutPageTest extends BaseTest {

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

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userCanOrderItems(){
        loginAndAddProductToTheCart();
        cartPage.clickOnCheckoutButton();
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepOneURL);
        inputValidInformation();
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.overviewTitleIsDisplayed());
        Assert.assertTrue(checkoutPage.overviewIsNotEmpty());
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepTwoURL);

        checkoutPage.clickOnFinishButton();
        Assert.assertTrue(checkoutPage.completeMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.completeMessage.getText(), finishMessage);
        Assert.assertEquals(driver.getCurrentUrl(), finishURL);

        checkoutPage.clickOnBackHomeButton();
        Assert.assertEquals(driver.getCurrentUrl(), inventoryURL);
        Assert.assertFalse(inventoryPage.isNotEmptyCart());
        Assert.assertTrue(inventoryPage.removeButton.isEmpty());
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotOrderItemsWithEmptyFirstName(){
        loginAndAddProductToTheCart();
        cartPage.clickOnCheckoutButton();
        String firstName = "";
        String validLastName = excelReader.getStringData("CheckoutPage", 1,1);
        String validPostalCode = String.valueOf(excelReader.getIntegerData("CheckoutPage", 1,2));
        checkoutPage.inputFirstName(firstName);
        checkoutPage.inputLastName(validLastName);
        checkoutPage.inputPostalCode(validPostalCode);
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.errorMessage.getText(), errorRequireFirstName);
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepOneURL);
    }

    @Test(priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotOrderItemsWithEmptyLastName(){
        loginAndAddProductToTheCart();
        cartPage.clickOnCheckoutButton();
        String validFirstName = excelReader.getStringData("CheckoutPage",1,0);
        String lastName = "";
        String validPostalCode = String.valueOf(excelReader.getIntegerData("CheckoutPage", 1,2));
        checkoutPage.inputFirstName(validFirstName);
        checkoutPage.inputLastName(lastName);
        checkoutPage.inputPostalCode(validPostalCode);
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.errorMessage.getText(), errorRequireLastName);
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepOneURL);
    }

    @Test(priority = 40, retryAnalyzer = RetryAnalyzer.class)
    public void userCannotOrderItemWithEmptyPostalCode(){
        loginAndAddProductToTheCart();
        cartPage.clickOnCheckoutButton();
        String validFirstName = excelReader.getStringData("CheckoutPage",1,0);
        String validLastName = excelReader.getStringData("CheckoutPage", 1,1);
        String postalCode = "";
        checkoutPage.inputFirstName(validFirstName);
        checkoutPage.inputLastName(validLastName);
        checkoutPage.inputPostalCode(postalCode);
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertEquals(checkoutPage.errorMessage.getText(), errorRequirePostalCode);
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepOneURL);
    }

    @Test(priority = 50, retryAnalyzer = RetryAnalyzer.class)
    public void userCanCancelCheckout(){
        loginAndAddProductToTheCart();
        cartPage.clickOnCheckoutButton();
        inputValidInformation();
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.overviewTitleIsDisplayed());
        Assert.assertTrue(checkoutPage.overviewIsNotEmpty());

        checkoutPage.clickOnCancelButton();
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(driver.getCurrentUrl(), inventoryURL);
        Assert.assertFalse(inventoryPage.removeButton.isEmpty());
        inventoryPage.clickOnCartIcon();
        Assert.assertFalse(cartPage.cartIsEmpty());
    }

    @Test(priority = 60, retryAnalyzer = RetryAnalyzer.class)
    public void subtotalPriceWithoutTaxIsCorrect(){
        loginUser();
        inventoryPage.selectSortOption(HighToLow);
        inventoryPage.clickOnAddToCartButton(2);
        inventoryPage.clickOnCartIcon();
        double price = cartPage.totalPriceWithoutTax();
        cartPage.clickOnCheckoutButton();
        inputValidInformation();
        checkoutPage.clickOnContinueButton();

        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepTwoURL);
        Assert.assertEquals(checkoutPage.subtotalPrice(), price);
    }

    @Test(priority = 70, retryAnalyzer = RetryAnalyzer.class)
    public void totalPriceWithTaxIsCorrect(){
        loginUser();
        inventoryPage.selectSortOption(HighToLow);
        inventoryPage.clickOnAddToCartButton(2);
        inventoryPage.clickOnCartIcon();
        double price = cartPage.totalPriceWithoutTax();
        cartPage.clickOnCheckoutButton();
        inputValidInformation();
        checkoutPage.clickOnContinueButton();
        double expectedTotalPrice = price + checkoutPage.tax();

        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepTwoURL);
        Assert.assertEquals(checkoutPage.totalPrice(), roundingNumbers(expectedTotalPrice));
    }

    @AfterMethod
    public void tearDownTest(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
