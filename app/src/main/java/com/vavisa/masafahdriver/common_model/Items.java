package com.vavisa.masafahdriver.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items implements Parcelable {

    @SerializedName("address_to")
    private AddressModel address_to;
    @SerializedName("products")
    private ArrayList<Shipment> products;


    protected Items(Parcel in) {
        address_to = in.readParcelable(AddressModel.class.getClassLoader());
        products = in.createTypedArrayList(Shipment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(address_to, flags);
        dest.writeTypedList(products);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    public AddressModel getAddress_to() {
        return address_to;
    }
    public void setAddress_to(AddressModel address_to) {
        this.address_to = address_to;
    }

    public ArrayList<Shipment> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Shipment> products) {
        this.products = products;
    }
}
