package org.askOmDch.tests;

import io.qameta.allure.*;
import org.askOmDch.api.actions.CartApi;
import org.askOmDch.base.BaseTest;
import org.askOmDch.dataproviders.DataProviders;
import org.askOmDch.objects.Product;
import org.askOmDch.pages.CartPage;
import org.askOmDch.pages.HomePage;
import org.askOmDch.pages.ProductPage;
import org.askOmDch.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Regression suite")
@Feature("Add to cart")
public class AddToCartTest extends BaseTest {

    @Link("https://example.org/")
    @Link(name = "allure", type = "myLinks")
    @TmsLink("12345")
    @Issue("TS-32114")
    @Test(description = "Add product to a Cart from Storage page.")
    @Description("1. Load a product from a products.jason file by ID." +
            "2. Add tha product to a Cart though the UI using Storage page." +
            "3. Check that product is displayed in the cart.")
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);

        CartPage cartPage = new StorePage(getDriver()).load()
                .getProductItem()
                .clickAddToCartBtn(product.getName())
                .clickViewCartLink();

        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Test(description = "Add product to a Cart from Product page")
    @Description("1. Load a product from a products.jason file by ID." +
            "2. Add the product to a Cart through the UI using Product page." +
            "3. Check that product is displayed in the cart.")
    public void addToCartFromProductPage() throws IOException {
        Product product = new Product(1215);
        String productNameSeparatedByDash = product.getName().toLowerCase().replaceAll(" ", "-");

        ProductPage productPage = new ProductPage(getDriver())
                .loadProduct(productNameSeparatedByDash)
                .addToCart();
        Assert.assertTrue(productPage.getAlert().contains("“" + product.getName() +"” has been added to your cart."));
    }

    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = DataProviders.class,
            description = "Add featured product to a Cart.")
    @Description("1. Get a product as input parameter" +
            "2. Add the product to a Cart through the UI using Storage Page. " +
            "3. Check that product is displayed in the cart.")
    public void addToCartFeaturedProducts(Product product) {
        CartPage cartPage = new HomePage(getDriver()).load()
                .getProductItem()
                .clickAddToCartBtn(product.getName())
                .clickViewCartLink();

        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Test(description = "Add 2 products to a Cart from Storage page.")
    @Description("1. Add two predefined products to a cards using Cart API calls. " +
            "2. Checks that 2 products are displayed in the cart.")
    public void addTwoProductsToCartFromStorePage() throws IOException {
        CartApi cartApi = new CartApi();
        CartPage cartPage = new CartPage(getDriver());

        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());

        cartApi.addToCart(1198, 1);
        injectCookiesToBrowser(cartApi.getCookies());

        cartPage.load();
    }
}
