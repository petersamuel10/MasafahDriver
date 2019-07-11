package com.vavisa.masafahdriver.tap_profile.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers.BalanceOffersActivity;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

public class WalletActivity extends BaseActivity implements WalletViews {

    private TextView add_balance_btn, balance_txt, free_delivery_txt;
    private RecyclerView wallet_rec;
    private WalletPresenter presenter;
    private WalletAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();

        presenter = new WalletPresenter();
        presenter.attachView(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.getWalletDetails();

    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.wallet_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        wallet_rec = findViewById(R.id.buy_points_list);
        add_balance_btn = findViewById(R.id.add_balance_tag);
        balance_txt = findViewById(R.id.balance_txt);
        free_delivery_txt = findViewById(R.id.free_delivery_txt);


        add_balance_btn.setOnClickListener(v -> startActivity(new Intent(WalletActivity.this, BalanceOffersActivity.class)));

        wallet_rec.setLayoutManager(new LinearLayoutManager(this));
        wallet_rec.addItemDecoration(new BottomSpaceItemDecoration(25));
    }

    @Override
    public void DisplayWalletDetails(WalletModel walletModel) {

        balance_txt.setText(String.format("%.3f", Float.valueOf(walletModel.getWallet_balance())) + " " + getString(R.string.kd));
        free_delivery_txt.setText(walletModel.getFree_deliveries() + " " + getString(R.string.free_delevery));

        adapter = new WalletAdapter(walletModel.getTransactionDetails());

        wallet_rec.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
