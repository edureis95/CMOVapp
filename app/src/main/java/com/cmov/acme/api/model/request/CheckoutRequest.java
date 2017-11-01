package com.cmov.acme.api.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.FieldMap;

/**
 * Created by ricardoduarte on 01/11/17.
 */

public class CheckoutRequest {

    @SerializedName("product_id")
    private int product_id;
    @SerializedName("quantity")
    private int quantity;

    public CheckoutRequest(int id, int quantity){
        this.product_id = id;
        this.quantity = quantity;
    }
}
