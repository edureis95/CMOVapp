package com.cmov.acme.api.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 03/11/2017.
 */

public class PrinterResponse {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    @SerializedName("receipt_date")
    private String receipt_date;

    @SerializedName("name")
    private String name;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("adress")
    private String address;

    @SerializedName("nif")
    private String nif;

    @SerializedName("bar_code")
    private String bar_code;

    @SerializedName("maker")
    private String maker;

    @SerializedName("model")
    private String model;


    public PrinterResponse(int quantity, double price, String receipt_date, String name, String user_name, String address, String nif, String bar_code, String maker, String model) {
        this.quantity = quantity;
        this.price = price;
        this.receipt_date = receipt_date;
        this.name = name;
        this.user_name = user_name;
        this.address = address;
        this.nif = nif;
        this.bar_code = bar_code;
        this.maker = maker;
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAddress() {
        return address;
    }

    public String getNif() {
        return nif;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }
}
