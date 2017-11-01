package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 25/10/2017.
 */

public class LoginRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("publickey")
    private String publickey;

    public LoginRequest(String username, String password, String publickey) {
        this.username = username;
        this.password = password;
        this.publickey = publickey;
    }
}
