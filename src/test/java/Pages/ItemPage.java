package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemPage extends BaseTest {

    public ItemPage(){
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div[data-test='inventory-item-name']")
    public WebElement nameOfProduct;

    @FindBy(id = "add-to-cart")
    public WebElement addButton;

    @FindBy(id = "back-to-products")
    public WebElement backToProductsButton;

    @FindBy(className = "shopping_cart_link")
    public WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    public WebElement cartBadge;

    @FindBy(className = "inventory_details_img")
    public WebElement productImage;

    @FindBy(id = "remove")
    public WebElement removeButton;

    //-----------------------------------

    public void clickOnAddButton(){
        addButton.click();
    }

    public void clickOnBackToProductsButton(){
        backToProductsButton.click();
    }

    public void clickOnCartIcon(){
        cartIcon.click();
    }
    //Method for checking if the cart is empty.
    public boolean isNotemptyCart() {
        boolean isEmpty = false;
        try {
            isEmpty = cartBadge.isDisplayed();
        } catch (Exception e) {

        }
        return isEmpty;
    }
    //Method for checking if the image of product is displayed.
    public boolean imageCanBeSeen(){
        boolean isPresent = false;
        try {
            isPresent = productImage.isDisplayed();
        } catch (Exception e) {

        }
        return isPresent;
    }

    public void clickOnRemoveButton(){
        removeButton.click();
    }

}
