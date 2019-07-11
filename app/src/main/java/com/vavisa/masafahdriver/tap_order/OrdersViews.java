package com.vavisa.masafahdriver.tap_order;

import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseView;

import java.util.ArrayList;

public interface OrdersViews extends BaseView {

    void displayShipment(ArrayList<ShipmentModel> shipmentList);
}
