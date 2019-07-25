package com.vavisa.masafahdriver.tap_order.filterCities;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterCityPresenter extends BasePresenter<FilterCity> {

    public void getMyCities() {
        getView().showProgress();
        APIManager.getInstance().getAPI().myCitiesCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<ArrayList<CountryModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CountryModel>> call, Response<ArrayList<CountryModel>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayMyCities(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CountryModel>> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
}
