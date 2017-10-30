package com.cmov.acme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.cmov.acme.R;
import com.cmov.acme.api.model.response.PastTransactionsResponse;
import com.cmov.acme.api.service.PastTransactions_service;
import com.cmov.acme.singletons.RetrofitSingleton;
import com.cmov.acme.singletons.User;
import com.cmov.acme.utils.ShowDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PastTransactions extends AppCompatActivity {
    private Retrofit retrofit;
    private ShowDialog dialog;
    private ArrayList<PastTransactionsResponse> listaResposta;
    private ListView listView;
    private String id;
    private TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_transactions);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }
        dialog = new ShowDialog();

        listaResposta = new ArrayList<PastTransactionsResponse>();

        listView = (ListView) findViewById(R.id.listview);

        TextView name = (TextView) findViewById(R.id.text_compra);
        name.setText("Transaction nº " + id);

        totalPrice = (TextView) findViewById(R.id.text_price);


        retrofit = RetrofitSingleton.getInstance();
        PastTransactions_service transactions_service = retrofit.create(PastTransactions_service.class);
        Call<ArrayList<PastTransactionsResponse>> call = transactions_service.getPastTransactions("Bearer "+ User.getInstance().getToken(),id);

        call.enqueue(new Callback<ArrayList<PastTransactionsResponse>> () {
            @Override
            public void onResponse(Call<ArrayList<PastTransactionsResponse>> call, Response<ArrayList<PastTransactionsResponse>> response) {
                if(response.isSuccessful() ) {
                    listaResposta = response.body();
                    PastTransactionsAdapter adapter = new PastTransactionsAdapter(PastTransactions.this, listaResposta);
                    listView.setAdapter(adapter);

                    double price = 0;
                    for(PastTransactionsResponse r : listaResposta) {
                        price += Double.parseDouble(r.getPrice())*Double.parseDouble(r.getQuantity());
                    }
                    totalPrice.setText("Total Price: " + price+"€");

                } else {
                    dialog.showDialog(PastTransactions.this, response.toString());
                }
            }


            @Override
            public void onFailure(Call<ArrayList<PastTransactionsResponse>>  call, Throwable t) {
                dialog.showDialog(PastTransactions.this, t.getMessage().toString());

            }
        });

    }
}
