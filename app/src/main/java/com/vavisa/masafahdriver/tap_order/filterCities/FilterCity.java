package com.vavisa.masafahdriver.tap_order.filterCities;

import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.login.CountryModel;

import java.util.ArrayList;

public interface FilterCity extends BaseView {
    void displayMyCities(ArrayList<CountryModel> citiesList);
}
