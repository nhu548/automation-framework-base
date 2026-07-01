package ui.tests;

import ui.base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.TransferPage;
import utils.ConfigReader;

import static ui.testdata.UITestData.*;

public class TransferFundsTest extends BaseAuthenticatedTest {

    private TransferPage transferPage;

    // =========================================================
    // TEST DATA
    // =========================================================

    private final String primarySourceAccountId =
            ConfigReader.getProperty("primarySourceAccountId");

    private final String primaryDestinationAccountId =
            ConfigReader.getProperty("primaryDestinationAccountId");

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupTransferPage() {

        transferPage =
                dashboardPage.navigateToTransferFunds();
    }

    // =========================================================
    // UI17 - Verify user can transfer funds successfully
    // =========================================================

    @Test(description = "UI17 - Verify user can transfer funds successfully")
    public void UI17_verifySuccessfulFundTransfer() {

        test.info("STEP 1 - Submit transfer request");

        transferPage.transferFunds(
                primarySourceAccountId,
                primaryDestinationAccountId,
                VALID_TRANSFER_AMOUNT);

        test.info("STEP 2 - Verify transfer completed successfully");

        Assert.assertTrue(
                transferPage.isTransferSuccessful(),
                "Transfer should complete successfully"
        );

        test.info("STEP 3 - Verify success confirmation message");
        Assert.assertEquals(
                transferPage.getTransferSuccessMessage(),
                TRANSFER_SUCCESS_MESSAGE,
                "Incorrect success message displayed"
        );

        test.pass("PASSED - UI17 - Verify user can transfer funds successfully");
    }

    // =========================================================
    // UI18 - Verify transfer rejected when amount is zero
    // =========================================================
    /**
     * Verify that the transfer is rejected when the transfer amount is zero.
     *
     * Business Rule:
     * A banking system should not allow transferring zero amount.
     *
     * Note:
     * Disabled because ParaBank demo does not enforce this validation consistently.
     */
    @Test(description = "UI18 - Verify transfer rejected when amount is zero", enabled = false)
    public void UI18_verifyTransferRejectedWhenAmountIsZero() {

        test.info("STEP 1 - Submit transfer request with invalid amount");
        transferPage.transferFunds(
                primarySourceAccountId,
                primaryDestinationAccountId,
                ZERO_AMOUNT);

        test.info("STEP 2 - Verify transfer is rejected");
        Assert.assertFalse(
                transferPage.isTransferSuccessful(),
                "Transfer should fail with invalid amount"
        );

        test.pass("PASSED - UI18 - Verify transfer rejected when amount is zero");
    }

    // =========================================================
    // UI19 - Verify transfer rejected due to insufficient balance
    // =========================================================
    /**
     * Verify that the transfer is rejected when the source account
     * has insufficient balance.
     *
     * Business Rule:
     * A banking system should reject transfers that exceed the available balance
     * unless overdraft is explicitly supported.
     *
     * Note:
     * Disabled because ParaBank demo allows negative balances and does not
     * validate insufficient funds.
     */
    @Test(description = "UI19 - Verify transfer rejected due to insufficient balance", enabled = false)
    public void UI19_verifyTransferRejectedDueToInsufficientBalance() {

        test.info("STEP 1 - Submit transfer request with excessive amount");

        transferPage.transferFunds(
                primarySourceAccountId,
                primaryDestinationAccountId,
                EXCESSIVE_AMOUNT);

        test.info("STEP 2 - Verify transfer is rejected due to insufficient balance");

        Assert.assertFalse(
                transferPage.isTransferSuccessful(),
                "Transfer should fail due to insufficient balance"
        );

        test.pass("PASSED - UI19 - Verify transfer rejected due to insufficient balance");
    }

}