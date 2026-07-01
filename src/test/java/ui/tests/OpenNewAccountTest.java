package ui.tests;

import ui.base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.AccountsOverviewPage;
import ui.pages.OpenNewAccountPage;
import ui.testdata.UITestData;
import utils.ConfigReader;

import java.util.List;

public class OpenNewAccountTest extends BaseAuthenticatedTest {

    private OpenNewAccountPage openNewAccountPage;

    private final String primarySourceAccountId =
            ConfigReader.getProperty("primarySourceAccountId");

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupOpenNewAccountPage() {

        openNewAccountPage =
                dashboardPage.navigateToOpenNewAccount();
    }

    // =========================================================
    // UI14 - Verify Open New Account page is displayed
    // =========================================================

    @Test(description = "UI14 - Verify Open New Account page is displayed")
    public void UI14_verifyOpenNewAccountPageDisplayed() {

        test.info("STEP 1 - Verify Open New Account page is displayed");

        Assert.assertTrue(
                openNewAccountPage.isOpenNewAccountPageDisplayed(),
                "Open New Account page should be displayed"
        );

        test.pass("PASSED - UI14 - Verify Open New Account page is displayed");
    }

    // =========================================================
    // UI15 - Verify user can create a new checking account
    // =========================================================

    @Test(description = "UI15 - Verify user can create a new checking account")
    public void UI15_verifyCreateNewCheckingAccount() {

        test.info("STEP 1 - Create new checking account");

        String newAccountNumber =
                openNewAccountPage.openNewAccountAndGetAccountId(
                        UITestData.CHECKING_ACCOUNT_TYPE,
                        primarySourceAccountId
                        );

        Assert.assertNotNull(
                newAccountNumber,
                "New account number should not be null"
        );

        Assert.assertFalse(
                newAccountNumber.trim().isEmpty(),
                "New account number should not be empty"
        );

        test.pass("PASSED - UI15 - Verify user can create a new checking account");
    }
    // =========================================================
    // UI16 - Verify newly created account appears in Accounts Overview
    // =========================================================

    @Test(description = "UI16 - Verify newly created account appears in Accounts Overview")
    public void UI16_verifyNewAccountAppearsInAccountsOverview() {

        test.info("STEP 1 - Create new checking account");

        String newAccountNumber =
                openNewAccountPage.openNewAccountAndGetAccountId(
                        UITestData.CHECKING_ACCOUNT_TYPE,
                        primarySourceAccountId
                );

        Assert.assertNotNull(
                newAccountNumber,
                "New account number should not be null"
        );

        Assert.assertFalse(
                newAccountNumber.trim().isEmpty(),
                "New account number should not be empty"
        );

        test.info("STEP 2 - Navigate to Accounts Overview");

        AccountsOverviewPage accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info("STEP 3 - Get account numbers from Accounts Overview");

        List<String> accountNumbers =
                accountsOverviewPage.getAccountNumbers();

        test.info("STEP 4 - Verify new account exists in Accounts Overview");

        Assert.assertTrue(
                accountNumbers.contains(newAccountNumber),
                "New account should appear in Accounts Overview"
        );

        test.pass("PASSED - UI16 - Verify newly created account appears in Accounts Overview");
    }
}
