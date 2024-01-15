package org.askOmDch.factory.abstractFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FireFoxDriverManagerAbstract extends DriverManagerAbstract{

    @Override
    protected void startDriver() {
        WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
        driver =  new FirefoxDriver();
        driver.manage().window().maximize();
    }
}
