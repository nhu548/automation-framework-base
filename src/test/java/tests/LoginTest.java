package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
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
                ConfigReader.getProperty("username1");

        String password =
                ConfigReader.getProperty("password1");

        return loginPage.login(username, password);
    }

    // =========================================================
    // TC01 - Verify login page title displayed correctly
    // =========================================================

    @Test(description = "Verify login page title is displayed correctly")
    public void verifyLoginPageTitle() {

        String actualTitle = driver.getTitle();

        Assert.assertTrue(
                actualTitle.contains("ParaBank"),
                "Login page title is incorrect"
        );

        test.pass("Login page title verified successfully");
    }

    // =========================================================
    // TC02 - Verify successful login with valid credentials
    // =========================================================

    @Test(description = "Verify user can login successfully with valid credentials")
    public void verifyLoginWithValidCredentials() {

        DashboardPage dashboardPage =
                loginWithValidCredentials();

        String actualHeader =
                dashboardPage.getDashboardHeader();

        Assert.assertEquals(
                actualHeader,
                "Accounts Overview",
                "Dashboard header is incorrect after login"
        );

        test.pass("User logged in successfully");
    }

    // =========================================================
    // TC03 - Verify login with invalid credentials
    // =========================================================

    @Test(description = "Verify error message displayed for invalid login")
    public void verifyLoginWithInvalidCredentials() {

        loginPage.login(
                "invalidUser",
                "invalidPassword"
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

        test.pass("Proper error message displayed for invalid login");
    }

    // =========================================================
    // TC04 - Verify successful logout
    // =========================================================

    @Test(description = "Verify user can logout successfully")
    public void verifySuccessfulLogout() {

        DashboardPage dashboardPage =
                loginWithValidCredentials();

        test.info("User logged into application");

        loginPage =
                dashboardPage.logout();

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page should be displayed after logout"
        );

        test.pass("Logout functionality verified successfully");
    }
}