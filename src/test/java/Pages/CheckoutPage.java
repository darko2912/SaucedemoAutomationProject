package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends BaseTest {

    public CheckoutPage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "first-name")
    public WebElement firtsNameField;

    @FindBy(id = "last-name")
    public WebElement lastNameField;

    @FindBy(id = "postal-code")
    public WebElement postalCodeField;

    @FindBy(id = "continue")
    public WebElement continueButton;

    @FindBy(id = "cancel")
    public WebElement cancelButton;

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(css = "span[data-test='title']")
    public WebElement overviewTitle;

    @FindBy(className = "cart_item")
    public List<WebElement> lsitOfProducts;

    @FindBy(className = "complete-header")
    public WebElement completeMessage;

    @FindBy(id = "back-to-products")
    public WebElement backHomeButton;

    @FindBy(css = "h3[data-test='error']")
    public WebElement errorMessage;

    //-----------------------------

    public void inputFirstName(String firstName){
        firtsNameField.clear();
        firtsNameField.sendKeys(firstName);
    }

    public void inputLastName(String lastName){
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void inputPostalCode(String postalCode){
        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
    }

    public void clickOnContinueButton(){
        continueButton.click();
    }

    public void clickOnCancelButton(){
        cancelButton.click();
    }

    public void clickOnFinishButton(){
        finishButton.click();
    }

    public boolean overviewTitleIsDisplayed(){
        boolean isPresent = false;
        if (overviewTitle.isDisplayed() && overviewTitle.getText().equals("Checkout: Overview")){
            isPresent=true;
        }
        return isPresent;
    }

    public boolean overviewIsNotEmpty(){
        boolean notEmpty = false;
        if (!lsitOfProducts.isEmpty()){
            notEmpty = true;
        }
        return notEmpty;
    }

    public void clickOnBackHomeButton(){
        backHomeButton.click();
    }
}
