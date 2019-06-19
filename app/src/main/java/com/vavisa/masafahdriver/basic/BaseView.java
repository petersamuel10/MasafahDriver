package com.vavisa.masafahdriver.basic;

import retrofit2.Response;

public interface BaseView {

    void showErrorConnection();

    void hideErrorConnection();

    void showMissingData(Response response);

    void hideMissingData();

    void showProgress();

    void hideProgress();

    void showMessageToast(String msg);
}
