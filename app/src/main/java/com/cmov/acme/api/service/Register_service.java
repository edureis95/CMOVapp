package com.cmov.acme.api.service;

import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.request.RegisterRequest;
import com.cmov.acme.api.model.response.LoginResponse;
import com.cmov.acme.api.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mauro on 25/10/2017.
 */

public interface Register_service {
    @POST("/users/register")
    Call<RegisterResponse> sendRegister(@Body RegisterRequest body);
}
