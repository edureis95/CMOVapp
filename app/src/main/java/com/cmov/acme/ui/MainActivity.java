package com.cmov.acme.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmov.acme.R;
import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends AppCompatActivity {

    private Button login_button;
    private Button shop_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = (Button)findViewById(R.id.login_button);
        final Activity activity = this;
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        shop_button = (Button)findViewById(R.id.shop_button);
        shop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });






        /*  LOGIN

        Login_service loginService = retrofit.create(Login_service.class);
        LoginRequest request = new LoginRequest("maurr","cenas");
        Call<LoginResponse> call = loginService.sendLogin(request);


        call.enqueue(new Callback<LoginResponse>() {
                         @Override
                         public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                             if(response.isSuccessful() && response.body().getToken() != null) {
                                 Log.i(TAG, "token = " + response.body().getToken());
                             } else {
                                 Log.i(TAG, "Unsuccessful login");
                             }
                         }

                         @Override
                         public void onFailure(Call<LoginResponse> call, Throwable t) {
                             Log.i(TAG, t.getMessage());
                         }
                     });

    }
    */


        /* REGISTER

        Register_service register_service = retrofit.create(Register_service.class);
        RegisterRequest request = new RegisterRequest("Mauro Rodrigues","maurrea","cenas","123131","britiande","121331","2131313","visa","10/12/1994");
        Call<RegisterResponse> call = register_service.sendRegister(request);


        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful() && response.body().getToken() != null) {
                    Log.i(TAG, "token = " + response.body().getToken());
                } else {
                    Log.i(TAG, "Unsuccessful register");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    */
    }
}
