package com.vavisa.masafahdriver.util.dialogs;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

public class FailedMessage {

    private String error_msg;
    private static FailedMessage mInstance;
    private AlertDialog alertDialog;

    public static synchronized FailedMessage getInstance() {
        if (mInstance == null)
            mInstance = new FailedMessage();
        return mInstance;
    }

    public void show(Activity activity, Response response) {
        if (alertDialog != null && alertDialog.isShowing())
            return;

        try {
            JSONObject error_object = new JSONObject(response.errorBody().string());
            error_msg = error_object.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.failed_message_layout, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView error_message = view.findViewById(R.id.error_message);
        error_message.setText(error_msg);

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
