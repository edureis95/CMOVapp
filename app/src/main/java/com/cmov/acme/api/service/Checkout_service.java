package com.cmov.acme.api.service;

import com.cmov.acme.api.model.request.CheckoutRequest;
import com.cmov.acme.api.model.response.CheckoutResponse;
import com.cmov.acme.api.model.response.RegisterResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ricardoduarte on 01/11/17.
 */

public interface Checkout_service {
    @POST("/purchase")
    Call<CheckoutResponse> sendPurchase(@Header("Signature") String sign, @Header("Authorization") String token, @Body List<CheckoutRequest> body);
}
