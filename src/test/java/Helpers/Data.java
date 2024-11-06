package Helpers;

import java.util.ArrayList;
import java.util.Random;

import static Base.BaseTest.excelReader;

public class Data {
    //Valid username and valid password for log in.
    public static final String validUsername = excelReader.getStringData("Login", 1,0);
    public static final String validPassword = excelReader.getStringData("Login", 1,1);
    //List of items which are located on website sorted by A to Z.
    public static ArrayList<String> listOfItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Sauce Labs Backpack");
        items.add("Sauce Labs Bike Light");
        items.add("Sauce Labs Bolt T-Shirt");
        items.add("Sauce Labs Fleece Jacket");
        items.add("Sauce Labs Onesie");
        items.add("Test.allTheThings() T-Shirt (Red)");
        return items;
    }
    //Choose randome product name
    public String randomProductName(){
        Random random = new Random();
        int randomProduct = random.nextInt(listOfItems().size());
        return listOfItems().get(randomProduct);
    }
    //Sort value
    public static final String AtoZ = "az";
    public static final String ZtoA = "za";
    public static final String HighToLow = "hilo";
    public static final String LowToHigh = "lohi";
    //Create a list that will add products name and check products layout
    ArrayList<String> saveList;
    public ArrayList getSaveList(){
        return saveList;
    }
    public void setSaveList(ArrayList saveList){
        this.saveList = saveList;
    }
    //Error messages.
    public static final String errorRequireUsername = "Epic sadface: Username is required";
    public static final String errorIvnalidUsernameAndPassword = "Epic sadface: Username and password do not match any user in this service";
    public static final String errorLockedOutUser = "Epic sadface: Sorry, this user has been locked out.";
    public static final String errorRequireFirstName = "Error: First Name is required";
    public static final String errorRequireLastName = "Error: Last Name is required";
    public static final String errorRequirePostalCode = "Error: Postal Code is required";
    //Title and successful messages.
    public static final String titleAboutPage = "Sauce Labs: Cross Browser Testing, Selenium Testing & Mobile Testing";
    public static final String finishMessage = "Thank you for your order!";
    //Name of menu options
    public static final String allItems = "All Items";
    public static final String about = "About";
    public static final String logout = "Logout";
    public static final String resetAppState = "Reset App State";
}
