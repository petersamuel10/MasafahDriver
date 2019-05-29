package com.vavisa.masafahdriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;

public class LoginActivity extends BaseActivity {

    private TextInputEditText email_ed;
    private TextInputEditText password_ed;
    private TextView create_new_account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button continueButton = findViewById(R.id.continue_buton);
        email_ed = findViewById(R.id.email_ed);
        password_ed = findViewById(R.id.password_ed);
        create_new_account = findViewById(R.id.create_new_account);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(LoginActivity.this, VerifyYourNumberActivity.class);
                       // intent.putExtra("mobileNumber", mobileNumber.getText().toString());
                        startActivity(new Intent(LoginActivity.this, VerifyYourNumberActivity.class));
                    }
                });

        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });
    }
}
