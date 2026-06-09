package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OpenNewAccountPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private By accountTypeDropdown =
            By.id("type");

    private By fromAccountDropdown =
            By.id("fromAccountId");

    private By openAccountButton =
            By.xpath("//input[@value='Open New Account']");

    private By openAccountTitle  =
            By.xpath("//h1[contains(text(),'Open New Account')]");

    private By successMessage  =
            By.xpath("//h1[text()='Account Opened!']");

    private By newAccountNumber =
            By.id("newAccountId");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public OpenNewAccountPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // PRIVATE HELPER METHODS
    // =========================================================

    private void selectAccountType(String accountType) {

        selectByVisibleText(accountTypeDropdown, accountType);
    }

    private void selectFromAccount(String fromAccount) {

        selectFromDropdown(fromAccountDropdown, fromAccount);
    }

    private void clickOpenAccountButton() {

        clickElement(openAccountButton);
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

    public boolean isOpenNewAccountPageDisplayed() {

        return isElementDisplayed(openAccountTitle);

    }

    public OpenNewAccountPage openNewAccount(String accountType, String fromAccount){

        selectAccountType(accountType);
        selectFromAccount(fromAccount);
        clickOpenAccountButton();

        return this;
    }

    public boolean isAccountCreatedSuccessfully() {

        return isElementDisplayed(successMessage);
    }

    public String getSuccessMessage() {

        return getText(successMessage);
    }

    public String getNewAccountNumber() {

        return getText(newAccountNumber).trim();
    }
}
