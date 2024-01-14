package org.askOmDch.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.askOmDch.api.ApiRequest;
import org.askOmDch.constants.CountryCodes;
import org.askOmDch.constants.EndPoint;
import org.askOmDch.objects.BillingAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;

public class BillingApi {
    private Cookies cookies;

    public BillingApi(Cookies cookies){
        this.cookies = cookies;
    }

    public Response addBillingAddress(BillingAddress billingAddress){
        Header header = new Header("content-type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(header);

        HashMap<String, Object> formParameters = new HashMap<>();

        formParameters.put("billing_first_name", billingAddress.getFirstName());
        formParameters.put("billing_last_name",billingAddress.getLastName());
        formParameters.put("billing_country", CountryCodes.getCountryCode(billingAddress.getCountry()));
        formParameters.put("billing_address_1",billingAddress.getAddressLineOne());
        formParameters.put("billing_city",billingAddress.getCity());
        formParameters.put("billing_state",CountryCodes.getStateCode(billingAddress.getState()));
        formParameters.put("billing_postcode",billingAddress.getPostalCode());
        formParameters.put("billing_company",billingAddress.getCompany());
        formParameters.put("billing_phone",billingAddress.getPhone());
        formParameters.put("woocommerce-edit-address-nonce",fetchEditBillingAddressNonceValueUsingJsoup());
        formParameters.put("action","edit_address");
        formParameters.put("save_address","Save address");
        formParameters.put("billing_email",billingAddress.getEmail());

        Response response = ApiRequest.post(
                EndPoint.ACCOUNT_EDIT_BILLING_ADDRESS.url, headers, formParameters, cookies);

        if(response.getStatusCode()!=302)
        {
            throw new RuntimeException("Failed to edit the address of the account -" +response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }

    private String fetchEditBillingAddressNonceValueUsingJsoup(){
        Response response = getBillingAddress();
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-edit-address-nonce");
        assert element != null;
        return element.attr("value");
    }

    private Response getBillingAddress(){
        Response response = ApiRequest.get(EndPoint.ACCOUNT_EDIT_BILLING_ADDRESS.url, cookies);
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }
}
