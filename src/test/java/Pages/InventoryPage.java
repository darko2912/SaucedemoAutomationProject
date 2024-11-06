package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

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
    public WebElement selectDropdownList;

    @FindBy(className = "active_option")
    public WebElement ActiveSort;

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

    @FindBy(className = "bm-menu-wrap")
    public WebElement hiddenMenu;

    @FindBy(css = ".bm-item.menu-item")
    public List<WebElement> listOfMenu;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement allItemsButton;

    //-------------------------------------

    public void clickOnHamburgerButton(){
        hamburgerButton.click();
    }

    public void clickOnLogoutButton(){
        logoutButton.click();
    }
    //Method for checking if the logout button is visible.
    public boolean logoutButtonIsDisplayed(){
        boolean isPresentLogoutButton = false;
        try {
            isPresentLogoutButton = logoutButton.isDisplayed();
        } catch (Exception e) {

        }
        return isPresentLogoutButton;
    }
    //Method for checking if the products are visible on the page.
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
    //Method to select product sorting options
    public void selectSortOption(String sortBy){
        Select dropdown = new Select(selectDropdownList);
        dropdown.selectByValue(sortBy);
    }
    public String getActiveSort() {
        return ActiveSort.getText();
    }
    //Add products name into list
    public ArrayList<String> getAllItemsName(){
        ArrayList<String> products = new ArrayList<>();
        for (WebElement i : productsName){
            products.add(i.getText());
        }
        return products;
    }
    //Create sorted list for saveList product
    public ArrayList<String> getSortedList(ArrayList list){
        Collections.sort(list);
        return list;
    }
    //Create reversed list for saveList product
    public ArrayList<String> getReversedList(ArrayList list){
        list.sort(Collections.reverseOrder());
        return list;
    }
    //Create double list for products price
    public ArrayList<Double> getPricesList(){
        ArrayList<Double> list = new ArrayList<>();
        for (int i=0; i<priceOfProducts.size(); i++){
            String price = priceOfProducts.get(i).getText().replaceAll("\\$", "");
            double p = Double.parseDouble(price);
            list.add(p);
        }
        return list;
    }
    //Sort prices list
    public boolean pricesAreSorted(String sort, ArrayList<Double> list) throws Exception {
        switch (sort){
            case "highlow":
                for (int i=0; i<list.size()-1; i++){
                    if (list.get(i) < list.get(i+1)){
                        return false;
                    }
                }
                return true;
            case "lowhigh":
                for (int i=0; i<list.size()-1; i++){
                    if (list.get(i) > list.get(i+1)){
                        return false;
                    }
                }
                return true;

            default: throw new Exception("Wrong sort input. Please provide 'highlow' or 'lowhigh'");
        }
    }
    //Method for clicking multiple add to cart buttons.
    public void clickOnAddToCartButton(int addProducts){
        for (int i=addProducts-1; i>=0; i--){
            addToCartButton.get(i).click();
        }
    }

    public void clickOnCartIcon(){
        cartIcon.click();
    }
    //Method for checking if the cart is empty.
    public boolean isNotEmptyCart() {
        boolean isEmpty = false;
        try {
            isEmpty = cartBadge.isDisplayed();
        } catch (Exception e) {

        }
        return isEmpty;
    }
    //Method for clicking on a specific product.
    public void clickOnProduct(String nameOfProduct){
        for (int i=0; i<productsName.size(); i++){
            if (productsName.get(i).getText().equals(nameOfProduct)){
                productsName.get(i).click();
                break;
            }
        }
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

    public void clickOnResetAppState(){
        resetAppStateButton.click();
    }
}
