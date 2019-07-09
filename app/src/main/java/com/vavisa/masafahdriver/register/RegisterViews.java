package com.vavisa.masafahdriver.register;

import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.login.CountryModel;

import java.util.ArrayList;

public interface RegisterViews extends BaseView {

    void handleRegister(RegisterResponse registerResponse);
    void displayCountries(ArrayList<CountryModel> countryList);
}
