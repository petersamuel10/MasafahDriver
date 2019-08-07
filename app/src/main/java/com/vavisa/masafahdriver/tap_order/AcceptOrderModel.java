package com.vavisa.masafahdriver.tap_order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptOrderModel {

    @SerializedName("shipment_ids")
    private List<String> shipment_ids;
    @SerializedName("use_free_deliveries")
    private boolean use_free_deliveries;

    public AcceptOrderModel(List<String> shipment_ids, boolean use_free_deliveries) {
        this.shipment_ids = shipment_ids;
        this.use_free_deliveries = use_free_deliveries;
    }

    public void setShipment_ids(List<String> shipment_ids) {
        this.shipment_ids = shipment_ids;
    }
    public void setUse_free_deliveries(boolean use_free_deliveries) {
        this.use_free_deliveries = use_free_deliveries;
    }
}
