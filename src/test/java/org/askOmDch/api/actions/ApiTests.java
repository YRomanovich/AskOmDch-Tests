package org.askOmDch.api.actions;

import org.askOmDch.objects.User;
import org.askOmDch.utils.FakerUtils;

public class ApiTests {
    public static void main(String[] args){
        String username = "user" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demo").
                setEmail(username + "@test.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        System.out.println("REGISTER COOKIES: " + signUpApi.getCookies());
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(1215, 1);
        System.out.println("CART COOKIES: " + cartApi.getCookies());
    }
}

