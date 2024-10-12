package eg.amazon.base;

import eg.amazon.factory.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BaseTest {

    protected SoftAssert softAssert = new SoftAssert();
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";


    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    public WebDriver getDriver() {
        return this.driver.get();
    }

    @Step("Initialize web driver")
    @BeforeClass
    public void setup() {
        WebDriver driver = new DriverFactory().initializeDriver();
        setDriver(driver);
    }


    @Step("Driver quit")
    @AfterClass
    public void tearDown() {
        getDriver().quit();
    }

    @Step("Clear Session, and take screenshot")
    @AfterMethod
    public void tearDownI(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName();
        boolean status = result.isSuccess();
        File destFile = new File("target" + File.separator + "screenshots" + File.separator + testCaseName + ".png");
        takeScreenshot(destFile);
        if (status) {
            System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLACK + testCaseName + " Test Case Succeed" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED_BACKGROUND + ANSI_BLACK + testCaseName + " Test Case Failed" + ANSI_RESET);
        }
        List<String> allWindows = new ArrayList<>(getDriver().getWindowHandles());
        for (int i = 1; i < allWindows.size(); i++) {
            getDriver().switchTo().window(allWindows.get(i));
            getDriver().close();
        }
        getDriver().switchTo().window(allWindows.getFirst());
        if (Arrays.toString(result.getMethod().getGroups()).contains("changePassword"))
            getDriver().manage().deleteAllCookies();

    }

    public void takeScreenshot(File destFile) {
        File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, destFile);
            InputStream is = new FileInputStream(destFile);
            Allure.addAttachment("screenshot", is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
