package org.askOmDch.pages;

import org.askOmDch.base.BasePage;
import org.askOmDch.pages.components.Header;
import org.askOmDch.pages.components.ProductItem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.askOmDch.constants.EndPoint.HOME;

public class HomePage extends BasePage {
    private Header header;
    private ProductItem  productItem;
    public final String title = "AskOmDch – Become a Selenium automation expert!";

    public Header getHeader() {
        return header;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public HomePage(WebDriver driver){
        super(driver);
        header = new Header(driver);
        productItem = new ProductItem(driver);
    }

    public HomePage load(){
        load(HOME.url);
        waitLong.until(ExpectedConditions.titleContains(title));
        return this;
    }
}
