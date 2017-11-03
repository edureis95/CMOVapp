package com.cmov.acme.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmov.acme.R;
import com.cmov.acme.ui.ShoppingCartActivity;

/**
 * Created by ricardoduarte on 03/11/17.
 */

public class ShowDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View mView = getActivity().getLayoutInflater().inflate(R.layout.insert_barcode_dialog, null);
        final EditText barcode_input = mView.findViewById(R.id.input_barcode);
        Button submit_barcode = mView.findViewById(R.id.submit_barcode);

        submit_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!barcode_input.getText().toString().isEmpty()){
                    //open products page with the bar code
                    Toast.makeText(getActivity(), "Submitted barcode", Toast.LENGTH_LONG).show();
                    ShoppingCartActivity activity = (ShoppingCartActivity) getActivity();
                    activity.submitBarcode(barcode_input.getText().toString(), false);
                }
                else{
                    Toast.makeText(getActivity(), "Please, insert the product barcode ", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setView(mView);

        // Create the AlertDialog object and return it
        return builder.create();



    }
}
