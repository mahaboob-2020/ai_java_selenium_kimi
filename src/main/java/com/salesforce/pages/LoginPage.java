package com.salesforce.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMeCheckbox;

    @FindBy(xpath = "//div[@id='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[@id='forgot_password_link']")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//div[@class='loginError'][@id='usernamegroup']//div[@class='loginError'][contains(text(),'Please enter your username')]")
    private WebElement usernameError;

    @FindBy(xpath = "//div[@class='loginError'][@id='passwordgroup']//div[@class='loginError'][contains(text(),'Please enter your password')]")
    private WebElement passwordError;

    @FindBy(xpath = "//div[@class='content'][contains(text(),'To access Salesforce, please')]")
    private WebElement invalidCredentialsError;

    @FindBy(xpath = "//h1[contains(text(),'Home')]")
    private WebElement homePageHeader;

    @FindBy(xpath = "//span[@id='userNavLabel']")
    private WebElement userNavLabel;

    @FindBy(xpath = "//title[contains(text(),'Login') or contains(text(),'Salesforce')]")
    private WebElement pageTitle;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            usernameField.clear();
            usernameField.sendKeys(username);
            logger.info("Username entered successfully");
        } catch (Exception e) {
            logger.error("Failed to enter username: " + e.getMessage());
            throw e;
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            passwordField.clear();
            passwordField.sendKeys(password);
            logger.info("Password entered successfully");
        } catch (Exception e) {
            logger.error("Failed to enter password: " + e.getMessage());
            throw e;
        }
    }

    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
            logger.info("Login button clicked");
        } catch (Exception e) {
            logger.error("Failed to click login button: " + e.getMessage());
            throw e;
        }
    }

    public void checkRememberMe() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
            if (!rememberMeCheckbox.isSelected()) {
                rememberMeCheckbox.click();
                logger.info("Remember Me checkbox selected");
            }
        } catch (Exception e) {
            logger.error("Failed to check Remember Me: " + e.getMessage());
            throw e;
        }
    }

    public void uncheckRememberMe() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
            if (rememberMeCheckbox.isSelected()) {
                rememberMeCheckbox.click();
                logger.info("Remember Me checkbox unselected");
            }
        } catch (Exception e) {
            logger.error("Failed to uncheck Remember Me: " + e.getMessage());
            throw e;
        }
    }

    public void clickForgotPassword() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));
            forgotPasswordLink.click();
            logger.info("Forgot Password link clicked");
        } catch (Exception e) {
            logger.error("Failed to click Forgot Password: " + e.getMessage());
            throw e;
        }
    }

    public void performLogin(String username, String password) {
        try {
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
            logger.info("Login performed with username: " + username);
        } catch (Exception e) {
            logger.error("Login failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            logger.error("Error checking error message display: " + e.getMessage());
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            String text = errorMessage.getText();
            logger.info("Error message displayed: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get error message: " + e.getMessage());
            return "";
        }
    }

    public boolean isInvalidCredentialsErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(invalidCredentialsError));
            return invalidCredentialsError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return usernameField.isDisplayed() && passwordField.isDisplayed() && loginButton.isDisplayed();
        } catch (Exception e) {
            logger.error("Login page display check failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(userNavLabel));
            return userNavLabel.isDisplayed();
        } catch (Exception e) {
            logger.error("Home page check failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isUsernameFieldDisplayed() {
        try {
            return usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldDisplayed() {
        try {
            return passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginButtonDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRememberMeCheckboxDisplayed() {
        try {
            return rememberMeCheckbox.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isForgotPasswordLinkDisplayed() {
        try {
            return forgotPasswordLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
