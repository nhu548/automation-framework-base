package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailsPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By transactionTable = By.id("transactionTable");

    private By transactionRows =
            By.xpath("//table[@id='transactionTable']/tbody/tr");

    private final By dateCells =
            By.xpath("//table[@id='transactionTable']/tbody/tr/td[1]");

    private final By descriptionCells =
            By.xpath("//table[@id='transactionTable']/tbody/tr/td[2]");

    private By transactionDescriptions =
            By.xpath("//table[@id='transactionTable']//tbody/tr/td[2]");

    private final By amountCells =
            By.xpath("//table[@id='transactionTable']/tbody/tr/td[3]");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AccountDetailsPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    public boolean isTransactionTableDisplayed() {
        waitForElementVisible(transactionTable);
        return isElementDisplayed(transactionTable);
    }

    public int  getTransactionCount() {
        waitForElementVisible(transactionRows);
        return driver.findElements(transactionRows).size();
    }

    // =========================================================
    // GET SINGLE ROW DATA
    // =========================================================

    public String[] getTransactionDetails(int rowNumber) {
        String baseXpath = "//table[@id='transactionTable']/tbody/tr[" + rowNumber + "]";

        String date = driver.findElement(By.xpath(baseXpath + "/td[1]")).getText();
        String description = driver.findElement(By.xpath(baseXpath + "/td[2]")).getText();
        String amount = driver.findElement(By.xpath(baseXpath + "/td[3]")).getText();

        return new String[]{date, description, amount};
    }

    public String getTransactionAmount(int rowNumber) {
        String xpath = "//table[@id='transactionTable']/tbody/tr[" + rowNumber + "]/td[3]";
        return driver.findElement(By.xpath(xpath)).getText();
    }

    // =========================================================
    // GET LIST DATA
    // =========================================================

    public List<String> getTransactionDates() {
        return extractColumnTexts(dateCells);
    }

    public List<String> getTransactionAmounts() {
        waitForElementVisible(amountCells);
        return extractColumnTexts(amountCells);
    }


    private List<String> extractColumnTexts(By locator) {
        List<String> values = new ArrayList<>();
        List<WebElement> elements = driver.findElements(locator);

        for (WebElement el : elements) {
            values.add(el.getText());
        }
        return values;
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
}