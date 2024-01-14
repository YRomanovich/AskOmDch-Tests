package org.askOmDch.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.Cookies;
import org.askOmDch.api.actions.BillingApi;
import org.askOmDch.api.actions.CartApi;
import org.askOmDch.api.actions.SignUpApi;
import org.askOmDch.base.BaseTest;
import org.askOmDch.objects.BillingAddress;
import org.askOmDch.objects.Product;
import org.askOmDch.objects.User;
import org.askOmDch.pages.CheckoutPage;
import org.askOmDch.utils.FakerUtils;
import org.askOmDch.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Regression suite")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {
    private CheckoutPage checkoutPage;
    private BillingAddress billingAddress;
    private Product product;
    private CartApi cartApi;

    @BeforeTest
    public void setUp() throws IOException {
        checkoutPage = new CheckoutPage(getDriver()).load();
        billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json",
                BillingAddress.class);
        product = new Product(1215);
    }

    @Test(description = "Guest checkout (without login) using direct bank transfer")
    @Description("1. Add a product from products.json file to a cart using Cart API. " +
            "2. Fill in form on Checkout page for direct bank transfer and place an order. " +
            "3. Check notification about successful order placement on Checkout page.")
    public void guestCheckoutUsingDirectBankTransfer(){
        cartApi = new CartApi();
        cartApi.addToCart(product.getId(), product.getQuantity());
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();

        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

    @Test(description = "Checkout as logged in user using direct bank transfer.")
    @Description("1. Register a test user and log in using SignIn API. " +
            "2. Add a product from products.json file to a cart using Cart API. " +
            "3. Fill in form on Checkout page for direct bank transfer and place an order. " +
            "4. Check notification about successful order placement on Checkout page. ")
    public void loginAndCheckoutUsingDirectBankTransfer(){
        String username = "user" + new FakerUtils().generateRandomNumber();

        User user = new User().setUsername(username)
                .setPassword("demo")
                .setEmail(username + "@test.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(product.getId(), product.getQuantity());

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .clickPlaceOrder();

        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

    @Test(description = "Guest checkout using cash on delivery.")
    @Description("1. Add a product from products.json file to a cart using Cart API. " +
            "2. Fill in form on Checkout page for cash on delivery and place an order. " +
            "3. Check notification about successful order placement on Checkout page.")
    public void GuestCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json",
                BillingAddress.class);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .setBillingAddress(billingAddress)
                .selectCashOnDeliveryTransfer()
                .clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

    @Test(description = "Checkout as logged in user using cash on delivery.")
    @Description("1. Register a test user and log in using SignIn API. " +
            "2. Add a product from products.json file to a cart using Cart API. " +
            "3. Fill in form on Checkout page for cash on delivery and place an order. " +
            "4. Check notification about successful order placement on Checkout page.")
    public void LoginAndCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User(username, "demopwd", username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriverManager().getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load();
        checkoutPage.setBillingAddress(billingAddress)
                .selectCashOnDeliveryTransfer()
                .clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

    @Test(description = "Checkout as logged in user with billing address using direct bank transfer.")
    @Description("1. Register a test user and log in using SignIn API. " +
            "2. Add a product from products.json file to a cart using Cart API. " +
            "3. Add billing address from MyBillingAddress.json for signed in user using Billing API" +
            "4. Fill in form on Checkout page for direct bank transfer and place an order. " +
            "5. Check notification about successful order placement on Checkout page. ")
    public void CheckoutWithAnAccountHavingBillingAddress() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json",
                BillingAddress.class);
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User(username, "demopwd", username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        BillingApi billingApi = new BillingApi(signUpApi.getCookies());
        billingApi.addBillingAddress(billingAddress);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriverManager().getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load();
        checkoutPage.selectDirectBankTransfer().clickPlaceOrder();
        Assert.assertEquals(checkoutPage.getSuccessNotice(), "Thank you. Your order has been received.");
    }

}
