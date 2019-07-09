package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import com.vavisa.masafahdriver.basic.BaseView;

import java.util.ArrayList;
import java.util.HashMap;

public interface BalanceOffersViews extends BaseView {

    void displayOffers(ArrayList<BalanceOfferModel> offersList);
    void addBalanceRes(HashMap<String,String> result);
}
