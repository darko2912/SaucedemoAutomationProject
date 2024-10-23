package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InventoryPage extends BaseTest {

    public InventoryPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_item")
    public List<WebElement> listOfProducts;

    @FindBy(id = "logout_sidebar_link")
    public WebElement logoutButton;

    @FindBy(id = "react-burger-menu-btn")
    public WebElement hamburgerButton;

    @FindBy(className = "product_sort_container")
    public WebElement sortDropdownList;

    @FindBy(css = "option[value='az']")
    public WebElement sortNameAtoZ;

    @FindBy(css = "option[value='za']")
    public WebElement sortNameZtoA;

    @FindBy(css = "option[value='lohi']")
    public WebElement sortPriceLowToHigh;

    @FindBy(css = "option[value='hilo']")
    public WebElement sortPriceHighToLow;

    @FindBy(className = "inventory_item_name")
    public List<WebElement> productsName;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> priceOfProducts;

    @FindBy(css = ".btn.btn_primary.btn_small.btn_inventory")
    public List<WebElement> addToCartButton;

    @FindBy(className = "shopping_cart_link")
    public WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    public WebElement cartBadge;

    @FindBy(css = ".btn.btn_secondary.btn_small.btn_inventory")
    public List<WebElement> removeButton;

    @FindBy(css = "a[data-test='social-linkedin']")
    public WebElement linkedinIcon;

    @FindBy(css = "a[data-test='social-facebook']")
    public WebElement facebookIcon;

    @FindBy(css = "a[data-test='social-twitter']")
    public WebElement twitterIcon;

    @FindBy(id = "about_sidebar_link")
    public WebElement aboutButton;

    @FindBy(id = "reset_sidebar_link")
    public WebElement resetAppStateButton;

    //-------------------------------------

    public void clickOnHamburgerButton(){
        hamburgerButton.click();
    }

    public void clickOnLogoutButton(){
        logoutButton.click();
    }

    public boolean logoutButtonIsDisplayed(){
        boolean isPresentLogoutButton = false;
        try {
            isPresentLogoutButton = logoutButton.isDisplayed();
        } catch (Exception e) {

        }
        return isPresentLogoutButton;
    }

    public boolean productsAreVisible(){
        boolean isPresentProducts = false;
        for (int i=0; i< listOfProducts.size(); i++){
            try {
                isPresentProducts = listOfProducts.get(i).isDisplayed();
            } catch (Exception e) {

            }
        }
        return isPresentProducts;
    }

    public void clickOnSortDropdown(){
        sortDropdownList.click();
    }

    public void clickOnSortNameAtoZ(){
        sortNameAtoZ.click();
    }

    public void clickOnSortNameZtoA(){
        sortNameZtoA.click();
    }

    public void clickOnSortPriceLowToHigh(){
        sortPriceLowToHigh.click();
    }

    public void clickOnSortPriceHighToLow(){
        sortPriceHighToLow.click();
    }

    public void clickOnAddToCartButton(int addProducts){
        for (int i=addProducts-1; i>=0; i--){
            addToCartButton.get(i).click();
        }
    }

    public void clickOnCartIcon(){
        cartIcon.click();
    }

    public boolean isNotEmptyCart() {
        boolean isEmpty = false;
        try {
            isEmpty = cartBadge.isDisplayed();
        } catch (Exception e) {

        }
        return isEmpty;
    }

    public void clickOnProduct(String nameOfProduct){
        for (int i=0; i<productsName.size(); i++){
            if (productsName.get(i).getText().equals(nameOfProduct)){
                productsName.get(i).click();
                break;
            }
        }
    }

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

    public void clickOnResetAppState(){
        resetAppStateButton.click();
    }
}
