package pe.com.bcp.techcases.testautomation.ui.pages.sauce;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;



@DefaultUrl("page:webdriver.base.url")
public class SauceDemoLoginPage extends PageObject {

    private final By userNameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("div.login_container div.login_wrapper div.login_wrapper-inner div.form_column div.login-box form:nth-child(1) > div.error-message-container.error:nth-child(3)");


    public void typeUser(String user){
        getDriver().findElement(userNameInput).sendKeys(user);
    }

    public void typePassword(String pass){
        getDriver().findElement(passwordInput).sendKeys(pass);
    }

    public void logIn(){
        getDriver().findElement(loginButton).click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
