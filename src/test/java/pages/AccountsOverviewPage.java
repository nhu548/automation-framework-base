package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsOverviewPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private By accountsTable =
            By.id("accountTable");

    private By accountRows =
            By.xpath("//table[@id='accountTable']/tbody/tr");

    private By firstAccountLink =
            By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");

    private By firstAccountNumber =
            By.xpath("//table//tbody/tr[1]/td[1]/a");

    private By firstAccountBalance =
            By.xpath("//table//tbody/tr[1]/td[2]");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountsOverviewPage(WebDriver driver) {

        super(driver);
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

    public boolean isAccountsTableDisplayed() {

        return isElementDisplayed(accountsTable);
    }

    public int getAccountsCount() {

        waitForAccountsLoaded();

        return driver.findElements(accountRows).size();
    }

    public AccountDetailsPage clickFirstAccount() {

        clickElement(firstAccountLink);

        return new AccountDetailsPage(driver);
    }

    public void waitForAccountsLoaded() {

        wait.until(
                driver ->
                        driver.findElements(accountRows).size() > 0
        );
    }

    public String getFirstAccountNumber() {

        return getText(firstAccountNumber);
    }

    // Generate Selenium Page Object method to get first account balance
    public String getFirstAccountBalance() {

        return getText(firstAccountBalance);
    }
}