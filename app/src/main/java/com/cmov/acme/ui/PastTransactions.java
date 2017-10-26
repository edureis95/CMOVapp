package com.cmov.acme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmov.acme.R;
import com.cmov.acme.api.model.response.PastTransactionsResponse;
import com.cmov.acme.api.service.PastTransactions_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.utils.ShowDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PastTransactions extends AppCompatActivity {
    private Retrofit retrofit;
    private ShowDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_transactions);

        dialog = new ShowDialog();

        retrofit = RetrofitSingleton.getInstance();
        PastTransactions_service transactions_service = retrofit.create(PastTransactions_service.class);
        Call<List<PastTransactionsResponse>> call = transactions_service.getPastTransactions("Bearer "+ User.getInstance().getToken());

        call.enqueue(new Callback<List<PastTransactionsResponse>> () {
            @Override
            public void onResponse(Call<List<PastTransactionsResponse>> call, Response<List<PastTransactionsResponse>> response) {
                if(response.isSuccessful() ) {
                    dialog.showDialog(PastTransactions.this, response.body().get(2).getName()); // TODO mostrar no ecra as cenas
                } else {
                    dialog.showDialog(PastTransactions.this, response.toString());
                }
            }


            @Override
            public void onFailure(Call<List<PastTransactionsResponse>>  call, Throwable t) {
                dialog.showDialog(PastTransactions.this, t.getMessage().toString());

            }
        });
    }
}
