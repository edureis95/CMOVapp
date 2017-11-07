package com.cmov.acme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cmov.acme.R;

public class AccountActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button changeButton;
    View accountlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        accountlayout =  findViewById(R.id.account_layout);
        changeButton = (Button) findViewById(R.id.change_button);


    }


    private void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.GONE : View.VISIBLE);
        changeButton.setVisibility(show ? View.VISIBLE : View.GONE);
        accountlayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }   
}
