package org.askOmDch.pages;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.askOmDch.objects.Product;
import org.askOmDch.pages.components.ProductItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

import static org.askOmDch.constants.EndPoint.STORE;

public class StorePage extends BasePage {
    @FindBy(id = "woocommerce-product-search-field-0")
    @CacheLookup
    private WebElement searchFld;
    @FindBy(css = "button[value='Search']")
    @CacheLookup
    private WebElement searchBtn;
    @FindBy(css = ".woocommerce-products-header__title.page-title")
    private WebElement title;
    @FindBy(css = ".woocommerce-info")
    private WebElement infoTxt;

    private ProductItem productItem;

    public StorePage(WebDriver driver){
        super(driver);
        productItem = new ProductItem(driver);
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public Boolean isLoaded(){
        return waitLong.until(ExpectedConditions.urlContains("/store"));
    }

    @Step("Enter search value {0} in Search field on Store page")
    public StorePage enterValueInSearchFld(String value){
        enterValue(searchFld, value);
        return this;
    }

    @Step("Click Search button on Store page")
    public StorePage clickSearchBtn(){
        clickElement(searchBtn);
        return this;
    }

    public StorePage load(){
        load(STORE.url);
        return this;
    }

    @Step("Search for {0} product from Store page")
    public ProductPage searchProduct(String product){
        enterValueInSearchFld(product).clickSearchBtn();
        return new ProductPage(driver);
    }

    @Step("Search for {0} product on Store page")
    public StorePage search(String txt){
        enterValueInSearchFld(txt).clickSearchBtn();
        return this;
    }

    @Step("Get title from Storage page")
    public String getTitle(){
        return getText(title);
    }

    @Step("Navigate to a product with ID {0} from Storage page")
    public ProductPage navigateToTheProduct(Integer id) throws IOException {
        waitLong.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[normalize-space()='"+
                new Product(id).getName() + "']"))).click();
        return new ProductPage(driver);
    }

    @Step("Get product's information from Storage page")
    public String getInfo(){
        return getText(infoTxt);
    }
}
