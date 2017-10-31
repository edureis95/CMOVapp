package com.cmov.acme.api.service;

import com.cmov.acme.api.model.response.PastTransactionsResponse;
import com.cmov.acme.api.model.response.ReceiptResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by mauro on 30/10/2017.
 */

public interface Receipts_service {
    @GET("/receipts/receipts")
    Call<ArrayList<ReceiptResponse>> getReceipts(@Header("Authorization") String token);
}
