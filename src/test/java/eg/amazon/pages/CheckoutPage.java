package eg.amazon.pages;

import com.github.javafaker.Faker;
import eg.amazon.base.BasePage;
import eg.amazon.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class CheckoutPage extends BasePage<CheckoutPage> {
    Faker faker = new Faker();

    //Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //Elements
    @FindBy(css = "div.a-column.a-span8 > h1")
    private WebElement checkoutHeader;
    @FindBy(id = "address-ui-widgets-countryCode-dropdown-nativeId")
    private WebElement countryDropdown;
    @FindBy(id = "address-ui-widgets-enterAddressFullName")
    private WebElement fullNameInput;
    @FindBy(id = "address-ui-widgets-enterAddressISDDropdown")
    private WebElement isdDropdown;
    @FindBy(id = "address-ui-widgets-enterAddressPhoneNumber")
    private WebElement phoneNumberInput;
    @FindBy(id = "address-ui-widgets-enterAddressLine1")
    private WebElement streetNameInput;
    @FindBy(id = "address-ui-widgets-enter-building-name-or-number")
    private WebElement buildingNoInput;
    @FindBy(id = "address-ui-widgets-enterAddressCity")
    private WebElement areaInput;
    @FindBy(xpath = "//li[@class='autoOp' and contains(., '6th of October City')]")
    private WebElement selectedArea;
    @FindBy(id = "address-ui-widgets-enterAddressDistrictOrCounty")
    private WebElement districtInput;
    @FindBy(xpath = "//li[@class='autoOp' and text()='7th District']")
    private WebElement selectedDistrict;
    @FindBy(id = "address-ui-widgets-addr-details-res-radio")
    private WebElement addressTypeRadioButton;
    @FindBy(css = "[aria-labelledby=\"address-ui-widgets-form-submit-button-announce\"]")
    private WebElement submitButton;
    @FindBy(xpath = "//td[contains(@class, 'grand-total-price') and contains(@class, 'a-text-bold')]")
    private WebElement orderTotalElement;

    //Methods
    @Step("Load the Brand Page")
    @Override
    public CheckoutPage load() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        return this;
    }

    public boolean isCheckoutHeaderDisplayed() {
        return checkoutHeader.isDisplayed();
    }

    public double fillAddressForm() {
        selectOptionByValueFromDropDown(countryDropdown, "EG");
        fullNameInput.sendKeys(faker.name().firstName() + " " + faker.name().lastName());
        selectOptionByValueFromDropDown(isdDropdown, "EG +20");
        phoneNumberInput.sendKeys(ConfigUtils.generateRandomMobileNumber());
        streetNameInput.sendKeys(faker.address().streetName());
        buildingNoInput.sendKeys(faker.address().buildingNumber());
        areaInput.sendKeys("6th of October City");
        selectedArea.click();
        districtInput.sendKeys("7th District");
        selectedDistrict.click();
        addressTypeRadioButton.click();
        submitButton.click();
        String orderTotalText = orderTotalElement.getText().replace("EGP", "").replace(",", "").trim();
        double orderTotal = Double.parseDouble(orderTotalText);
        return orderTotal;
    }

    public CartPage navigateBackToCart() {
        driver.navigate().back();
        return new CartPage(driver);
    }
}
