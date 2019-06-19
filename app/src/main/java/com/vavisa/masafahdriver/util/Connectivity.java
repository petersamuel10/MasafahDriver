package com.vavisa.masafahdriver.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vavisa.masafahdriver.basic.BaseApplication;

public class Connectivity {

    public static boolean checkInternetConnection() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
