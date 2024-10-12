package eg.amazon.base;

import eg.amazon.pages.HomePage;
import eg.amazon.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BasePage<T extends BasePage<T>> {

    //Driver
    protected WebDriver driver;

    //Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Elements
    @FindBy(id = "nav-link-accountList")
    protected WebElement accountTab;
    @FindBy(xpath = "//div[@id='nav-flyout-ya-signin']//a[@data-nav-role='signin']")
    protected WebElement signInButton;
    @FindBy(id = "nav_prefetch_youraddresses")
    protected WebElement yourAddressesButton;
    @FindBy(id = "ya-myab-address-delete-btn-0")
    protected WebElement deleteAddressButton;
    @FindBy(id = "deleteAddressModal-0-submit-btn")
    protected WebElement confirmDeletion;


    public T load() {
        return (T) this;
    }

    @Step("get Current Url")
    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public Select interactWithDropDown(WebElement dropDownElement) {
        return new Select(dropDownElement);
    }

    public WebDriverWait explicitWait() {
        return new WebDriverWait(this.driver, Duration.ofSeconds(15));

    }

    public Actions actions() {
        return new Actions(this.driver);

    }


    public void selectOptionByIndexFromDropDown(WebElement dropDownElement, int index) {
        Select dropDown = interactWithDropDown(dropDownElement);
        dropDown.selectByIndex(index);
    }

    public void selectOptionByValueFromDropDown(WebElement dropDownElement, String value) {
        Select dropDown = interactWithDropDown(dropDownElement);
        dropDown.selectByValue(value);
    }

    public void closePreviousTabAndKeepCurrent() {
        explicitWait().until(ExpectedConditions.numberOfWindowsToBe(2));
        String originalWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                driver.switchTo().window(originalWindowHandle).close();
                break;
            }
        }
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }

    public void refresh() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @Step("Hover Over Account Tab")
    public HomePage hoverOverAccountTab() {
        actions().moveToElement(accountTab).perform();
        return new HomePage(driver);
    }

    @Step("Click on Login button")
    public LoginPage clickOnLoginButton() {
        explicitWait().until(ExpectedConditions.visibilityOf(signInButton)).click();
        return new LoginPage(driver);
    }

    @Step("Click on Your Addresses button")
    public HomePage clickOnYourAddressesButton() {
        explicitWait().until(ExpectedConditions.visibilityOf(yourAddressesButton)).click();
        return new HomePage(driver);
    }

    @Step("Click on Delete Address button")
    public void RemoveAddress() {
        explicitWait().until(ExpectedConditions.visibilityOf(deleteAddressButton)).click();
        explicitWait().until(ExpectedConditions.visibilityOf(confirmDeletion)).click();
    }

}