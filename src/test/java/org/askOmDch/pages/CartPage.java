package org.askOmDch.pages;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.askOmDch.constants.EndPoint.CART;

public class CartPage extends BasePage {
    @FindBy(css = "")
    private WebElement productName;
    @FindBy(css = "")
    @CacheLookup
    private WebElement checkoutBtn;

    public CartPage(WebDriver driver) { super(driver); }

    public CartPage load(){
        load(CART.url);
        return this;
    }

    public Boolean isLoaded(){
        return waitLong.until(ExpectedConditions.urlContains("/cart"));
    }

    @Step("Get product name")
    public String getProductName(){
        return getText(productName);
    }

    @Step("Click Checkout button")
    public CheckoutPage clickCheckoutBtn(){
        clickElement(checkoutBtn);
        return new CheckoutPage(driver);
    }
}
