package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseView;

public interface ShipmentDetailsViews extends BaseView {

    void displayDetails(ShipmentModel shipmentModel);
    void handlePickupAndDelivery(String action,String msg);
}
