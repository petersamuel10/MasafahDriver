package com.vavisa.masafahdriver.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafahdriver.login.CountryModel;

public class AddressModel implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("block")
    private String block;
    @SerializedName("street")
    private String street;
    @SerializedName("country")
    private CountryModel country;
    @SerializedName("city")
    private CountryModel city;
    @SerializedName("governorate")
    private CountryModel governorate;
    @SerializedName("building")
    private String building;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("details")
    private String details;
    @SerializedName("notes")
    private String notes;


    protected AddressModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        block = in.readString();
        street = in.readString();
        country = in.readParcelable(CountryModel.class.getClassLoader());
        city = in.readParcelable(CountryModel.class.getClassLoader());
        governorate = in.readParcelable(CountryModel.class.getClassLoader());
        building = in.readString();
        mobile = in.readString();
        details = in.readString();
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(block);
        dest.writeString(street);
        dest.writeParcelable(country, flags);
        dest.writeParcelable(city, flags);
        dest.writeParcelable(governorate, flags);
        dest.writeString(building);
        dest.writeString(mobile);
        dest.writeString(details);
        dest.writeString(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBlock() {
        return block;
    }
    public String getStreet() {
        return street;
    }
    public CountryModel getCountry() {
        return country;
    }
    public CountryModel getCity() {
        return city;
    }
    public CountryModel getGovernorate() {
        return governorate;
    }
    public String getBuilding() {
        return building;
    }
    public String getMobile() {
        return mobile;
    }
    public String getDetails() {
        return details;
    }
    public String getNotes() {
        return notes;
    }


}
