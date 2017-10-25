package com.cmov.acme.api.service;

import com.cmov.acme.api.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ricardo on 24-Oct-17.
 */

public interface Client_service {
    @GET("/products/{product_barcode}")
    Call<Product> getProduct(@Path("product_barcode") String product);
}
