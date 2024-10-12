package eg.amazon.pages;

import eg.amazon.base.BasePage;
import eg.amazon.config.EndPoint;
import eg.amazon.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;


public class VideoGamesPage extends BasePage<VideoGamesPage> {
    List<Double> productsPrices = new ArrayList<>();

    //Constructor
    public VideoGamesPage(WebDriver driver) {
        super(driver);
    }

    //Elements
    @FindBy(xpath = "//div[@class='fst-h1-st pageBanner']/h1/b[text()='Video Games']")
    private WebElement videoGamesPageBanner;
    @FindBy(id = "p_n_free_shipping_eligible/21909080031")
    private WebElement freeShippingFilter;
    @FindBy(xpath = "//a[@class='a-link-normal' and span[text()='New']]")
    private WebElement newFilter;
    @FindBy(id = "s-result-sort-select")
    private WebElement sortingDropdown;
    @FindBy(css = "div.s-main-slot")
    private WebElement productList;

    @FindAll(@FindBy(css = "div.s-main-slot div[data-component-type='s-search-result']"))
    private List<WebElement> products;

    @FindBy(css = "div.ewc-go-to-cart a.a-button-text")
    private WebElement goToBasketButton;

    //Methods
    @Step("Load the Video Games Page")
    @Override
    public VideoGamesPage load() {
        driver.get(ConfigUtils.getInstance().getBaseUrl() + EndPoint.VIDEOGAMES_PAGE_END_POINT);
        return this;
    }

    public VideoGamesPage clickOnFreeShippingFilter() {
        explicitWait().until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        freeShippingFilter.click();
        return this;
    }

    public VideoGamesPage clickOnNewFilter() {
        actions().scrollToElement((newFilter)).moveToElement(newFilter).click().perform();
        return this;
    }

    public VideoGamesPage sortByPriceHighToLow() {
        selectOptionByValueFromDropDown(sortingDropdown, "price-desc-rank");
        return this;
    }

    public boolean isVideoGamesPageBannerDisplayed() {
        return explicitWait().until(ExpectedConditions.visibilityOf(videoGamesPageBanner)).isDisplayed();
    }

    public List<String> addProductsToCart() {
        List<String> addedProducts = new ArrayList<>();

        for (int page = 1; addedProducts.size() < 5; page++) {
            try {
                explicitWait().until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.s-main-slot")));

                for (WebElement product : products) {
                    if (addedProducts.size() >= 5) break;

                    try {
                        String productName = product.findElement(By.cssSelector("h2 a span")).getText();
                        List<WebElement> priceElements = product.findElements(By.cssSelector(".a-price-whole"));

                        if (priceElements.isEmpty()) continue;

                        String priceText = priceElements.get(0).getText().replace(",", "");
                        double price = Double.parseDouble(priceText);

                        if (price < 15000) {
                            WebElement addToCartButton = product.findElement(By.cssSelector("button.a-button-text"));
                            addToCartButton.click();
                            addedProducts.add(productName + " - " + price);
                            productsPrices.add(price);
                            explicitWait().until(ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//span[contains(text(), 'Item added')]")));
                        }
                    } catch (Exception ignored) {
                    }
                }

                if (addedProducts.size() < 5) {
                    try {
                        WebElement nextPageButton = driver.findElement(By.cssSelector("a.s-pagination-next"));
                        nextPageButton.click();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        break;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }

        return addedProducts;
    }

    public CartPage clickOnGoToBasketButton() {
        actions().moveToElement(goToBasketButton).click().perform();
        return new CartPage(driver);
    }

    public List<Double> getProductsPrices() {
        return productsPrices;
    }

}
