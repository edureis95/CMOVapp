package com.cmov.acme.api.service;

import com.cmov.acme.api.model.request.ChangeAccountRequest;
import com.cmov.acme.api.model.response.AccountResponse;
import com.cmov.acme.api.model.response.CheckoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mauro on 07/11/2017.
 */

public interface Account_service {
    @GET("/account")
    Call<AccountResponse> getUserInfo(@Header("Authorization") String token);

    @POST("/account")
    Call<CheckoutResponse> changeInfo(@Header("Authorization") String token, @Body ChangeAccountRequest body);
}
