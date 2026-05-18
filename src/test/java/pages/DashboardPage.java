package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    WebDriver driver;
    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Locators

    By dashboardHeader = By.xpath("//h6[text()='Dashboard']");

    // Methods

    public String getDashboardHeader() {
        return driver.findElement(dashboardHeader).getText();
    }
}
