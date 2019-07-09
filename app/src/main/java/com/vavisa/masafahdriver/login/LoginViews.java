package com.vavisa.masafahdriver.login;

import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.register.RegisterResponse;

public interface LoginViews extends BaseView {

    void handleLogin(RegisterResponse loginResponse);
}
