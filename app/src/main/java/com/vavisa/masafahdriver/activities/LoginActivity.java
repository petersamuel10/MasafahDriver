package com.vavisa.masafahdriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.vavisa.masafahdriver.R;

public class LoginActivity extends BaseActivity {

    private TextInputEditText mobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button continueButton = findViewById(R.id.continue_buton);
        mobileNumber = findViewById(R.id.mobile_number);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(LoginActivity.this, VerifyYourNumberActivity.class);
                        intent.putExtra("mobileNumber", mobileNumber.getText().toString());
                        startActivity(new Intent(LoginActivity.this, VerifyYourNumberActivity.class));
                    }
                });
    }
}
