package tests;

import base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.TransferPage;

import static testdata.TransferTestData.*;

public class TransferFundsTest extends BaseAuthenticatedTest {

    private TransferPage transferPage;

    // =========================================================
    // TEST DATA
    // =========================================================

    private final String fromAccount = FROM_ACCOUNT;
    private final String toAccount = TO_ACCOUNT;

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupTransferPage() {

        transferPage =
                dashboardPage.navigateToTransferFunds();
    }

    // =========================================================
    // TC01 - Verify successful fund transfer
    // =========================================================

    @Test(description = "Verify user can transfer funds successfully")
    public void verifySuccessfulFundTransfer() {

        test.info("STEP 1 - Submit transfer request");

        transferPage.transferFunds(
                fromAccount,
                toAccount,
                VALID_TRANSFER_AMOUNT);

        test.info("STEP 2 - Verify transfer completed successfully");

        Assert.assertTrue(
                transferPage.isTransferSuccess(),
                "Transfer should complete successfully"
        );

        test.info("STEP 3 - Verify success confirmation message");
        Assert.assertEquals(
                transferPage.getTransferSuccessMessage(),
                SUCCESS_MESSAGE,
                "Incorrect success message displayed"
        );

        test.pass("Fund transfer completed successfully");
    }

    // =========================================================
    // TC02 - Verify transfer rejected when amount is zero
    // =========================================================

    @Test(description = "Verify transfer rejected when amount is zero", enabled = false)
    public void verifyTransferRejectedWhenAmountIsZero() {

        test.info("STEP 1 - Submit transfer request with invalid amount");
        transferPage.transferFunds(
                fromAccount,
                toAccount,ZERO_AMOUNT);

        test.info("STEP 2 - Verify transfer is rejected");
        Assert.assertFalse(
                transferPage.isTransferSuccess(),
                "Transfer should fail with invalid amount"
        );

        test.pass("Transfer validation verified successfully");
    }

    // =========================================================
    // TC03 - Verify transfer rejected due to insufficient balance
    // =========================================================

    @Test(description = "Verify transfer rejected due to insufficient balance", enabled = false)
    public void verifyTransferRejectedDueToInsufficientBalance() {

        test.info("STEP 1 - Submit transfer request with excessive amount");

        transferPage.transferFunds(
                fromAccount,
                toAccount,
                EXCESSIVE_AMOUNT);

        test.info("STEP 2 - Verify transfer is rejected due to insufficient balance");

        Assert.assertFalse(
                transferPage.isTransferSuccess(),
                "Transfer should fail due to insufficient balance"
        );

        test.pass("Insufficient balance validation verified successfully");
    }
}