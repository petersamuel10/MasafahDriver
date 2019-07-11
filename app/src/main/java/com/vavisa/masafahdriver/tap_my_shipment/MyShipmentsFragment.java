package com.vavisa.masafahdriver.tap_my_shipment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.tap_my_shipment.shipment_details.ShipmmentUpdateCallback;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;

@SuppressLint("ParcelCreator")
public class MyShipmentsFragment extends BaseFragment implements MyShipmentViews, ShipmmentUpdateCallback {

    private transient View fragment;
    private transient RecyclerView myShipment_rec;
    private transient NestedScrollView scrollView;
    private transient ImageView noShipmentsImage;
    private transient TextView noShipmentsMessage;
    private transient MyShipmentPresenter presenter;
    private transient MyShipmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_my_shipments, container, false);

            scrollView = fragment.findViewById(R.id.scrollView);
            myShipment_rec = fragment.findViewById(R.id.my_shipment_list);
            noShipmentsImage = fragment.findViewById(R.id.no_shipments_image);
            noShipmentsMessage = fragment.findViewById(R.id.no_shipments_message);

            myShipment_rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            myShipment_rec.addItemDecoration(new BottomSpaceItemDecoration(50));
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_UP));

            presenter = new MyShipmentPresenter();
            presenter.attachView(this);
            presenter.getMyShipment();

        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        return fragment;
    }

    @Override
    public void displayMyShipment(ArrayList<ShipmentModel> myShipmentList) {

        if (myShipmentList.size() == 0) {
            noShipmentsMessage.setVisibility(View.VISIBLE);
            noShipmentsImage.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            adapter = new MyShipmentAdapter(this, myShipmentList);
            myShipment_rec.setAdapter(adapter);
        }
    }

    @Override
    public void handlePickedAction(ShipmentModel shipmentModel) {
        adapter.updateShipmentItem(shipmentModel);
    }

    @Override
    public void handleDeliveredAction(ShipmentModel shipmentModel) {
        adapter.removeShipmentItem(shipmentModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
