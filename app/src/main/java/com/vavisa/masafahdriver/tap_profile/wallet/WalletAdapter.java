package com.vavisa.masafahdriver.tap_profile.wallet;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private ArrayList<WalletModel.TransactionDetails> walletList;
    private Context context;

    public WalletAdapter(ArrayList<WalletModel.TransactionDetails> walletList) {
        this.walletList = walletList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(walletList.get(position));
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, amount, order_id_tag, order_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            order_id_tag = itemView.findViewById(R.id.order_id_tag);
            order_id = itemView.findViewById(R.id.order_id);
        }

        public void bind(WalletModel.TransactionDetails transaction) {

            if (transaction.getIn()) {
                amount.setTextColor(context.getResources().getColor(R.color.green));
                order_id_tag.setVisibility(View.GONE);
                order_id.setVisibility(View.GONE);
                amount.setText("+ " + String.format("%.3f", Float.valueOf(transaction.getAmount())) + " " + context.getString(R.string.kd));
            } else {
                order_id_tag.setVisibility(View.VISIBLE);
                order_id.setVisibility(View.VISIBLE);
                amount.setTextColor(Color.parseColor("#ffcc0000"));
                amount.setText("- " + String.format("%.3f", Float.valueOf(transaction.getAmount())) + " " + context.getString(R.string.kd));
            }

            date.setText(transaction.getCreated_at());
            order_id.setText(transaction.getOrder_id());
        }
    }
}
