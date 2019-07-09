package com.vavisa.masafahdriver.basic;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.onesignal.OneSignal;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.util.Constants;
import com.vavisa.masafahdriver.util.Preferences;


public class BaseApplication extends Application {

    public static String error_msg;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        error_msg = context.getString(R.string.error_occurred);
        setupOnSignal();
    }

    private void setupOnSignal() {
        OneSignal.startInit(this)
                // .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                // .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(
                new OneSignal.IdsAvailableHandler() {
                    @Override
                    public void idsAvailable(String userId, String registrationId) {
                    //    Log.d("debug", "User:" + userId);
                        Constants.oneSignalToken = userId;
                        if (registrationId != null)
                            Log.d("debug", "registrationId:" + registrationId);
                    }
                });
    }

    public static void preventAccess() {
        Preferences.getInstance().remove("access_token");
        Preferences.getInstance().remove("user_id");
        Preferences.getInstance().remove("mobile");
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public static Context getAppContext() {
        return context;
    }

}
