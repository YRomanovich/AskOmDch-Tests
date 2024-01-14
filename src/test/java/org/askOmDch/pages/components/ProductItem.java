package org.askOmDch.pages.components;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.askOmDch.objects.Product;
import org.askOmDch.pages.CartPage;
import org.askOmDch.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class ProductItem extends BasePage{
    @FindBy(css = "")
    private WebElement viewCartLink;
    public ProductItem(WebDriver driver) {
        super(driver);
    }

    private By getAddToCartBtnElement(String productName){
        return By.cssSelector("Add " + productName + " to cart");
    }
    @Step("Click Add to Cart button for product {0}")
    public ProductItem clickAddToCartBtn(String productName){
        By addToCartBtn = getAddToCartBtnElement(productName);
        waitLong.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }

    @Step("Click View Cart link")
    public CartPage clickViewCartLink(){
        clickElement(viewCartLink);
        return new CartPage(driver);
    }

    @Step("Open product's page for product {0}")
    public ProductPage navigateToTheProduct(int id) throws IOException {
        driver.findElement(By.xpath("//h2[normalize-space()='" +
                new Product(id).getName() + "']")).click();

        return new ProductPage(driver);
    }
}
