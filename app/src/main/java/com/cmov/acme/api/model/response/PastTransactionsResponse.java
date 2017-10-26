package com.cmov.acme.api.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 26/10/2017.
 */

public class PastTransactionsResponse {
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("receipt_date")
    private String receipt_date;
    @SerializedName("name")
    private String name;
    @SerializedName("bar_cod")
    private String bar_cod;
    @SerializedName("maker")
    private String maker;
    @SerializedName("model")
    private String model;

    public PastTransactionsResponse(String quantity, String price, String receipt_date, String name, String bar_cod, String maker, String model) {
        this.quantity = quantity;
        this.price = price;
        this.receipt_date = receipt_date;
        this.name = name;
        this.bar_cod = bar_cod;
        this.maker = maker;
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getName() {
        return name;
    }

    public String getBar_cod() {
        return bar_cod;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }
}
