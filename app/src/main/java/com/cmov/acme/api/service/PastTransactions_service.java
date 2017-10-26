package com.cmov.acme.api.service;

import com.cmov.acme.api.model.response.PastTransactionsResponse;
import com.cmov.acme.singletons.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by mauro on 26/10/2017.
 */

public interface PastTransactions_service {
    @GET("/receipts")
    Call<List<PastTransactionsResponse>> getPastTransactions(@Header("Authorization") String token);
}
