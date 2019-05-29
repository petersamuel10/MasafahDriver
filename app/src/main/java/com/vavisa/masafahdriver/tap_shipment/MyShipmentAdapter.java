package com.vavisa.masafahdriver.tap_shipment;

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
import com.vavisa.masafahdriver.tap_order.OrderModel;

import java.util.List;

public class MyShipmentAdapter extends RecyclerView.Adapter<MyShipmentAdapter.ViewHolder> {

    private boolean isLongClick = false;
    private List<OrderModel> orderList;
    private Context context;
    private MyShipmentsFragment orderFragment;

    public MyShipmentAdapter(MyShipmentsFragment orderFragment, List<OrderModel> orderList) {
        this.orderFragment = orderFragment;
        this.orderList = orderList;
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
    public void onBindViewHolder(@NonNull ViewHolder myShipmentsViewHolder, int i) {

        final View itemView = myShipmentsViewHolder.itemView;
        final int position = myShipmentsViewHolder.getAdapterPosition();


        myShipmentsViewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ShipmentDetailsActivity.class));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return 3;
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