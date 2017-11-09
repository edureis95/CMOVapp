package com.cmov.acme.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.api.model.request.ChangeAccountRequest;
import com.cmov.acme.api.model.response.AccountResponse;
import com.cmov.acme.api.model.response.CheckoutResponse;
import com.cmov.acme.api.service.Account_service;
import com.cmov.acme.api.service.Register_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

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
                    myCalendar = Calendar.getInstance();
                    date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel();
                        }

                    };

                    card_validity.setKeyListener(null);

                    card_validity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            DatePickerDialog dialog = new DatePickerDialog(AccountActivity.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH));
                            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            dialog.show();
                        }
                    });
                    address.setText(response.body().getAddress(), TextView.BufferType.EDITABLE);
                    card_number.setText(response.body().getCard_number(), TextView.BufferType.EDITABLE);
                    card_type.setText(response.body().getCard_type(), TextView.BufferType.EDITABLE);

                    String myFormat = "dd/MM/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                    String oldFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
                    SimpleDateFormat oldsdf = new SimpleDateFormat(oldFormat, Locale.UK);
                    try {
                        card_validity.setText(sdf.format(oldsdf.parse(response.body().getCard_validity()).getTime()), TextView.BufferType.EDITABLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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

        changeButton.setOnClickListener(changeHandler);

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        card_validity.setText(sdf.format(myCalendar.getTime()));
    };


    View.OnClickListener changeHandler = new View.OnClickListener() {
        public void onClick(View v) {
            showProgress(false);
            Account_service account_service = retrofit.create(Account_service.class);

            ChangeAccountRequest request = new ChangeAccountRequest(address.getText().toString(),card_number.getText().toString()
                    ,card_type.getText().toString(),card_validity.getText().toString(),password.getText().toString());

            Call<CheckoutResponse> call = account_service.changeInfo("Bearer " + User.getInstance().getToken(), request);


            call.enqueue(new Callback<CheckoutResponse>() {
                @Override
                public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                    if(response.isSuccessful() ) {
                        if(response.body().getMessage() != null) {
                            showProgress(true);
                            Toast.makeText(AccountActivity.this,"Data changed successfully ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AccountActivity.this,"Invalid data / empty data", Toast.LENGTH_LONG).show();
                        showProgress(true);
                    }
                }

                @Override
                public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                    Toast.makeText(AccountActivity.this,"Unable to connect to server", Toast.LENGTH_LONG).show();
                    showProgress(true);
                }
            });

        }
    };


    private void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.GONE : View.VISIBLE);
        changeButton.setVisibility(show ? View.VISIBLE : View.GONE);
        accountlayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }   
}
