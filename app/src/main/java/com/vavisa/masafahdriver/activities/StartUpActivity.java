package com.vavisa.masafahdriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vavisa.masafahdriver.R;
import com.crashlytics.android.Crashlytics;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.login.LoginActivity;

import io.fabric.sdk.android.Fabric;

public class StartUpActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_startup);

    Thread timerThread =
        new Thread() {
          public void run() {
            try {
              sleep(1);
            } catch (InterruptedException e) {
            } finally {
              startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
            }
          }
        };
    timerThread.start();
  }
}
