package com.vavisa.masafahdriver.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BuyPointsActivity extends BaseActivity {

  private RecyclerView buyPointsList;
  private List<BuyPoint> buyPoints;
  private static int selectedPosition = -1;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buy_points);

    buyPointsList = findViewById(R.id.buy_points_list);

    buyPoints = new ArrayList<>();

    BuyPoint buyPoint = new BuyPoint();
    buyPoint.setBuyPoint("100 point");
    buyPoint.setPrice("1 KD");

    buyPoints.add(buyPoint);

    buyPoint = new BuyPoint();
    buyPoint.setBuyPoint("250 point");
    buyPoint.setPrice("2 KD");

    buyPoints.add(buyPoint);

    buyPoint = new BuyPoint();
    buyPoint.setBuyPoint("500 point");
    buyPoint.setPrice("3 KD");

    buyPoints.add(buyPoint);

    buyPoint = new BuyPoint();
    buyPoint.setBuyPoint("1000 point");
    buyPoint.setPrice("4 KD");

    buyPoints.add(buyPoint);

    buyPointsList.setLayoutManager(new LinearLayoutManager(this));
    buyPointsList.addItemDecoration(new BottomSpaceItemDecoration(25));
    buyPointsList.setAdapter(new BuyPointsAdapter());
  }

  private class BuyPointViewHolder extends RecyclerView.ViewHolder {

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
  }

  private class BuyPoint {
    boolean selected;
    String buyPoint;
    String price;

    public boolean isSelected() {
      return selected;
    }

    public void setSelected(boolean selected) {
      this.selected = selected;
    }

    public String getBuyPoint() {
      return buyPoint;
    }

    public void setBuyPoint(String buyPoint) {
      this.buyPoint = buyPoint;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }
  }
}
