package eg.amazon.pages;

import eg.amazon.base.BasePage;
import eg.amazon.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;


public class CartPage extends BasePage<CartPage> {
    List<Double> cartPrices = new ArrayList<>();

    //Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Elements
    @FindBy(id = "sc-active-cart")
    private WebElement cart;

    @FindBy(id = "sc-buy-box-ptc-button")
    private WebElement proceedToCheckoutButton;

    @FindAll
            (@FindBy(css = "div[data-name='Active Items'] div.sc-list-item"))
    private List<WebElement> cartItems;

    @FindAll(@FindBy(css = "[data-feature-id=\"item-delete-button\"]"))
    private List<WebElement> deleteButtons;

    //Methods
    @Step("Load the Brand Page")
    @Override
    public CartPage load() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        return this;
    }

    public boolean isCartDisplayed() {
        return cart.isDisplayed();
    }

    public CheckoutPage clickOnProceedToCheckoutButton() {
        actions().moveToElement(proceedToCheckoutButton).click().perform();
        return new CheckoutPage(driver);
    }

    public List<String> getCartProductDetails() {

        List<String> cartProducts = new ArrayList<>();

        for (WebElement item : cartItems) {
            try {
                String productName = item.findElement(By.cssSelector("span.sc-product-title")).getText().trim();

                String priceText = item.findElement(By.cssSelector("span.sc-price"))
                        .getText().replaceAll("[^0-9.]", "");
                double price = Double.parseDouble(priceText);

                cartProducts.add(productName + " - " + price);
                cartPrices.add(price);
            } catch (Exception e) {
                System.out.println("Error extracting product: " + e.getMessage());
            }
        }
        return cartProducts;
    }

    public List<Double> getCartPrices() {
        return cartPrices;
    }

    public CartPage removeAllProductsFromCart() {

        if (deleteButtons.isEmpty()) {
            System.out.println("Cart is already empty.");
            return this;
        }

        while (!deleteButtons.isEmpty()) {
            explicitWait().until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0))).click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            deleteButtons = driver.findElements(By.cssSelector("[data-feature-id='item-delete-button']"));
        }


        return this;
    }

}
