package com.vavisa.masafahdriver.register;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.network.APIManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter extends BasePresenter<RegisterViews> {

    public void getCountries() {
        getView().showProgress();
        APIManager.getInstance().getAPI().countryCall().enqueue(new Callback<ArrayList<CountryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryModel>> call, Response<ArrayList<CountryModel>> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().displayCountries(response.body());
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

    public void register(UserModel userModel) {
        getView().showProgress();
        APIManager.getInstance().getAPI().registerCall(userModel).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                getView().hideProgress();
                if (response.code() == 200)
                    getView().handleRegister(response.body());
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

    public String getImageBase64FromView(ImageView imageView) {
        if (imageView.getDrawable() != null) {
            Bitmap defaultPhotoBitMap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            defaultPhotoBitMap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return "";
    }

}
