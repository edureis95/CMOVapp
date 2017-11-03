package com.cmov.acme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cmov.acme.R;
import com.cmov.acme.api.model.response.PrinterResponse;
import com.cmov.acme.api.model.response.ReceiptResponse;
import com.cmov.acme.ui.PastTransactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mauro on 30/10/2017.
 */

public class PrinterAdapter extends ArrayAdapter<PrinterResponse> {

    private Context context;
    private ArrayList<PrinterResponse> list;
    PrinterResponse itemPosition;

    public PrinterAdapter(@NonNull Context context, ArrayList<PrinterResponse> list) {
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PrinterResponse itemPosition =  this.list.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.custom_transaction, null);


        TextView name = (TextView) convertView.findViewById(R.id.text_name);
        name.setText(itemPosition.getName());

        TextView price = (TextView) convertView.findViewById(R.id.text_price);
        price.setText("Price: " + itemPosition.getPrice()+"€");

        TextView quantity = (TextView) convertView.findViewById(R.id.text_quantity);
        quantity.setText("Quantity: " + itemPosition.getQuantity());

        return convertView;
    }


}
