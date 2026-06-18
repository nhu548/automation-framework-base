package api.tests;

import api.base.BaseApiTest;
import api.clients.AccountApiClient;
import api.testdata.ApiTestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class AccountApiTest extends BaseApiTest {

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    @Test(description = "API06 - Verify account details can be retrieved successfully")
    public void API06_verifyAccountDetailsCanBeRetrievedSuccessfully() {

        Reporter.log("STEP 1 - Send GET account details request", true);
        Response response =
                accountApiClient.getAccountById(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Verify response status code is 200", true);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200"
        );

        Reporter.log("STEP 3 - Extract account details from response", true);

        String accountId =
                response.xmlPath().getString("account.id");

        String customerId =
                response.xmlPath().getString("account.customerId");

        String accountType =
                response.xmlPath().getString("account.type");

        String balance =
                response.xmlPath().getString("account.balance");

        Reporter.log("STEP 4 - Verify account details are valid", true);

        Assert.assertEquals(
                accountId,
                ApiTestData.VALID_ACCOUNT_ID,
                "Account ID should match requested account ID"
        );

        accountApiClient.assertFieldIsNotBlank(
                customerId,
                "Customer ID"
        );

        Assert.assertTrue(
                customerId.matches("\\d+"),
                "Customer ID should contain digits only"
        );

        accountApiClient.assertFieldIsNotBlank(
                accountType,
                "Account type"
        );

        Assert.assertTrue(
                accountType.equals("CHECKING")
                        || accountType.equals("SAVINGS"),
                "Account type should be CHECKING or SAVINGS"
        );

        accountApiClient.assertFieldIsNotBlank(
                balance,
                "Balance"
        );

        Assert.assertTrue(
                Double.parseDouble(balance) >= 0,
                "Balance should be greater than or equal to zero"
        );

        Reporter.log("PASSED - API06 - Verify account details can be retrieved successfully", true);
    }

    @Test(description = "API07 - Verify error response is returned for invalid account ID")
    public void API07_verifyErrorResponseReturnedForInvalidAccountId() {

        Reporter.log("STEP 1 - Send GET account details request with invalid account ID", true);

        Response response =
                accountApiClient.getAccountById(
                        ApiTestData.INVALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Verify status code is 400", true);

        Assert.assertEquals(
                response.statusCode(),
                400,
                "Status code should be 400 for invalid account ID"
        );

        Reporter.log("STEP 3 - Verify error message contains invalid account ID", true);

        String errorMessage =
                response.asString();

        Assert.assertTrue(
                errorMessage.contains(
                        "Could not find account #"
                                + ApiTestData.INVALID_ACCOUNT_ID
                ),
                "Error message should contain invalid account ID"
        );

        Reporter.log("PASSED - API07 - Verify error response is returned for invalid account ID", true);

    }

    @Test(description = "API08 - Verify user can create a new checking account successfully")
    public void API08_verifyCreateNewCheckingAccountSuccessfully() {

        Reporter.log("STEP 1 - Get customer ID from existing source account", true);

        String customerId =
                accountApiClient.getCustomerIdByAccountId(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        accountApiClient.assertFieldIsNotBlank(
                customerId,
                "Customer ID"
        );

        Reporter.log("STEP 2 - Send create checking account request", true);

        Response response =
                accountApiClient.createNewAccount(
                        customerId,
                        ApiTestData.CHECKING_ACCOUNT_TYPE,
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 3 - Verify status code is 200", true);

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200"
        );

        Reporter.log("STEP 4 - Extract new account details from response", true);

        String newAccountId =
                response.xmlPath().getString("account.id");

        String newCustomerId =
                response.xmlPath().getString("account.customerId");

        String accountType =
                response.xmlPath().getString("account.type");

        String balance =
                response.xmlPath().getString("account.balance");

        Reporter.log("STEP 5 - Verify new account ID is generated", true);

        accountApiClient.assertFieldIsNotBlank(
                newAccountId,
                "New account ID"
        );

        Assert.assertTrue(
                newAccountId.matches("\\d+"),
                "New account ID should contain digits only"
        );

        Reporter.log("STEP 6 - Verify new account belongs to source customer", true);

        Assert.assertEquals(
                newCustomerId,
                customerId,
                "New account should belong to the same customer"
        );

        Reporter.log("STEP 7 - Verify account type is CHECKING", true);

        Assert.assertEquals(
                accountType,
                "CHECKING",
                "Account type should be CHECKING"
        );

        Reporter.log("STEP 8 - Verify balance is returned", true);

        accountApiClient.assertFieldIsNotBlank(
                balance,
                "Balance"
        );

        Assert.assertTrue(
                Double.parseDouble(balance) >= 0,
                "Balance should be greater than or equal to zero"
        );

        Reporter.log("PASSED - API08 - Verify user can create a new checking account successfully", true);
    }

}