package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;


public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginPageTitle() {
        test = extent.createTest("Verify Login Page Title");

        String title = driver.getTitle();

        Assert.assertTrue(title.contains("OrangeHRM"));

        test.pass("Login page title verified successfully");
    }
    //create a test method to verify the login functionality with valid credentials

    @Test
    public void verifyLoginWithValidCredentials() {

        test = extent.createTest("Verify Login With Valid Credentials");

        String username = ConfigReader.getProperty("username");

        String password = ConfigReader.getProperty("password");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        DashboardPage dashboardPage =
                new DashboardPage(driver);

        // Verify that we are logged in by checking the presence of the dashboard header

        Assert.assertEquals(
                dashboardPage.getDashboardHeader(),
                "Dashboard"
        );

        test.pass("Login successful");

    }

}