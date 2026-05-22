package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private By dashboardHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");

    private By transferFundsLink = By.linkText("Transfer Funds");

    private By logoutButton =
            By.xpath("//a[text()='Log Out']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getDashboardHeader() {
        return getText(dashboardHeader);
    }

    public boolean isDashboardDisplayed() {
        return driver.findElement(dashboardHeader).isDisplayed();
    }

    public TransferPage navigateToTransferFunds() {
        clickElement(transferFundsLink);
        return new TransferPage(driver);
    }

    public LoginPage logout() {

        driver.findElement(logoutButton).click();

        return new LoginPage(driver);
    }
}
