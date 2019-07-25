package com.vavisa.masafahdriver.tap_order.invoice;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InvoiceModel implements Parcelable {

    private String total_shipment;

    @SerializedName("message")
    private String message;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("total_amount")
    private String total_amount;
    @SerializedName("free_deliveries_used")
    private String free_deliveries_used;
    @SerializedName("wallet_amount_used")
    private String wallet_amount_used;
    @SerializedName("card_amount")
    private String card_amount;


    protected InvoiceModel(Parcel in) {
        total_amount = in.readString();
        total_shipment = in.readString();
        message = in.readString();
        order_id = in.readString();
        free_deliveries_used = in.readString();
        wallet_amount_used = in.readString();
        card_amount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total_amount);
        dest.writeString(total_shipment);
        dest.writeString(message);
        dest.writeString(order_id);
        dest.writeString(free_deliveries_used);
        dest.writeString(wallet_amount_used);
        dest.writeString(card_amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InvoiceModel> CREATOR = new Creator<InvoiceModel>() {
        @Override
        public InvoiceModel createFromParcel(Parcel in) {
            return new InvoiceModel(in);
        }

        @Override
        public InvoiceModel[] newArray(int size) {
            return new InvoiceModel[size];
        }
    };

    public String getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_shipment() {
        return total_shipment;
    }
    public void setTotal_shipment(String total_shipment) {
        this.total_shipment = total_shipment;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFree_deliveries_used() {
        return free_deliveries_used;
    }
    public void setFree_deliveries_used(String free_deliveries_used) {
        this.free_deliveries_used = free_deliveries_used;
    }

    public String getWallet_amount_used() {
        return wallet_amount_used;
    }
    public void setWallet_amount_used(String wallet_amount_used) {
        this.wallet_amount_used = wallet_amount_used;
    }

    public String getCard_amount() {
        return card_amount;
    }
    public void setCard_amount(String card_amount) {
        this.card_amount = card_amount;
    }
}
