package ui.tests;

import ui.testdata.UITestData;
import ui.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {

        loginPage = new LoginPage(driver);
    }

    // =========================================================
    // Helper Method - Login with default credentials
    // =========================================================

    private DashboardPage loginWithValidCredentials() {

        String username =
                ConfigReader.getProperty("primaryUsername");

        String password =
                ConfigReader.getProperty("primaryPassword");

        return loginPage.login(username, password);
    }

    // =========================================================
    // UI01 - Verify Login Page Title Is Displayed Correctly
    // =========================================================

    @Test(description = "UI01 - Verify login page title is displayed correctly")
    public void UI01_verifyLoginPageTitleIsDisplayedCorrectly() {

        String actualTitle = driver.getTitle();

        Assert.assertTrue(
                actualTitle.contains("ParaBank"),
                "Login page title is incorrect"
        );

        test.pass("PASSED - UI01 - Verify login page title is displayed correctly");
    }

    // =========================================================
    // UI02 - Verify User Can Login With Valid Credentials
    // =========================================================

    @Test(description = "UI02 - Verify user can login with valid credentials")
    public void UI02_verifyUserCanLoginWithValidCredentials() {

        DashboardPage dashboardPage =
                loginWithValidCredentials();

        String actualHeader =
                dashboardPage.getDashboardHeader();

        Assert.assertEquals(
                actualHeader,
                "Accounts Overview",
                "Dashboard header is incorrect after login"
        );

        test.pass("PASSED - UI02 - Verify user can login with valid credentials");
    }

    // =========================================================
    // UI03 - Verify Error Message Is Displayed For Invalid Credentials
    // =========================================================

    @Test(description = "UI03 - Verify error message is displayed for invalid credentials")
    public void UI03_verifyErrorMessageIsDisplayedForInvalidCredentials() {

        loginPage.login(
                UITestData.INVALID_USERNAME,
                UITestData.INVALID_PASSWORD
        );

        String actualErrorMessage =
                loginPage.getLoginErrorMessage();

        Assert.assertTrue(
                actualErrorMessage.equals(
                        "The username and password could not be verified."
                )
                ||
                actualErrorMessage.equals(
                        "An internal error has occurred and has been logged."
                ),
                "Unexpected error message displayed"
        );

        test.pass("PASSED - UI03 - Verify error message is displayed for invalid credentials");
    }

    // =========================================================
    // UI04 - Verify User Can Logout Successfully
    // =========================================================

    @Test(description = "UI04 - Verify user can logout successfully")
    public void UI04_verifyUserCanLogoutSuccessfully() {

        DashboardPage dashboardPage =
                loginWithValidCredentials();

        test.info("User logged into application");

        loginPage =
                dashboardPage.logout();

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page should be displayed after logout"
        );

        test.pass("PASSED - UI04 - Verify user can logout successfully");
    }
}