package com.cmov.acme.api.service;

import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mauro on 25/10/2017.
 */

public interface Login_service {
    @POST("/users/login")
    Call<LoginResponse> sendLogin(@Body LoginRequest body);
}
