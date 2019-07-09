package com.vavisa.masafahdriver.tap_profile.wallet;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletPresenter extends BasePresenter<WalletViews> {

    public void getWalletDetails() {
        getView().showProgress();
        APIManager.getInstance().getAPI().walletDetailsCall(Preferences.getInstance().getString("access_token"))
                .enqueue(new Callback<WalletModel>() {
                    @Override
                    public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                        getView().hideProgress();
                        if (response.code() == 200)
                            getView().DisplayWalletDetails(response.body());
                        else
                            getView().showMissingData(response);
                    }

                    @Override
                    public void onFailure(Call<WalletModel> call, Throwable t) {
                        getView().hideProgress();
                        getView().showMessage(BaseApplication.error_msg);
                    }
                });
    }
}
