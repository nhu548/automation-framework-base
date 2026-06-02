package tests;

import base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;

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
    // TC01 - Verify accounts overview table is displayed
    // =========================================================

    @Test(description = "Verify accounts overview table is displayed")
    public void verifyAccountsOverviewTableDisplayed() {

        test.info("STEP 1 - Verify accounts table is displayed");

        boolean isAccountsTableDisplayed =
                accountsOverviewPage.isAccountsTableDisplayed();

        Assert.assertTrue(
                isAccountsTableDisplayed,
                "Accounts overview table should be displayed"
        );

        test.pass("Accounts overview table displayed successfully");
    }

    // =========================================================
    // TC02 - Verify customer accounts are displayed
    // =========================================================

    @Test(description = "Verify customer accounts are displayed")
    public void verifyCustomerAccountsDisplayed() {

        test.info("STEP 1 - Get total accounts");

        int accountCount =
                accountsOverviewPage.getAccountsCount();

        test.info("STEP 2 - Verify accounts exist");

        Assert.assertTrue(
                accountCount > 0,
                "Customer should have at least one account"
        );

        test.pass("Customer accounts displayed successfully");
    }

    // =========================================================
    // TC03 - Verify user can open account details
    // =========================================================

    @Test(description = "Verify user can open account details")
    public void verifyUserCanOpenAccountDetails() {

        test.info("STEP 1 - Click first account");

        accountsOverviewPage.clickFirstAccount();

        test.info("STEP 2 - Verify account details page opened");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("activity"),
                "Account details page should open"
        );

        test.pass("Account details page opened successfully");
    }

// =========================================================
// TC04 - Verify account balance displayed
// =========================================================

    @Test(description = "Verify account balance displayed")
    public void verifyAccountBalanceDisplayed() {

        test.info("STEP 1 - Get account balance");

        String balance =
                accountsOverviewPage.getFirstAccountBalance();

        test.info("STEP 2 - Verify balance is displayed");

        Assert.assertFalse(
                balance.isEmpty(),
                "Account balance should be displayed"
        );

        test.pass("Account balance displayed successfully");
    }
    
}