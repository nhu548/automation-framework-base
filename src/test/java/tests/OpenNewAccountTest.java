package tests;

import base.BaseAuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.OpenNewAccountPage;
import testdata.OpenAccountTestData;

import java.util.List;

public class OpenNewAccountTest extends BaseAuthenticatedTest {

    private OpenNewAccountPage openNewAccountPage;

    private static final String SUCCESS_MESSAGE =
            "Account Opened!";

    // =========================================================
    // SETUP
    // =========================================================

    @BeforeMethod
    public void setupOpenNewAccountPage() {

        openNewAccountPage =
                dashboardPage.navigateToOpenNewAccount();
    }

    // =========================================================
    // SETUP
    // =========================================================

    private String createCheckingAccountAndGetAccountNumber() {

        openNewAccountPage.openNewAccount(
                OpenAccountTestData.ACCOUNT_CHECKING_TYPE,OpenAccountTestData.FROM_ACCOUNT
        );

        Assert.assertEquals(
                openNewAccountPage.getSuccessMessage(),
                SUCCESS_MESSAGE,
                "Incorrect success message displayed"
        );

        String newAccountNumber =
                openNewAccountPage.getNewAccountNumber();

        Assert.assertFalse(
                newAccountNumber.isEmpty(),
                "New account number should not be empty"
        );

        Assert.assertTrue(
                newAccountNumber.matches("\\d+"),
                "New account number should contain digits only"
        );

        return newAccountNumber;
    }

    // =========================================================
    // TC01 - Verify Open New Account page is displayed
    // =========================================================

    @Test(description = "Verify Open New Account page is displayed")
    public void verifyOpenNewAccountPageDisplayed() {

        test.info("STEP 1 - Verify Open New Account page is displayed");

        Assert.assertTrue(
                openNewAccountPage.isOpenNewAccountPageDisplayed(),
                "Open New Account page should be displayed"
        );

        test.pass("Open New Account page displayed successfully");
    }

    // =========================================================
    // TC02 - Verify user can create a new checking account
    // =========================================================

    @Test(description = "Verify user can create a new checking account")
    public void verifyCreateNewCheckingAccount() {

        test.info(
                "STEP 1 - Create new checking account"
        );

        String newAccountNumber =
                createCheckingAccountAndGetAccountNumber();

        test.pass(
                "New checking account created successfully with account number: "
                        + newAccountNumber
        );
    }
    // =========================================================
    // TC03 - Verify newly created account appears in Accounts Overview
    // =========================================================

    @Test(description = "Verify newly created account appears in Accounts Overview")
    public void verifyNewAccountAppearsInAccountsOverview() {

        test.info(
                "STEP 1 - Create new checking account"
        );

        String newAccountNumber =
                createCheckingAccountAndGetAccountNumber();

        test.info(
                "STEP 2 - Navigate to Accounts Overview"
        );

        AccountsOverviewPage accountsOverviewPage =
                dashboardPage.navigateToAccountsOverview();

        test.info(
                "STEP 3 - Get account numbers from Accounts Overview"
        );

        List<String> accountNumbers =
                accountsOverviewPage.getAccountNumbers();

        test.info(
                "STEP 4 - Verify new account exists in Accounts Overview"
        );

        Assert.assertTrue(
                accountNumbers.contains(newAccountNumber),
                "New account should appear in Accounts Overview"
        );

        test.pass(
                "New account appears in Accounts Overview successfully"
        );
    }
}
