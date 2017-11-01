package com.cmov.acme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.adapters.ReceiptAdapter;
import com.cmov.acme.api.model.response.ReceiptResponse;
import com.cmov.acme.api.service.Receipts_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.utils.ShowDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReceiptsActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ArrayList<ReceiptResponse> listaResposta;
    private ListView listView;
    private ProgressBar progressBar;
    private View receipts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        receipts = findViewById(R.id.transactionsview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        listaResposta = new ArrayList<ReceiptResponse>();
        listView = (ListView) findViewById(R.id.listview);
        retrofit = RetrofitSingleton.getInstance();
        Receipts_service receipts_service = retrofit.create(Receipts_service.class);
        Call<ArrayList<ReceiptResponse>> call = receipts_service.getReceipts("Bearer "+ User.getInstance().getToken());

        showProgress(true);
        call.enqueue(new Callback<ArrayList<ReceiptResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ReceiptResponse>> call, Response<ArrayList<ReceiptResponse>> response) {
                if(response.isSuccessful() ) {
                    listaResposta = response.body();
                    ReceiptAdapter adapter = new ReceiptAdapter(ReceiptsActivity.this, listaResposta);
                    listView.setAdapter(adapter);
                    showProgress(false);
                } else {
                    Toast.makeText(ReceiptsActivity.this,response.message(), Toast.LENGTH_LONG).show();
                    showProgress(false);
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ReceiptResponse>>  call, Throwable t) {
                Toast.makeText(ReceiptsActivity.this,"Unable to connect to server", Toast.LENGTH_LONG).show();
                showProgress(false);
                finish();

            }
        });

    }

    private void showProgress(final boolean show) {
        receipts.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
