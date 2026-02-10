package com.salesforce.tests;

import com.salesforce.base.BaseTest;
import com.salesforce.pages.LoginPage;
import com.salesforce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginValidTest extends BaseTest {
    private LoginPage loginPage;

    private boolean hasValidCredentials() {
        String username = ConfigReader.getUsername();
        String password = ConfigReader.getPassword();
        return !username.isEmpty() && !password.isEmpty() && !username.equals("encrypted_user");
    }

    @Test(priority = 1)
    public void testValidLoginWithRememberMe() {
        if (!hasValidCredentials()) {
            throw new SkipException("Skipping test: Update global.properties with real Salesforce credentials (Base64 encoded) to run valid login tests");
        }
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.checkRememberMe();
        loginPage.performLogin(ConfigReader.getUsername(), ConfigReader.getPassword());
        boolean isHomePageDisplayed = loginPage.isHomePageDisplayed();
        Assert.assertTrue(isHomePageDisplayed, "Home page should be displayed after successful login");
    }

    @Test(priority = 2)
    public void testValidLoginWithoutRememberMe() {
        if (!hasValidCredentials()) {
            throw new SkipException("Skipping test: Update global.properties with real Salesforce credentials (Base64 encoded) to run valid login tests");
        }
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.uncheckRememberMe();
        loginPage.performLogin(ConfigReader.getUsername(), ConfigReader.getPassword());
        boolean isHomePageDisplayed = loginPage.isHomePageDisplayed();
        Assert.assertTrue(isHomePageDisplayed, "Home page should be displayed after successful login");
    }
}
