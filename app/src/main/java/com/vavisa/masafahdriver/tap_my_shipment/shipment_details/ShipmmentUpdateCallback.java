package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import com.vavisa.masafahdriver.activities.ShipmentModel;

import java.io.Serializable;

public interface ShipmmentUpdateCallback extends Serializable {
    void handleDeliveredAction(ShipmentModel shipmentModel);
    void handlePickedAction(ShipmentModel shipmentModel);

}
