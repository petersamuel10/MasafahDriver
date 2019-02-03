package com.vavisa.masafahdriver.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;

public class ShipmentHistoryFragment extends BaseFragment implements View.OnClickListener {

  private View fragment;
  private RecyclerView myShipmentListView;
  private List<String> myShipments = new ArrayList<>();
  private ImageView noShipmentsImage;
  private TextView noShipmentsMessage;
  private Button deliveredButton;
  private Button canceledButton;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragment == null) {
      fragment = inflater.inflate(R.layout.fragment_shipment_history, container, false);

      Toolbar toolbar = fragment.findViewById(R.id.shipment_history_toolbar);
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

      myShipmentListView = fragment.findViewById(R.id.my_shipments_list);
      noShipmentsImage = fragment.findViewById(R.id.no_shipments_image);
      noShipmentsMessage = fragment.findViewById(R.id.no_shipments_message);

      final ConstraintLayout buttonsLayout = fragment.findViewById(R.id.buttons_layout);

      deliveredButton = buttonsLayout.findViewById(R.id.delivered_button);
      canceledButton = buttonsLayout.findViewById(R.id.canceled_button);

      buttonsLayout.post(
          new Runnable() {
            @Override
            public void run() {
              int height = buttonsLayout.getHeight();
              RelativeLayout.LayoutParams layoutParams =
                  (RelativeLayout.LayoutParams) buttonsLayout.getLayoutParams();
              layoutParams.topMargin = -(height / 2);
              buttonsLayout.setLayoutParams(layoutParams);
            }
          });

      deliveredButton.setOnClickListener(this);
      canceledButton.setOnClickListener(this);

      myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    } else {
      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(1).setChecked(true);
    }

    myShipments.add("Test");

    myShipmentListView.setAdapter(new MyShipmentsDeliveredAdapter());

    myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

    if (myShipments.size() == 0) {
      noShipmentsMessage.setVisibility(View.VISIBLE);
      noShipmentsImage.setVisibility(View.VISIBLE);
    }

    return fragment;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.delivered_button:
        deliveredButton.setBackground(
            getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
        deliveredButton.setTextColor(getResources().getColor(android.R.color.white));
        canceledButton.setBackground(null);
        canceledButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        myShipmentListView.setAdapter(new MyShipmentsDeliveredAdapter());
        break;

      case R.id.canceled_button:
        canceledButton.setBackground(
            getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
        deliveredButton.setBackground(null);
        canceledButton.setTextColor(getResources().getColor(android.R.color.white));
        deliveredButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        myShipmentListView.setAdapter(new MyShipmentsCanceledAdapter());
        break;
    }
  }

  private class MyShipmentsViewHolder extends RecyclerView.ViewHolder {

    public MyShipmentsViewHolder(@NonNull View itemView) {
      super(itemView);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        itemView.setClipToOutline(true);
      }
    }
  }

  private class MyShipmentsCanceledAdapter extends RecyclerView.Adapter<MyShipmentsViewHolder> {

    @NonNull
    @Override
    public MyShipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.shipment_history_canceled_list_item, viewGroup, false);

      return new MyShipmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyShipmentsViewHolder myShipmentsViewHolder, int i) {}

    @Override
    public int getItemCount() {
      return myShipments.size();
    }
  }

  private class MyShipmentsDeliveredAdapter extends RecyclerView.Adapter<MyShipmentsViewHolder> {

    @NonNull
    @Override
    public MyShipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.shipment_history_delivered_list_item, viewGroup, false);

      return new MyShipmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyShipmentsViewHolder myShipmentsViewHolder, int i) {}

    @Override
    public int getItemCount() {
      return myShipments.size();
    }
  }
}
