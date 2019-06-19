package com.vavisa.masafahdriver.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vavisa.masafahdriver.util.dialogs.ConnectionMessage;
import com.vavisa.masafahdriver.util.dialogs.FailedMessage;
import com.vavisa.masafahdriver.util.dialogs.ProgressDialog;

import retrofit2.Response;


public class BaseActivity extends AppCompatActivity implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void start(Class<? extends BaseActivity> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void showErrorConnection() {
        ConnectionMessage.getInstance().show(this);
    }

    @Override
    public void hideErrorConnection() {
        ConnectionMessage.getInstance().dismiss();
    }

    @Override
    public void showMissingData(Response response) {
        FailedMessage.getInstance().show(this, response);
    }

    @Override
    public void hideMissingData() {
        FailedMessage.getInstance().dismiss();
    }

    @Override
    public void showProgress() {
        ProgressDialog.getInstance().show(this);
    }

    @Override
    public void hideProgress() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
