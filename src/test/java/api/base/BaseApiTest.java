package api.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ExtentManager;

import java.lang.reflect.Method;

/**
 * Base class for all API tests.
 * Handles API base URI setup and Extent Report test result logging.
 */
public class BaseApiTest {

    // =========================================================
    // FIELDS
    // =========================================================

    protected ExtentTest test;
    private static final ExtentReports extent =
            ExtentManager.getInstance();


    // =========================================================
    // TEST LIFECYCLE
    // =========================================================

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI =
                ConfigReader.getProperty("baseUrl")
                        + "/services/bank";
    }

    @BeforeMethod
    public void setUpApiTest(Method method) {
        createExtentTest(method);
    }

    @AfterMethod
    public void tearDownApiTest(ITestResult result) {
        updateTestResult(result);
        extent.flush();
    }


    // =========================================================
    // EXTENSION POINTS
    // =========================================================

    protected String getTestCategory() {
        return "API";
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private void createExtentTest(Method method) {

        Test testAnnotation =
                method.getAnnotation(Test.class);

        String testName =
                testAnnotation.description().isEmpty()
                        ? method.getName()
                        : testAnnotation.description();

        test = extent.createTest(testName);

        test.assignAuthor("Hoai Nhu");
        test.assignCategory(getTestCategory());
    }

    private void updateTestResult(ITestResult result) {

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                test.pass("Test Passed");
                break;

            case ITestResult.FAILURE:
                test.fail("Test Failed: " + result.getThrowable());
                break;

            case ITestResult.SKIP:
                test.skip("Test Skipped: " + result.getThrowable());
                break;

            default:
                test.warning("Test finished with unknown status.");
                break;
        }
    }
}