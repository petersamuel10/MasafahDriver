package com.vavisa.masafahdriver.tap_order.invoice.payment_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.MainActivity;

public class PaymentResult extends AppCompatActivity {

    String status, payment_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result);

        status = getIntent().getStringExtra("status");
        payment_id = getIntent().getStringExtra("payment_id");

        Button continue_btn = findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentResult.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        switch (status) {
            case "-1":

                break;
            case "0":

                break;
            case "1":

                break;
        }
    }
}
