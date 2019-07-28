package com.vavisa.masafahdriver.tap_order.invoice.payment_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.MainActivity;
import com.vavisa.masafahdriver.tap_profile.wallet.WalletActivity;

public class PaymentResult extends AppCompatActivity {

    private String status;
    private ImageView img;
    private TextView msg;
    private Button continue_btn;
    private boolean isAddBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result);

        status = getIntent().getStringExtra("status");
        isAddBalance = getIntent().getBooleanExtra("isAddBalance", false);

        img = findViewById(R.id.imageView);
        msg = findViewById(R.id.result_ms);
        continue_btn = findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(v -> {
            Intent intent;
            if (isAddBalance)
                intent = new Intent(PaymentResult.this, WalletActivity.class);
            else
                intent = new Intent(PaymentResult.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        if (status.equals("\"0\"")) {
            img.setImageResource(R.drawable.ic_failed_big);
            msg.setText(R.string.transaction_failed);
        }else{
            img.setImageResource(R.drawable.ic_success);
            msg.setText(R.string.transaction_successful);
        }
    }
}
