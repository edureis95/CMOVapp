package com.cmov.acme.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.cmov.acme.ui.LoginActivity;

/**
 * Created by mauro on 26/10/2017.
 */

public class ShowDialog {
    public void showDialog(Activity activity, String text) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(activity);
        dlgAlert.setMessage(text);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


    }
}
