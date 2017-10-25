package com.cmov.acme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.Product;
import com.cmov.acme.api.service.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "RESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Client client = retrofit.create(Client.class);

        Call<Product> call = client.getProduct("61234567890"); //codigo de barras de um produto

        call.enqueue(new Callback<Product>(){
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.i(TAG, response.toString());
                Log.i(TAG, "GREAT");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.i(TAG, "ERROR");
            }
        });

    }
}
