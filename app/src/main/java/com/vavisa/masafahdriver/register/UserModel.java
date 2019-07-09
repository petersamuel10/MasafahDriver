package com.vavisa.masafahdriver.register;

import com.google.gson.annotations.SerializedName;
import com.vavisa.masafahdriver.login.CountryModel;

public class UserModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm_password")
    private String confirm_password;
    @SerializedName("image")
    private String image;
    @SerializedName("country_id")
    private Integer country_id;
    @SerializedName("player_id")
    private String player_id;
    @SerializedName("device_type")
    private Integer device_type;
    @SerializedName("country")
    private CountryModel countryModel;

    // for register
    public UserModel(String name, String email, String mobile, String password, String confirm_password, String image, Integer country_id) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.confirm_password = confirm_password;
        this.image = image;
        this.country_id = country_id;
    }
    //for login
    public UserModel(String email, String password, String player_id, Integer device_type) {
        this.email = email;
        this.password = password;
        this.player_id = player_id;
        this.device_type = device_type;
    }
    // for update profile
    public UserModel(String name, String email, String mobile, String image, Integer country_id) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.image = image;
        this.country_id = country_id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountry_id() {
        return country_id;
    }
    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getPlayer_id() {
        return player_id;
    }
    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public Integer getDevice_type() {
        return device_type;
    }
    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public CountryModel getCountryModel() {
        return countryModel;
    }
    public void setCountryModel(CountryModel countryModel) {
        this.countryModel = countryModel;
    }
}
