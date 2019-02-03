package com.vavisa.masafahdriver.fragments;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentDetailsActivity;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;

public class OrderFragment extends BaseFragment {

  private View fragment;
  private RecyclerView myShipmentListView;
  private boolean isLongClick = false;
  private List<MyShipments> myShipments;
  private int selectedCount = 0;
  private RelativeLayout deliveryButtonLayout;
  private Button deliveryNow;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragment == null) {
      fragment = inflater.inflate(R.layout.fragment_orders, container, false);
      Toolbar toolbar = fragment.findViewById(R.id.orders_toolbar);
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

      getActivity().setTitle("");

      final ScrollView scrollView = fragment.findViewById(R.id.scrollView);
      deliveryButtonLayout = fragment.findViewById(R.id.delivery_button_layout);
      deliveryNow = deliveryButtonLayout.findViewById(R.id.deliver_now_button);

      scrollView.post(
          new Runnable() {
            @Override
            public void run() {
              scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
          });

      final ConstraintLayout locationLayout = fragment.findViewById(R.id.location_layout);

      locationLayout.post(
          new Runnable() {
            @Override
            public void run() {
              int height = locationLayout.getHeight();
              RelativeLayout.LayoutParams layoutParams =
                  (RelativeLayout.LayoutParams) locationLayout.getLayoutParams();
              layoutParams.topMargin = -(height / 2);
              locationLayout.setLayoutParams(layoutParams);
            }
          });

      myShipmentListView = fragment.findViewById(R.id.my_shipment_list);
      myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

      myShipmentListView.setAdapter(new MyShipmentsAdapter());

      myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

      myShipments = new ArrayList<>();

      myShipments.add(new MyShipments());
      myShipments.add(new MyShipments());
      myShipments.add(new MyShipments());

    } else {
      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(0).setChecked(true);
    }

    return fragment;
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

  private class MyShipmentsAdapter extends RecyclerView.Adapter<MyShipmentsViewHolder> {

    @NonNull
    @Override
    public MyShipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.my_shipments_list_item, viewGroup, false);

      return new MyShipmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyShipmentsViewHolder myShipmentsViewHolder, int i) {

      final View itemView = myShipmentsViewHolder.itemView;
      final int position = myShipmentsViewHolder.getAdapterPosition();

      myShipmentsViewHolder.itemView.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

              isLongClick = true;
              deliveryButtonLayout.setVisibility(View.VISIBLE);

              if (myShipments.get(position).isSelected) {
                itemView.setBackground(
                    getResources().getDrawable(R.drawable.rounded_corners_white_filled));
                myShipments.get(position).setSelected(false);
                selectedCount--;
                deliveryNow.setText("Delivery now (" + selectedCount + ")");

                if (selectedCount == 0) {
                  isLongClick = false;
                  deliveryButtonLayout.setVisibility(View.GONE);
                }
              } else {
                myShipments.get(position).setSelected(true);
                selectedCount++;
                itemView.setBackground(
                    getResources().getDrawable(R.drawable.rounded_primary_border));

                deliveryNow.setText("Delivery now (" + selectedCount + ")");
              }

              return true;
            }
          });

      myShipmentsViewHolder.itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (!isLongClick) {
                startActivity(new Intent(getActivity(), ShipmentDetailsActivity.class));
              } else {
                if (myShipments.get(position).isSelected) {
                  itemView.setBackground(
                      getResources().getDrawable(R.drawable.rounded_corners_white_filled));
                  myShipments.get(position).setSelected(false);
                  selectedCount--;

                  deliveryNow.setText("Delivery now (" + selectedCount + ")");

                  if (selectedCount == 0) {
                    isLongClick = false;
                    deliveryButtonLayout.setVisibility(View.GONE);
                  }
                } else {
                  itemView.setBackground(
                      getResources().getDrawable(R.drawable.rounded_primary_border));
                  myShipments.get(position).setSelected(true);
                  selectedCount++;
                  deliveryNow.setText("Delivery now (" + selectedCount + ")");
                }
              }
            }
          });
    }

    @Override
    public int getItemCount() {
      return myShipments.size();
    }
  }

  private class MyShipments {
    private boolean isSelected;

    public boolean isSelected() {
      return isSelected;
    }

    public void setSelected(boolean selected) {
      isSelected = selected;
    }
  }
}
