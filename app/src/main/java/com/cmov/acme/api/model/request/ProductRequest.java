package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ricardo on 25-Oct-17.
 */

public class ProductRequest {
    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("bar_code")
    private String bar_code;

    @SerializedName("maker")
    private String maker;

    @SerializedName("model")
    private String model;

    public ProductRequest(String name, int price, String bar_code, String maker, String model) {
        this.name = name;
        this.price = price;
        this.bar_code = bar_code;
        this.maker = maker;
        this.model = model;
    }
}
