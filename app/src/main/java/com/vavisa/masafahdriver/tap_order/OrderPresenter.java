package com.vavisa.masafahdriver.tap_order;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.tap_order.invoice.InvoiceModel;
import com.vavisa.masafahdriver.util.Connectivity;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 class OrderPresenter extends BasePresenter<OrdersViews> {

     void getShipment(String from_id, String to_id) {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().orderListCall(Preferences.getInstance().getString("access_token"), from_id, to_id)
                    .enqueue(new Callback<ArrayList<ShipmentModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ShipmentModel>> call, Response<ArrayList<ShipmentModel>> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().displayShipment(response.body());
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

    void getMyCities() {
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

    void acceptShipment(AcceptOrderModel acceptOrderModel) {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().acceptShipmentCall(Preferences.getInstance().getString("access_token"), acceptOrderModel)
                    .enqueue(new Callback<InvoiceModel>() {
                        @Override
                        public void onResponse(Call<InvoiceModel> call, Response<InvoiceModel> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().payment(response.body());
                            else
                                getView().showMissingData(response);
                        }

                        @Override
                        public void onFailure(Call<InvoiceModel> call, Throwable t) {
                            getView().hideProgress();
                            getView().showMessage(BaseApplication.error_msg);
                        }
                    });
        } else
            getView().showErrorConnection();
    }
}
