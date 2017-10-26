package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ricardo on 25-Oct-17.
 */

public class ProductRequest {
    @SerializedName("bar_code")
    private String bar_code;

    public ProductRequest(String bar_code) {
        this.bar_code = bar_code;
    }
}
