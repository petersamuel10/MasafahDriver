package com.vavisa.masafahdriver.tap_profile.shipment_history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;


public class ShipmentHistoryFragment extends BaseFragment implements ShipmentHistoryViews {

    private View fragment;
    private RecyclerView shipment_rec;
    private ImageView noShipmentsImage;
    private TextView noShipmentsMessage;
    private ShipmentHistoryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_shipment_history, container, false);

            shipment_rec = fragment.findViewById(R.id.my_shipments_list);
            noShipmentsImage = fragment.findViewById(R.id.no_shipments_image);
            noShipmentsMessage = fragment.findViewById(R.id.no_shipments_message);

            shipment_rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            shipment_rec.addItemDecoration(new BottomSpaceItemDecoration(50));

            presenter = new ShipmentHistoryPresenter();
            presenter.attachView(this);
            presenter.getShipment();

        }

        return fragment;
    }

    @Override
    public void displayShipments(ArrayList<ShipmentModel> shipmentList) {

        if (shipmentList.size() == 0) {
            noShipmentsMessage.setVisibility(View.VISIBLE);
            noShipmentsImage.setVisibility(View.VISIBLE);
        } else
            shipment_rec.setAdapter(new MyShipmentsDeliveredAdapter(shipmentList));


    }
}
