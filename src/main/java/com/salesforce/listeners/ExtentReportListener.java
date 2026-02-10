package com.salesforce.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.salesforce.utils.ExtentReportManager;
import com.salesforce.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class ExtentReportListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getName());
        ExtentReportManager.createTest(result.getName());
        ExtentReportManager.getTest().log(Status.INFO, "Test Started: " + result.getName());
        ExtentReportManager.getTest().assignCategory(result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName() + "_PASS");
            ExtentReportManager.getTest().log(Status.PASS, "Test Passed Successfully");
            if (screenshotPath != null) {
                try {
                    ExtentReportManager.getTest().info("Screenshot:", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getName());
        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName() + "_FAIL");
            String errorMessage = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";
            ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + errorMessage);
            if (screenshotPath != null) {
                try {
                    ExtentReportManager.getTest().info("Screenshot on Failure:", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: " + result.getName());
        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName() + "_SKIP");
            ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getName());
            if (screenshotPath != null) {
                try {
                    ExtentReportManager.getTest().info("Screenshot:", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot: " + e.getMessage());
                }
            }
        } else {
            ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: " + context.getName());
        System.out.println("Report Location: " + ExtentReportManager.getReportFilePath());
        ExtentReportManager.flush();
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(testInstance);
        } catch (Exception e) {
            logger.error("Failed to get driver from test instance: " + e.getMessage());
            return null;
        }
    }
}
