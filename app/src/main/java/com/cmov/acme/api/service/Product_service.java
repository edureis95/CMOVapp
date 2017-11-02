package com.cmov.acme.api.service;

import com.cmov.acme.api.model.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ricardo on 24-Oct-17.
 */

public interface Product_service {
    @GET("/products/{bar_code}")
    Call<ProductResponse> getProduct(@Path("bar_code") String product);
}
