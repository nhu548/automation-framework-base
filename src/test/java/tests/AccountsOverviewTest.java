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
    // TC04 -  Verify Customer Account Information Displayed
    // =========================================================

    @Test(description = "Verify customer account information displayed")
    public void verifyCustomerAccountInformationDisplayed() {

        test.info("STEP 1 - Get first account balance");

        String balance =
                accountsOverviewPage.getFirstAccountBalance();

        test.info("STEP 2 - Verify balance format");

        Assert.assertTrue(
                balance.matches("\\$\\d+\\.\\d{2}"),
                "Balance format is invalid"
        );

        test.pass("Account information displayed successfully");
    }
}