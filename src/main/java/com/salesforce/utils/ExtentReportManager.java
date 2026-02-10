package com.salesforce.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportFolderPath;
    private static String reportFilePath;
    private static String screenshotFolderPath;
    private static final String REPORT_BASE_DIR = "reports";
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            initializeReportPaths();
            createReportDirectories();
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Salesforce Automation Report");
            sparkReporter.config().setReportName("Login Test Execution Report");
            sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            sparkReporter.config().setJs("document.getElementsByClassName('logo')[0].style.display='none';");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Report Path", reportFilePath);
        }
        return extent;
    }
    
    private static void initializeReportPaths() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportFolderPath = REPORT_BASE_DIR + "/run_" + timestamp;
        reportFilePath = reportFolderPath + "/ExtentReport.html";
        screenshotFolderPath = reportFolderPath + "/screenshots";
    }
    
    private static void createReportDirectories() {
        try {
            Files.createDirectories(Paths.get(reportFolderPath));
            Files.createDirectories(Paths.get(screenshotFolderPath));
            System.out.println("Report directory created: " + reportFolderPath);
        } catch (IOException e) {
            System.err.println("Failed to create report directories: " + e.getMessage());
        }
    }
    
    public static String getReportFolderPath() {
        return reportFolderPath;
    }
    
    public static String getReportFilePath() {
        return reportFilePath;
    }
    
    public static String getScreenshotFolderPath() {
        return screenshotFolderPath;
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = getInstance().createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
            System.out.println("Report generated at: " + reportFilePath);
        }
    }
}
