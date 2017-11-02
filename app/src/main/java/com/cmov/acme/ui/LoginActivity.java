package com.cmov.acme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.response.LoginResponse;
import com.cmov.acme.api.service.Login_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.utils.Keygenerator;
import com.cmov.acme.utils.ShowDialog;

import java.security.KeyPair;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private Button login_button;
    private ProgressBar progressBar;
    private EditText password_text;
    private EditText username_text;
    private View login;
    private String publicKey;
    private String privateKey;
    private KeyPair kp;
    private Button register_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        retrofit = RetrofitSingleton.getInstance();


        login_button = (Button) findViewById(R.id.loginButton);
        login_button.setOnClickListener(loginHandler);

        register_button = (Button) findViewById(R.id.registerButton);
        register_button.setOnClickListener(registerHandler);


        password_text = (EditText) findViewById(R.id.password_text);
        username_text = (EditText) findViewById(R.id.username_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    View.OnClickListener registerHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };


    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Login_service loginService = retrofit.create(Login_service.class);
            Keygenerator gen = new Keygenerator();
            publicKey = gen.getPublicKey();
            privateKey = gen.getPrivateKey();
            kp = gen.getKeyPair();


            LoginRequest request = new LoginRequest(username_text.getText().toString(),password_text.getText().toString(),publicKey);
            Call<LoginResponse> call = loginService.sendLogin(request);
            showProgress(true);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful() && response.body().getToken() != null) {
                        User user =  User.getInstance();
                        user.setToken(response.body().getToken());
                        user.setKeyPair(publicKey, privateKey);
                        user.setKp(kp);
                        Intent intent = new Intent(LoginActivity.this, ShoppingCartActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this,"Invalid credentials", Toast.LENGTH_LONG).show();
                        showProgress(false);
                    }
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Unable to connect to server", Toast.LENGTH_LONG).show();
                    showProgress(false);
                }
            });

        }
    };

    private void showProgress(final boolean show) {
        login.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress(false);
    }
}
