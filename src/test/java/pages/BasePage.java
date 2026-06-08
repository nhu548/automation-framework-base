package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;

    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {

        this.driver = driver;

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    // =========================================================
    // ELEMENT METHODS
    // =========================================================

    public WebElement getElement(By locator) {

        waitForElementVisible(locator);

        return driver.findElement(locator);
    }

    public void clickElement(By locator) {

        getElement(locator).click();
    }

    public void enterText(By locator, String text) {

        waitForElementVisible(locator);

        WebElement element =
                driver.findElement(locator);

        element.clear();

        element.sendKeys(text);
    }

    public String getText(By locator) {

        return getElement(locator).getText();
    }

    // =========================================================
    // VALIDATION METHODS
    // =========================================================

    public boolean isElementDisplayed(By locator) {

        try {

            waitForElementVisible(locator);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // =========================================================
    // DROPDOWN METHODS
    // =========================================================

    public void selectFromDropdown(By locator, String value) {

        waitForDropdownOptions(locator);

        Select select =
                new Select(getElement(locator));
        select.selectByValue(value);

    }

    public void selectByVisibleText(
            By locator,
            String visibleText
    ) {

        Select select =
                new Select(driver.findElement(locator));

        select.selectByVisibleText(visibleText);
    }

    // =========================================================
    // WAIT METHODS
    // =========================================================

    public void waitForElementVisible(By locator) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        );
    }

    public void waitForDropdownOptions(By locator) {

        wait.until(driver -> {

            Select select =
                    new Select(driver.findElement(locator));

            return select.getOptions().size() > 0;
        });
    }
}