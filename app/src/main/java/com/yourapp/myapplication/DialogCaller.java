package com.yourapp.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Nirmal on 29-10-2015.
 */

//http://stackoverflow.com/a/27033964
public class DialogCaller {
    public static void showDialog(Context context, String title, String message,
                                  DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(R.drawable.notification);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNegativeButton("Cancel", null);
        dialog.setPositiveButton("OK", onClickListener);
        dialog.show();
    }
}

