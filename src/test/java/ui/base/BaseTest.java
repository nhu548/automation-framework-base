package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtil;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeMethod
    public void setUp(Method method) {

        extent = ExtentManager.getInstance();

//        test = extent.createTest(method.getName());
//
        Test annotation = method.getAnnotation(Test.class);
        String testName = annotation.description().isEmpty() ?
                method.getName() :
                annotation.description();
        test = extent.createTest(testName);

        WebDriverManager.chromedriver().setup();

        //Init driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //Open application
        driver.get(ConfigReader.getProperty("baseUrl"));

        test.info("Browser launched and URL opened");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            test.fail("Test Failed");

            ScreenshotUtil.captureScreenshot(
                    driver,
                    result.getName()
            );
        }else if (ITestResult.SUCCESS == result.getStatus()) {

            test.pass("Test Passed");
        }
        extent.flush();

        driver.quit();
    }
}