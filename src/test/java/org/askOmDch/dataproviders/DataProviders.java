package org.askOmDch.dataproviders;

import org.askOmDch.objects.Product;
import org.askOmDch.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Arrays;

public class DataProviders {

    @DataProvider(name = "getFeaturedProducts", parallel = true)
    public Object[] getFeaturedProducts() throws IOException {
        Product[] allProducts = JacksonUtils.deserializeJson("products.json", Product[].class);

        return Arrays.stream(allProducts)
                .filter(product -> product.getFeatured().equals(Boolean.TRUE))
                .toArray();
    }
}
