package com.cmov.acme.ui;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.adapters.PrinterAdapter;
import com.cmov.acme.api.model.response.PastTransactionsResponse;
import com.cmov.acme.api.model.response.PrinterResponse;
import com.cmov.acme.api.service.Printer_Service;
import com.cmov.acme.singletons.RetrofitSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NFC_receive extends AppCompatActivity {

    private TextView token_text;
    private TextView name_text;
    private TextView address_text;
    private TextView total_price;
    private TextView date_text;
    private TextView nif_text;
    private Retrofit retrofit;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_receive);
        token_text = (TextView) findViewById(R.id.token_text);
        total_price = (TextView) findViewById(R.id.total_price);
        retrofit = RetrofitSingleton.getInstance();
        listView = (ListView) findViewById(R.id.listview);
        nif_text = (TextView) findViewById(R.id.nif_text);
        date_text = (TextView) findViewById(R.id.date_text);
        address_text = (TextView) findViewById(R.id.address_text);
        name_text = (TextView) findViewById(R.id.name_text);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        token_text.setText(new String(msg.getRecords()[0].getPayload()));


        Printer_Service printer_service = retrofit.create(Printer_Service.class);
        Call<ArrayList<PrinterResponse>> call = printer_service.getReceipt(new String(msg.getRecords()[0].getPayload()));


        call.enqueue(new Callback<ArrayList<PrinterResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PrinterResponse>> call, Response<ArrayList<PrinterResponse>> response) {
                if(response.isSuccessful() ) {
                    ArrayList<PrinterResponse> listaResposta = response.body();


                    String oldstring = listaResposta.get(0).getReceipt_date();
                    Date newDate = null;
                    String newstring = null;
                    try {
                        newDate = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
                        newstring = new SimpleDateFormat("EEE, MMM d, ''yy").format(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date_text.setText(newstring);
                    name_text.setText(listaResposta.get(0).getUser_name());
                    nif_text.setText(listaResposta.get(0).getNif());
                    address_text.setText(listaResposta.get(0).getAddress());

                    double price = 0;
                    for(PrinterResponse r : listaResposta) {
                        price += r.getPrice()*r.getQuantity();
                    }
                    total_price.setText("Total Price: " + price+"€");


                    PrinterAdapter adapter = new PrinterAdapter(NFC_receive.this, listaResposta);
                    listView.setAdapter(adapter);

                } else {
                    Toast.makeText(NFC_receive.this,response.message(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PrinterResponse>>  call, Throwable t) {
                Log.i("ERRO - " ,t.getMessage());
                finish();

            }
        });
    }
}
