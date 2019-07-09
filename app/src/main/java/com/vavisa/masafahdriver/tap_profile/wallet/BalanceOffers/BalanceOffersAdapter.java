package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;

import java.util.ArrayList;

public class BalanceOffersAdapter extends RecyclerView.Adapter<BalanceOffersAdapter.ViewHolder> {

    private ArrayList<BalanceOfferModel> balanceList;
    private Context context;
    private int selectedPosition = -1;
    private BalanceOfferModel selectedPackage;

    public BalanceOffersAdapter(ArrayList<BalanceOfferModel> balanceList) {
        this.balanceList = balanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bind(balanceList.get(position));

        if (position == selectedPosition)
            holder.balance_layer.setBackground(context.getResources().getDrawable(R.drawable.rounded_primary_border));
        else
            holder.balance_layer.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_white_filled));

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            selectedPackage = balanceList.get(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return balanceList.size();
    }

    public BalanceOfferModel getSelectedPackage() {
        return selectedPackage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout balance_layer;
        TextView amount, offer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            balance_layer = itemView.findViewById(R.id.balance_layer);
            amount = itemView.findViewById(R.id.amount);
            offer = itemView.findViewById(R.id.offer);
        }


        private void bind(BalanceOfferModel balance) {

            amount.setText(String.format("%.3f",Float.valueOf(balance.getAmount())) + " " + context.getString(R.string.kd));
            offer.setText(balance.getFree_deliveries() + " " + context.getString(R.string.free_delevery));
        }
    }
}
