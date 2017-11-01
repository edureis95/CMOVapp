package com.cmov.acme.api.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricardoduarte on 01/11/17.
 */

public class CheckoutResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public CheckoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
