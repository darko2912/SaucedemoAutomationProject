package Helpers;

import java.util.ArrayList;

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
