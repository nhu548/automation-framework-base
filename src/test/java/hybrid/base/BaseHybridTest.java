package hybrid.base;

import org.testng.annotations.BeforeMethod;
import ui.base.BaseTest;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import utils.ConfigReader;

/**
 * Base class for hybrid tests that require both UI and API interactions.
 * Automatically logs in before each test.
 */
public class BaseHybridTest extends BaseTest {

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

    // =========================================================
    // EXTENSION POINTS
    // =========================================================

    @Override
    protected String getTestCategory() {
        return "Hybrid";
    }

}
