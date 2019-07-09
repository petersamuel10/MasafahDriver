package com.vavisa.masafahdriver.network;

import com.vavisa.masafahdriver.basic.BaseApplication;
import com.vavisa.masafahdriver.util.Connectivity;
import com.vavisa.masafahdriver.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private static APIManager mInstance;
    private Retrofit mRetrofit;
    private InternetConnectionListener mInternetConnectionListener;

    public void setInternetConnectionListener(InternetConnectionListener listener) {
        this.mInternetConnectionListener = listener;
    }

    public void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }

    public APIManager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept-Language", Constants.LANGUAGE)
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Version", "Android-1");

                        Request newRequest = builder.build();

                        Response response = chain.proceed(newRequest);
                        if (response.code() == 403) {
                            BaseApplication.preventAccess();
                        }

                        return response;
                    }
                }).addInterceptor(interceptor)
                .addInterceptor(new NetworkConnectionInterceptor() {
                    @Override
                    public boolean isInternetAvailable() {
                        return Connectivity.checkInternetConnection();
                    }

                    @Override
                    public void onInternetUnavailable() {
                        if (mInternetConnectionListener != null)
                            mInternetConnectionListener.onInternetUnavailable();
                    }
                }).build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

    }

    public static APIManager getInstance() {
        if (mInstance == null) {
            mInstance = new APIManager();
        }
        return mInstance;
    }

    public APIFunctions getAPI() {
        return mRetrofit.create(APIFunctions.class);
    }

}
