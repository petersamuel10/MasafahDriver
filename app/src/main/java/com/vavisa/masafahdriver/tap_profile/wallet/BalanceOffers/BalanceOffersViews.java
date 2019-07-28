package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.tap_order.invoice.PaidModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface BalanceOffersViews extends BaseView {

    void displayOffers(ArrayList<BalanceOfferModel> offersList);
    void addBalanceRes(PaidModel paidModel);
}
