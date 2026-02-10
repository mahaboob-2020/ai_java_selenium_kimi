# Salesforce Selenium Automation Framework

An enterprise-level Selenium automation framework built with Java, Maven, TestNG, and Extent Reports for testing Salesforce login functionality.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Viewing Reports](#viewing-reports)
- [Test Cases](#test-cases)
- [Framework Architecture](#framework-architecture)

## Features

- **Page Object Model (POM)** with PageFactory pattern
- **TestNG** for test execution and management
- **Extent Reports** with timestamped reports and embedded screenshots
- **Log4j2** for comprehensive logging
- **WebDriverManager** for automatic driver management
- **Encrypted Credentials** support for secure credential storage
- **Cross-browser Support** (Chrome, Firefox, Edge, Safari)
- **Screenshot Capture** on pass, fail, and skip
- **Structured Exception Handling** with try-catch blocks
- **Maven** for dependency management and build automation

## Technology Stack

| Component | Version |
|-----------|---------|
| Java | 11+ |
| Selenium | 4.18.1 |
| TestNG | 7.7.1 |
| Extent Reports | 5.1.1 |
| WebDriverManager | 5.8.0 |
| Log4j2 | 2.22.1 |
| Maven | 3.6+ |

## Project Structure

```
salesforce-automation/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   ├── main/java/com/salesforce/
│   │   ├── base/
│   │   │   └── BaseTest.java        # Base test class with setup/teardown
│   │   ├── listeners/
│   │   │   └── ExtentReportListener.java  # TestNG listener for reports
│   │   ├── pages/
│   │   │   └── LoginPage.java       # Login page object model
│   │   └── utils/
│   │       ├── ConfigReader.java    # Property file reader with decryption
│   │       ├── ExtentReportManager.java  # Report manager with timestamps
│   │       └── ScreenshotUtil.java  # Screenshot capture utility
│   ├── test/java/com/salesforce/tests/
│   │   ├── LoginInvalidTest.java    # Invalid login test cases
│   │   └── LoginValidTest.java      # Valid login test cases
│   └── test/resources/
│       ├── global.properties        # Configuration file
│       ├── log4j2.xml              # Log4j2 configuration
│       ├── testng.xml              # Default test suite
│       ├── testng-all.xml          # All tests suite
│       └── testng-invalid-only.xml # Invalid tests only suite
├── reports/                         # Extent reports output
│   └── run_YYYYMMDD_HHMMSS/        # Timestamped run folder
│       ├── ExtentReport.html       # HTML report
│       └── screenshots/            # Test screenshots
├── logs/                           # Log files
│   └── automation.log
└── target/                         # Maven build output
```

## Prerequisites

1. **Java 11 or higher** installed
   ```bash
   java -version
   ```

2. **Maven** installed
   ```bash
   mvn -version
   ```

3. **Git** installed (optional, for cloning)
   ```bash
   git --version
   ```

4. **Chrome/Firefox/Edge** browser installed (Chrome is default)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/mahaboob-2020/ai_java_selenium_kimi.git
cd ai_java_selenium_kimi
```

### 2. Configure Credentials

To run valid login tests, you need to update the credentials in `src/test/resources/global.properties`.

#### Encode Your Credentials

**Windows (PowerShell):**
```powershell
[Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("your_salesforce_username@example.com"))
[Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("your_salesforce_password"))
```

**Linux/Mac:**
```bash
echo -n "your_salesforce_username@example.com" | base64
echo -n "your_salesforce_password" | base64
```

#### Update Configuration File

Edit `src/test/resources/global.properties`:

```properties
browser=chrome
url=https://login.salesforce.com/?locale=in
encryptedUsername=your_base64_encoded_username
encryptedPassword=your_base64_encoded_password
```

**Note:** If credentials are not updated, valid login tests will be skipped.

### 3. Install Dependencies

```bash
mvn clean install -DskipTests
```

## Configuration

### Browser Configuration

Change the browser in `src/test/resources/global.properties`:

```properties
browser=chrome    # Options: chrome, firefox, edge, safari
```

### URL Configuration

The default URL is Salesforce India login page:

```properties
url=https://login.salesforce.com/?locale=in
```

## Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Only Invalid Login Tests (No credentials needed)

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng-invalid-only.xml
```

### Run All Tests Including Valid Login

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng-all.xml
```

### Run Specific Test Class

```bash
# Run only invalid login tests
mvn clean test -Dtest=LoginInvalidTest

# Run only valid login tests
mvn clean test -Dtest=LoginValidTest
```

### Run with Force Dependency Update

```bash
mvn clean test -U
```

### Run in Debug Mode

```bash
mvn clean test -X
```

## Viewing Reports

### Extent Reports

After test execution, reports are generated in timestamped folders:

```
reports/run_YYYYMMDD_HHMMSS/
├── ExtentReport.html      # Main HTML report
└── screenshots/           # Test screenshots
    ├── testName_PASS_timestamp.png
    └── testName_FAIL_timestamp.png
```

**Open the report:**
```bash
# Windows
start reports/run_YYYYMMDD_HHMMSS/ExtentReport.html

# Mac
open reports/run_YYYYMMDD_HHMMSS/ExtentReport.html

# Linux
xdg-open reports/run_YYYYMMDD_HHMMSS/ExtentReport.html
```

### Console Output

The report location is printed in console:
```
Report Location: reports/run_20260210_205501/ExtentReport.html
```

### Logs

Check `logs/automation.log` for detailed execution logs.

## Test Cases

### Invalid Login Tests (`LoginInvalidTest.java`)

| Test Case | Description |
|-----------|-------------|
| `testInvalidUsernameAndPassword` | Login with invalid credentials |
| `testEmptyUsername` | Login with empty username |
| `testEmptyPassword` | Login with empty password |
| `testEmptyUsernameAndPassword` | Login with both fields empty |
| `testInvalidEmailFormat` | Login with invalid email format |
| `testLoginPageUIElements` | Verify all UI elements on login page |
| `testForgotPasswordLink` | Verify forgot password link navigation |

### Valid Login Tests (`LoginValidTest.java`)

| Test Case | Description |
|-----------|-------------|
| `testValidLoginWithRememberMe` | Valid login with "Remember Me" checked |
| `testValidLoginWithoutRememberMe` | Valid login without "Remember Me" |

**Note:** Valid login tests require real Salesforce credentials in `global.properties`.

## Framework Architecture

### Page Object Model (POM)

The framework uses Page Object Model with PageFactory for maintainability:

```java
@FindBy(xpath = "//input[@id='username']")
private WebElement usernameField;
```

### Base Test Class

`BaseTest.java` handles:
- WebDriver initialization
- Browser setup
- Implicit and explicit waits
- Test setup and teardown
- Report initialization

### Listeners

`ExtentReportListener.java` provides:
- Automatic test logging
- Screenshot capture on pass/fail/skip
- Report generation with timestamps

### Utilities

| Utility | Purpose |
|---------|---------|
| `ConfigReader` | Reads encrypted credentials from properties file |
| `ExtentReportManager` | Manages Extent Reports with timestamped folders |
| `ScreenshotUtil` | Captures and saves screenshots |

### Exception Handling

All page methods include structured try-catch blocks:

```java
public void enterUsername(String username) {
    try {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);
        logger.info("Username entered successfully");
    } catch (Exception e) {
        logger.error("Failed to enter username: " + e.getMessage());
        throw e;
    }
}
```

## Troubleshooting

### Common Issues

#### 1. Driver Not Found
**Solution:** WebDriverManager handles driver downloads automatically. Ensure you have internet connectivity.

#### 2. Credentials Not Working
**Solution:** Ensure credentials are Base64 encoded properly and saved in `global.properties`.

#### 3. Tests Skipping
**Solution:** Valid login tests skip if credentials are not updated. Update `global.properties` with real credentials.

#### 4. Screenshots Not Attaching
**Solution:** Screenshots are saved in the same folder as the report. Check `reports/run_*/screenshots/`.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please create an issue in the GitHub repository.

---

**Repository:** https://github.com/mahaboob-2020/ai_java_selenium_kimi.git
