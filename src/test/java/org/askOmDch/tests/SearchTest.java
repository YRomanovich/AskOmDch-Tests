package org.askOmDch.tests;


import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.askOmDch.base.BaseTest;
import org.askOmDch.objects.Product;
import org.askOmDch.pages.ProductPage;
import org.askOmDch.pages.StorePage;
import org.askOmDch.utils.FakerUtils;
import org.askOmDch.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Regression suite")
@Feature("Search")
public class SearchTest extends BaseTest {

    @Test(description = "Search a product with partial matching.")
    @Description("1. Load a product from products.json." +
            "2. Search for the product using partial matching on Storage page." +
            "3. Check that search results contain matching products.")
    public void searchWithPartialMatch() throws IOException {
        Product product = JacksonUtils.deserializeJson("products.json",
                Product.class);
        StorePage storePage = new StorePage(getDriver()).load()
                .search(product.getName());

        Assert.assertEquals(storePage.getTitle(),"Search results: " + product.getName());
    }

    @Test(description = "Search a product with exact matching.")
    @Description("1. Load a product from products.json." +
            "2. Search for the product using exact matching on Storage page." +
            "3. Check that search results contain matching products.")
    public void searchWithExactMatch() throws IOException {
        Product product = new Product(1215);
        ProductPage productPage = new StorePage(getDriver()).load()
                .searchProduct(product.getName());
        Assert.assertEquals(productPage.getProductTitle(),product.getName());
    }

    @Test(description = "Search for non existing product.")
    @Description("1. Generate fake product name." +
            "2. Search for the product on Storage page." +
            "3. Check that search results are empty.")
    public void SearchForNonExistingProduct() {
        StorePage storePage = new StorePage(getDriver()).load()
                .search(new FakerUtils().generateRandomName());
        Assert.assertEquals(storePage.getInfo(),"No products were found matching your selection.");
    }
}
