package com.vavisa.masafahdriver.tap_order.invoice.payment_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vavisa.masafahdriver.R;

public class PaymentPage extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView wb_payment;
    private String url, payment_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wb_payment = findViewById(R.id.wb_payment);
        url = getIntent().getStringExtra("payment_url");

        loadPage();
    }

    private void loadPage() {

        WebSettings webSettings = wb_payment.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wb_payment.loadUrl(url);


        wb_payment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                wb_payment.evaluateJavascript("document.getElementById('PaymentID').value", value -> {

                    Log.i("xx1", value);
                    payment_id = value.substring(1, value.length() - 1);
                });

                wb_payment.evaluateJavascript("document.getElementById('is_captured').value", value -> {

                    Log.i("xx6", value);

                    Intent intent = new Intent(PaymentPage.this, PaymentResult.class);
                    intent.putExtra("payment_id", payment_id);
                    intent.putExtra("status", value);
                    startActivity(intent);
                    finish();

                });
            }
        });

    }
}
