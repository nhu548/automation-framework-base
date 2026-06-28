package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Accounts Details page.
 */
public class AccountDetailsPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By transactionTable = By.id("transactionTable");
    private final By transactionRows = By.xpath("//table[@id='transactionTable']/tbody/tr");
    private final By dateCells = By.xpath("//table[@id='transactionTable']/tbody/tr/td[1]");
    private final By transactionDescriptions = By.xpath("//table[@id='transactionTable']//tbody/tr/td[2]");
    private final By amountCells = By.xpath("//table[@id='transactionTable']/tbody/tr/td[3]");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountDetailsPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public int getTransactionCount() {
        waitForElementVisible(transactionRows);
        return driver.findElements(transactionRows).size();
    }

    public List<String> getTransactionDates() {
        return extractColumnTexts(dateCells);
    }

    public List<String> getTransactionAmounts() {
        waitForElementVisible(amountCells);
        return extractColumnTexts(amountCells);
    }

    public List<String> getTransactionDescriptions() {

        wait.until(driver ->
                driver.findElements(transactionDescriptions).size() > 0
        );

        return driver.findElements(transactionDescriptions)
                .stream()
                .map(e -> e.getText().trim())
                .toList();
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================

    public boolean isTransactionTableDisplayed() {
        waitForElementVisible(transactionTable);
        return isElementDisplayed(transactionTable);
    }

    public boolean isValidDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        try {
            LocalDate.parse(date.trim(), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isValidAmountFormat(String amount) {
        return amount != null && amount.matches("\\$\\d+\\.\\d{2}");
    }

    public boolean isTransactionDisplayed(String expectedDescription, String expectedAmount) {

        waitForElementVisible(transactionRows);

        return driver.findElements(transactionRows)
                .stream()
                .anyMatch(row ->

                        row.getText().contains(expectedDescription)
                                &&
                                row.getText().contains(expectedAmount)
                );
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private List<String> extractColumnTexts(By locator) {
        List<String> values = new ArrayList<>();
        List<WebElement> elements = driver.findElements(locator);

        for (WebElement el : elements) {
            values.add(el.getText().trim());
        }
        return values;
    }
}