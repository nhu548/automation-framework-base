package api.tests;


import api.base.BaseApiTest;
import api.clients.AccountApiClient;
import api.clients.LoginApiClient;
import api.clients.CustomerApiClient;
import api.testdata.ApiTestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;

public class CustomerApiTest extends BaseApiTest {

    private final String username =
            ConfigReader.getProperty("username1");

    private final String password =
            ConfigReader.getProperty("password1");

    private final LoginApiClient loginApiClient =
            new LoginApiClient();

    private final CustomerApiClient customerApiClient =
            new CustomerApiClient();

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    @Test(description = "API04 - Verify customer details can be retrieved successfully")
    public void API04_verifyCustomerDetailsCanBeRetrievedSuccessfully() {

        Reporter.log("STEP 1 - Send GET customer details request using a valid customer ID.", true);

        String getCustomerId =
                loginApiClient.getCustomerId(
                        username,
                        password
                );

        Response response =
                customerApiClient.getCustomerById(getCustomerId);

        Reporter.log("STEP 2 - Verify response status code.", true);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Customer details API status code should be 200"
        );

        Reporter.log("STEP 3 - Verify customer information is returned.", true);

        String customerId = response.xmlPath().getString("customer.id");
        String firstName = response.xmlPath().getString("customer.firstName");
        String lastName = response.xmlPath().getString("customer.lastName");
        String phoneNumber = response.xmlPath().getString("customer.phoneNumber");
        String city = response.xmlPath().getString("customer.address.city");

        Reporter.log("STEP 4 - Verify customer ID matches requested customer ID.", true);
        Assert.assertEquals(
                customerId,
                ApiTestData.VALID_CUSTOMER_ID,
                "Customer ID should match the requested customer ID"
        );

        Reporter.log("STEP 5 - Verify first name is returned.", true);

        Assert.assertEquals(
                firstName,
                ApiTestData.EXPECTED_FIRST_NAME,
                "First name should match"
        );

        Reporter.log("STEP 6 - Verify last name is returned.", true);

        Assert.assertEquals(
                lastName,
                ApiTestData.EXPECTED_LAST_NAME,
                "Last name should match"
        );

        Reporter.log("STEP 7 - Verify phone number is returned.", true);

        Assert.assertEquals(
                phoneNumber,
                ApiTestData.EXPECTED_PHONE_NUMBER,
                "Phone number should match"
        );

        accountApiClient.assertFieldIsNotBlank(
                city,
                "HCM"
        );

        Reporter.log("PASSED - API04 - Verify customer details can be retrieved successfully", true);

    }

    @Test(description = "API05 - Verify customer accounts can be retrieved successfully")
    public void API05_verifyCustomerAccountsCanBeRetrievedSuccessfully() {

        Reporter.log("STEP 1 - Get customer ID from login API", true);
        String customerId =
                loginApiClient.getCustomerId(
                        username,
                        password
                );

        Assert.assertNotNull(
                customerId,
                "Customer ID should be returned from login API"
        );

        Reporter.log("STEP 2 - Send GET customer accounts request using a valid customer ID.", true);
        Response response =
                customerApiClient.getAccountsByCustomerId(
                        customerId
                );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Customer accounts API status code should be 200"
        );

        Reporter.log("STEP 4 - Verify at least one account is returned", true);

        List<String> accountIds =
                response.xmlPath().getList("accounts.account.id");

        Assert.assertFalse(
                accountIds.isEmpty(),
                "At least one account should be returned"
        );


        Reporter.log("STEP 3 - Verify account information is returned.", true);

         String accountId = response.xmlPath().getString("accounts.account[0].id");
         String accountType = response.xmlPath().getString("accounts.account[0].type");
         String accountBalance = response.xmlPath().getString("accounts.account[0].balance");

        Reporter.log("STEP 4 - Verify account ID is returned.", true);
        Assert.assertNotNull(
                accountId,
                "Account ID should be returned"
        );

        Reporter.log("STEP 5 - Verify account type is returned.", true);

        Assert.assertNotNull(
                accountType,
                "Account type should be returned"
        );

        Reporter.log("STEP 6 - Verify account balance is returned.", true);

        Assert.assertNotNull(
                accountBalance,
                "Account balance should be returned"
        );

        double balance = Double.parseDouble(accountBalance);
        Assert.assertTrue(
                balance >= 0,
                "Account balance should be greater than or equal to zero"
        );

        Reporter.log("PASSED - API05 - Verify customer accounts can be retrieved successfully", true);
    }
}
