package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.tap_order.invoice.PaidModel;
import com.vavisa.masafahdriver.util.Preferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceOffersPresenter extends BasePresenter<BalanceOffersViews> {

    public void getOffers() {
        getView().showProgress();
        APIManager.getInstance().getAPI().balanceOffersCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<ArrayList<BalanceOfferModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BalanceOfferModel>> call, Response<ArrayList<BalanceOfferModel>> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().displayOffers(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<BalanceOfferModel>> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }

    public void addToWallet(AddBalanceModel addBalanceModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().addBalanceCall(Preferences.getInstance().getString("access_token"), addBalanceModel)
                .enqueue(new Callback<PaidModel>() {
                    @Override
                    public void onResponse(Call<PaidModel> call, Response<PaidModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().addBalanceRes(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<PaidModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
}
