package com.vavisa.masafahdriver.tap_profile.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.basic.BasePresenter;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.network.APIManager;
import com.vavisa.masafahdriver.register.RegisterResponse;
import com.vavisa.masafahdriver.register.UserModel;
import com.vavisa.masafahdriver.util.Connectivity;
import com.vavisa.masafahdriver.util.Preferences;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileViews> {

    public void getProfileDetails() {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().getProfileCall(Preferences.getInstance().getString("access_token"))
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().displayProfileDetails(response.body());
                            else
                                getView().showMissingData(response);
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            getView().hideProgress();
                            getView().showMessage(BaseApplication.error_msg);
                        }
                    });
        } else
            getView().showErrorConnection();

    }

    public void updateProfileDetails(UserModel userModel) {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().updateProfileCall(Preferences.getInstance().getString("access_token"), userModel)
                    .enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            getView().hideProgress();
                            if (response.code() == 200)
                                getView().displayProfileDetails(response.body().getUser());
                            else
                                getView().showMissingData(response);
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            getView().hideProgress();
                            getView().showMessage(BaseApplication.error_msg);
                        }
                    });
        } else
            getView().showErrorConnection();

    }

    public void logout(HashMap<String, String> player_id) {
        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().logoutCall(Preferences.getInstance()
                    .getString("access_token"), player_id).enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    getView().hideProgress();
                    if (response.code() == 200) {
                        // to open again app with the selection language
                        String current_lan = Preferences.getInstance().getString("lan");
                        Preferences.getInstance().clear();
                        Preferences.getInstance().putString("lan", current_lan);
                        getView().logout();
                    } else
                        getView().showMissingData(response);
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
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

    void getCountries() {

        if (Connectivity.checkInternetConnection()) {
            getView().showProgress();
            APIManager.getInstance().getAPI().countryCall().enqueue(new Callback<ArrayList<CountryModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CountryModel>> call, Response<ArrayList<CountryModel>> response) {
                    getView().hideProgress();
                    if (response.code() == 200)
                        getView().getCountriesList(response.body());
                    else
                        getView().showMissingData(response);
                }

                @Override
                public void onFailure(Call<ArrayList<CountryModel>> call, Throwable t) {
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
