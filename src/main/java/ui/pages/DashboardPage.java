package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Dashboard page.
 *
 * This page provides navigation to the main banking features
 * after a successful login.
 */
public class DashboardPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By dashboardHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");
    private final By transferFundsLink = By.linkText("Transfer Funds");
    private final By logoutButton = By.xpath("//a[text()='Log Out']");
    private final By accountsOverviewLink = By.linkText("Accounts Overview");
    private final By openNewAccountLink = By.linkText("Open New Account");


    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    /**
     * Initialize Dashboard page.
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    /**
     * Get dashboard header text.
     */
    public String getDashboardHeader() {
        return getText(dashboardHeader);
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    /**
     * Log out from the application.
     */
    public LoginPage logout() {
        clickElement(logoutButton);
        return new LoginPage(driver);
    }

    /**
     * Navigate to Accounts Overview page.
     */
    public AccountsOverviewPage navigateToAccountsOverview() {
        clickElement(accountsOverviewLink);
        return new AccountsOverviewPage(driver);
    }

    /**
     * Navigate to Open New Account page.
     */
    public OpenNewAccountPage navigateToOpenNewAccount() {
        clickElement(openNewAccountLink);
        return new OpenNewAccountPage(driver);
    }

    /**
     * Navigate to Transfer Funds page.
     */
    public TransferPage navigateToTransferFunds() {
        clickElement(transferFundsLink);
        return new TransferPage(driver);
    }

}
