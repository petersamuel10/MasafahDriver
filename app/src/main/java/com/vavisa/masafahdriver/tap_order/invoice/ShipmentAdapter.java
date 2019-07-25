package com.vavisa.masafahdriver.tap_order.invoice;

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
import com.vavisa.masafahdriver.common_model.ShipmentModel;

import java.util.ArrayList;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {

    private ArrayList<ShipmentModel> shipmentList;
    private Context context;

    public ShipmentAdapter(ArrayList<ShipmentModel> shipmentList) {
        this.shipmentList = shipmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_shipment_paid, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(shipmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return shipmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shipment_number_txt,
                shipment_content_txt,
                pickup_location_txt,
                drop_location_txt,
                pick_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location_area);
            pick_time = itemView.findViewById(R.id.pick_time);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }

        public void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getCity().getName());
            drop_location_txt.setText(shipmentModel.getAddress_to().getCity().getName());

            if (shipmentModel.getIs_today()) {
                pick_time.setText(context.getString(R.string.today));
                pick_time.setTextColor(Color.parseColor("#3F82DC"));
                pick_time.setTypeface(Typeface.DEFAULT_BOLD);
            } else
                pick_time.setText(shipmentModel.getPickup_time_from().concat(" ").concat(context.getString(R.string.to)).concat(" ")
                        .concat(shipmentModel.getPickup_time_to()));


            StringBuilder item_str = new StringBuilder();
            for (Items item : shipmentModel.getItems()) {
                item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
            }

            shipment_content_txt.setText(item_str.toString());
        }
    }
}
