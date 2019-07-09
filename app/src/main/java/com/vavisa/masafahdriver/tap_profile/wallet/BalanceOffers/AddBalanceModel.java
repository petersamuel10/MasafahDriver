package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import com.google.gson.annotations.SerializedName;

public class AddBalanceModel {

    @SerializedName("wallet_id")
    private String wallet_id;
    @SerializedName("amount")
    private String amount;
    @SerializedName("isOffer")
    private Boolean isOffer;

    public AddBalanceModel(String wallet_id, String amount, Boolean isOffer) {
        this.wallet_id = wallet_id;
        this.amount = amount;
        this.isOffer = isOffer;
    }

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getOffer() {
        return isOffer;
    }

    public void setOffer(Boolean offer) {
        isOffer = offer;
    }
}
