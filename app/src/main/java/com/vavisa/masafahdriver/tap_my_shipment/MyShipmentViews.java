package com.vavisa.masafahdriver.tap_my_shipment;

import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseView;

import java.io.Serializable;
import java.util.ArrayList;

public interface MyShipmentViews extends BaseView , Serializable {

    void displayMyShipment(ArrayList<ShipmentModel> shipmentList);
}
