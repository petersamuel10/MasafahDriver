package com.vavisa.masafahdriver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vavisa.masafahdriver.R;

public class VerifyYourNumberActivity extends BaseActivity {

  private EditText otpCode1;
  private EditText otpCode2;
  private EditText otpCode3;
  private EditText otpCode4;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_phone_number);

    Toolbar toolbar = findViewById(R.id.verify_number_toolbar);
    setSupportActionBar(toolbar);
    setTitle("");

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    otpCode1 = findViewById(R.id.otp_code_1);
    otpCode2 = findViewById(R.id.otp_code_2);
    otpCode3 = findViewById(R.id.otp_code_3);
    otpCode4 = findViewById(R.id.otp_code_4);

    Button verifyButton = findViewById(R.id.verify_button);

    verifyButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(VerifyYourNumberActivity.this, MainActivity.class));
          }
        });

    otpCode1.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            otpCode1.clearFocus();
            otpCode2.requestFocus();
          }
        });
    otpCode2.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            otpCode2.clearFocus();
            otpCode3.requestFocus();
          }
        });
    otpCode3.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            otpCode3.clearFocus();
            otpCode4.requestFocus();
          }
        });
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return super.onSupportNavigateUp();
  }
}
