package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;


public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginPageTitle() {

        String title = driver.getTitle();

        Assert.assertTrue(title.contains("OrangeHRM"));
    }
    //create a test method to verify the login functionality with valid credentials

    @Test
    public void verifyLoginWithValidCredentials() {
        String username = ConfigReader.getProperty("username");

        String password = ConfigReader.getProperty("password");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        // Verify that we are logged in by checking the presence of the dashboard header

        Assert.assertEquals(
                loginPage.getDashboardHeader(),
                "Dashboard"
        );
    }

}