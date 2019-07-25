package com.vavisa.masafahdriver.tap_order.invoice;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaidModel {

    @SerializedName("message")
    private String message;
    @SerializedName("payment_methods")
    private ArrayList<PaymentMethod> paymentMethod;

    public String getMessage() {
        return message;
    }
    public ArrayList<PaymentMethod> getPaymentMethod() {
        return paymentMethod;
    }


    public class PaymentMethod {

        @SerializedName("PaymentMethodName")
        private String PaymentMethodName;
        @SerializedName("PaymentMethodUrl")
        private String PaymentMethodUrl;
        @SerializedName("PaymentMethodCode")
        private String PaymentMethodCode;

        public String getPaymentMethodName() {
            return PaymentMethodName;
        }
        public String getPaymentMethodUrl() {
            return PaymentMethodUrl;
        }
        public String getPaymentMethodCode() {
            return PaymentMethodCode;
        }
    }
}
