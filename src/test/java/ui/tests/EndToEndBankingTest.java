package ui.tests;

import ui.base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.pages.AccountDetailsPage;
import ui.pages.AccountsOverviewPage;
import ui.pages.OpenNewAccountPage;
import ui.pages.TransferPage;
import ui.testdata.UITestData;

import java.math.BigDecimal;

public class EndToEndBankingTest extends BaseAuthenticatedTest {

    @Test(description = "E2E01 - Verify user can create account, transfer funds, and validate balances and transaction history")
    public void E2E01_verifyCreateAccountTransferFundsAndTransactionHistory() {

        test.info("STEP 1 - Navigate to Accounts Overview page");

        AccountsOverviewPage accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        String sourceAccountId =
                accountsOverviewPage.getFirstAccountNumber();

        test.info("Source Account ID: " + sourceAccountId);

        test.info("STEP 2 - Navigate to Open New Account page");

        OpenNewAccountPage openNewAccountPage =
                dashboardPage.navigateToOpenNewAccount();

        test.info("STEP 3 - Create new checking account");

        String destinationAccountId =
                openNewAccountPage.openNewAccountAndGetAccountId(
                        UITestData.CHECKING_ACCOUNT_TYPE,
                        sourceAccountId
        );

        Assert.assertFalse(
                destinationAccountId.isBlank(),
                "Destination account ID should not be empty"
        );

        test.info("Destination Account ID: " + destinationAccountId);

        test.info("STEP 4 - Navigate back to Accounts Overview page");

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info("STEP 5 - Get account balances before transfer");

        BigDecimal sourceBalanceBefore =
                accountsOverviewPage.getAccountBalance(sourceAccountId);

        BigDecimal destinationBalanceBefore =
                accountsOverviewPage.getAccountBalance(destinationAccountId);

        test.info("Source Balance Before: " + sourceBalanceBefore);
        test.info("Destination Balance Before: " + destinationBalanceBefore);

        test.info("STEP 6 - Navigate to Transfer Funds page");

        TransferPage transferPage =
                dashboardPage.navigateToTransferFunds();

        test.info("STEP 7 - Transfer funds from source account to destination account");

        transferPage.transferFunds(
                sourceAccountId,
                destinationAccountId,
                UITestData.VALID_TRANSFER_AMOUNT
                );

        test.info("STEP 8 - Verify transfer completed successfully");

        Assert.assertTrue(
                transferPage.isTransferSuccessful(),
                "Transfer should complete successfully"
        );

        Assert.assertEquals(
                transferPage.getTransferSuccessMessage(),
                UITestData.TRANSFER_SUCCESS_MESSAGE,
                "Incorrect transfer success message displayed"
        );

        test.info("STEP 9 - Navigate back to Accounts Overview");

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info("STEP 10 - Get source account balance after transfer");

        BigDecimal sourceBalanceAfter =
                accountsOverviewPage.getAccountBalance(sourceAccountId);

        BigDecimal destinationBalanceAfter  =
                accountsOverviewPage.getAccountBalance(destinationAccountId);

        test.info("Source Balance After: " + sourceBalanceAfter);
        test.info("Destination Balance After: " + destinationBalanceAfter);

        test.info("STEP 11 - Verify source account balance decreased by transfer amount");
        BigDecimal transferAmount =
                new BigDecimal(
                        UITestData.VALID_TRANSFER_AMOUNT
                );

        Assert.assertEquals(
                sourceBalanceAfter,
                sourceBalanceBefore.subtract(transferAmount),
                "Source account balance should decrease by transfer amount"
        );

        test.info("STEP 12 - Verify destination account balance increased by transfer amount");

        Assert.assertEquals(
                destinationBalanceAfter,
                destinationBalanceBefore.add(transferAmount),
                "New account balance should increase by transfer amount"
        );

        test.info("STEP 13 - Open source account details page");

        AccountDetailsPage sourceAccountDetailsPage =
                accountsOverviewPage.clickAccountByNumber(sourceAccountId);

        test.info("STEP 14 - Verify sent transaction is displayed in source account transaction history");

        Assert.assertTrue(
                sourceAccountDetailsPage.isTransactionDisplayed(
                        UITestData.SENT_TRANSACTION,
                        "$" + transferAmount
                ),
                "Sent transaction should be displayed in source account with correct amount"
        );

        test.info("STEP 15 - Open destination account details page");

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        AccountDetailsPage accountDetailsPage =
                accountsOverviewPage.clickAccountByNumber(destinationAccountId);

        test.info("STEP 16 - Verify received transaction is displayed in transaction history");

        Assert.assertTrue(
                accountDetailsPage.isTransactionDisplayed(
                        UITestData.RECEIVED_TRANSACTION,
                        "$" + transferAmount
                ),
                "Received transaction should be displayed with correct amount"
        );

        test.pass("PASSED - E2E01 - Create account, transfer funds, balance update, and transaction history verified successfully");
    }
}
