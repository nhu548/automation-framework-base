package ui.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.lang.reflect.Method;

/**
 * Base class for all UI test classes.
 * Responsibilities:
 * - Initialize Extent Report
 * - Launch browser
 * - Open application
 * - Record test result
 * - Capture screenshot on failure
 * - Close browser after each test
 */

public class BaseTest {

    // =========================================================
    // FIELDS
    // =========================================================

    protected WebDriver driver;
    protected ExtentTest test;

    // Single ExtentReport instance shared across all tests
    private static final ExtentReports extent =
            ExtentManager.getInstance();

    // =========================================================
    // TEST LIFECYCLE
    // =========================================================

    @BeforeMethod
    public void setUp(Method method) {
        createExtentTest(method);
        initializeDriver();
        openApplication();

        test.info("Browser launched and application opened");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        updateTestResult(result);
        closeBrowser();

        // Write all test results to the report
        extent.flush();
    }

    // =========================================================
    // EXTENSION POINTS
    // =========================================================

    protected String getTestCategory() {
        return "UI";
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================
    /**
     * Create an Extent Report test using the @Test description.
     * If no description is provided, use the method name.
     */
    private void createExtentTest(Method method) {
        Test annotation = method.getAnnotation(Test.class);

        String testName = annotation.description().isEmpty()
                ? method.getName()
                : annotation.description();

        test = extent.createTest(testName);
        test.assignAuthor("Hoai Nhu");
        test.assignCategory(getTestCategory());
        test.assignDevice(
                ConfigReader.getProperty("browser")
        );
    }

    /**
     * Initialize Chrome browser and maximize the window.
     */
    private void initializeDriver() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Open the application using the configured base URL.
     */
    private void openApplication() {
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    /**
     * Update the Extent Report based on the test execution result.
     * Attach a screenshot automatically when the test fails.
     */
    private void updateTestResult(ITestResult result) {
        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                test.pass("Test Passed");
                break;

            case ITestResult.FAILURE:
                test.fail("Test Failed: " + result.getThrowable());
                attachScreenshot(result);
                break;

            case ITestResult.SKIP:
                test.skip("Test Skipped: " + result.getThrowable());
                break;
        }
    }

    private void attachScreenshot(ITestResult result) {

        String screenshotPath =
                ScreenshotUtil.captureScreenshot(
                        driver,
                        result.getName()
                );

        if (screenshotPath != null) {
            test.addScreenCaptureFromPath(screenshotPath);
        }
    }

    /**
     * Close the browser safely after test execution.
     */
    private void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}