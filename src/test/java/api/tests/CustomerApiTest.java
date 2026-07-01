package api.tests;


import api.base.BaseApiTest;
import api.clients.AccountApiClient;
import api.clients.CustomerApiClient;
import api.clients.LoginApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.AssertUtil;

import java.math.BigDecimal;
import java.util.List;

public class CustomerApiTest extends BaseApiTest {

    private final LoginApiClient loginApiClient =
            new LoginApiClient();

    private final CustomerApiClient customerApiClient =
            new CustomerApiClient();

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    private final String primaryUsername =
            ConfigReader.getProperty("primaryUsername");

    private final String primaryPassword =
            ConfigReader.getProperty("primaryPassword");

    private final String secondaryUsername =
            ConfigReader.getProperty("secondaryUsername");

    private final String secondaryPassword =
            ConfigReader.getProperty("secondaryPassword");

    private final String primaryFirstName =
            ConfigReader.getProperty("primaryFirstName");

    private final String primaryLastName =
            ConfigReader.getProperty("primaryLastName");

    private final String primaryPhoneNumber=
            ConfigReader.getProperty("primaryPhoneNumber");

    private final String primaryCustomerId =
            ConfigReader.getProperty("primaryCustomerId");

    // =========================================================
    // API04 - Verify customer details can be retrieved successfully
    // =========================================================

    @Test(description = "API04 - Verify customer details can be retrieved successfully")
    public void API04_verifyCustomerDetailsCanBeRetrievedSuccessfully() {

        Reporter.log("STEP 1 - Send GET customer details request using a valid customer ID.", true);

        String getCustomerId =
                loginApiClient.getCustomerId(
                        primaryUsername,
                        primaryPassword
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
                primaryCustomerId,
                "Customer ID should match the requested customer ID"
        );

        Reporter.log("STEP 5 - Verify first name is returned.", true);

        Assert.assertEquals(
                firstName,
                primaryFirstName,
                "First name should match"
        );

        Reporter.log("STEP 6 - Verify last name is returned.", true);

        Assert.assertEquals(
                lastName,
                primaryLastName,
                "Last name should match"
        );

        Reporter.log("STEP 7 - Verify phone number is returned.", true);

        Assert.assertEquals(
                phoneNumber,
                primaryPhoneNumber,
                "Phone number should match"
        );

        AssertUtil.assertFieldIsNotBlank(
                city,
                "HCM"
        );

        Reporter.log("PASSED - API04 - Verify customer details can be retrieved successfully", true);

    }

    // =========================================================
    // API05 - Verify customer accounts can be retrieved successfully
    // =========================================================

    @Test(description = "API05 - Verify customer accounts can be retrieved successfully")
    public void API05_verifyCustomerAccountsCanBeRetrievedSuccessfully() {

        Reporter.log("STEP 1 - Get customer ID from login API", true);

        String customerId =
                loginApiClient.getCustomerId(
                        secondaryUsername,
                        secondaryPassword
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

        Reporter.log("STEP 3 - Verify at least one account is returned", true);

        List<String> accountIds =
                response.xmlPath().getList("accounts.account.id");

        Assert.assertFalse(
                accountIds.isEmpty(),
                "At least one account should be returned"
        );


        Reporter.log("STEP 4 - Verify first account details are returned.", true);

         String accountId = response.xmlPath().getString("accounts.account[0].id");
         String accountType = response.xmlPath().getString("accounts.account[0].type");
         String accountBalance = response.xmlPath().getString("accounts.account[0].balance");

        AssertUtil.assertFieldIsNotBlank(
                accountId,
                "Account ID"
        );

        AssertUtil.assertFieldIsNotBlank(
                accountType,
                "Account Type"
        );

        Assert.assertTrue(
                accountType.equals("CHECKING")
                        || accountType.equals("SAVINGS"),
                "Account type should be CHECKING or SAVINGS"
        );

        AssertUtil.assertFieldIsNotBlank(
                accountBalance,
                "Account Balance"
        );

        Reporter.log("STEP 5 - Verify account balance is valid", true);

        AssertUtil.assertFieldIsNotBlank(
                accountBalance,
                "Account balance"
        );

        BigDecimal balance =
                new BigDecimal(accountBalance);

        Reporter.log(
                "Account balance can be parsed successfully: " + balance,
                true
        );

        Reporter.log("PASSED - API05 - Verify customer accounts can be retrieved successfully", true);
    }
}
