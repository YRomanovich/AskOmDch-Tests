package org.askOmDch.tests;

import org.askOmDch.base.BaseTest;
import org.askOmDch.objects.BillingAddress;
import org.askOmDch.objects.Product;
import org.askOmDch.objects.User;
import org.askOmDch.pages.CheckoutPage;
import org.askOmDch.pages.HomePage;
import org.askOmDch.pages.StorePage;
import org.askOmDch.pages.CartPage;
import org.askOmDch.utils.ConfigLoader;
import org.askOmDch.utils.JacksonUtils;
import org.testng.Assert;

import java.io.IOException;

public class CheckoutTests extends BaseTest {

//    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json",
                BillingAddress.class);
        Product product = JacksonUtils.deserializeJson("products.json",
                Product.class);

        StorePage storePage = new HomePage(getDriver()).load()
                .getHeader()
                .clickStoreLink()
                .search(product.getName());

        Assert.assertEquals(storePage.getTitle(),"Search results: " + product.getName());

        storePage.getProductItem().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductItem().clickViewCartLink();

        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkoutPage = cartPage.clickCheckoutBtn()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();

        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

//    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json",
                BillingAddress.class);
        User user = new User(ConfigLoader.getInstance().getUsername(),
                ConfigLoader.getInstance().getPassword());
        Product product = new Product(1215);

        StorePage storePage = new HomePage(getDriver()).load()
                .getHeader()
                .clickStoreLink()
                .search(product.getName());

        Assert.assertEquals(storePage.getTitle(),"Search results: “" + product.getName() + "”");

        storePage.getProductItem().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductItem().clickViewCartLink();

        Assert.assertEquals(cartPage.getProductName(), product.getName());

        CheckoutPage checkoutPage = cartPage.clickCheckoutBtn()
                .clickLogInLink()
                .login(user)
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();

        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }
}
