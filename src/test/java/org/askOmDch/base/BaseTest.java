package org.askOmDch.base;

import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.askOmDch.constants.DriverType;
import org.askOmDch.factory.abstractFactory.DriverManagerAbstract;
import org.askOmDch.factory.abstractFactory.DriverManagerFactoryAbstract;
import org.askOmDch.utils.CookiesUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();

    private void setDriver(WebDriver driver){ this.driver.set(driver); }
    private void setDriverManager(DriverManagerAbstract driverManager) {
        this.driverManager.set(driverManager);
    }

    protected WebDriver getDriver(){return this.driver.get();}
    protected DriverManagerAbstract getDriverManager(){
        return this.driverManager.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser){
//        browser = System.getProperty("browser", browser);
//        if(browser == null) browser = "CHROME";
//        setDriver(new DriverManager().initializeDriver(browser));
//        setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
//        setDriver(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)).getDriver());
        setDriverManager(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));
        getDriverManager().getDriver();
    }

    @AfterMethod
    public synchronized void quitDriver(@Optional String browser, ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE){
            File destination = new File("src" + File.separator +
                    browser + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");

            takeScreenshot(destination);
//            takeScreenshotUsingAShot(destination);
        }
        getDriverManager().getDriver().quit();
    }

    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = new CookiesUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);

        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }

    private void takeScreenshot(File destinationFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, destinationFile);
    }

    private void takeScreenshotUsingAShot(File destFile){
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());

        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
