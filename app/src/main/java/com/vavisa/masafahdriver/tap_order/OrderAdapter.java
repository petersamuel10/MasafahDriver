package com.vavisa.masafahdriver.tap_order;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.common_model.Items;
import com.vavisa.masafahdriver.common_model.Shipment;
import com.vavisa.masafahdriver.common_model.ShipmentModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<ShipmentModel> orderList, selectedShipment;
    private int selectedCount = 0;
    private Context context;
    private OrderFragment orderFragment;

    OrderAdapter(OrderFragment orderFragment, ArrayList<ShipmentModel> orderList) {
        this.orderFragment = orderFragment;
        this.orderList = orderList;
        selectedShipment = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.my_shipments_list_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(orderList.get(position));

        final View itemView = holder.itemView;

        holder.itemView.setOnClickListener(
                v -> {
                    orderFragment.deliveryButtonLayout.setVisibility(View.VISIBLE);

                    if (orderList.get(position).isSelected()) {
                        itemView.setBackground(
                                context.getResources().getDrawable(R.drawable.rounded_corners_white_filled));
                        orderList.get(position).setSelected(false);
                        selectedShipment.remove(orderList.get(position));
                        selectedCount--;
                        orderFragment.deliveryNow.setText(context.getString(R.string.accepted).concat("(").concat(String.valueOf(selectedCount)).concat(")"));

                        if (selectedCount == 0) {
                            orderFragment.deliveryButtonLayout.setVisibility(View.GONE);
                        }
                    } else {
                        orderList.get(position).setSelected(true);
                        selectedShipment.add(orderList.get(position));
                        selectedCount++;
                        itemView.setBackground(
                                context.getResources().getDrawable(R.drawable.rounded_primary_border));

                        orderFragment.deliveryNow.setText(context.getString(R.string.accepted).concat("(").concat(String.valueOf(selectedCount)).concat(")"));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView shipment_number_txt,
                shipment_content_txt,
                pickup_location_txt,
                drop_location_txt,
                pick_time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location);
            pick_time = itemView.findViewById(R.id.pick_time);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }

        void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getCity().getName());
            StringBuilder item_str = new StringBuilder();
            StringBuilder drop_address_str = new StringBuilder();

            for (Items item : shipmentModel.getItems()) {
                drop_address_str.append("\u25CF").append(item.getAddress_to().getCity().getName()).append("\n");
                item_str.append("\n\u25CF ").append(item.getAddress_to().getCity().getName()).append("\n");
                for (Shipment shipment : item.getProducts()) {
                    item_str.append("\t\t\t\u25CF").append(shipment.getQuantity()).append(" x   ").append(shipment.getCategory_name()).append("\n");
                }
            }

            if (shipmentModel.getIs_today()) {
                pick_time.setText(context.getString(R.string.today));
                pick_time.setTextColor(Color.parseColor("#3F82DC"));
                pick_time.setTypeface(Typeface.DEFAULT_BOLD);
            } else
                pick_time.setText(shipmentModel.getPickup_time_from().concat(" ").concat(
                        context.getString(R.string.to)).concat(" ").concat(shipmentModel.getPickup_time_to()));

            drop_location_txt.setText(drop_address_str);
            shipment_content_txt.setText(item_str.toString());
        }
    }

    ArrayList<ShipmentModel> getSelectedShipment() {
        return selectedShipment;
    }

}

