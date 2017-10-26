package com.cmov.acme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.request.ProductRequest;
import com.cmov.acme.api.model.response.LoginResponse;
import com.cmov.acme.api.model.response.ProductResponse;
import com.cmov.acme.api.service.Login_service;
import com.cmov.acme.api.service.Product_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private TextView name;
    private TextView price;
    private TextView maker;
    private TextView model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = (TextView) findViewById(R.id.product_name);
        price = (TextView) findViewById(R.id.product_price);
        maker = (TextView) findViewById(R.id.product_maker);

        retrofit = RetrofitSingleton.getInstance();

        Intent intent = getIntent();
        getProductContent(intent.getStringExtra("bar_code"));
    }


    public void getProductContent(String bar_code){
        Product_service productService = retrofit.create(Product_service.class);
        Call<ProductResponse> call = productService.getProduct(bar_code);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                name.setText(response.body().getName());
                price.setText(Integer.toString(response.body().getPrice()));
                maker.setText(response.body().getMaker());
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.i("FAIL", t.toString());
            }
        });
    }

    public void setProductContent(String name, int price, String maker, String model){


    }
}
