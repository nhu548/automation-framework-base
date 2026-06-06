package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsOverviewPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================
    private By accountsTable = By.id("accountTable");

    private By accountRows =
            By.xpath("//table[@id='accountTable']/tbody/tr");

    private final By firstAccountLink =
            By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");

    private final By firstAccountBalance =
            By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[2]");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }
    // =========================================================
    // ACTIONS / STATE CHECKS
    // =========================================================
    public boolean isAccountsTableDisplayed() {
        return isElementDisplayed(accountsTable);
    }

    public void waitForAccountsLoaded() {

        wait.until(
                driver ->
                        driver.findElements(accountRows).size() > 0
        );
    }

    public int getAccountsCount() {
        waitForAccountsLoaded();
        return driver.findElements(accountRows).size();
    }

    // =========================================================
    // FIRST ROW DATA (COMMON BUSINESS CASE)
    // =========================================================

    public String getFirstAccountNumber() {
        return getText(firstAccountLink);
    }

    public String getFirstAccountBalance() {
        return getText(firstAccountBalance);
    }

    public AccountDetailsPage clickFirstAccount() {
        clickElement(firstAccountLink);
        return new AccountDetailsPage(driver);
    }


}