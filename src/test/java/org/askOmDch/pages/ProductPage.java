package org.askOmDch.pages;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.askOmDch.constants.EndPoint.PRODUCT;

public class ProductPage extends BasePage {
    @FindBy(xpath = "//button[@class='single_add_to_cart_button button alt']")
    private WebElement addToCartBtn;
    @FindBy(css = ".product_title.entry-title")
    private WebElement productTitle;
    @FindBy(css = "div[role='alert']")
    private WebElement alert;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return getText(productTitle);
    }

    @Step("Click add to cart for a product on Product page")
    public ProductPage addToCart(){
        clickElement(addToCartBtn);
        return this;
    }

    public ProductPage loadProduct(String productNameSeparatedByDash){
        load(PRODUCT.url + productNameSeparatedByDash + "/");
        return this;
    }

    @Step("Get alert text")
    public String getAlert(){
        return alert.getText();
    }
}
