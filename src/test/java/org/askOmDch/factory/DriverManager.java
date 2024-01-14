package org.askOmDch.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.askOmDch.constants.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    public WebDriver initializeDriver(String browser){
        WebDriver driver;

        driver = switch (DriverType.valueOf(browser)) {
            case CHROME -> {
                WebDriverManager.chromedriver().cachePath("Drivers").setup();
                yield new ChromeDriver();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
                yield new FirefoxDriver();
            }
            default -> {
                throw new IllegalStateException("Invalid browser name: " + browser);
            }
        };

        driver.manage().window().maximize();

        return driver;
    }
}
