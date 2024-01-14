package org.askOmDch.objects;

import org.askOmDch.utils.JacksonUtils;

import java.io.IOException;

public class Product {
    private int id;
    private String name;
    private Boolean featured;
    private int quantity;

    public Product(){}

    public Product(int id) throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json", Product[].class);
        for(Product product: products){
            if(product.getId() == id){
                this.id = id;
                this.name = product.getName();
                this.featured = product.getFeatured();
                this.quantity = product.getQuantity();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Boolean getFeatured() { return featured; }

    public void setFeatured(Boolean featured) { this.featured = featured;}
}
