package org.askOmDch.pages;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.askOmDch.constants.EndPoint.ACCOUNT;
public class AccountPage extends BasePage {
    @FindBy(css = "#username")
    private WebElement usernameFld;
    @FindBy(css = "#password")
    private WebElement passwordFld;
    @FindBy(css = "button[value='Log in']")
    private WebElement loginBtn;
    @FindBy(xpath = "//ul[@class='woocommerce-error']/child::li")
    private WebElement errorMessage;

    public AccountPage(WebDriver driver) {
        super(driver);
    }
    @Step("Log in to the system as {0} user and with {1} password")
    public AccountPage login(String username, String password){
        enterValue(usernameFld, username);
        enterValue(passwordFld, password);
        clickElement(loginBtn);
        return this;
    }

    public AccountPage load(){
        load(ACCOUNT.url);
        return this;
    }
    @Step("Get error's text")
    public String getErrorTxt(){
        return getText(errorMessage);
    }
}
