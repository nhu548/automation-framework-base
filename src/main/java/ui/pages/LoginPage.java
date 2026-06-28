package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for ParaBank Login page.
 */
public class LoginPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//input[@type='submit']");
    private final By registerLink = By.linkText("Register");
    private final By errorMessage = By.cssSelector(".error");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Initialize Login page.
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    /**
     * Login with username and password.
     */
    public DashboardPage login(String username, String password) {
        enterText(usernameInput, username);
        enterText(passwordInput, password);
        clickElement(loginButton);

        return new DashboardPage(driver);
    }

    /**
     * Navigate to Register page.
     */
    public RegisterPage navigateToRegisterPage() {
        clickElement(registerLink);

        return new RegisterPage(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    /**
     * Get login error message after failed login.
     */
    public String getLoginErrorMessage() {
        return getText(errorMessage);
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================

    /**
     * Check whether login page is displayed.
     */
    public boolean isLoginPageDisplayed() {

        return isElementDisplayed(loginButton);
    }

}