package base;

import org.testng.annotations.BeforeMethod;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import utils.ConfigReader;

public class BaseAuthenticatedTest extends BaseTest {

    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;

    @BeforeMethod
    public void login() {

        loginPage = new LoginPage(driver);

        dashboardPage = loginPage.login(
                ConfigReader.getProperty("username1"),
                ConfigReader.getProperty("password1")
        );
    }
}