package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.common_model.Items;
import com.vavisa.masafahdriver.common_model.ShipmentModel;

public class ShipmentDetailsFragment extends BaseFragment implements ShipmentDetailsViews {

    private ScrollView scrollView;
    private TextView shipment_description,
            pickup_location, pickup_block_street, pickup_building, pickup_mobile,
            drop_location, drop_block_street, drop_building, drop_mobile,
            time_from_txt, total_txt;

    private Button pickup_btn;
    private ShipmentDetailsPresenter presenter;
    private String shipment_id, pickup_mobile_str, drop_mobile_str;
    private ShipmentModel shipmentModel;

    public ShipmentDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shipment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = view.findViewById(R.id.scrollView);
        shipment_description = view.findViewById(R.id.shipment_description);
        pickup_location = view.findViewById(R.id.pickup_location);
        pickup_block_street = view.findViewById(R.id.pickup_block_street);
        pickup_building = view.findViewById(R.id.pickup_building);
        pickup_mobile = view.findViewById(R.id.pickup_mobile);
        drop_location = view.findViewById(R.id.drop_location);
        drop_block_street = view.findViewById(R.id.drop_block_street);
        drop_building = view.findViewById(R.id.drop_building);
        drop_mobile = view.findViewById(R.id.drop_mobile);
        time_from_txt = view.findViewById(R.id.time_from);
        total_txt = view.findViewById(R.id.total_amount);
        pickup_btn = view.findViewById(R.id.pickup_btn);

        presenter = new ShipmentDetailsPresenter();
        presenter.attachView(this);
        presenter.getDetails(getArguments().getString("shipment_id"));

        pickup_btn.setOnClickListener(v -> {
            if (pickup_btn.getText().toString().equals(getString(R.string.pickup)))
                presenter.pickupShipment(shipment_id);
            else
                presenter.deliveredShipment(shipment_id);
        });

        pickup_mobile.setOnClickListener(v -> showCallAlert(pickup_mobile_str));
        drop_mobile.setOnClickListener(v -> showCallAlert(drop_mobile_str));

    }

    private void showCallAlert(String mobile_str) {

        AlertDialog.Builder callAlert = new AlertDialog.Builder(getContext());
        callAlert.setTitle(R.string.call);
        callAlert.setMessage(getString(R.string.are_you_want_to_call) + " " + mobile_str + " " + getString(R.string.question_mark));

        callAlert.setPositiveButton(R.string.call, (dialog, which) -> {
            boolean call = getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
            if (call) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_str));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        callAlert.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        callAlert.create().show();
    }

    @Override
    public void displayDetails(ShipmentModel shipmentModel) {
        this.shipment_id = shipmentModel.getId();
        this.shipmentModel = shipmentModel;

        scrollView.setVisibility(View.VISIBLE);
        StringBuilder item_str = new StringBuilder();
        for (Items item : shipmentModel.getItems()) {
            item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
        }

        shipment_description.setText(item_str.toString());
        pickup_location.setText(shipmentModel.getAddress_from().getCity().getName());
        pickup_block_street.setText(shipmentModel.getAddress_from().getBlock().concat(" - ")
                .concat(shipmentModel.getAddress_from().getStreet()));
        pickup_building.setText(shipmentModel.getAddress_from().getBuilding().concat(" - "));
        pickup_mobile.setText(shipmentModel.getAddress_from().getMobile());
        pickup_mobile_str = shipmentModel.getAddress_from().getMobile();

        drop_location.setText(shipmentModel.getAddress_to().getCity().getName());
        drop_block_street.setText(shipmentModel.getAddress_to().getBlock().concat(" - ")
                .concat(shipmentModel.getAddress_from().getStreet()));
        drop_building.setText(shipmentModel.getAddress_to().getBuilding().concat(" - "));
        drop_mobile.setText(shipmentModel.getAddress_to().getMobile());
        drop_mobile_str = shipmentModel.getAddress_to().getMobile();

        if (shipmentModel.getIs_today()) {
            time_from_txt.setText(getString(R.string.today));
            time_from_txt.setTextColor(Color.parseColor("#3F82DC"));
            time_from_txt.setTypeface(Typeface.DEFAULT_BOLD);
        } else
            time_from_txt.setText(shipmentModel.getPickup_time_from().concat(" ").concat(getString(R.string.to)).concat(" ")
                    .concat(shipmentModel.getPickup_time_to()));
        total_txt.setText(shipmentModel.getPrice().concat(" ").concat(getString(R.string.kd)));

        if (shipmentModel.getStatus().equals("3"))
            pickup_btn.setText(getString(R.string.delivered));
        else
            pickup_btn.setText(getString(R.string.pickup));
    }

    @Override
    public void handlePickupAndDelivery(String action, String msg) {
        showMessage(msg);
        Intent intent = new Intent();
        intent.putExtra("action", action);
        intent.putExtra("datafrom", shipmentModel);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                intent);
        getActivity().onBackPressed();

    }
}
