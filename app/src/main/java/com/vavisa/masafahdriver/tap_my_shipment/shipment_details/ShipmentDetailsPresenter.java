package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentDetailsPresenter extends BasePresenter<ShipmentDetailsViews> {

    public void getDetails(String shipment_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().shipmentDetailsCall(Preferences.getInstance().getString("access_token"), shipment_id)
                .enqueue(new Callback<ShipmentModel>() {
                    @Override
                    public void onResponse(Call<ShipmentModel> call, Response<ShipmentModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayDetails(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<ShipmentModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
    public void pickupShipment(String shipment_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().markAsPickupCall(Preferences.getInstance().getString("access_token"), shipment_id)
                .enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().handlePickupAndDelivery("pickup",response.body().get("message"));
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
    public void deliveredShipment(String shipment_id) {
        getView().showProgress();
        APIManager.getInstance().getAPI().markAsDeliveryCall(Preferences.getInstance().getString("access_token"), shipment_id)
                .enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().handlePickupAndDelivery("delivered",response.body().get("message"));
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
}
