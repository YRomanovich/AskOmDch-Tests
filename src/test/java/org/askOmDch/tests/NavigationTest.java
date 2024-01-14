package org.askOmDch.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.askOmDch.base.BaseTest;
import org.askOmDch.objects.Product;
import org.askOmDch.pages.HomePage;
import org.askOmDch.pages.ProductPage;
import org.askOmDch.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Regression suite")
@Feature("Re-designed navigation")
public class NavigationTest extends BaseTest {

    @Story("Re-design navigation between pages")
    @Test(description = "Check navigation from Home page to Store page using main menu.")
    @Description("1. Click store  link on a Home page." +
            "2. Check that Storage page is opened.")
    public void NavigationFromHomeToSearchUsingMainMenu(){
        StorePage storePage = new HomePage(getDriver()).load()
                .getHeader()
                .clickStoreLink();

        Assert.assertEquals(storePage.getTitle(),"Store");
    }

    @Story("Re-design navigation between pages")
    @Test(description = "Check navigation from Store page to Product page by clicking on a product.")
    @Description("1. Open Storage page." +
            "2. Click on a product on the Storage page." +
            "3. Check that product page for selected product is opened.")
    public void NavigateFromStoreToTheProduct() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new StorePage(getDriver()).load()
                .navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(), product.getName());
    }

    @Story("Re-design navigation between pages")
    @Test(description = "Check navigation from Home page to featured Product page.")
    @Description("1. Click on a product on a Home page." +
            "2. Check that product page for selected product is opened.")
    public void NavigateFromHomeToTheFeaturedProduct() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new HomePage(getDriver()).load()
                .getProductItem()
                .navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle(), product.getName());
    }
}
