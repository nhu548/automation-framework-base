package ui.tests;

import ui.base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.AccountDetailsPage;
import ui.pages.AccountsOverviewPage;

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
    // UI09 - Verify transaction table is displayed
    // =========================================================

    @Test(description = "UI09 - Verify transaction table is displayed")
    public void UI09_verifyTransactionTableDisplayed() {

        test.info("STEP 1 - Verify transaction table is displayed");

        boolean isTransactionTableDisplayed =
                accountDetailsPage.isTransactionTableDisplayed();

        Assert.assertTrue(
                isTransactionTableDisplayed,
                "Transaction table should be displayed"
        );

        test.pass("PASSED - UI09 - Verify transaction table is displayed");
    }

    // =========================================================
    // UI10 - Verify transaction history exists
    // =========================================================
    @Test(description = "UI10 - Verify transaction history exists")
    public void UI10_verifyTransactionHistoryExists() {

        test.info("STEP 1 - Get transaction count");

        int transactionCount =
                accountDetailsPage.getTransactionCount();

        test.info("STEP 2 - Verify transactions exist");

        Assert.assertTrue(
                transactionCount > 0,
                "Account should have at least one transaction"
        );

        test.pass("PASSED - UI10 - Verify transaction history exists");
    }

    // =========================================================
    // UI11 - Verify transaction date format
    // =========================================================

    @Test(description = "UI11 - Verify transaction date format")
    public void UI11_verifyTransactionDateFormat(){

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

        test.pass("PASSED - UI11 - Verify transaction date format");
    }

    // =========================================================
    // UI12 - Verify transaction amounts are displayed in valid currency format
    // =========================================================

    @Test(description = "UI12 - Verify transaction amounts are displayed in valid currency format")
    public void UI12_verifyTransactionAmountFormat() {

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

        test.pass("PASSED - UI12 - Verify transaction amounts are displayed in valid currency format");
    }

    // =========================================================
    // UI13 - Verify transaction description displayed
    // =========================================================

    @Test(description = "UI13 - Verify transaction description displayed")
    public void UI13_verifyTransactionDescriptionDisplayed() {

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

        test.pass("PASSED - UI13 - Verify transaction description displayed");
    }
}
