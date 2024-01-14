package org.askOmDch.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.askOmDch.api.actions.CartApi;
import org.askOmDch.api.actions.SignUpApi;
import org.askOmDch.base.BaseTest;
import org.askOmDch.objects.Product;
import org.askOmDch.objects.User;
import org.askOmDch.pages.AccountPage;
import org.askOmDch.pages.CheckoutPage;
import org.askOmDch.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Regression suite")
@Feature("Log in")
public class LogInTest extends BaseTest {
    private StringBuilder username;

    @BeforeTest
    public void setUp(){
        username = new StringBuilder("user" + new FakerUtils().generateRandomNumber());
    }

    @Test(description = "Log in during checkout process.")
    @Description("1. Load a product from a products.jason file by ID." +
            "2. Register a test user using SignUp API" +
            "3. Add the product to a cart using Cart API." +
            "4. Log in to a system as test user from Checkout page" +
            "5. Check that Checkout page contains the product for logged in user")
    public void loginDuringCheckout() throws IOException {
        Product product = new Product(1215);

        User user = new User().setUsername(username.toString())
                .setPassword("demo")
                .setEmail(username.toString() + "@test.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        cartApi.addToCart(product.getId(), product.getQuantity());

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());

        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load()
                .clickLogInLink()
                .login(user);

        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }

    @Test(description = "Try to log in using incorrect password.")
    @Description("1. Register a test user using SignUp API." +
            "2. Try to log in as the test user using Account page." +
            "3. Check that error message is displayed on Checkout page.")
    public void shouldNotLoginWithAnInvalidPassword(){
        User user = new User(username.toString(), "demopwd", username.toString() + "@askomdch.com");
        new SignUpApi().register(user);

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(user.getUsername(), "invalidPassword");
        Assert.assertEquals(accountPage.getErrorTxt(), "Error: The password you entered for the username "
                + user.getUsername() + " is incorrect. Lost your password?");
    }

    @Test(description = "Try to log in using non existing user.")
    @Description("1. Generate test user that is not registered in the system." +
            "2. Try to log in as the test user using Account page." +
            "3. Check that error message is displayed on Checkout page.")
    public void shouldNotLoginWithANonExistingUser(){
        User user = new User(username.toString(), "demopwd", username.toString() + "@askomdch.com");

        AccountPage accountPage = new AccountPage(getDriver()).load();
        accountPage.login(user.getUsername(), "demopwd");
        Assert.assertEquals(accountPage.getErrorTxt(), "Error: The username " + user.getUsername() +
                " is not registered on this site." +
                " If you are unsure of your username, try your email address instead.");
    }
}
