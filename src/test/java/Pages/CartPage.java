package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BaseTest {

    public CartPage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_item_name")
    public List<WebElement> listOfProductsName;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(id = "react-burger-menu-btn")
    public WebElement hamburgerButton;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement allItemsButton;

    @FindBy(css = ".btn.btn_secondary.btn_small.cart_button")
    public List<WebElement> removeButton;

    @FindBy(css = "a[data-test='social-linkedin']")
    public WebElement linkedinIcon;

    @FindBy(css = "a[data-test='social-facebook']")
    public WebElement facebookIcon;

    @FindBy(css = "a[data-test='social-twitter']")
    public WebElement twitterIcon;

    @FindBy(id = "about_sidebar_link")
    public WebElement aboutButton;

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    //-------------------------------------
    //Method for checking if a specific product is in the cart.
    public boolean productIsInTheCart(String productName){
        boolean isPresent = false;
        for (int i=0; i<listOfProductsName.size(); i++){
            if (listOfProductsName.get(i).getText().equals(productName)){
                isPresent=true;
                break;
            }
        }
        return isPresent;
    }
    //Method for checking if the cart is empty.
    public boolean cartIsEmpty(){
        boolean emptyCart = false;
        try {
            emptyCart= listOfProductsName.isEmpty();
        } catch (Exception e) {

        }
        return emptyCart;
    }

    public void clickOnContinueShoppingButton(){
        continueShoppingButton.click();
    }

    public void clickOnHamburgerButton(){
        hamburgerButton.click();
    }

    public void clickOnAllItemsButton(){
        allItemsButton.click();
    }
    //Method for clicking multiple remove buttons.
    public void clickOnRemoveButton(int removeProducts){
        for (int i=removeButton.size()-1; i>=0; i--){
            removeButton.get(i).click();
        }
    }

    public void clickOnLinkedinIcon(){
        linkedinIcon.click();
    }

    public void clickOnFacebookIcon(){
        facebookIcon.click();
    }

    public void clickOnTwitterIcon(){
        twitterIcon.click();
    }

    public void clickOnAboutButton(){
        aboutButton.click();
    }

    public void clickOnCheckoutButton(){
        checkoutButton.click();
    }
    //Method for clicking a specific product in the cart.
    public void selectSpecifiedProductFromCart(String nameOfProduct){
        for (int i=0; i<listOfProductsName.size(); i++){
            if (listOfProductsName.get(i).getText().equals(nameOfProduct)) {
                listOfProductsName.get(i).click();
                break;
            }
        }
    }
}
