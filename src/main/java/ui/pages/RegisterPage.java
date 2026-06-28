package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Register page.
 */
public class RegisterPage extends BasePage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By firstNameInput = By.id("customer.firstName");
    private final By lastNameInput = By.id("customer.lastName");
    private final By addressInput = By.id("customer.address.street");
    private final By cityInput = By.id("customer.address.city");
    private final By stateInput = By.id("customer.address.state");
    private final By zipCodeInput = By.id("customer.address.zipCode");
    private final By phoneInput = By.id("customer.phoneNumber");
    private final By ssnInput = By.id("customer.ssn");
    private final By usernameInput = By.id("customer.username");
    private final By passwordInput = By.id("customer.password");
    private final By repeatedPasswordInput = By.id("repeatedPassword");
    private final By registerButton = By.cssSelector("input[value='Register']");
    private final By successTitle = By.cssSelector("#rightPanel h1");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // ACTIONS
    // =========================================================

    public void registerNewUser(
            String firstName,
            String lastName,
            String address,
            String city,
            String state,
            String zipCode,
            String phone,
            String ssn,
            String username,
            String password
    ) {

        enterText(firstNameInput, firstName);
        enterText(lastNameInput, lastName);
        enterText(addressInput, address);
        enterText(cityInput, city);
        enterText(stateInput, state);
        enterText(zipCodeInput, zipCode);
        enterText(phoneInput, phone);
        enterText(ssnInput, ssn);
        enterText(usernameInput, username);
        enterText(passwordInput, password);
        enterText(repeatedPasswordInput, password);

        clickElement(registerButton);
    }

    // =========================================================
    // VALIDATIONS
    // =========================================================

    public boolean isRegistrationSuccessful() {
        return getText(successTitle).contains("Welcome");
    }
}