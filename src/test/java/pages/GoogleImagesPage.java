package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleImagesPage {

    WebDriver driver;

    By clickOnImageLink = By.linkText("Images");
    By getWebElements = By.xpath("//a/div/img");

    public GoogleImagesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnImageLink_Google()
    {
        driver.findElement(clickOnImageLink).click();
    }

    public List<WebElement> getWebElements()
    {
        return driver.findElements(getWebElements);
    }
}
