package com.vavisa.masafahdriver.tap_profile.shipment_history;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.ShipmentDetailsActivity;

public class MyShipmentsCanceledAdapter extends RecyclerView.Adapter<MyShipmentsCanceledAdapter.ViewHolder> {

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.shipment_history_canceled_list_item, viewGroup, false);

        context = viewGroup.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ShipmentDetailsActivity.class));
                    }
                });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }
    }
}
