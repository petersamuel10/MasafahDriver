package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import com.google.gson.annotations.SerializedName;

public class BalanceOfferModel {

    @SerializedName("id")
    private String id;
    @SerializedName("amount")
    private String amount;
    @SerializedName("free_deliveries")
    private String free_deliveries;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFree_deliveries() {
        return free_deliveries;
    }
    public void setFree_deliveries(String free_deliveries) {
        this.free_deliveries = free_deliveries;
    }
}
