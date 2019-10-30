package com.vavisa.masafahdriver.tap_order.invoice;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Connectivity;
import com.vavisa.masafahdriver.util.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class InvoicePresenter extends BasePresenter<InvoiceViews> {

    void payOrder(String order_id) {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().payOrderCall(Preferences.getInstance().getString("access_token"), order_id)
                    .enqueue(new Callback<PaidModel>() {
                        @Override
                        public void onResponse(Call<PaidModel> call, Response<PaidModel> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().displayPaymentMethod(response.body());
                            else
                                getView().showMissingData(response);
                        }

                        @Override
                        public void onFailure(Call<PaidModel> call, Throwable t) {
                            getView().hideProgress();
                            getView().showMessage(BaseApplication.error_msg);
                        }
                    });
        } else
            getView().showErrorConnection();
    }
}
