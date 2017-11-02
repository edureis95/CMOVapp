package com.cmov.acme.api.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 26/10/2017.
 */

public class ReceiptResponse {

    @SerializedName("id")
    private String receipt_id;
    @SerializedName("receipt_date")
    private String receipt_date;
    @SerializedName("token")
    private String token;

    public ReceiptResponse(String receipt_id, String receipt_date, String token) {
        this.receipt_id = receipt_id;
        this.receipt_date = receipt_date;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getReceipt_id() {
        return receipt_id;
    }
}
