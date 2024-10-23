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

public class CheckoutPageTest extends BaseTest {

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
    public void userCanOrderItems(){
        loginAndAddProductToTheCart();
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
        inventoryPage.clickOnCartIcon();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Test(priority = 20)
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
        Assert.assertEquals(checkoutPage.errorMessage.getText(), "Error: First Name is required");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test(priority = 30)
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
        Assert.assertEquals(checkoutPage.errorMessage.getText(), "Error: Last Name is required");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test(priority = 40)
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
        Assert.assertEquals(checkoutPage.errorMessage.getText(), "Error: Postal Code is required");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test(priority = 50)
    public void userCanCancelCheckout(){
        loginAndAddProductToTheCart();
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

        checkoutPage.clickOnCancelButton();
        Assert.assertTrue(inventoryPage.isNotEmptyCart());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertFalse(inventoryPage.removeButton.isEmpty());
        inventoryPage.clickOnCartIcon();
        Assert.assertFalse(cartPage.cartIsEmpty());
    }

    @AfterMethod
    public void tearDownTest(){
        //driver.quit();
    }
}