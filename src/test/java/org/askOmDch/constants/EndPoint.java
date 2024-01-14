package org.askOmDch.constants;

public enum EndPoint {
    STORE("/store/"),
    ACCOUNT("/account/"),
    CHECKOUT("/checkout/"),
    HOME("/"),
    PRODUCT("/product/"),
    CART("/cart/"),
    ADD_TO_CART("/?wc-ajax=add_to_cart"),
    ACCOUNT_EDIT_BILLING_ADDRESS("/account/edit-address/billing/");

    public final String url;

    EndPoint(String url) {
        this.url = url;
    }
}
