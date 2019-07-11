package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import com.vavisa.masafahdriver.activities.ShipmentModel;

public interface ShipmmentUpdateCallback {
    void handleDeliveredAction(ShipmentModel shipmentModel);

    void handlePickedAction(ShipmentModel shipmentModel);

}
