package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    // =========================
    // COMMON METHODS
    // =========================

    public void clickElement(By locator) {

        waitForVisibility(locator);

        driver.findElement(locator).click();
    }

    public void enterText(By locator, String text) {

        waitForVisibility(locator);

        driver.findElement(locator).clear();

        driver.findElement(locator).sendKeys(text);
    }

    public String getText(By locator) {

        waitForVisibility(locator);

        return driver.findElement(locator).getText();
    }

    public boolean isDisplayed(By locator) {

        waitForVisibility(locator);

        return driver.findElement(locator).isDisplayed();
    }

    // =========================
    // WAIT METHODS
    // =========================

    public void waitForVisibility(By locator) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        );
    }
}