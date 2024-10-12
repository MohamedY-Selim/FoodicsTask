package eg.amazon.pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import eg.amazon.base.BasePage;
import eg.amazon.config.EndPoint;
import eg.amazon.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage<LoginPage> {
    Faker faker = new Faker();

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Elements
    @FindBy(xpath = "//div[@class='a-box']")
    private WebElement loginForm;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//span[@id='continue']")
    private WebElement continueButton;

    @FindBy(id = "ap_password")
    private WebElement passwordInput;

    @FindBy(id = "signInSubmit")
    private WebElement loginButton;

    // Methods
    @Step("Load the Login Page")
    @Override
    public LoginPage load() {
        driver.get(ConfigUtils.getInstance().getBaseUrl() + EndPoint.LOGIN_PAGE_END_POINT);
        return this;
    }

    @Step("Fill Email Input with valid email")
    public LoginPage fillEmailInput() {
        emailInput.sendKeys(ConfigUtils.getInstance().getEmail());
        return this;
    }

    @Step("Fill Password Input with valid password")
    public LoginPage fillPasswordInput() {
        explicitWait().until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(ConfigUtils.getInstance().getPassword());
        return this;
    }

    @Step("Check if login form displayed")
    public boolean isLoginFormDisplayed() {
        return loginForm.isDisplayed();
    }

    @Step("Click on Continue Button")
    public LoginPage clickOnContinueButton() {
        continueButton.click();
        return this;
    }

    @Step("Click on Login Button")
    public HomePage clickOnLoginSubmitButton() {
        loginButton.click();
        return new HomePage(driver);
    }


}
