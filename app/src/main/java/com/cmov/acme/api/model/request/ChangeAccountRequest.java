package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 09/11/2017.
 */

public class ChangeAccountRequest {

    @SerializedName("adress")
    private String address;
    @SerializedName("card_number")
    private String card_number;
    @SerializedName("card_type")
    private String card_type;
    @SerializedName("card_validity")
    private String card_validity;
    @SerializedName("password")
    private String password;


    public ChangeAccountRequest(String address, String card_number, String card_type, String card_validity, String password) {
        this.address = address;
        this.card_number = card_number;
        this.card_type = card_type;
        this.card_validity = card_validity;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getCard_type() {
        return card_type;
    }

    public String getCard_validity() {
        return card_validity;
    }

    public String getPassword() {
        return password;
    }
}
