package com.vavisa.masafahdriver.tap_profile.termsAndConditions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseFragment;

public class TermsAndConditions extends BaseFragment implements Terms {

    private View view;
    private TextView terms_txt;
    private TermsPresenter presenter;

    public TermsAndConditions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);

        terms_txt = view.findViewById(R.id.terms_txt);

        presenter = new TermsPresenter();
        presenter.attachView(this);

        // presenter.getTerms();
        return view;
    }

    @Override
    public void getTerms(String terms_str) {

        terms_txt.setText(terms_str);
    }
}
