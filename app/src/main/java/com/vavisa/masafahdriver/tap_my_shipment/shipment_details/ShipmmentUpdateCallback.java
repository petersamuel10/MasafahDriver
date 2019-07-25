package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import com.vavisa.masafahdriver.common_model.ShipmentModel;

public interface ShipmmentUpdateCallback {
    void handleDeliveredAction(ShipmentModel shipmentModel);

    void handlePickedAction(ShipmentModel shipmentModel);

}
