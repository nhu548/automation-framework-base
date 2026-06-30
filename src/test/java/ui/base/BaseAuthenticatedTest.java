package ui.base;

import org.testng.annotations.BeforeMethod;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import utils.ConfigReader;

/**
 * Base test class for UI tests that require an authenticated user.
 * Automatically logs in before each test method.
 */
public class BaseAuthenticatedTest extends BaseTest {

    // =========================================================
    // FIELDS
    // =========================================================

    protected DashboardPage dashboardPage;

    private static final String USERNAME =
            ConfigReader.getProperty("primaryUsername");

    private static final String PASSWORD =
            ConfigReader.getProperty("primaryPassword");

    // =========================================================
    // TEST LIFECYCLE
    // =========================================================

    @BeforeMethod
    public void login() {

        dashboardPage = new LoginPage(driver)
                .login(USERNAME, PASSWORD);
    }
}