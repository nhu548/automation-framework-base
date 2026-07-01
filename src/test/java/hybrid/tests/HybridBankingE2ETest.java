package hybrid.tests;

import api.clients.AccountApiClient;
import api.clients.TransactionApiClient;
import api.testdata.ApiTestData;
import hybrid.base.BaseHybridTest;
import ui.testdata.UITestData;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.OpenNewAccountPage;
import ui.pages.TransferPage;
import utils.AssertUtil;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.util.List;

import static ui.testdata.UITestData.*;
import static utils.AssertUtil.assertFieldIsNotBlank;

public class HybridBankingE2ETest extends BaseHybridTest {

    private OpenNewAccountPage openNewAccountPage;

    private TransferPage transferPage;

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    private final TransactionApiClient transactionApiClient =
            new TransactionApiClient();

    private final String primarySourceAccountId=
            ConfigReader.getProperty("primarySourceAccountId");

    private static final String ACCOUNT_OPENED_SUCCESS_MESSAGE =
            "Account Opened!";

    private static final String TRANSFER_SUCCESS_MESSAGE =
            "Transfer Complete!";

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupHybridTest() {

        RestAssured.baseURI =
                ConfigReader.getProperty("apiBaseUrl");

        openNewAccountPage =
                dashboardPage.navigateToOpenNewAccount();
    }

    // =========================================================
    // HYBRID01 - Verify new account created by UI can be retrieved By API
    // =========================================================

    @Test(description = "HYBRID01 - Verify new account created by UI can be retrieved By API")
    public void HYBRID01_verifyNewAccountCreatedByUICanBeRetrievedByAPI() {

        test.info("STEP 1 - Create a new CHECKING account by UI");

        String accountType =
                UITestData.CHECKING_ACCOUNT_TYPE;

        openNewAccountPage.openNewAccount(
                accountType,
                primarySourceAccountId
        );

        test.info("STEP 2 - Capture newly created account ID from UI");

        Assert.assertEquals(
                openNewAccountPage.getSuccessMessage(),
                ACCOUNT_OPENED_SUCCESS_MESSAGE,
                "Incorrect success message displayed"
        );

        String newAccountIdFromUI =
                openNewAccountPage.getNewAccountId();

        Assert.assertFalse(
                newAccountIdFromUI.isEmpty(),
                "New account number should not be empty"
        );

        test.info("STEP 3 - Send GET account details API request using new account ID");

        io.restassured.response.Response response =
                accountApiClient.getAccountById(
                        newAccountIdFromUI
                );

        test.info("STEP 4 - Verify API status code is 200");

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Status code should be 200"
        );

        test.info("STEP 5 - Verify account ID from API matches new account ID from UI");

        String accountIdFromApi =
                response.xmlPath().getString("account.id");

        Assert.assertEquals(
                accountIdFromApi,
                newAccountIdFromUI,
                "Account ID should match requested account ID"
        );

        test.info("STEP 6 - Verify customer ID is returned");

        String customerId =
                response.xmlPath().getString("account.customerId");

        AssertUtil.assertFieldIsNotBlank(
                customerId,
                "Customer ID"
        );

        Assert.assertTrue(
                customerId.matches("\\d+"),
                "Customer ID should contain digits only"
        );

        test.info("STEP 7 - Verify account type is CHECKING");
        String accountTypeFromApi =
                response.xmlPath().getString("account.type");

        AssertUtil.assertFieldIsNotBlank(
                accountTypeFromApi,
                "Account type"
        );

        Assert.assertEquals(
                accountTypeFromApi,
                accountType,
                "Account type from API should match account type selected on UI"
        );

        test.info("STEP 8 - Verify balance is returned and can be parsed");
        String balance =
                response.xmlPath().getString("account.balance");

        AssertUtil.assertFieldIsNotBlank(
                balance,
                "Balance"
        );

        new BigDecimal(balance);

