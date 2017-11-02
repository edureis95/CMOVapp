package com.cmov.acme.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.request.ProductRequest;
import com.cmov.acme.api.model.response.LoginResponse;
import com.cmov.acme.api.model.response.ProductResponse;
import com.cmov.acme.api.service.Login_service;
import com.cmov.acme.api.service.Product_service;
import com.cmov.acme.models.Product;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;

import java.util.ArrayList;


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
    private Button addCart;
    private String bar_code;
    private View productlayout;
    private ProgressBar progressBar;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = (TextView) findViewById(R.id.product_name);
        price = (TextView) findViewById(R.id.product_price);
        maker = (TextView) findViewById(R.id.product_maker);
        model = (TextView) findViewById(R.id.product_model);
        productlayout = findViewById(R.id.productlayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar4);


        retrofit = RetrofitSingleton.getInstance();

        Intent intent = getIntent();
        getProductContent(intent.getStringExtra("bar_code"));


        addCart = (Button)findViewById(R.id.button_add_product);
        addCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {   //quando o utilizador clica para comprar, manda produto como resposta
                Intent intent = new Intent();
                if(product != null){
                    intent.putExtra("Product", product);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(ProductActivity.this, "Error adding product", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            name.setText(product.getName());
            price.setText(Integer.toString(product.getPrice()) + "€");
            maker.setText(product.getMaker());
            model.setText(product.getModel());
        }
    };



    public void getProductContent(final String bar_code){
        Product_service productService = retrofit.create(Product_service.class);

        Call<ProductResponse> call = productService.getProduct(bar_code);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()) {
                    product = new Product(response.body().getId(),
                            response.body().getName(),
                            response.body().getPrice(),
                            bar_code,
                            response.body().getMaker(),
                            response.body().getModel()); //cria instancia do produto para mandar como resposta
                    handler.sendEmptyMessage(0); //criar handler para a thread ter menos custo
                    showProgress(false);
                } else {
                    Toast.makeText(ProductActivity.this, "Product not found", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Unable to connect to server", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void showProgress(final boolean show) {
        productlayout.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
