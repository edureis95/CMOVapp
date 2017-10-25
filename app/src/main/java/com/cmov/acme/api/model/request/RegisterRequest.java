package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 25/10/2017.
 */

public class RegisterRequest {
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("nif")
    private String nif;
    @SerializedName("address")
    private String address;
    @SerializedName("publickey")
    private String publickey;
    @SerializedName("card_number")
    private String card_number;
    @SerializedName("card_type")
    private String card_type;
    @SerializedName("card_validity")
    private String card_validity;


    public RegisterRequest(String name, String username, String password, String nif, String address, String publickey, String card_number, String card_type, String card_validity) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.nif = nif;
        this.address = address;
        this.publickey = publickey;
        this.card_number = card_number;
        this.card_type = card_type;
        this.card_validity = card_validity;
    }
}
