package com.cmov.acme.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.LoginRequest;
import com.cmov.acme.api.model.response.LoginResponse;
import com.cmov.acme.api.service.Login_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.utils.ShowDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private Button login_button;
    private ProgressBar progressBar;
    private EditText password_text;
    private EditText username_text;
    private ShowDialog dialog;
    private View login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ShowDialog();
        login = findViewById(R.id.login);
        retrofit = RetrofitSingleton.getInstance();


        login_button = (Button) findViewById(R.id.loginButton);
        login_button.setOnClickListener(loginHandler);



        password_text = (EditText) findViewById(R.id.password_text);
        username_text = (EditText) findViewById(R.id.username_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



    }

    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Login_service loginService = retrofit.create(Login_service.class);
            LoginRequest request = new LoginRequest(username_text.getText().toString(),password_text.getText().toString());
            Call<LoginResponse> call = loginService.sendLogin(request);
            showProgress(true);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful() && response.body().getToken() != null) {
                        User user =  User.getInstance();
                        user.setToken(response.body().getToken());
                        Intent intent = new Intent(LoginActivity.this, ReceiptsActivity.class);
                        startActivity(intent);
                        //  finish();
                    } else {
                        dialog.showDialog(LoginActivity.this, "Unsuccessful login");
                        showProgress(false);
                    }
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    dialog.showDialog(LoginActivity.this, "Unable to connect to  the server. Try again later.");
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
