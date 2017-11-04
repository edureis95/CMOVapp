package com.cmov.acme.api.service;

import com.cmov.acme.api.model.response.PrinterResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mauro on 03/11/2017.
 */

public interface Printer_Service {
    @GET("/print/{token}")
    Call<ArrayList<PrinterResponse>> getReceipt(@Path("token") String token);
}
