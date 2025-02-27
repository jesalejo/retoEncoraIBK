package pe.com.bcp.techcases.testautomation.ui.pages.orange;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import net.serenitybdd.core.annotations.findby.FindBy;
import org.openqa.selenium.WebElement;

@DefaultUrl("page:webdriver.base.url")
public class OrangeLoginPage extends PageObject {
    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
    private WebElement errorMessage;


    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }


    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }


    public void clickLogin() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
