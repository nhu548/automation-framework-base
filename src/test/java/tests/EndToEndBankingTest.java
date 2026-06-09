package tests;

import base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountDetailsPage;
import pages.AccountsOverviewPage;
import pages.OpenNewAccountPage;
import pages.TransferPage;
import testdata.OpenAccountTestData;
import testdata.TransferTestData;

public class EndToEndBankingTest extends BaseAuthenticatedTest {

    private static final String TRANSFER_AMOUNT =
            "50";

    private static final double TRANSFER_AMOUNT_VALUE =
            50.00;

    private static final String RECEIVED_TRANSACTION =
            "Funds Transfer Received";

    // =========================================================
    // E2E - Create account, transfer funds, verify transaction
    // =========================================================
    @Test(description = "Verify user can create account, transfer funds, and validate balances and transaction history")
    public void verifyCreateAccountTransferFundsAndTransactionHistory() {

        test.info("STEP 4 - Navigate to Accounts Overview");

        AccountsOverviewPage accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        String sourceAccount =
                accountsOverviewPage.getFirstAccountNumber();

        test.info("STEP 1 - Navigate to Open New Account page");
        OpenNewAccountPage openNewAccountPage =
                dashboardPage.navigateToOpenNewAccount();

        test.info("STEP 2 - Create new checking account");
        openNewAccountPage.openNewAccount(
                OpenAccountTestData.ACCOUNT_CHECKING_TYPE,sourceAccount
        );

        test.info("STEP 3 - Get newly created account number");
        String newAccountNumber =
                openNewAccountPage.getNewAccountNumber();

        Assert.assertFalse(
                newAccountNumber.isEmpty(),
                "New account number should not be empty"
        );

        test.info("New Account Number = " + newAccountNumber);

        test.info("STEP 4 - Navigate to Accounts Overview");

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info("STEP 5 - Get source account balance before transfer");

        double sourceBalanceBefore =
                accountsOverviewPage.getAccountBalance(sourceAccount);

        test.info("STEP 6 - Get new account balance before transfer");

        double newAccountBalanceBefore =
                accountsOverviewPage.getAccountBalance(newAccountNumber);

        test.info("STEP 7 - Navigate to Transfer Funds page");

        TransferPage transferPage =
                dashboardPage.navigateToTransferFunds();

        test.info("STEP 8 - Transfer funds from source account to new account");

        transferPage.transferFunds(
                TransferTestData.FROM_ACCOUNT,
                        newAccountNumber,
                TRANSFER_AMOUNT
                );

        test.info("STEP 9 - Verify transfer completed successfully");
        Assert.assertTrue(
                transferPage.isTransferSuccess(),
                "Transfer should complete successfully"
        );

        Assert.assertEquals(
                transferPage.getTransferSuccessMessage(),
                TransferTestData.SUCCESS_MESSAGE,
                "Incorrect transfer success message displayed"
        );

        test.info("STEP 10 - Navigate back to Accounts Overview");

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info("STEP 11 - Get source account balance after transfer");
        double sourceBalanceAfter =
                accountsOverviewPage.getAccountBalance(sourceAccount);

        test.info("STEP 12 - Get new account balance after transfer");

        double newAccountBalanceAfter =
                accountsOverviewPage.getAccountBalance(newAccountNumber);

        test.info("STEP 13 - Verify source account balance decreased by transfer amount");
        Assert.assertEquals(
                sourceBalanceAfter,
                sourceBalanceBefore - TRANSFER_AMOUNT_VALUE,
                0.01,
                "Source account balance should decrease by transfer amount"
        );

        test.info("STEP 14 - Verify new account balance increased by transfer amount");
        Assert.assertEquals(
                newAccountBalanceAfter,
                newAccountBalanceBefore + TRANSFER_AMOUNT_VALUE,
                0.01,
                "New account balance should increase by transfer amount"
        );

        test.info("STEP 15 - Open new account details");
        AccountDetailsPage accountDetailsPage =
                accountsOverviewPage.clickAccountByNumber(newAccountNumber);

        test.info("STEP 16 - Verify received transaction is displayed in transaction history");
        Assert.assertTrue(
                accountDetailsPage.isTransactionDisplayed(
                        RECEIVED_TRANSACTION,
                        "$" + TRANSFER_AMOUNT + ".00"
                ),
                "Received transaction should be displayed with correct amount"
        );

        test.pass("End-to-end banking flow verified successfully");
    }
}
