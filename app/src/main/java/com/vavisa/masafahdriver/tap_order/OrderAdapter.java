package com.vavisa.masafahdriver.tap_order;

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

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private boolean isLongClick = false;
    private List<OrderModel> orderList;
    private int selectedCount = 0;
    private Context context;
    private OrderFragment orderFragment;

    public OrderAdapter(OrderFragment orderFragment, List<OrderModel> orderList) {
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

        myShipmentsViewHolder.itemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        isLongClick = true;
                        orderFragment.deliveryButtonLayout.setVisibility(View.VISIBLE);

                        if (orderList.get(position).isSelected()) {
                            itemView.setBackground(
                                    context.getResources().getDrawable(R.drawable.rounded_corners_white_filled));
                            orderList.get(position).setSelected(false);
                            selectedCount--;
                            orderFragment.deliveryNow.setText("Delivery now (" + selectedCount + ")");

                            if (selectedCount == 0) {
                                isLongClick = false;
                                orderFragment.deliveryButtonLayout.setVisibility(View.GONE);
                            }
                        } else {
                            orderList.get(position).setSelected(true);
                            selectedCount++;
                            itemView.setBackground(
                                    context.getResources().getDrawable(R.drawable.rounded_primary_border));

                            orderFragment.deliveryNow.setText("Delivery now (" + selectedCount + ")");
                        }

                        return true;
                    }
                });

        myShipmentsViewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isLongClick) {
                            context.startActivity(new Intent(context, ShipmentDetailsActivity.class));
                        } else {
                            if (orderList.get(position).isSelected()) {
                                itemView.setBackground(
                                        context.getResources().getDrawable(R.drawable.rounded_corners_white_filled));
                                orderList.get(position).setSelected(false);
                                selectedCount--;

                                orderFragment.deliveryNow.setText("Delivery now (" + selectedCount + ")");

                                if (selectedCount == 0) {
                                    isLongClick = false;
                                    orderFragment.deliveryButtonLayout.setVisibility(View.GONE);
                                }
                            } else {
                                itemView.setBackground(
                                        context.getResources().getDrawable(R.drawable.rounded_primary_border));
                                orderList.get(position).setSelected(true);
                                selectedCount++;
                                orderFragment.deliveryNow.setText("Delivery now (" + selectedCount + ")");
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
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

