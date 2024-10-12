package eg.amazon.testcases;

import eg.amazon.config.EndPoint;
import eg.amazon.pages.*;
import eg.amazon.utils.ConfigUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import eg.amazon.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

@Feature("Ordering")
public class OrderTest extends BaseTest {
    @Story("Make an Order")
    @Test(description = "Verify That User Can Complete Order")
    public void VerifyThatUserCanCompleteOrder() throws InterruptedException {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage =
                homePage
                        .load()
                        .hoverOverAccountTab()
                        .clickOnLoginButton();
        boolean isLoginPageDisplayed = loginPage.isLoginFormDisplayed();
        softAssert.assertTrue(isLoginPageDisplayed);
        softAssert.assertTrue(loginPage.getCurrentPageUrl().contains(ConfigUtils.getInstance().getBaseUrl() + EndPoint.LOGIN_PAGE_END_POINT));
        softAssert.assertAll();
        boolean isUserGreetingDisplayed =
                loginPage
                        .fillEmailInput()
                        .clickOnContinueButton()
                        .fillPasswordInput()
                        .clickOnLoginSubmitButton()
                        .isUserGreetingDisplayed();
        softAssert.assertTrue(isUserGreetingDisplayed);
        softAssert.assertTrue(homePage.getCurrentPageUrl().contains(ConfigUtils.getInstance().getBaseUrl()));
        softAssert.assertAll();

        VideoGamesPage videoGamesPage = homePage
                .clickOnAllTab()
                .clickOnSeeAllButton()
                .clickOnVideoGamesTab()
                .clickOnAllVideoGamesTab();
        boolean isVideoGamesPageDisplayed = videoGamesPage
                .isVideoGamesPageBannerDisplayed();
        Assert.assertTrue(isVideoGamesPageDisplayed);
        List<String> addedProducts = videoGamesPage.clickOnNewFilter().clickOnFreeShippingFilter().sortByPriceHighToLow().addProductsToCart();
        CartPage cartPage = videoGamesPage.clickOnGoToBasketButton();
        boolean isCartDisplayed = cartPage.isCartDisplayed();
        Assert.assertTrue(isCartDisplayed);
        List<String> cartProducts = cartPage.getCartProductDetails();
        Collections.sort(addedProducts);
        Collections.sort(cartProducts);
        boolean allMatch = addedProducts.stream()
                .allMatch(addedProduct -> cartProducts.stream()
                        .anyMatch(cartProduct -> cartProduct.startsWith(addedProduct.substring(0, Math.min(30, addedProduct.length())))));
        List<Double> addedProductPrices = videoGamesPage.getProductsPrices();
        double productsTotal = addedProductPrices.stream().mapToDouble(Double::doubleValue).sum();
        List<Double> cartProductPrices = cartPage.getCartPrices();
        double cartTotal = cartProductPrices.stream().mapToDouble(Double::doubleValue).sum();

        softAssert.assertTrue(allMatch);
        softAssert.assertEquals(cartTotal, productsTotal);
        softAssert.assertAll();
        CheckoutPage checkoutPage = cartPage.clickOnProceedToCheckoutButton();
        boolean isCheckoutHeaderDisplayed = checkoutPage.isCheckoutHeaderDisplayed();
        Assert.assertTrue(isCheckoutHeaderDisplayed);
        double totalPrice = checkoutPage.fillAddressForm();
        Assert.assertEquals(productsTotal, totalPrice);
        checkoutPage.navigateBackToCart().removeAllProductsFromCart().hoverOverAccountTab().clickOnYourAddressesButton().RemoveAddress();
        homePage.load();
    }

}
