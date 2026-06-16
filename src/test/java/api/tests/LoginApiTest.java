package api.tests;

import api.base.BaseApiTest;
import api.clients.LoginApiClient;
import api.testdata.ApiTestData;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LoginApiTest extends BaseApiTest {

    private final String username =
            ConfigReader.getProperty("username1");

    private final String password =
            ConfigReader.getProperty("password1");

    private final LoginApiClient loginApiClient =
            new LoginApiClient();

    @Test(description = "API01 - Verify user can login successfully with valid credentials")
    public void API01_verifyUserCanLoginSuccessfullyWithValidCredentials() {

        Reporter.log("STEP 1 - Send login request with valid credentials", true);

        Response response =
                loginApiClient.login(
                        username,
                        password
                );

        Reporter.log("STEP 2 - Verify response status code is 200", true);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Login API status code should be 200"
        );

        Reporter.log("STEP 3 - Extract customer information from response", true);

        XmlPath xmlPath =
                new XmlPath(response.asString());

        String customerId =
                xmlPath.getString("customer.id");

        String firstName =
                xmlPath.getString("customer.firstName");

        String lastName =
                xmlPath.getString("customer.lastName");

        String phoneNumber =
                xmlPath.getString("customer.phoneNumber");

        Reporter.log("STEP 4 - Verify customer information is returned correctly", true);

        Assert.assertTrue(
                customerId.matches("\\d+"),
                "Customer ID should contain digits only"
        );

        Assert.assertEquals(
                firstName,
                ApiTestData.EXPECTED_FIRST_NAME,
                "First name should match"
        );

        Assert.assertEquals(
                lastName,
                ApiTestData.EXPECTED_LAST_NAME,
                "Last name should match"
        );

        Assert.assertEquals(
                phoneNumber,
                ApiTestData.EXPECTED_PHONE_NUMBER,
                "Phone number should match"
        );

        Reporter.log("PASSED - API01 - Verify user can login successfully with valid credentials", true);
    }

    @Test(description = "API02 - Verify login request returns error for invalid username")
    public void API02_verifyLoginRequestReturnsErrorForInvalidUsername() {

        Reporter.log(
                "STEP 1 - Send login request with invalid username",
                true
        );

        Response response =
                loginApiClient.login(
                        ApiTestData.INVALID_USERNAME,
                        password
                );

        Reporter.log(
                "STEP 2 - Verify login request is rejected",
                true
        );

        Assert.assertEquals(
                response.statusCode(),
                400,
                "Login API status code should be 400 for invalid username"
        );

        Reporter.log(
                "STEP 3 - Verify error message",
                true
        );

        String responseBody =
                response.asString();

        Assert.assertTrue(
                responseBody.contains(
                        "Invalid username and/or password"
                ),
                "Error message should indicate invalid credentials"
        );

        Reporter.log("PASSED - API02 - Verify login request returns error for invalid username", true);
    }

    @Test(description = "API03 - Verify login request returns error for invalid password")
    public void API03_verifyLoginRequestReturnsErrorForInvalidPassword() {

        Reporter.log(
                "STEP 1 - Send login request with invalid password",
                true
        );

        Response response =
                loginApiClient.login(
                        username,
                        ApiTestData.INVALID_PASSWORD
                );

        Reporter.log(
                "STEP 2 - Verify response status code is 400",
                true
        );

        Assert.assertEquals(
                response.statusCode(),
                400,
                "Login API status code should be 400 for invalid password"
        );

        Reporter.log(
                "STEP 3 - Verify error message",
                true
        );

        String responseBody =
                response.asString();

        Assert.assertTrue(
                responseBody.contains(
                        "Invalid username and/or password"
                ),
                "Error message should indicate invalid credentials"
        );

        Reporter.log("PASSED - API03 - Verify login request returns error for invalid password", true);
    }

}