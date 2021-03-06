package com.vavisa.masafahdriver.tap_my_shipment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.MainActivity;
import com.vavisa.masafahdriver.common_model.Items;
import com.vavisa.masafahdriver.common_model.Shipment;
import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.tap_my_shipment.shipment_details.ShipmentDetailsFragment;
import com.vavisa.masafahdriver.util.Constants;

import java.util.ArrayList;

public class MyShipmentAdapter extends RecyclerView.Adapter<MyShipmentAdapter.ViewHolder> {

    private ArrayList<ShipmentModel> myShipmentList;
    private Context context;
    private FragmentActivity activity;
    private MyShipmentsFragment myShipmentsFragment;

    MyShipmentAdapter(ArrayList<ShipmentModel> myShipmentList, FragmentActivity activity, MyShipmentsFragment myShipmentsFragment) {
        this.myShipmentList = myShipmentList;
        this.activity = activity;
        this.myShipmentsFragment = myShipmentsFragment;
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

            Bundle bundle = new Bundle();
            bundle.putString("shipment_id", myShipmentList.get(position).getId());
            Fragment fragment = new ShipmentDetailsFragment();
            fragment.setTargetFragment(myShipmentsFragment, 101);
            fragment.setArguments(bundle);

            ((MainActivity) activity).pushFragments(Constants.TAB_SHIPMENT, fragment, true);

        });
    }

    @Override
    public int getItemCount() {
        return myShipmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView shipment_number_txt,
                shipment_content_txt,
                pickup_location_txt,
                drop_location_txt,
                pick_time;
        ImageView picked_img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location);
            pick_time = itemView.findViewById(R.id.pick_time);
            picked_img = itemView.findViewById(R.id.picked_img);

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

            if (shipmentModel.getStatus().equals("3"))
                picked_img.setImageResource((Constants.LANGUAGE.equals("en")) ? R.drawable.ic_picked_en : R.drawable.ic_picked_ar);

            drop_location_txt.setText(drop_address_str);
            shipment_content_txt.setText(item_str.toString());

        }
    }

    void updateShipmentItem(ShipmentModel shipmentModel) {
        int index = getShipmentIndex(shipmentModel);
        shipmentModel.setStatus("3");
        myShipmentList.set(index, shipmentModel);
        notifyDataSetChanged();
    }

    void removeShipmentItem(ShipmentModel shipmentModel) {
        int index = getShipmentIndex(shipmentModel);
        myShipmentList.remove(index);
        notifyItemRemoved(index);
    }

    private int getShipmentIndex(ShipmentModel shipment) {
        for (int i = 0; i < myShipmentList.size(); i++) {
            if (shipment.getId().equals(myShipmentList.get(i).getId()))
                return i;
        }
        return 0;
    }

}