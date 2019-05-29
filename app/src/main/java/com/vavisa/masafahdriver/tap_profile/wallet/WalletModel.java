package com.vavisa.masafahdriver.tap_profile.wallet;

public class WalletModel {
    boolean selected;
    String date;
    String amount;
    String order_id;

    public WalletModel() {
    }

    public WalletModel(String date, String amount, String order_id) {
        this.date = date;
        this.amount = amount;
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}