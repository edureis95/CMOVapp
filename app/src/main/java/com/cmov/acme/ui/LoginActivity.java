package com.cmov.acme.ui;

import android.content.DialogInterface;
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

    private final static String TAG = "RESPONSE";
    private Retrofit retrofit;

    private Button login_button;
    private ProgressBar progressBar;
    private EditText password_text;
    private EditText username_text;
    private ShowDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ShowDialog();

        retrofit = RetrofitSingleton.getInstance();


        login_button = (Button) findViewById(R.id.loginButton);
        login_button.setOnClickListener(loginHandler);



        password_text = (EditText) findViewById(R.id.password_text);
        username_text = (EditText) findViewById(R.id.username_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


    }

    View.OnClickListener loginHandler = new View.OnClickListener() {
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            Login_service loginService = retrofit.create(Login_service.class);
            LoginRequest request = new LoginRequest(username_text.getText().toString(),password_text.getText().toString());
            Call<LoginResponse> call = loginService.sendLogin(request);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful() && response.body().getToken() != null) {
                        User user =  User.getInstance();
                        user.setToken(response.body().getToken());
                        dialog.showDialog(LoginActivity.this,user.getToken());
                    } else {
                        dialog.showDialog(LoginActivity.this, "Unsuccessful login");
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    dialog.showDialog(LoginActivity.this, "Unable to connect to  the server. Try again later.");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    };

}
