package Base;

import Helpers.Data;
import Helpers.URLs;
import Pages.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.ArrayList;

public class BaseTest {

    public static WebDriver driver;
    public WebDriverWait wait;

    public static ExcelReader excelReader;
    public LoginPage loginPage;
    public InventoryPage inventoryPage;
    public ItemPage itemPage;
    public CartPage cartPage;
    public CheckoutPage checkoutPage;
    public URLs urls;
    public Data data;

    @BeforeClass
    public void setUp() throws IOException {

        excelReader = new ExcelReader("Test Data.xlsx");
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        itemPage = new ItemPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        urls = new URLs();
        data = new Data();

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
    //Method for scrolling to the desired element.
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    //Login method.
    public void loginUser() {
        String validUsername = excelReader.getStringData("Login", 1, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();
    }
    //Method for setting focus on a specific tab.
    public void switchTab(int tab){
        ArrayList<String> listaTabova = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(listaTabova.get(tab));
    }
    //Login method.
    public void loginProblem_user() {
        String validUsername = excelReader.getStringData("Login", 3, 0);
        String validPassword = excelReader.getStringData("Login", 1, 1);
        loginPage.inputUsername(validUsername);
        loginPage.inputPassword(validPassword);
        loginPage.clickOnLoginButton();
    }
    //Method for logging in and adding a product to the cart.
    public void loginAndAddProductToTheCart(){
        loginUser();
        int numberOfAddedProducts = 1;
        inventoryPage.clickOnAddToCartButton(numberOfAddedProducts);
        inventoryPage.clickOnCartIcon();
    }
}
