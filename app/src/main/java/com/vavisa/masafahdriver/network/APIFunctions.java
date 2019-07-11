package com.vavisa.masafahdriver.network;

import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.register.RegisterResponse;
import com.vavisa.masafahdriver.register.UserModel;
import com.vavisa.masafahdriver.tap_profile.termsAndConditions.TermsModel;
import com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers.AddBalanceModel;
import com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers.BalanceOfferModel;
import com.vavisa.masafahdriver.tap_profile.wallet.WalletModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<HashMap<String,String>> addBalanceCall(@Header("Authorization") String Authorization, @Body AddBalanceModel addBalanceModel);

    @PUT("updateProfile")
    Call<RegisterResponse> updateProfileCall(@Header("Authorization") String authorization, @Body UserModel userModel);

    @GET("getShipmentHistory")
    Call<ArrayList<ShipmentModel>> getShipmentHistoryCall(@Header("Authorization") String Authorization);

    @GET("getPendingShipments")
    Call<ArrayList<ShipmentModel>> orderListCall(@Header("Authorization") String authorization);

    @GET("getMyShipments")
    Call<ArrayList<ShipmentModel>> getMyShipmentCall(@Header("Authorization") String authorization);

    @GET("getShipmentById/{shipment_id}")
    Call<ShipmentModel> shipmentDetailsCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("markShipmentAsPicked/{shipment_id}")
    Call<HashMap<String,String>> markAsPickupCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

    @GET("markShipmentAsDelivered/{shipment_id}")
    Call<HashMap<String,String>> markAsDeliveryCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);

//    @POST("public/api/user/verifyOTP")
//    Call<VerifyResponseModel> verifyOtpCall(@Body Login login);
//
//    @POST("public/api/user/resendOTP")
//    Call<LoginResponse> resendOtpCall(@Body Login login);
//
//    @GET("public/api/user/getCategories")
//    Call<ArrayList<CategoryModel>> getCategoriesCall(@Header("Authorization") String authorization);
//
//    @GET("public/api/user/getCompanies")
//    Call<ArrayList<CompanyModel>> getCompaniesCall(@Header("Authorization") String authorization);
//
//    @PUT("public/api/user/updateProfile")
//    Call<User> updateProfileCall(@Header("Authorization") String authorization, @Body EditProfileModel editProfileModel);
//
//    @PATCH("public/api/user/changeMobileNumber")
//    Call<LoginResponse> changeMobileNumberCall(@Header("Authorization") String authorization, @Body HashMap<String, String> mobile);
//
//    @PATCH("public/api/user/updateMobileNumber")
//    Call<VerifyResponseModel> updateMobileNumberCall(@Header("Authorization") String Authorization,@Body Login login);
//
//    @POST("public/api/user/addAddress")
//    Call<AddressModel> addAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);
//
//    @POST("public/api/user/addShipment")
//    Call<HashMap<String, String>> addShipmentCall(@Header("Authorization") String Authorization, @Body AddShipmentModel addShipmentModel);
//
//    @GET("public/api/user/getAddresses")
//    Call<ArrayList<AddressModel>> getMyAddressesCall(@Header("Authorization") String Authorization);
//
//    @GET("public/api/user/getAddressById/{id}")
//    Call<AddressModel> getAddressDetailsCall(@Header("Authorization") String Authorization, @Path("id") String address_id);
//
//    @PUT("public/api/user/editAddress")
//    Call<AddressModel> editAddressCall(@Header("Authorization") String Authorization, @Body AddressModel addressModel);
//
//    @DELETE("public/api/user/deleteAddressById/{address_id}")
//    Call<HashMap<String,String>> deleteAddressCall(@Header("Authorization") String Authorization, @Path("address_id") String address_id);
//
//    @GET("public/api/user/getShipmentHistory")
//    Call<ArrayList<ShipmentModel>> getShipmentHistoryCall(@Header("Authorization") String Authorization);
//
//    @DELETE("public/api/user/deleteShipmentById/{shipment_id}")
//    Call<HashMap<String,String>> deleteShipmentCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);
//
//    @GET("public/api/user/getShipmentDetails/{shipment_id}")
//    Call<ShipmentModel> getShipmentDetailsCall(@Header("Authorization") String Authorization, @Path("shipment_id") String shipment_id);
//
//    @GET("public/api/user/getCompanyDetailsById/{company_id}")
//    Call<CompanyModel> getCompanyDetailsCall(@Header("Authorization") String Authorization, @Path("company_id") String company_id);
//
//    @POST("public/api/user/rateCompany")
//    Call<HashMap<String,String>> rateCompanyCall(@Header("Authorization") String Authorization, @Body RatingModel ratingModel);
//
//    @GET("public/api/user/getShipmentPrice")
//    Call<HashMap<String,String>> priceCall(@Header("Authorization") String Authorization);
}
