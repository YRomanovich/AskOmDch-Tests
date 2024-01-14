package org.askOmDch.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askOmDch.api.ApiRequest;
import org.askOmDch.constants.EndPoint;
import org.askOmDch.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CartApi {
    private Cookies cookies;

    public CartApi(){}

    public CartApi(Cookies cookies){
        this.cookies = cookies;
    }

    public Cookies getCookies(){
        return cookies;
    }

    public Response addToCart(int productID, int quantity){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);

        HashMap<String,Object> formParameters = new HashMap<>();
        formParameters.put("product_sku", "");
        formParameters.put("product_id", productID);
        formParameters.put("quantity", quantity);

        Response response = ApiRequest.post(EndPoint.ADD_TO_CART.url, headers, formParameters, cookies);

        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to add product" + productID +", HTTP Status Code: " +
                    response.getStatusCode());
        }

        this.cookies = response.getDetailedCookies();

        return response;
    }
}
