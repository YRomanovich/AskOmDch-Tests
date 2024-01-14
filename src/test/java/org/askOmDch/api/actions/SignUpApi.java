package org.askOmDch.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askOmDch.api.ApiRequest;
import org.askOmDch.constants.EndPoint;
import org.askOmDch.objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;

public class SignUpApi {
    private Cookies cookies;

    public Cookies getCookies(){
        return cookies;
    }

    public String fetchRegisterNonceValueUsingGroovy(){
        Response response = getAccount();
        return response.htmlPath().get("**.findAll { it.@name  == 'woocommerce-register-nonce' }.@value");
    }

    public String fetchRegisterNonceValueUsingJsoup(){
        Response response = getAccount();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-register-nonce");

        assert element != null;
        return element.attr("value");
    }

    private Response getAccount(){
        Cookies cookies = new Cookies();
        Response response = ApiRequest.get(EndPoint.ACCOUNT.url, cookies);

        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }

        return response;
    }

    public Response register(User user){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);

        HashMap<String, Object> formParameters = new HashMap<>();
        formParameters.put("username", user.getUsername());
        formParameters.put("password", user.getPassword());
        formParameters.put("email", user.getEmail());
        formParameters.put("woocommerce-register-nonce", fetchRegisterNonceValueUsingJsoup());
        formParameters.put("register", "Register");

        Response response = ApiRequest.post(EndPoint.ACCOUNT.url, headers, formParameters, cookies);

        if(response.getStatusCode() != 302){
            throw new RuntimeException("Failed to register the account, HTTP Status Code: " + response.getStatusCode());
        }

        this.cookies = response.getDetailedCookies();

        return response;
    }
}
