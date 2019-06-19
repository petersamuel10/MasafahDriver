package com.vavisa.masafahdriver.tap_profile.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.tap_profile.balance.AddBalance;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;

public class WalletActivity extends BaseActivity {

  private TextView add_balance_btn;
  private RecyclerView wallet_rec;
  private ArrayList<WalletModel> walletList;
  private static int selectedPosition = -1;
  private WalletAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);
    wallet_rec = findViewById(R.id.buy_points_list);
    add_balance_btn = findViewById(R.id.add_balance_tag);
    add_balance_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(WalletActivity.this, AddBalance.class));
      }
    });


    walletList = new ArrayList<>();
    walletList.add(new WalletModel("27/9/2019","+120.000 Kd",""));
    walletList.add(new WalletModel("27/9/2019","-30.000 Kd","1258"));
    walletList.add(new WalletModel("27/9/2019","-20.000 Kd","1258"));
    walletList.add(new WalletModel("27/9/2019","-70.000 Kd","1258"));

    wallet_rec.setLayoutManager(new LinearLayoutManager(this));
    wallet_rec.addItemDecoration(new BottomSpaceItemDecoration(25));
    adapter = new WalletAdapter(walletList);
    wallet_rec.setAdapter(adapter);
  }

 /* private class BuyPointViewHolder extends RecyclerView.ViewHolder {

    private TextView buyPoint;
    private TextView price;

    public BuyPointViewHolder(@NonNull View itemView) {
      super(itemView);

      buyPoint = itemView.findViewById(R.id.buy_point);
      price = itemView.findViewById(R.id.amount);
    }

    public void bindData(BuyPoint eachBuyPoint) {

      if (eachBuyPoint.isSelected()) {
        itemView.setBackgroundResource(R.drawable.button_rounded_primary_border);
      } else {
        itemView.setBackgroundResource(R.drawable.edit_text_rounded_white_filled);
      }
      buyPoint.setText(eachBuyPoint.getBuyPoint());
      price.setText(eachBuyPoint.getPrice());
    }
  }

  private class BuyPointsAdapter extends RecyclerView.Adapter<BuyPointViewHolder> {

    @NonNull
    @Override
    public BuyPointViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.buy_points_list_item, viewGroup, false);

      return new BuyPointViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BuyPointViewHolder buyPointViewHolder, int i) {

      buyPointViewHolder.bindData(buyPoints.get(i));

      final int position = buyPointViewHolder.getAdapterPosition();

      buyPointViewHolder.itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (selectedPosition > -1) {
                buyPoints.get(selectedPosition).setSelected(false);
                selectedPosition = position;
                buyPoints.get(selectedPosition).setSelected(true);
                buyPointViewHolder.itemView.setBackgroundResource(
                    R.drawable.button_rounded_primary_border);
              } else {
                selectedPosition = position;
                buyPoints.get(selectedPosition).setSelected(true);
                buyPointViewHolder.itemView.setBackgroundResource(
                    R.drawable.button_rounded_primary_border);
              }
              notifyDataSetChanged();
            }
          });
    }

    @Override
    public int getItemCount() {
      return buyPoints.size();
    }
  }*/

}
