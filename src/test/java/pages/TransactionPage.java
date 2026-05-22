package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionPage extends BasePage {

    By transactionTable = By.id("transactionTable");

    public TransactionPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTransactionVisible() {
        return driver.findElement(transactionTable).isDisplayed();
    }
}
