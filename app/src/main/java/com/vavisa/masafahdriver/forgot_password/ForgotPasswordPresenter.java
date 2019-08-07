package com.vavisa.masafahdriver.forgot_password;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Connectivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordViews> {

    public void forgot_password(String email) {

        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().forgotPasswordCall(email).enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    getView().hideProgress();
                    if (response.code() == 200)
                        getView().handleForgotPasswordRes(response.body().get("message"));
                    else
                        getView().showMissingData(response);
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    getView().hideProgress();
                    getView().showMessage(BaseApplication.error_msg);
                }
            });
        } else
            getView().showErrorConnection();

    }
}
