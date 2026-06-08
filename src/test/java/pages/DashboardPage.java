package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private By dashboardHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");

    private By transferFundsLink = By.linkText("Transfer Funds");

    private By logoutButton =
            By.xpath("//a[text()='Log Out']");

    private By accountsOverviewLink =
            By.linkText("Accounts Overview");

    private By openNewAccountLink =
            By.linkText("Open New Account");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getDashboardHeader() {
        return getText(dashboardHeader);
    }

    public boolean isDashboardDisplayed() {
        return driver.findElement(dashboardHeader).isDisplayed();
    }

    public TransferPage navigateToTransferFunds() {
        clickElement(transferFundsLink);
        return new TransferPage(driver);
    }

    public LoginPage logout() {

        driver.findElement(logoutButton).click();

        return new LoginPage(driver);
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

    public AccountsOverviewPage navigateToAccountsOverview() {

        clickElement(accountsOverviewLink);

        return new AccountsOverviewPage(driver);
    }

    public OpenNewAccountPage navigateToOpenNewAccount() {

        clickElement(openNewAccountLink);

        return new OpenNewAccountPage(driver);
    }

}
