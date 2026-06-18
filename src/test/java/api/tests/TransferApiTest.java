package api.tests;

import api.base.BaseApiTest;
import api.clients.*;
import api.testdata.ApiTestData;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.util.List;

public class TransferApiTest extends BaseApiTest {

    private final AccountApiClient accountApiClient =
            new AccountApiClient();

    private final TransferApiClient transferApiClient =
            new TransferApiClient();

    private final TransactionApiClient transactionApiClient =
            new TransactionApiClient();

    private final CustomerApiClient customerApiClient =
            new CustomerApiClient();

    private final LoginApiClient loginApiClient =
            new LoginApiClient();

    private final String username2 =
            ConfigReader.getProperty("username2");

    private final String password2 =
            ConfigReader.getProperty("password2");


    @Test(description = "API09 - Verify funds can be transferred successfully and balances are updated correctly")
    public void API09_verifyTransferFundsSuccessfullyAndBalancesUpdatedCorrectly() {

        Reporter.log("STEP 1 - Create destination checking account", true);

        String destinationAccountId =
                accountApiClient.createNewCheckingAccount(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("destinationAccountId: " + destinationAccountId, true);

        Reporter.log("STEP 2 - Get balances before transfer", true);

        BigDecimal sourceBalanceBefore =
                accountApiClient.getAccountBalance(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        BigDecimal destinationBalanceBefore =
                accountApiClient.getAccountBalance(
                        destinationAccountId
                );

        Reporter.log("STEP 4 - Transfer funds", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        ApiTestData.TRANSFER_AMOUNT
                );

        Reporter.log("STEP 5 - Verify transfer response status code", true);

        Assert.assertEquals(
                transferResponse.statusCode(),
                200,
                "Transfer API status code should be 200"
        );

        Reporter.log("STEP 6 - Get balances after transfer", true);

        BigDecimal sourceBalanceAfter =
                accountApiClient.getAccountBalance(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        BigDecimal destinationBalanceAfter =
                accountApiClient.getAccountBalance(
                        destinationAccountId
                );

        BigDecimal transferAmount =
                new BigDecimal(ApiTestData.TRANSFER_AMOUNT);

        Reporter.log("STEP 7 - Verify source balance decreased", true);

        BigDecimal expectedSourceBalance =
                sourceBalanceBefore.subtract(
                        transferAmount
                );

        Assert.assertEquals(
                sourceBalanceAfter.compareTo(
                        expectedSourceBalance
                ),
                0,
                "Source balance should decrease by transfer amount"
        );

        Reporter.log("STEP 8 - Verify destination balance increased", true);

        BigDecimal expectedDestinationBalance =
                destinationBalanceBefore.add(
                        transferAmount
                );

        Assert.assertEquals(
                destinationBalanceAfter.compareTo(
                        expectedDestinationBalance
                ),
                0,
                "Destination balance should increase by transfer amount"
        );

        Reporter.log("PASSED - API09 - Verify funds can be transferred successfully and balances are updated correctly", true);
    }

    /**
     * Disabled because ParaBank demo API does not reject same source and destination account as expected.
     */
    @Test(description = "API12 - Verify transfer request is rejected when source and destination accounts are the same", enabled = false)
    public void API12_verifyTransferRequestIsRejectedWhenSourceAndDestinationAccountsAreTheSame() {

        Reporter.log("STEP 1 - Attempt to transfer funds from an account to itself", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        ApiTestData.VALID_ACCOUNT_ID,
                        ApiTestData.TRANSFER_AMOUNT
                );

        Reporter.log("STEP 2 - Verify transfer response status code is 400", true);

        Assert.assertEquals(
                transferResponse.statusCode(),
                400,
                "Transfer API status code should be 400 when source and destination accounts are the same"
        );

        Reporter.log("STEP 3 - Verify error message in response body", true);

        String errorMessage =
                transferResponse.jsonPath().getString("error");

        Assert.assertEquals(
                errorMessage,
                "Source and destination accounts cannot be the same",
                "Error message should indicate that source and destination accounts cannot be the same"
        );

        Reporter.log("PASSED - API12 - Verify transfer request is rejected when source and destination accounts are the same", true);
    }

    /**
     * Disabled because ParaBank demo API accepts transfer amount equals zero
     * instead of rejecting it.
     */
    @Test(description = "API15 - Verify transfer request is rejected when transfer amount equals zero", enabled = false)
    public void API15_verifyTransferRequestIsRejectedWhenAmountEqualsZero() {

        Reporter.log("STEP 1 - Create destination checking account", true);
        String destinationAccountId =
                accountApiClient.createNewCheckingAccount(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Attempt to transfer zero amount from source account to destination account", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        "0"
                );

        Reporter.log("STEP 3 - Verify transfer response status code is 400", true);

        Assert.assertEquals(
                transferResponse.statusCode(),
                400,
                "Transfer API status code should be 400 when transfer amount equals zero"
        );

        Reporter.log("STEP 4 - Verify error message in response body", true);

        String errorMessage =
                transferResponse.jsonPath().getString("error");

        Assert.assertEquals(
                errorMessage,
                "Transfer amount must be greater than zero",
                "Error message should indicate that transfer amount must be greater than zero"
        );

        Reporter.log("PASSED - API15 - Verify transfer request is rejected when transfer amount equals zero", true);
    }

    /**
     * Disabled because ParaBank demo API accepts transfer amount is negative
     * instead of rejecting it.
     */
    @Test(description = "API14 - Verify transfer request is rejected when transfer amount is negative", enabled = false)
    public void API14_verifyTransferRequestIsRejectedWhenAmountIsNegative() {

        Reporter.log("STEP 1 - Create destination checking account", true);
        String destinationAccountId =
                accountApiClient.createNewCheckingAccount(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Attempt to transfer negative amount from source account to destination account", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        "-100"
                );

        Reporter.log("STEP 3 - Verify transfer response status code is 400", true);

        Assert.assertEquals(
                transferResponse.statusCode(),
                400,
                "Transfer API status code should be 400 when transfer amount is negative"
        );

        Reporter.log("STEP 4 - Verify error message in response body", true);

        String errorMessage =
                transferResponse.jsonPath().getString("error");

        Assert.assertEquals(
                errorMessage,
                "Transfer amount must be greater than zero",
                "Error message should indicate that transfer amount must be greater than zero"
        );

        Reporter.log("PASSED - API14 - Verify transfer request is rejected when transfer amount is negative", true);
    }

    /**
     * Disabled because ParaBank demo API accepts transfer amount exceeds available balance
     * instead of rejecting it.
     */
    @Test(description = "API13 - Verify transfer request is rejected when transfer amount exceeds available balance", enabled = false)
    public void API13_verifyTransferRequestIsRejectedWhenAmountExceedsAvailableBalance() {

        Reporter.log("STEP 1 - Get source account balance", true);
        BigDecimal sourceBalanceBefore =
                accountApiClient.getAccountBalance(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Create destination checking account", true);
        String destinationAccountId =
                accountApiClient.createNewCheckingAccount(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 3 - Attempt to transfer amount greater than available balance from source account to destination account", true);

        BigDecimal transferAmount =
                sourceBalanceBefore.add(
                        new BigDecimal("1000")
                ); // Attempt to transfer more than available balance

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        transferAmount.toPlainString()
                );

        Reporter.log("STEP 4 - Verify transfer response status code is 400", true);

        Assert.assertEquals(
                transferResponse.statusCode(),
                400,
                "Transfer API status code should be 400 when transfer amount exceeds available balance"
        );

        Reporter.log("STEP 5 - Verify error message in response body", true);

        String errorMessage =
                transferResponse.jsonPath().getString("error");

        Assert.assertEquals(
                errorMessage,
                "Insufficient funds in source account",
                "Error message should indicate that there are insufficient funds in the source account"
        );

        Reporter.log("PASSED - API13 - Verify transfer request is rejected when transfer amount exceeds available balance", true);
    }

    @Test(description = "API10 - Verify transfer transaction is recorded in transaction history with correct details")
    public void API10_verifyTransferTransactionIsRecordedInTransactionHistoryWithCorrectDetails() {

        Reporter.log("STEP 1 - Create destination checking account", true);
        String destinationAccountId =
                accountApiClient.createNewCheckingAccount(
                        ApiTestData.VALID_ACCOUNT_ID
                );

        Reporter.log("STEP 2 - Get destination transaction IDs before transfer", true);

        Response transactionsResponseBefore =
                transactionApiClient.getTransactionsByAccountId(
                        destinationAccountId
                );

        Assert.assertEquals(
                transactionsResponseBefore.statusCode(),
                200,
                "Get Transactions API status code should be 200 before transfer"
        );

        XmlPath xmlPathBefore = new XmlPath(transactionsResponseBefore.asString());

        List<String> transactionIdsBefore =
                xmlPathBefore.getList(
                        "transactions.transaction.id"
                );

        int transactionCountBefore =
                transactionIdsBefore.size();

        Reporter.log("STEP 3 - Transfer funds from source account to destination account", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        ApiTestData.TRANSFER_AMOUNT
                );

        Assert.assertEquals(
                transferResponse.statusCode(),
                200,
                "Transfer API status code should be 200 for successful transfer"
        );

        String bodyMessage =
                transferResponse.asString();

        Assert.assertTrue(
                bodyMessage.contains(
                        "Successfully transferred $" +  ApiTestData.TRANSFER_AMOUNT + " from account #"
                                + ApiTestData.VALID_ACCOUNT_ID + " to account #" + destinationAccountId
                ),
                "Response body should show successful transfer message with correct details"
        );

        Reporter.log("STEP 4 - Get destination transaction IDs after transfer", true);

        Response transactionsResponseAfter =
                transactionApiClient.getTransactionsByAccountId(
                        destinationAccountId
                );

        Assert.assertEquals(
                transactionsResponseAfter.statusCode(),
                200,
                "Get Transactions API status code should be 200"
        );

        XmlPath xmlPathAfter =
                new XmlPath(
                        transactionsResponseAfter.asString()
                );

        List<String> transactionIdsAfter =
                xmlPathAfter.getList(
                        "transactions.transaction.id"
                );

        int transactionCountAfter =
                transactionIdsAfter.size();

        Reporter.log(
                "Transaction IDs after transfer: " + transactionIdsAfter,
                true
        );

        Reporter.log("STEP 5 - Verify transaction count increased by 1", true);

        Assert.assertEquals(
                transactionCountAfter,
                transactionCountBefore + 1,
                "Transaction count should increase by 1 after transfer"
        );

        Reporter.log("STEP 6 - Find newly created transaction ID", true);

        String newTransactionId = null;

        for (String transactionId : transactionIdsAfter) {

            if (!transactionIdsBefore.contains(transactionId)) {

                newTransactionId = transactionId;
                break;
            }
        }

        Assert.assertNotNull(
                newTransactionId,
                "New transaction should be created after transfer"
        );

        Reporter.log(
                "New Transaction ID: " + newTransactionId,
                true
        );

        Reporter.log("STEP 7 - Verify newly created transaction details", true);

        String newTransactionType =
                xmlPathAfter.getString(
                        "transactions.transaction.find { it.id == '" + newTransactionId + "' }.type"
                );

        String newTransactionAmount =
                xmlPathAfter.getString(
                        "transactions.transaction.find { it.id == '" + newTransactionId + "' }.amount"
                );

        String newTransactionDescription =
                xmlPathAfter.getString(
                        "transactions.transaction.find { it.id == '" + newTransactionId + "' }.description"
                );

        Assert.assertEquals(
                newTransactionType,
                "Credit",
                "New transaction type should be Credit for destination account"
        );

        BigDecimal expectedAmount =
                new BigDecimal(
                        ApiTestData.TRANSFER_AMOUNT
                );

        BigDecimal actualAmount =
                new BigDecimal(
                        newTransactionAmount
                );

        Assert.assertEquals(
                actualAmount.compareTo(expectedAmount),
                0,
                "New transaction amount should match the transfer amount"
        );

        Assert.assertEquals(
                newTransactionDescription,
                "Funds Transfer Received",
                "New transaction description should indicate funds received"
        );

        Reporter.log(
                "API10 PASSED - Transfer transaction is recorded in transaction history with correct details",
                true
        );

    }

    @Test(description = "API11 - Verify transfer request is successful between accounts of different customers")
    public void API11_verifyTransferRequestIsSuccessfulBetweenAccountsOfDifferentCustomers(){

        Reporter.log("STEP 1 - Get destination customer id and checking account for destination", true);
        String destinationCustomerId =
                loginApiClient.getCustomerId(
                        username2,
                        password2
                );

        Assert.assertNotNull(
                destinationCustomerId,
                "Destination customer ID should not be null"
        );

        Reporter.log("Destination Customer ID: " + destinationCustomerId, true);

        Reporter.log("STEP 2 - Get destination account ID from customer accounts API", true);

        String destinationAccountId =
                customerApiClient.getLatestAccountIdByCustomerId(
                        destinationCustomerId
                );

        Assert.assertNotNull(
                destinationAccountId,
                "Destination account ID should not be null"
        );

        Reporter.log("Destination Account ID: " + destinationAccountId, true);

        Reporter.log("STEP 3 - Get destination transaction IDs before transfer", true);

        Response destinationTransactionIDsBefore =
                transactionApiClient.getTransactionsByAccountId(
                        destinationAccountId
                );

        Assert.assertEquals(
                destinationTransactionIDsBefore.statusCode(),
                200,
                "Get Transactions API status code should be 200 before transfer"
        );

        XmlPath xmlPathBefore = new XmlPath(destinationTransactionIDsBefore.asString());

        List<String> transactionIdsBefore = xmlPathBefore.getList("transactions.transaction.id");

        int transactionCountBefore = transactionIdsBefore.size();

        Reporter.log("Transaction IDs in destination account before transfer: " + transactionIdsBefore, true);

        Reporter.log("STEP 4 - Transfer funds from source account to destination account of different customer", true);

        Response transferResponse =
                transferApiClient.transferFunds(
                        ApiTestData.VALID_ACCOUNT_ID,
                        destinationAccountId,
                        ApiTestData.TRANSFER_AMOUNT
                );

        Assert.assertEquals(
                transferResponse.statusCode(),
                200,
                "Transfer API status code should be 200 for successful transfer between different customers"
        );
        Reporter.log("STEP 5 - Get destination transaction IDs after transfer", true);

        Response destinationTransactionIDsAfter =
                transactionApiClient.getTransactionsByAccountId(
                        destinationAccountId
                );

        Assert.assertEquals(
                destinationTransactionIDsAfter.statusCode(),
                200,
                "Get Transactions API status code should be 200 after transfer"
        );

        XmlPath xmlPathAfter = new XmlPath(destinationTransactionIDsAfter.asString());

        List<String> transactionIdsAfter = xmlPathAfter.getList("transactions.transaction.id");

        int transactionCountAfter =
                transactionIdsAfter.size();

        Reporter.log("Transaction IDs after transfer" + transactionCountAfter,true);

        Reporter.log("STEP 6 - Verify transaction count increased by 1", true);

        Assert.assertTrue(
                transactionIdsAfter.containsAll(transactionIdsBefore) && transactionIdsAfter.size() == transactionIdsBefore.size() + 1,
                "A new transaction should be added to the destination account after transfer between different customers"
        );

        Reporter.log("STEP 7 - Find newly created transaction ID", true);

        String newTransactionId = null;

        for (String transactionId : transactionIdsAfter) {
            if (!transactionIdsBefore.contains(transactionId)) {
                newTransactionId = transactionId;
                break;
            }
        }

        Assert.assertNotNull(
                newTransactionId,
                "New transaction should be created after transfer"
        );

        Reporter.log("New transaction ID added after transfer: " + newTransactionId, true);

        Reporter.log("STEP 8 - Verify newly created transaction details", true);

        String newTransactionType = xmlPathAfter.getString("transactions.transaction.find { it.id == '" + newTransactionId + "' }.type");
        String newTransactionAmount = xmlPathAfter.getString("transactions.transaction.find { it.id == '" + newTransactionId + "' }.amount");
        String newTransactionDescription = xmlPathAfter.getString("transactions.transaction.find { it.id == '" + newTransactionId + "' }.description");

        Assert.assertEquals(
                newTransactionType,
                "Credit",
                "Most recent transaction type should be 'Transfer'"
        );

        BigDecimal expectedAmount =
                new BigDecimal(ApiTestData.TRANSFER_AMOUNT);

        BigDecimal actualAmount =
                new BigDecimal(newTransactionAmount);

        Assert.assertEquals(
                actualAmount.compareTo(expectedAmount),
                0,
                "New transaction amount should match the transfer amount"
        );

        Assert.assertEquals(
                newTransactionDescription,
                "Funds Transfer Received",
                "New transaction description should indicate funds received"
        );

        Reporter.log("PASSED - API11 - Verify transfer request is successful between accounts of different customers", true);
    }
}
