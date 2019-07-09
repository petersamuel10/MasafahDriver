package com.vavisa.masafahdriver.tap_profile.termsAndConditions;

import com.google.gson.annotations.SerializedName;

public class TermsModel {

    @SerializedName("terms_and_conditions")
    private String terms_and_conditions;

    public String getTerms_and_conditions() {
        return terms_and_conditions;
    }
    public void setTerms_and_conditions(String terms_and_conditions) {
        this.terms_and_conditions = terms_and_conditions;
    }
}
