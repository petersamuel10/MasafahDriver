package com.vavisa.masafahdriver.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.login.LoginActivity;
import com.vavisa.masafahdriver.util.Constants;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class StartUpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLocalization();
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_startup);
        Constants.LANGUAGE = (Locale.getDefault().getDisplayLanguage().equals("English")) ? "en" : "ar";
        Thread timerThread =
                new Thread() {
                    public void run() {
                        try     {
                            sleep(3);
                        } catch (InterruptedException e) {
                        } finally {
                            if (Preferences.getInstance().isHasKey("access_token"))
                                startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                            else
                                startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                        }
                    }
                };
        timerThread.start();
    }

    private void setLocalization() {
        String lang;
        if (Preferences.getInstance().isHasKey("lan"))
            lang = (Preferences.getInstance().getString("lan").equals("English")) ? "en" : "ar";
        else {
            lang = "en";
            Preferences.getInstance().putString("lan", "English");
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
