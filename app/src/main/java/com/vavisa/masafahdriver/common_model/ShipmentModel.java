package com.vavisa.masafahdriver.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShipmentModel implements Parcelable {

    private boolean isSelected;

    @SerializedName("id")
    private String id;
    @SerializedName("price")
    private String price;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("is_today")
    private Boolean is_today;
    @SerializedName("pickup_time_from")
    private String pickup_time_from;
    @SerializedName("pickup_time_to")
    private String pickup_time_to;
    @SerializedName("status")
    private String status;
    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("items")
    private ArrayList<Items> items;
    @SerializedName("address_from")
    private AddressModel address_from;


    protected ShipmentModel(Parcel in) {
        isSelected = in.readByte() != 0;
        id = in.readString();
        price = in.readString();
        user_id = in.readString();
        byte tmpIs_today = in.readByte();
        is_today = tmpIs_today == 0 ? null : tmpIs_today == 1;
        pickup_time_from = in.readString();
        pickup_time_to = in.readString();
        status = in.readString();
        payment_type = in.readString();
        items = in.createTypedArrayList(Items.CREATOR);
        address_from = in.readParcelable(AddressModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeString(id);
        dest.writeString(price);
        dest.writeString(user_id);
        dest.writeByte((byte) (is_today == null ? 0 : is_today ? 1 : 2));
        dest.writeString(pickup_time_from);
        dest.writeString(pickup_time_to);
        dest.writeString(status);
        dest.writeString(payment_type);
        dest.writeTypedList(items);
        dest.writeParcelable(address_from, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShipmentModel> CREATOR = new Creator<ShipmentModel>() {
        @Override
        public ShipmentModel createFromParcel(Parcel in) {
            return new ShipmentModel(in);
        }

        @Override
        public ShipmentModel[] newArray(int size) {
            return new ShipmentModel[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }
    public String getPrice() {
        return price;
    }

    public String getUser_id() {
        return user_id;
    }
    public Boolean getIs_today() {
        return is_today;
    }

    public String getPickup_time_from() {
        return pickup_time_from;
    }
    public String getPickup_time_to() {
        return pickup_time_to;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_type() {
        return payment_type;
    }
    public ArrayList<Items> getItems() {
        return items;
    }

    public AddressModel getAddress_from() {
        return address_from;
    }


}
