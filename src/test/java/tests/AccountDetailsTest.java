package tests;

import base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountDetailsPage;
import pages.AccountsOverviewPage;

import java.util.List;

public class AccountDetailsTest extends BaseAuthenticatedTest {

    private AccountsOverviewPage accountsOverviewPage;
    private AccountDetailsPage accountDetailsPage;

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupAccountDetailsPage() {

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        accountsOverviewPage.clickFirstAccount();

        accountDetailsPage =
                new AccountDetailsPage(driver);
    }

    // =========================================================
    // TC01 - Verify transaction table is displayed
    // =========================================================

    @Test(description = "Verify transaction table is displayed")
    public void verifyTransactionTableDisplayed() {

        test.info("STEP 1 - Verify transaction table is displayed");

        boolean isTransactionTableDisplayed =
                accountDetailsPage.isTransactionTableDisplayed();

        Assert.assertTrue(
                isTransactionTableDisplayed,
                "Transaction table should be displayed"
        );

        test.pass("Transaction table displayed successfully");
    }

    // =========================================================
    // TC02 - Verify transaction history exists
    // =========================================================
    @Test(description = "Verify transaction history exists")
    public void verifyTransactionHistoryExists() {

        test.info("STEP 1 - Get transaction count");

        int transactionCount =
                accountDetailsPage.getTransactionCount();

        test.info("STEP 2 - Verify transactions exist");

        Assert.assertTrue(
                transactionCount > 0,
                "Account should have at least one transaction"
        );

        test.pass("Transaction history exists successfully");
    }

    // =========================================================
    // TC03 - Verify transaction date format
    // =========================================================

    @Test(description = "Verify transaction date format")
    public void verifyTransactionDateFormat(){

        test.info("STEP 1 - Get transaction dates");

        List<String> transactionDates =
                accountDetailsPage.getTransactionDates();

        test.info("STEP 2 - Verify date format is correct");

        for (String date : transactionDates) {
            Assert.assertTrue(
                    accountDetailsPage.isValidDateFormat(date),
                    "Invalid transaction date: " + date
            );
        }

        test.pass("Transaction date format verified successfully");
    }

    // =========================================================
    // TC04 - Verify transaction amount format
    // =========================================================

    @Test(description = "Verify transaction amounts are displayed in valid currency format")
    public void verifyTransactionAmountFormat() {

        test.info("STEP 1 - Get transaction amounts");

        List<String> transactionAmounts =
                accountDetailsPage.getTransactionAmounts();

        test.info("STEP 2 - Verify transaction amounts exist");

        Assert.assertFalse(
                transactionAmounts.isEmpty(),
                "Transaction amounts should not be empty"
        );

        test.info("STEP 3 - Verify amount format is correct");

        for (String amount : transactionAmounts) {
            Assert.assertTrue(
                    accountDetailsPage.isValidAmountFormat(amount),
                    "Invalid transaction amount: " + amount
            );
        }

        test.pass("Transaction amount format verified successfully");
    }

    // =========================================================
    // TC05 - Verify transaction description displayed
    // =========================================================

    @Test(description = "Verify transaction description displayed")
    public void verifyTransactionDescriptionDisplayed() {

        test.info("STEP 1 - Get transaction descriptions");

        List<String> transactionDescriptions =
                accountDetailsPage.getTransactionDescriptions();

        test.info("STEP 2 - Verify transaction descriptions exist");
        for (String description : transactionDescriptions) {

            Assert.assertTrue(
                    description.contains("Transfer"),
                    "Invalid transaction description: " + description
            );
        }

        Assert.assertFalse(
                transactionDescriptions.isEmpty(),
                "Transaction descriptions should not be empty"
        );

        test.info("STEP 3 - Verify each description is displayed");

        for (String description : transactionDescriptions) {

            Assert.assertFalse(
                    description.trim().isEmpty(),
                    "Transaction description should not be empty"
            );
        }

        for (String description : transactionDescriptions) {

            Assert.assertTrue(
                    description.contains("Transfer"),
                    "Invalid transaction description: " + description
            );
        }

        test.pass("Transaction descriptions verified successfully");
    }
}
