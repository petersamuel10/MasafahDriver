package com.vavisa.masafahdriver.tap_my_shipment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.tap_my_shipment.shipment_details.ShipmentDetailsActivity;
import com.vavisa.masafahdriver.tap_my_shipment.shipment_details.ShipmmentUpdateCallback;
import com.vavisa.masafahdriver.util.Constants;

import java.util.ArrayList;

public class MyShipmentAdapter extends RecyclerView.Adapter<MyShipmentAdapter.ViewHolder> {

    private ArrayList<ShipmentModel> myShipmentList;
    private Context context;
    private ShipmmentUpdateCallback callback;


    public MyShipmentAdapter(ShipmmentUpdateCallback callback, ArrayList<ShipmentModel> myShipmentList) {
        this.callback = callback;
        this.myShipmentList = myShipmentList;

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

        holder.bind(myShipmentList.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShipmentDetailsActivity.class);
            intent.putExtra("shipment_id", myShipmentList.get(position).getId());
            intent.putExtra("interface", callback);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myShipmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shipment_number_txt,
                shipment_content_txt,
                pickup_location_txt,
                drop_location_txt,
                pick_time;
        ImageView picked_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location_area);
            pick_time = itemView.findViewById(R.id.pick_time);
            picked_img = itemView.findViewById(R.id.picked_img);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }

        public void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getArea());
            drop_location_txt.setText(shipmentModel.getAddress_to().getArea());

            if (shipmentModel.getIs_today()) {
                pick_time.setText(context.getString(R.string.today));
                pick_time.setTextColor(Color.parseColor("#3F82DC"));
                pick_time.setTypeface(Typeface.DEFAULT_BOLD);
            } else
                pick_time.setText(shipmentModel.getPickup_time_from() + " " +
                        context.getString(R.string.to) + " " + shipmentModel.getPickup_time_to());

            if (shipmentModel.getStatus().equals("3"))
                picked_img.setImageResource((Constants.LANGUAGE.equals("en")) ? R.drawable.ic_picked_en : R.drawable.ic_picked_ar);

            StringBuilder item_str = new StringBuilder();
            for (ShipmentModel.Items item : shipmentModel.getItems()) {
                item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
            }

            shipment_content_txt.setText(item_str.toString());
        }
    }

    public void updateShipmentItem(ShipmentModel shipmentModel) {
        int index = myShipmentList.indexOf(shipmentModel);
        shipmentModel.setStatus("3");
        myShipmentList.set(index, shipmentModel);
        notifyDataSetChanged();
    }

    public void removeShipmentItem(ShipmentModel shipmentModel) {
        int index = myShipmentList.indexOf(shipmentModel);
        myShipmentList.remove(shipmentModel);
        notifyItemRemoved(index);
    }

}