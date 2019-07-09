package com.vavisa.masafahdriver.tap_profile.profile;

import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.register.UserModel;

import java.util.ArrayList;

public interface ProfileViews extends BaseView {

    void displayProfileDetails(UserModel user);
    void logout();
    void getCountriesList(ArrayList<CountryModel> countryList);


}
