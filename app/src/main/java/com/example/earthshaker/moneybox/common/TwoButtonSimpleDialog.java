package com.example.earthshaker.moneybox.common;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TwoButtonSimpleDialog {
    public TwoButtonSimpleDialog(AppCompatActivity activity, String title, String message,
                                 String positiveMessage, String negativeMessage,
                                 final TwoButtonDialogListener twoButtonDialogListener) {

        AlertDialog dialog = getDialog(activity, title, message).setPositiveButton(positiveMessage,
                (dialog1, which) -> twoButtonDialogListener.onAccept())
                .setNegativeButton(negativeMessage, (dialog12, which) -> twoButtonDialogListener.onReject())
                .create();
        dialog.show();
    }

    private AlertDialog.Builder getDialog(AppCompatActivity activity, String title, String message) {
        return new AlertDialog.Builder(activity).setTitle(
                Html.fromHtml("<font color='#000000'>" + title + "</font>"))
                .setMessage(Html.fromHtml("<font color='#000000'>" + message + "</font>"));
    }
}
