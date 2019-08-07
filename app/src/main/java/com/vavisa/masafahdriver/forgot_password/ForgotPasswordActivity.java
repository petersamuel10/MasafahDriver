package com.vavisa.masafahdriver.forgot_password;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordViews {

    private ImageView back_arrow;
    private TextInputEditText email_ed;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        back_arrow.setOnClickListener(v -> onBackPressed());
        ForgotPasswordPresenter presenter = new ForgotPasswordPresenter();
        presenter.attachView(this);

        resetButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email_ed.getText()))
                showMessage(getString(R.string.please_enter_valid_email));
            else {
                presenter.forgot_password(email_ed.getText().toString());
            }
        });
    }

    private void initViews() {

        back_arrow = findViewById(R.id.back_arrow);
        email_ed = findViewById(R.id.email_ed);
        resetButton = findViewById(R.id.reset_buton);
    }

    @Override
    public void handleForgotPasswordRes(String msg) {
        showMessage(msg);
        onBackPressed();
    }
}
