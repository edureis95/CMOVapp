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
import com.cmov.acme.adapters.ReceiptAdapter;
import com.cmov.acme.api.model.response.PrinterResponse;
import com.cmov.acme.api.model.response.ReceiptResponse;
import com.cmov.acme.api.service.Printer_Service;
import com.cmov.acme.singletons.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NFC_receive extends AppCompatActivity {

    private TextView textview;
    private Retrofit retrofit;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_receive);
        textview = (TextView) findViewById(R.id.textView7);
        retrofit = RetrofitSingleton.getInstance();
        listView = (ListView) findViewById(R.id.listview);
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
        textview.setText(new String(msg.getRecords()[0].getPayload()));


        Printer_Service printer_service = retrofit.create(Printer_Service.class);
        Call<ArrayList<PrinterResponse>> call = printer_service.getReceipt(new String(msg.getRecords()[0].getPayload()));


        call.enqueue(new Callback<ArrayList<PrinterResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PrinterResponse>> call, Response<ArrayList<PrinterResponse>> response) {
                if(response.isSuccessful() ) {
                    ArrayList<PrinterResponse> listaResposta = response.body();
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
