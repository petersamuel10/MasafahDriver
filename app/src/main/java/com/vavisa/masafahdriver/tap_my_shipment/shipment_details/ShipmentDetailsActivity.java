package com.vavisa.masafahdriver.tap_my_shipment.shipment_details;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseActivity;

public class ShipmentDetailsActivity extends BaseActivity implements ShipmentDetailsViews {

    private ScrollView scrollView;
    private TextView shipment_description,
            pickup_location, pickup_block_street, pickup_building, pickup_mobile,
            drop_location, drop_block_street, drop_building, drop_mobile,
            time_from_txt, total_txt;

    private Button pickup_btn;
    private ShipmentDetailsPresenter presenter;
    private String shipment_id, pickup_mobile_str, drop_mobile_str;
    private ShipmentModel shipmentModel;
    private ShipmmentUpdateCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_details);
        initViews();

        this.callback = (ShipmmentUpdateCallback) getIntent().getSerializableExtra("interface");

        presenter = new ShipmentDetailsPresenter();
        presenter.attachView(this);
        presenter.getDetails(getIntent().getStringExtra("shipment_id"));

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

        AlertDialog.Builder callAlert = new AlertDialog.Builder(this);
        callAlert.setTitle(R.string.call);
        callAlert.setMessage(getString(R.string.are_you_want_to_call) + " " + mobile_str + " " + getString(R.string.question_mark));

        callAlert.setPositiveButton(R.string.call, (dialog, which) -> {
            boolean call = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
            if (call) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_str));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        callAlert.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        callAlert.create().show();
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.shipment_details_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scrollView = findViewById(R.id.scrollView);
        shipment_description = findViewById(R.id.shipment_description);
        pickup_location = findViewById(R.id.pickup_location);
        pickup_block_street = findViewById(R.id.pickup_block_street);
        pickup_building = findViewById(R.id.pickup_building);
        pickup_mobile = findViewById(R.id.pickup_mobile);
        drop_location = findViewById(R.id.drop_location);
        drop_block_street = findViewById(R.id.drop_block_street);
        drop_building = findViewById(R.id.drop_building);
        drop_mobile = findViewById(R.id.drop_mobile);
        time_from_txt = findViewById(R.id.time_from);
        total_txt = findViewById(R.id.total_amount);
        pickup_btn = findViewById(R.id.pickup_btn);
    }

    @Override
    public void displayDetails(ShipmentModel shipmentModel) {

        this.shipment_id = shipmentModel.getId();
        this.shipmentModel = shipmentModel;

        scrollView.setVisibility(View.VISIBLE);
        StringBuilder item_str = new StringBuilder();
        for (ShipmentModel.Items item : shipmentModel.getItems()) {
            item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
        }

        shipment_description.setText(item_str.toString());
        pickup_location.setText(shipmentModel.getAddress_from().getArea());
        pickup_block_street.setText(shipmentModel.getAddress_from().getBlock() + " - "
                + shipmentModel.getAddress_from().getStreet());
        pickup_building.setText(shipmentModel.getAddress_from().getBuilding() + " - ");
        pickup_mobile.setText(shipmentModel.getAddress_from().getMobile());
        pickup_mobile_str = shipmentModel.getAddress_from().getMobile();

        drop_location.setText(shipmentModel.getAddress_to().getArea());
        drop_block_street.setText(shipmentModel.getAddress_to().getBlock() + " - "
                + shipmentModel.getAddress_from().getStreet());
        drop_building.setText(shipmentModel.getAddress_to().getBuilding() + " - ");
        drop_mobile.setText(shipmentModel.getAddress_to().getMobile());
        drop_mobile_str = shipmentModel.getAddress_to().getMobile();

        if (shipmentModel.getIs_today()) {
            time_from_txt.setText(getString(R.string.today));
            time_from_txt.setTextColor(Color.parseColor("#3F82DC"));
            time_from_txt.setTypeface(Typeface.DEFAULT_BOLD);
        } else
            time_from_txt.setText(shipmentModel.getPickup_time_from() + " " + getString(R.string.to) + " " + shipmentModel.getPickup_time_to());
        total_txt.setText(shipmentModel.getPrice() + " " + getString(R.string.kd));

        if (shipmentModel.getStatus().equals("3"))
            pickup_btn.setText(getString(R.string.delivered));
        else
            pickup_btn.setText(getString(R.string.pickup));

    }

    @Override
    public void handlePickupAndDelivery(boolean isPickup, String msg) {
        showMessage(msg);
        if (isPickup)
            callback.handlePickedAction(shipmentModel);
        else
            callback.handleDeliveredAction(shipmentModel);
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
