package org.askOmDch.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FIreFoxDriverManager implements DriverManagerInterface{
    @Override
    public WebDriver createDriver() {
        WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
        WebDriver driver =  new FirefoxDriver();
        driver.manage().window().maximize();

        return driver;
    }
}