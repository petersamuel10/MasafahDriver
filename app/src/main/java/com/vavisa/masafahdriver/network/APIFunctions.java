package com.vavisa.masafahdriver.network;

import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.register.RegisterResponse;
import com.vavisa.masafahdriver.register.UserModel;
import com.vavisa.masafahdriver.tap_order.invoice.InvoiceModel;
import com.vavisa.masafahdriver.tap_order.invoice.PaidModel;
import com.vavisa.masafahdriver.tap_profile.termsAndConditions.TermsModel;
import com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers.AddBalanceModel;
import com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers.BalanceOfferModel;
import com.vavisa.masafahdriver.tap_profile.wallet.WalletModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIFunctions {

    @GET("getCountries")
    Call<ArrayList<CountryModel>> countryCall();

    @POST("register")
    Call<RegisterResponse> registerCall(@Body UserModel login);

    @POST("login")
    Call<RegisterResponse> loginCall(@Body UserModel login);

    @GET("getProfile")
    Call<UserModel> getProfileCall(@Header("Authorization") String authorization);

    @GET("getTermsAndConditions")
    Call<TermsModel> termsCall();

    @POST("logout")
    Call<HashMap<String, String>> logoutCall(@Header("Authorization") String Authorization, @Body HashMap<String, String> player_id);

    @GET("getWalletDetails")
    Call<WalletModel> walletDetailsCall(@Header("Authorization") String Authorization);

    @GET("getWalletOffers")
    Call<ArrayList<BalanceOfferModel>> balanceOffersCall(@Header("Authorization") String Authorization);

    @POST("addToWallet")
    Call<PaidModel> addBalanceCall(@Header("Authorization") String Authorization, @Body AddBalanceModel addBalanceModel);

    @PUT("updateProfile")
    Call<RegisterResponse> updateProfileCall(@Header("Authorization") String authorization, @Body UserModel userModel);

    @GET("getShipmentHistory")
    Call<ArrayList<ShipmentModel>> getShipmentHistoryCall(@Header("Authorization") String Authorization);

    @GET("getPendingShipments")
    Call<ArrayList<ShipmentModel>> orderListCall(@Header("Authorization") String authorization, @Query("from_id") String from_id, @Query("to_id") String to_id);

    @GET("getMyCities")
    Call<ArrayList<CountryModel>> myCitiesCall(@Header("Authorization") String authorization);

    @GET("getMyShipments")
    Call<ArrayList<ShipmentModel>> getMyShipmentCall(@Header("Authorization") String authorization);

    @GET("getShipmentById/{shipment_id}")
    Call<ShipmentModel> shipmentDetailsCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("markShipmentAsPicked/{shipment_id}")
    Call<HashMap<String, String>> markAsPickupCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("markShipmentAsDelivered/{shipment_id}")
    Call<HashMap<String, String>> markAsDeliveryCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @POST("acceptShipments")
    Call<InvoiceModel> acceptShipmentCall(@Header("Authorization") String Authorization, @Body HashMap<String, List<String>> shipment_ids);

    @GET("payOrder/{order_id}")
    Call<PaidModel> payOrderCall(@Header("Authorization") String Authorization, @Path("order_id") String order_id);

}
