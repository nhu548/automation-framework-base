package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Transfer page.
 */
public class TransferPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By amountInput = By.xpath("//input[@id='amount']");
    private final By transferButton = By.xpath("//input[@value='Transfer']");
    private final By transferSuccessMessage  = By.xpath("//h1[contains(text(),'Transfer Complete!')]");
    private final By fromAccountDropdown = By.xpath("//select[@id='fromAccountId']");
    private final By toAccountDropdown = By.xpath("//select[@id='toAccountId']");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    /**
     * Get transfer success message.
     */
    public String getTransferSuccessMessage() {
        return getText(transferSuccessMessage);
    }

    // =========================================================
    // ACTIONS
    // =========================================================
    /**
     * Transfer funds between two accounts.
     */
    public TransferPage transferFunds(
            String fromAccount,
            String toAccount,
            String amount
    ) {

        selectSourceAccount(fromAccount);
        selectDestinationAccount(toAccount);
        enterTransferAmount(amount);
        clickTransferButton();

        return this;
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================
    /**
     * Check whether the transfer is completed successfully.
     */
    public boolean isTransferSuccessful() {
        return isElementDisplayed(transferSuccessMessage);
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private void selectSourceAccount(String fromAccount) {
        selectFromDropdown(
                fromAccountDropdown,
                fromAccount
        );
    }

    private void selectDestinationAccount(String toAccount) {
        selectFromDropdown(
                toAccountDropdown,
                toAccount
        );
    }

    private void enterTransferAmount(String amount) {
        enterText(this.amountInput, amount);
    }

    private void clickTransferButton() {
        clickElement(transferButton);
    }
}
