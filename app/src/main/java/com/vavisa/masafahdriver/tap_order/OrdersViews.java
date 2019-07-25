package com.vavisa.masafahdriver.tap_order;

import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseView;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.tap_order.invoice.InvoiceModel;

import java.util.ArrayList;

public interface OrdersViews extends BaseView {

    void displayShipment(ArrayList<ShipmentModel> shipmentList);
    void payment(InvoiceModel paymentModel);
    void displayMyCities(ArrayList<CountryModel> citiesList);
}
