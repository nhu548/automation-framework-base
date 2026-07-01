package ui.tests;

import ui.base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.AccountsOverviewPage;

public class AccountsOverviewTest extends BaseAuthenticatedTest {

    private AccountsOverviewPage accountsOverviewPage;

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupAccountsOverviewPage() {

        accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();
    }

    // =========================================================
    // UI05 - Verify accounts overview table is displayed
    // =========================================================

    @Test(description = "UI05 - Verify accounts overview table is displayed")
    public void UI05_verifyAccountsOverviewTableDisplayed() {

        test.info("STEP 1 - Verify accounts table is displayed");

        boolean isAccountsTableDisplayed =
                accountsOverviewPage.isAccountsTableDisplayed();

        Assert.assertTrue(
                isAccountsTableDisplayed,
                "Accounts overview table should be displayed"
        );

        test.pass("PASSED - UI05 - Verify accounts overview table is displayed");
    }

    // =========================================================
    // UI06 - Verify customer accounts are displayed
    // =========================================================

    @Test(description = "UI06 - Verify customer accounts are displayed")
    public void UI06_verifyCustomerAccountsDisplayed() {

        test.info("STEP 1 - Get total accounts");

        int accountCount =
                accountsOverviewPage.getAccountsCount();

        test.info("STEP 2 - Verify accounts exist");

        Assert.assertTrue(
                accountCount > 0,
                "Customer should have at least one account"
        );

        test.pass("PASSED - UI06 - Verify customer accounts are displayed");
    }

    // =========================================================
    // UI07 - Verify user can open account details
    // =========================================================

    @Test(description = "UI07 - Verify user can open account details")
    public void UI07_verifyUserCanOpenAccountDetails() {

        test.info("STEP 1 - Click first account");

        accountsOverviewPage.clickFirstAccount();

        test.info("STEP 2 - Verify account details page opened");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("activity"),
                "Account details page should open"
        );

        test.pass("PASSED - UI07 - Verify user can open account details");
    }

    // =========================================================
    // UI08 - Verify customer account information displayed
    // =========================================================

    @Test(description = "UI08 - Verify customer account information displayed")
    public void UI08_verifyCustomerAccountInformationDisplayed() {

        test.info("STEP 1 - Get first account balance");

        String balance =
                accountsOverviewPage.getFirstAccountBalance();

        test.info("STEP 2 - Verify balance format");

        Assert.assertTrue(
                balance.matches("\\$\\d+\\.\\d{2}"),
                "Balance format is invalid"
        );

        test.pass("PASSED - UI08 - Verify customer account information displayed");
    }
}