        test.info("PASSED - HYBRID01 - New account created by UI can be retrieved by API");
    }

    // =========================================================
    // HYBRID02 - Verify transfer performed by UI can be validated by API
    // =========================================================

    @Test(description = "HYBRID02 - Verify transfer performed by UI can be validated by API")
    public void HYBRID02_verifyTransferPerformedByUICanBeValidatedByAPI() {

        test.info("STEP 1 - Create destination account by API");

        String destinationAccountId =
                accountApiClient.createNewAccount(
                        primarySourceAccountId,
                        ApiTestData.CHECKING_ACCOUNT_TYPE
                );

        assertFieldIsNotBlank(
                destinationAccountId,
                "New account ID"
        );

        test.info("STEP 2 - Get source balance by API before transfer");

        BigDecimal sourceBalanceBefore =
                accountApiClient.getAccountBalance(
                        primarySourceAccountId
                );
        test.info("Source balance before: " + sourceBalanceBefore);

        test.info("STEP 3 - Get destination balance by API before transfer");

        BigDecimal destinationBalanceBefore =
                accountApiClient.getAccountBalance(
                        destinationAccountId
                );
        test.info("Destination balance before: " + destinationBalanceBefore);

        test.info("STEP 4 - Get source transactions by API before transfer");

        List<String> sourceTransactionIdsBefore =
                transactionApiClient.getTransactionIdsByAccountId(
                        primarySourceAccountId
                );

        test.info(
                "Source transaction IDs before transfer: "
                        + sourceTransactionIdsBefore
        );

        test.info("STEP 5 - Get destination transaction IDs by API before transfer");

        List<String> destinationTransactionIdsBefore =
                transactionApiClient.getTransactionIdsByAccountId(
                        destinationAccountId
                );

        test.info(
                "Destination transaction IDs before transfer: "
                        + destinationTransactionIdsBefore
        );

        test.info("STEP 6 - Navigate to Transfer Funds page");

        transferPage =
                dashboardPage.navigateToTransferFunds();

        test.info("STEP 7 - Transfer funds by UI");

        transferPage.transferFunds(
                primarySourceAccountId,
                destinationAccountId,
                VALID_TRANSFER_AMOUNT);

        test.info("STEP 8 - Verify transfer success message by UI");

        Assert.assertTrue(
                transferPage.isTransferSuccessful(),
                "Transfer should complete successfully"
        );

        Assert.assertEquals(
                transferPage.getTransferSuccessMessage(),
                TRANSFER_SUCCESS_MESSAGE,
                "Incorrect success message displayed"
        );

        test.info("STEP 9 - Get source and destination balances by API after transfer");

        BigDecimal sourceBalanceAfter =
                accountApiClient.getAccountBalance(
                        primarySourceAccountId
                );
        test.info("Source balance after: " + sourceBalanceAfter);

        BigDecimal destinationBalanceAfter =
                accountApiClient.getAccountBalance(
                        destinationAccountId
                );
        test.info("Destination balance after: " + destinationBalanceAfter);

        test.info("STEP 10 - Verify source balance decreased by API");

        BigDecimal transferAmount =
                new BigDecimal(ApiTestData.VALID_TRANSFER_AMOUNT);

        test.info("Transfer amount: " + transferAmount);

        BigDecimal expectedSourceBalance =
                sourceBalanceBefore.subtract(
                        transferAmount
                );

        test.info("Expected source balance after: " + expectedSourceBalance);

        Assert.assertEquals(
                sourceBalanceAfter.compareTo(
                        expectedSourceBalance
                ),
                0,
                "Source balance should decrease by transfer amount"
        );

        test.info("STEP 11 - Verify destination balance increased by API");

        BigDecimal expectedDestinationBalance =
                destinationBalanceBefore.add(
                        transferAmount
                );

        test.info("Expected destination balance after: " + expectedDestinationBalance);

        Assert.assertEquals(
                destinationBalanceAfter.compareTo(
                        expectedDestinationBalance
                ),
                0,
                "Destination balance should increase by transfer amount"
        );

        test.info("STEP 12 - Get source and destination transaction IDs after transfer by API");

        List<String> sourceTransactionIdsAfter =
                transactionApiClient.getTransactionIdsByAccountId(
                        primarySourceAccountId
                );

        List<String> destinationTransactionIdsAfter =
                transactionApiClient.getTransactionIdsByAccountId(
                        destinationAccountId
                );

        test.info("STEP 13 - Find new source and destination transaction IDs");

        String sourceTransactionId =
                transactionApiClient.findNewTransactionId(
                        sourceTransactionIdsBefore,
                        sourceTransactionIdsAfter
                );

        String destinationTransactionId =
                transactionApiClient.findNewTransactionId(
                        destinationTransactionIdsBefore,
                        destinationTransactionIdsAfter
                );

        test.info("STEP 14 - Verify source transaction details");

        io.restassured.response.Response sourceTransactionResponse =
                transactionApiClient.getTransactionById(
                        sourceTransactionId
                );

        Assert.assertEquals(
                sourceTransactionResponse.statusCode(),
                200,
                "Get Transactions API status code should be 200 after transfer"
        );

        Assert.assertNotNull(
                sourceTransactionResponse,
                "New transaction should be created after transfer"
        );

        String sourceTransactionType = sourceTransactionResponse.xmlPath().getString("transaction.type");
        String sourceTransactionAmount = sourceTransactionResponse.xmlPath().getString("transaction.amount");
        String sourceTransactionDescription = sourceTransactionResponse.xmlPath().getString("transaction.description");

        Assert.assertEquals(
                sourceTransactionType,
                ApiTestData.DEBIT_TRANSACTION_TYPE,
                "Source transaction type should be Debit"
        );

        BigDecimal actualAmount =
                new BigDecimal(sourceTransactionAmount);

        Assert.assertEquals(
                actualAmount.compareTo(transferAmount),
                0,
                "New transaction amount should match the transfer amount"
        );

        Assert.assertEquals(
                sourceTransactionDescription,
                ApiTestData.SENT_TRANSACTION,
                "Source transaction description should indicate funds sent"
        );

        test.info("STEP 15 - Verify destination transaction details");

        io.restassured.response.Response destinationTransactionResponse =
                transactionApiClient.getTransactionById(
                        destinationTransactionId
                );

        Assert.assertEquals(
                destinationTransactionResponse.statusCode(),
                200,
                "Get Transactions API status code should be 200 after transfer"
        );

        Assert.assertNotNull(
                destinationTransactionResponse,
                "New transaction should be created after transfer"
        );

        String destinationTransactionType = destinationTransactionResponse.xmlPath().getString("transaction.type");
        String destinationTransactionAmount = destinationTransactionResponse.xmlPath().getString("transaction.amount");
        String destinationTransactionDescription = destinationTransactionResponse.xmlPath().getString("transaction.description");

        Assert.assertEquals(
                destinationTransactionType,
                ApiTestData.CREDIT_TRANSACTION_TYPE,
                "Destination transaction type should be Credit"
        );

        BigDecimal actualDestinationAmount =
                new BigDecimal(destinationTransactionAmount);

        Assert.assertEquals(
                actualDestinationAmount.compareTo(transferAmount),
                0,
                "New transaction amount should match the transfer amount"
        );

        Assert.assertEquals(
                destinationTransactionDescription,
                ApiTestData.RECEIVED_TRANSACTION,
                "Destination transaction description should indicate funds received"
        );

        test.pass("PASSED - HYBRID02 - Verify transfer performed by UI can be validated by API");
    }
}
