package Helpers;

import Base.ExcelReader;

import java.util.ArrayList;

import static Base.BaseTest.excelReader;

public class Data {
    //Valid username and valid password for log in.
    public static final String validUsername = excelReader.getStringData("Login", 1,0);
    public static final String validPassword = excelReader.getStringData("Login", 1,1);
    //List of items which are located on website.
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
}
