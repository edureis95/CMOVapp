package com.cmov.acme.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.cmov.acme.R;
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
    private ShowDialog dialog;
    private ArrayList<ReceiptResponse> listaResposta;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        listaResposta = new ArrayList<ReceiptResponse>();
        dialog = new ShowDialog();
        listView = (ListView) findViewById(R.id.listview);

        retrofit = RetrofitSingleton.getInstance();
        Receipts_service receipts_service = retrofit.create(Receipts_service.class);
        Call<ArrayList<ReceiptResponse>> call = receipts_service.getReceipts("Bearer "+ User.getInstance().getToken());

        call.enqueue(new Callback<ArrayList<ReceiptResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ReceiptResponse>> call, Response<ArrayList<ReceiptResponse>> response) {
                if(response.isSuccessful() ) {
                    listaResposta = response.body();
                    ReceiptAdapter adapter = new ReceiptAdapter(ReceiptsActivity.this, listaResposta);
                    listView.setAdapter(adapter);
                } else {
                    dialog.showDialog(ReceiptsActivity.this, response.toString());
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ReceiptResponse>>  call, Throwable t) {
                dialog.showDialog(ReceiptsActivity.this, t.getMessage().toString());

            }
        });

    }
}
