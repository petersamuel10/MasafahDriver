package com.vavisa.masafahdriver.util.dialogs;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.vavisa.masafahdriver.R;

public class ConnectionMessage {

    private AlertDialog alertDialog;
    private static ConnectionMessage mInstance;

    public static synchronized ConnectionMessage getInstance() {
        if (mInstance == null)
            mInstance = new ConnectionMessage();
        return mInstance;
    }

    public void show(Activity activity) {
        if (alertDialog != null && alertDialog.isShowing())
            return;


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.connection_alert_layout, null);
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button ok_btn = view.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
