package com.vavisa.masafahdriver.tap_profile.shipment_history;

import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseView;

import java.util.ArrayList;

public interface ShipmentHistoryViews extends BaseView {

    void displayShipments(ArrayList<ShipmentModel> shipmentList);
}
