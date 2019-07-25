package com.vavisa.masafahdriver.tap_profile.termsAndConditions;

import android.util.Log;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.util.Connectivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class TermsPresenter extends BasePresenter<Terms> {

    public void getTerms() {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().termsCall().enqueue(new Callback<TermsModel>() {
                @Override
                public void onResponse(Call<TermsModel> call, Response<TermsModel> response) {
                    getView().hideProgress();
                    if (response.code() == 200)
                        getView().getTerms(response.body().getTerms_and_conditions());
                    else
                        getView().showMissingData(response);
                }

                @Override
                public void onFailure(Call<TermsModel> call, Throwable t) {
                    getView().hideProgress();
                    getView().showMessage(BaseApplication.error_msg);
                    if (t instanceof HttpException) {
                        ResponseBody body = ((HttpException) t).response().errorBody();
                        Log.d("error", body.toString());
                    }
                }
            });
        } else
            getView().showErrorConnection();
    }

}

interface Terms extends BaseView {

    void getTerms(String terms_str);
}
