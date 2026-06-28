package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Open New Account page.
 */
public class OpenNewAccountPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By accountTypeDropdown = By.id("type");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By openAccountButton = By.xpath("//input[@value='Open New Account']");
    private final By openAccountTitle = By.xpath("//h1[contains(text(),'Open New Account')]");
    private final By successMessage = By.xpath("//h1[text()='Account Opened!']");
    private final By newAccountId = By.id("newAccountId");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public OpenNewAccountPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public String getNewAccountId() {
        return getText(newAccountId).trim();
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    public OpenNewAccountPage openNewAccount(
            String accountType,
            String fromAccount
    ) {
        selectByVisibleText(accountTypeDropdown, accountType);
        selectFromDropdown(fromAccountDropdown, fromAccount);
        clickElement(openAccountButton);

        return this;
    }

    public String openNewAccountAndGetAccountId(
            String accountType,
            String sourceAccountId
    ) {
        openNewAccount(accountType, sourceAccountId);

        return getNewAccountId();
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================

    public boolean isOpenNewAccountPageDisplayed() {
        return isElementDisplayed(openAccountTitle);
    }
}
