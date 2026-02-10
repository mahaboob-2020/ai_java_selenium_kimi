package com.salesforce.tests;

import com.salesforce.base.BaseTest;
import com.salesforce.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginInvalidTest extends BaseTest {
    private LoginPage loginPage;

    @Test(priority = 1)
    public void testInvalidUsernameAndPassword() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.performLogin("invalid_user@test.com", "invalid_password123");
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for invalid credentials");
        String errorText = loginPage.getErrorMessageText();
        Assert.assertTrue(
                errorText.toLowerCase().contains("login") || errorText.toLowerCase().contains("password") || errorText.toLowerCase().contains("access"),
                "Error message should indicate invalid credentials. Actual: " + errorText);
    }

    @Test(priority = 2)
    public void testEmptyUsername() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.enterUsername("");
        loginPage.enterPassword("somePassword123");
        loginPage.clickLoginButton();
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for empty username");
    }

    @Test(priority = 3)
    public void testEmptyPassword() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.enterUsername("testuser@example.com");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for empty password");
    }

    @Test(priority = 4)
    public void testEmptyUsernameAndPassword() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for empty username and password");
    }

    @Test(priority = 5)
    public void testInvalidEmailFormat() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.performLogin("invalidemailformat", "password123");
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorDisplayed, "Error message should be displayed for invalid email format");
    }

    @Test(priority = 6)
    public void testLoginPageUIElements() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
        Assert.assertTrue(loginPage.isRememberMeCheckboxDisplayed(), "Remember Me checkbox should be displayed");
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "Forgot Password link should be displayed");
    }

    @Test(priority = 7)
    public void testForgotPasswordLink() {
        loginPage = new LoginPage(driver, wait);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        loginPage.clickForgotPassword();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("forgotpassword") || currentUrl.contains("ForgotPassword"),
                "Should navigate to forgot password page");
    }
}
