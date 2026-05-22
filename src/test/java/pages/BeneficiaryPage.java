package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BeneficiaryPage extends BasePage {

    WebDriver driver;

    public BeneficiaryPage(WebDriver driver) {

        super(driver);
        this.driver = driver;
    }

    // Locators

    By pimMenu =
            By.xpath("//span[text()='PIM']");

    By addEmployeeButton =
            By.xpath("//a[text()='Add Employee']");

    By firstNameField =
            By.name("firstName");

    By lastNameField =
            By.name("lastName");

    By saveButton =
            By.xpath("//button[@type='submit']");

    By successMessage =
            By.xpath("//h6[text()='Personal Details']");

    // Methods

    public void navigateToAddEmployee() {

        clickElement(pimMenu);

        clickElement(addEmployeeButton);
    }

    public void addBeneficiary(
            String firstName,
            String lastName
    ) {

        enterText(firstNameField, firstName);

        enterText(lastNameField, lastName);

        clickElement(saveButton);
    }

    public String getSuccessMessage() {

        return driver.findElement(successMessage)
                .getText();
    }
}