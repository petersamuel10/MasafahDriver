package com.vavisa.masafahdriver.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.util.dialogs.ConnectionMessage;
import com.vavisa.masafahdriver.util.dialogs.FailedMessage;
import com.vavisa.masafahdriver.util.dialogs.ProgressDialog;

import retrofit2.Response;


public class BaseFragment extends Fragment implements BaseView {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        return v;
    }

    @Override
    public void showErrorConnection() {
        ConnectionMessage.getInstance().show(getActivity());
    }

    @Override
    public void hideErrorConnection() {
        ConnectionMessage.getInstance().dismiss();
    }

    @Override
    public void showMissingData(Response response) {
        FailedMessage.getInstance().show(getActivity(), response);
    }

    @Override
    public void hideMissingData() {
        FailedMessage.getInstance().dismiss();
    }

    @Override
    public void showProgress() {
        ProgressDialog.getInstance().show(getActivity());
    }

    @Override
    public void hideProgress() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
