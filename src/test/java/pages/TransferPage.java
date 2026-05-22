package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferPage extends BasePage {

    By amount = By.id("amount");
    By fromAccount = By.id("fromAccountId");
    By toAccount = By.id("toAccountId");
    By transferBtn = By.xpath("//input[@value='Transfer']");

    By successMsg = By.xpath("//*[contains(text(),'Transfer Complete')]");
    By accountsOverviewLink = By.linkText("Accounts Overview");

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    public TransferPage transferMoney(String value) {
        enterText(amount, value);
        clickElement(transferBtn);
        return this;
    }

    public TransactionPage navigateToTransactionPage() {

        clickElement(accountsOverviewLink);

        return new TransactionPage(driver);
    }

    public boolean isTransferSuccess() {
        return driver.findElement(successMsg).isDisplayed();
    }
}
