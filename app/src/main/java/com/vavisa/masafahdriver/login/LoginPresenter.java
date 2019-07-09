package com.vavisa.masafahdriver.login;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.register.RegisterResponse;
import com.vavisa.masafahdriver.register.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginViews> {

    public void login(UserModel userModel) {

        getView().showProgress();
        APIManager.getInstance().getAPI().loginCall(userModel).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().handleLogin(response.body());
                else
                    getView().showMissingData(response);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                getView().hideProgress();
                getView().showMessage(BaseApplication.error_msg);
            }
        });

    }
}
