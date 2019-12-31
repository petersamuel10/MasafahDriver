package com.vavisa.masafahdriver.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.MainActivity;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.forgot_password.ForgotPasswordActivity;
import com.vavisa.masafahdriver.register.RegisterActivity;
import com.vavisa.masafahdriver.register.RegisterResponse;
import com.vavisa.masafahdriver.register.UserModel;
import com.vavisa.masafahdriver.util.Constants;
import com.vavisa.masafahdriver.util.Preferences;

public class LoginActivity extends BaseActivity implements LoginViews {

    private TextInputEditText email_ed, password_ed;
    private TextView create_new_account, forgot_password;
    private String email_str, password_str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button continueButton = findViewById(R.id.continue_buton);
        email_ed = findViewById(R.id.email_ed);
        password_ed = findViewById(R.id.password_ed);
        forgot_password = findViewById(R.id.forgot_password);
        create_new_account = findViewById(R.id.create_new_account);

        LoginPresenter presenter = new LoginPresenter();
        presenter.attachView(this);

        continueButton.setOnClickListener(v -> {
            if (isValid())
                presenter.login(new UserModel(email_str, password_str, Constants.oneSignalToken, 2));
        });
        forgot_password.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
        create_new_account.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

    }

    private boolean isValid() {

        if (TextUtils.isEmpty(email_ed.getText())) {
            showMessage(getString(R.string.please_enter_valid_email));
            return false;
        } else
            email_str = email_ed.getText().toString();

        if (TextUtils.isEmpty(password_ed.getText())) {
            showMessage(getString(R.string.please_enter_password));
            return false;
        } else
            password_str = password_ed.getText().toString();

        return true;
    }

    @Override
    public void handleLogin(RegisterResponse loginResponse) {

        Preferences.getInstance().putString("mobile", loginResponse.getUser().getMobile());
        Preferences.getInstance().putString("access_token", loginResponse.getAccess_token());
        Preferences.getInstance().putString("use_id", loginResponse.getUser().getId());

        start(MainActivity.class);

    }
}
