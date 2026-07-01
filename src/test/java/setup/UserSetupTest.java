package setup;

import api.clients.AccountApiClient;
import api.clients.CustomerApiClient;
import api.clients.LoginApiClient;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.base.BaseTest;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import ui.pages.RegisterPage;
import ui.testdata.RegisterTestData;
import utils.ConfigReader;
import utils.TestDataWriter;

import static api.testdata.ApiTestData.CHECKING_ACCOUNT_TYPE;

public class UserSetupTest extends BaseTest {

    @Test(description = "UI00 - Generate fresh test data")
    public void UI00_generateFreshTestData(){

        RestAssured.baseURI =
                ConfigReader.getProperty("apiBaseUrl");

        String primaryUsername = "nhuqa1" + System.currentTimeMillis();
        String secondaryUsername = "nhuqa2" + System.currentTimeMillis();
        String password = "Test@123";

        LoginApiClient loginApiClient = new LoginApiClient();
        CustomerApiClient customerApiClient = new CustomerApiClient();
        AccountApiClient accountApiClient = new AccountApiClient();

        DashboardPage dashboardPage1 = registerUser(primaryUsername, password);
        dashboardPage1.logout();

        registerUser(secondaryUsername, password);

        String primaryCustomerId =
                loginApiClient.getCustomerId(primaryUsername, password);

        String primarySourceAccountId =
                customerApiClient.getLatestAccountIdByCustomerId(primaryCustomerId);

        String primaryDestinationAccountId = accountApiClient.createNewAccount( primarySourceAccountId, CHECKING_ACCOUNT_TYPE );

        TestDataWriter.save(
                primaryUsername,
                password,
                secondaryUsername,
                password,
                primaryCustomerId,
                primarySourceAccountId,
                primaryDestinationAccountId
        );

        test.info("Username 1: " + primaryUsername);
        test.info("Password 1: " + password);
        test.info("Username 2: " + secondaryUsername);
        test.info("Password 2: " + password);
        test.info("Source Customer ID : " + primaryCustomerId);
        test.info("Source Account ID : " + primarySourceAccountId);
        test.info("Destination Account ID : " + primaryDestinationAccountId);

    }
        private DashboardPage registerUser(String username, String password) {
            LoginPage loginPage = new LoginPage(driver);

            RegisterPage registerPage =
                    loginPage.navigateToRegisterPage();

            registerPage.registerNewUser(
                    RegisterTestData.FIRST_NAME,
                    RegisterTestData.LAST_NAME,
                    RegisterTestData.ADDRESS,
                    RegisterTestData.CITY,
                    RegisterTestData.STATE,
                    RegisterTestData.ZIP_CODE,
                    RegisterTestData.PHONE_NUMBER,
                    RegisterTestData.SSN,
                    username,
                    password
            );

            Assert.assertTrue(
                    registerPage.isRegistrationSuccessful(),
                    "User registration should be successful"
            );
            return new DashboardPage(driver);
        }
}
