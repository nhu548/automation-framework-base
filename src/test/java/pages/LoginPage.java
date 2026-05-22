package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.xpath("//input[@type='submit']");
    By errorMessage = By.cssSelector(".error");

    public DashboardPage login(String username, String password) {
        enterText(usernameInput, username);
        enterText(passwordInput, password);
        clickElement(loginButton);

        return new DashboardPage(driver);
    }

    public String getLoginErrorMessage() {

        return driver.findElement(errorMessage).getText();
    }

    public boolean isLoginPageDisplayed() {

        return driver.findElement(loginButton).isDisplayed();
    }
}