package com.cmov.acme.api.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 25/10/2017.
 */

public class RegisterResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;


    public RegisterResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
