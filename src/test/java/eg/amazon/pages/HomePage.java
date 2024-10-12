package eg.amazon.pages;

import eg.amazon.base.BasePage;
import eg.amazon.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage<HomePage> {

    //Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    //Elements
    @FindBy(id = "nav-hamburger-menu")
    private WebElement allTab;
    @FindBy(id = "hmenu-canvas")
    private WebElement hamburgerMenu;
    @FindBy(xpath = "//a[@class='hmenu-item hmenu-compressed-btn']/div[text()='See all']")
    private WebElement seeAllButton;
    @FindBy(xpath = "//a[@class='hmenu-item']/div[text()='Video Games']")
    private WebElement videoGamesTab;
    @FindBy(xpath = "//a[@class='hmenu-item' and text()='All Video Games']")
    private WebElement allVideoGamesTab;
    @FindBy(id = "nav-link-accountList-nav-line-1")
    private WebElement userGreetingHeader;
    @FindBy(xpath = "//div[@class='hmenu-item hmenu-title' and text()='Video Games']")
    private WebElement videoGamesHeader;

    @FindBy(id = "navbar-backup-backup")
    private WebElement wrongPage;

    //Methods
    @Step("Load the Home Page")
    @Override
    public HomePage load() {
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        if (isWrongPageDisplayed()) {
            refresh();
            this.load();
        }
        return this;
    }


    @Step("Click on all Tab")
    public HomePage clickOnAllTab() {
        allTab.click();
        return this;
    }

    @Step("Click on see all Button")
    public HomePage clickOnSeeAllButton() {
        seeAllButton.click();
        return this;
    }

    @Step("Click on Video Games Tab")
    public HomePage clickOnVideoGamesTab() {
        explicitWait().until(ExpectedConditions.visibilityOf(hamburgerMenu));
        videoGamesTab.click();
        return this;
    }

    @Step("Click on All Video Games Tab")
    public VideoGamesPage clickOnAllVideoGamesTab() {
        actions().moveToElement(allVideoGamesTab).click().perform();
        return new VideoGamesPage(driver);
    }

    public boolean isWrongPageDisplayed() {
        try {
            return wrongPage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isUserGreetingDisplayed() {
        return userGreetingHeader.isDisplayed();
    }


}
