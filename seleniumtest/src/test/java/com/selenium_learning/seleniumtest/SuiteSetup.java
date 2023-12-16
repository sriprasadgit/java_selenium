package com.selenium_learning.seleniumtest;
import org.testng.annotations.BeforeSuite;

public class SuiteSetup {
    protected static String baseURL;

    @BeforeSuite
    public void setUpSuite() {
        // Initialize your URL here
        baseURL = "https://www.saucedemo.com/";
    }
}