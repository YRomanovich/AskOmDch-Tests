package org.askOmDch.base;

import io.qameta.allure.Step;
import org.askOmDch.constants.Timeouts;
import org.askOmDch.utils.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait waitLong;
    protected WebDriverWait waitShort;

    public BasePage(WebDriver driver){
        this.driver = driver;
        waitLong = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.LONG.timeout));
        waitShort = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.SHORT.timeout));
        PageFactory.initElements(driver, this);
    }

    public void load(String endPoint){
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
    }

    public void waitForSpinnerToDisappear(By element){
        List<WebElement> waitSpinners = driver.findElements(element);

        if(!waitSpinners.isEmpty()){
            waitLong.until(ExpectedConditions.invisibilityOfAllElements(waitSpinners));
        }
    }

    public WebElement waitElementToBeClickable(WebElement element){
        return waitLong.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement getElement(WebElement element){
        return waitShort.until(ExpectedConditions.visibilityOf(element));
    }

    public String getText(WebElement element){
        return waitShort.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public void enterValue(WebElement element, String value){
        waitShort.until(ExpectedConditions.visibilityOf(element)).sendKeys(value);
    }

    public void clickElement(WebElement element){
        waitLong.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
