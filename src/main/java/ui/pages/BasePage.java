package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

/**
 * Base page for all Page Objects.
 *
 * Provides common reusable methods such as:
 * - Element interaction
 * - Dropdown selection
 * - Wait utilities
 * - Basic validations
 */
public class BasePage {

    // =========================================================
    // FIELDS
    // =========================================================

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    /**
     * Initialize WebDriver and explicit wait.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =========================================================
    // ELEMENT METHODS
    // =========================================================
    /**
     * Wait until the element is visible before returning it.
     */
    public WebElement getElement(By locator) {
        waitForElementVisible(locator);
        return driver.findElement(locator);
    }

    /**
     * Click an element.
     */
    public void clickElement(By locator) {
        getElement(locator).click();
    }

    /**
     * Clear the input field and enter text.
     */
    public void enterText(By locator, String text) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from an element.
     */
    public String getText(By locator) {
        return getElement(locator).getText();
    }

    // =========================================================
    // DROPDOWN METHODS
    // =========================================================
    /**
     * Select an option from a dropdown by value.
     *
     * @param locator Dropdown locator.
     * @param value Option value to select.
     */
    public void selectFromDropdown(By locator, String value) {
        waitForDropdownOptions(locator);

        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }

    /**
     * Select an option from a dropdown by visible text.
     *
     * @param locator Dropdown locator.
     * @param visibleText Visible text to select.
     */
    public void selectByVisibleText(By locator, String visibleText) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(visibleText);
    }

    // =========================================================
    // VALIDATION METHODS
    // =========================================================
    /**
     * Check whether an element is displayed.
     *
     * @param locator Element locator.
     * @return true if the element is visible; otherwise false.
     */
    public boolean isElementDisplayed(By locator) {
        try {
            waitForElementVisible(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // =========================================================
    // WAIT METHODS
    // =========================================================
    /**
     * Wait until the element is visible.
     */
    public void waitForElementVisible(By locator) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    /**
     * Wait until the dropdown contains at least one option.
     */
    public void waitForDropdownOptions(By locator) {

        wait.until(driver -> {
            Select select = new Select(driver.findElement(locator));
            return !select.getOptions().isEmpty();
        });
    }
}