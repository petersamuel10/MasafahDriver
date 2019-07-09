package com.vavisa.masafahdriver.tap_profile.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletModel {

    @SerializedName("wallet_balance")
    private String wallet_balance;
    @SerializedName("transactionDetails")
    private ArrayList<TransactionDetails> transactionDetails;
    @SerializedName("free_deliveries")
    private String free_deliveries;

    public String getWallet_balance() {
        return wallet_balance;
    }
    public void setWallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public ArrayList<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }
    public void setTransactionDetails(ArrayList<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public String getFree_deliveries() {
        return free_deliveries;
    }
    public void setFree_deliveries(String free_deliveries) {
        this.free_deliveries = free_deliveries;
    }

    public class TransactionDetails {

        @SerializedName("amount")
        private String amount;
        @SerializedName("wallet_in")
        private Boolean in;
        @SerializedName("order_id")
        private String order_id;
        @SerializedName("created_at")
        private String created_at;


        public String getAmount() {
            return amount;
        }
        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Boolean getIn() {
            return in;
        }
        public void setIn(Boolean in) {
            this.in = in;
        }

        public String getOrder_id() {
            return order_id;
        }
        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getCreated_at() {
            return created_at;
        }
        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
