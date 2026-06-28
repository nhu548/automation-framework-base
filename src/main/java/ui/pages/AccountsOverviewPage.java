package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;

/**
 * Page Object for the Accounts Overview page.
 */
public class AccountsOverviewPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By accountsTable = By.id("accountTable");
    private final By accountRows = By.xpath("//table[@id='accountTable']/tbody/tr");
    private final By firstAccountLink = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");
    private final By firstAccountBalance = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[2]");
    private final By accountNumbers = By.xpath("//table[@id='accountTable']//tbody/tr/td[1]/a");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public int getAccountsCount() {
        waitForAccountsLoaded();
        return driver.findElements(accountRows).size();
    }

    public String getFirstAccountNumber() {
        return getText(firstAccountLink);
    }

    public String getFirstAccountBalance() {
        return getText(firstAccountBalance);
    }

    public BigDecimal getAccountBalance(String accountNumber) {

        String balanceText =
                getText(getAccountBalanceLocator(accountNumber))
                        .replace("$", "")
                        .replace(",", "")
                        .trim();

        return new BigDecimal(balanceText);
    }

    public List<String> getAccountNumbers() {
        waitForElementVisible(accountNumbers);

        return driver.findElements(accountNumbers)
                .stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    public AccountDetailsPage clickFirstAccount() {
        clickElement(firstAccountLink);

        return new AccountDetailsPage(driver);
    }

    public AccountDetailsPage clickAccountByNumber(String accountNumber) {
        clickElement(getAccountLinkLocator(accountNumber));

        return new AccountDetailsPage(driver);
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================

    public boolean isAccountsTableDisplayed() {
        return isElementDisplayed(accountsTable);
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private void waitForAccountsLoaded() {
        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        accountRows,
                        0
                )
        );
    }

    private By getAccountBalanceLocator(String accountNumber) {
        return By.xpath(
                "//a[text()='" + accountNumber + "']" +
                        "/parent::td/following-sibling::td[1]"
        );
    }

    private By getAccountLinkLocator(String accountNumber) {
        return By.linkText(accountNumber);
    }

}