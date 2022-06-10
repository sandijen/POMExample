package testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import library.SelectBrowser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.AppleLoginPage;
import pages.AppleMainPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AppleTest {

    WebDriver driver;
    AppleMainPage appleMainPage;
    AppleLoginPage appleLoginPage;

    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        //create the HtmlReporter in that path by the name of  MyOwnReport.html
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/AppleReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "ray.home-server.local");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Ray Gurung");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("AutomationTesting Google download pictures report");
        htmlReporter.config().setReportName("Google Search and Download Pictures Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);

    }
    @BeforeTest
    public void setUpBrowser() {
        driver = SelectBrowser.StartBrowser("Chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("http://apple.com");
    }
    @Test(priority = 1)
    public void click_on_bag_test() throws IOException {
        test = extent.createTest("click_on_bag_test", "Pass");
        test.addScreenCaptureFromPath("test-output/screenshots/main_page.png");
        appleMainPage = new AppleMainPage(driver);
        appleMainPage.clickOnShoppingBag();
        appleMainPage.clickOnSignInLink();
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("test-output/screenshots/main_page.png"));
    }

    @Test(priority = 2)
    public void login_into_with_invalid_pswd_test() throws IOException {
        appleLoginPage = new AppleLoginPage(driver);
        appleLoginPage.switchFrames();
        appleLoginPage.inputLogin("rayking132@gmail.com");
        appleLoginPage.clickButtonForLoginAndSignIn();
        appleLoginPage.inputPassword("1234");
        appleLoginPage.clickButtonForLoginAndSignIn();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errMsg")));


        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("test-output/screenshots/login_page.png"));

        String expected = "Your Apple ID or password was incorrect.";
        Assert.assertEquals(expected, appleLoginPage.getErrorMessage());
    }
    @AfterSuite

    public void tearDown()
    {
        extent.flush();
    }

}









