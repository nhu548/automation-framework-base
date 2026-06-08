package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

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

    private By accountNumbers =
            By.xpath("//table[@id='accountTable']//tbody/tr/td[1]/a");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }
    // =========================================================
    // PRIVATE HELPER METHODS
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
    // BUSINESS METHODS
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


    public List<String> getAccountNumbers() {

        waitForElementVisible(accountNumbers);

        return driver.findElements(accountNumbers)
                .stream()
                .map(element -> element.getText().trim())
                .toList();
    }


}