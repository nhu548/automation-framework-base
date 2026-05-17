package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    By usernameInput = By.name("username");

    By passwordInput = By.name("password");

    By loginButton = By.xpath("//button[@type='submit']");

    // Actions
    public void enterUsername(String username) {
        enterText(usernameInput, username);
    }

    public void enterPassword(String password) {
        enterText(passwordInput, password);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void login(String username, String password) {

        enterUsername(username);

        enterPassword(password);

        clickLoginButton();
    }
}