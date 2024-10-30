# SauceDemo

SauceDemo is a website for testing purposes where the main goal is to add items to cart and finish shopping

## Dependencies
* Run on Windows 10 OS
* IDE for this project is IntelliJ Idea Community Edition 2024.2.4
* Browsers needed is Chrome as mandatory but also preferred are Firefox, Edge and Safari

## Instalation

Open terminal in IDE and git clone the repository

```
git clone https://github.com/darko2912/SaucedemoAutomationProject.git
```
* java version "21" 2023-09-19 LTS

## Framework Walkthorugh
Packages:
* Base - Contains classes used through the app
* Helpers - Contains classes that could be useful for DDT
* Pages - Contains classes for each page
* Tests - Contains test classes

Files:
* pom.xml - Contains all dependencies used in the project (last updated: 20.10.2024.)
* TestData.xlsx - Excel file used to read some data for DDT as an alternative

## Naming convention
* Classes are written in camel case with first character in upper case
* Methods are written in camel case with first character in lower case
* Class objects are named the same as a class name with lower case for first character
* Test methods are named as test case names

## Other
* Test methods are kept clean
* Each test should have at least 2 assertions with few test exceptions
* Priorities are set with 10 increment, if higher priority occur later in testing, priority of such tests are placed between the two priorities
