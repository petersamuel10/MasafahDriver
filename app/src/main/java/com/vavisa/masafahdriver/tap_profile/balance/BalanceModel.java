package com.vavisa.masafahdriver.tap_profile.balance;

public class BalanceModel {

    private String amount;
    private String offer;

    public BalanceModel(String amount, String offer) {
        this.amount = amount;
        this.offer = offer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
