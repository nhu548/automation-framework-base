package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private By amount = By.xpath("//input[@id='amount']");
    private By transferBtn = By.xpath("//input[@value='Transfer']");

    private By successMsg = By.xpath("//*[contains(text(),'Transfer Complete')]");
    private By accountsOverviewLink = By.linkText("Accounts Overview");

    private By successMessage =
            By.xpath("//h1[contains(text(),'Transfer Complete!')]");

    private By fromAccountDropdown =
            By.xpath("//select[@id='fromAccountId']");

    private By toAccountDropdown =
            By.xpath("//select[@id='toAccountId']");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // PRIVATE HELPER METHODS
    // =========================================================

    private void selectSourceAccount(String fromAccount) {

        selectFromDropdown(
                fromAccountDropdown,
                fromAccount
        );
    }

    private void selectDestinationAccount(String toAccount) {
        System.out.println(
                driver.findElements(fromAccountDropdown).size()
        );
        selectFromDropdown(
                toAccountDropdown,
                toAccount
        );
    }

    private void enterTransferAmount(String amount) {

        enterText(this.amount, amount);
    }

    private void clickTransferButton() {

        clickElement(transferBtn);
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

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

    public TransactionPage navigateToAccountsOverview() {

        clickElement(accountsOverviewLink);

        return new TransactionPage(driver);
    }

    // =========================================================
    // VALIDATION METHODS
    // =========================================================

    public boolean isTransferSuccess() {

        return isElementDisplayed(successMsg);
    }

    public String getTransferSuccessMessage() {

        return driver.findElement(successMessage).getText();
    }
}
