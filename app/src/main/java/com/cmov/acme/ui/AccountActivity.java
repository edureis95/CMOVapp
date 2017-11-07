package com.cmov.acme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.response.AccountResponse;
import com.cmov.acme.api.service.Account_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button changeButton;
    private View accountlayout;
    private Retrofit retrofit;
    private EditText password;
    private EditText passwordRepeat;
    private EditText address;
    private EditText card_number;
    private EditText card_type;
    private EditText card_validity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        accountlayout =  findViewById(R.id.account_layout);
        changeButton = (Button) findViewById(R.id.change_button);

        password = (EditText) findViewById(R.id.input_password);
        passwordRepeat = (EditText) findViewById(R.id.input_password_repeat);
        address = (EditText) findViewById(R.id.input_address);
        card_number = (EditText) findViewById(R.id.input_creditcardnumber);
        card_type = (EditText) findViewById(R.id.input_creditcardtype);
        card_validity = (EditText) findViewById(R.id.input_creditcardvalidity);

        retrofit = RetrofitSingleton.getInstance();

        Account_service productService = retrofit.create(Account_service.class);

        Call<AccountResponse> call = productService.getUserInfo("Bearer " + User.getInstance().getToken());

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if(response.isSuccessful()) {
                    address.setText(response.body().getAddress(), TextView.BufferType.EDITABLE);
                    card_number.setText(response.body().getCard_number(), TextView.BufferType.EDITABLE);
                    card_type.setText(response.body().getCard_type(), TextView.BufferType.EDITABLE);
                    card_validity.setText(response.body().getCard_validity(), TextView.BufferType.EDITABLE);

                    showProgress(true);
                } else {
                    Toast.makeText(AccountActivity.this, "User not found", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(AccountActivity.this,"Unable to connect to server", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    private void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.GONE : View.VISIBLE);
        changeButton.setVisibility(show ? View.VISIBLE : View.GONE);
        accountlayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }   
}
