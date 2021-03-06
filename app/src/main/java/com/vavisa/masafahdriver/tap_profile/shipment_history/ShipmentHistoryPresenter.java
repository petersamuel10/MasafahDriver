package com.vavisa.masafahdriver.tap_profile.shipment_history;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Connectivity;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentHistoryPresenter extends BasePresenter<ShipmentHistoryViews> {

    public void getShipment() {

        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().getShipmentHistoryCall(Preferences.getInstance().getString("access_token"))
                    .enqueue(new Callback<ArrayList<ShipmentModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ShipmentModel>> call, Response<ArrayList<ShipmentModel>> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().displayShipments(response.body());
                            else
                                getView().showMissingData(response);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ShipmentModel>> call, Throwable t) {
                            getView().hideProgress();
                            getView().showMessage(BaseApplication.error_msg);
                        }
                    });
        } else
            getView().showErrorConnection();
    }
}
