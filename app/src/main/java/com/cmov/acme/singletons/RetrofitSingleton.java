package com.cmov.acme.singletons;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mauro on 26/10/2017.
 */

public class RetrofitSingleton {
    private static Retrofit instance = null;
    protected RetrofitSingleton() {
        // Exists only to defeat instantiation.
    }
    public static Retrofit getInstance() {
        if(instance == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000")
                    .addConverterFactory(GsonConverterFactory.create());

            instance = builder.build();
        }
        return instance;
    }
}
