package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class BalanceOffersActivity extends BaseActivity implements BalanceOffersViews {

    private RecyclerView balance_rec;
    private Button add_btn;
    private BalanceOffersAdapter adapter;
    private ArrayList<BalanceOfferModel> balanceOfferList;
    private BalanceOffersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        balance_rec = findViewById(R.id.balance_rec);
        add_btn = findViewById(R.id.add_balance_btn);
        balance_rec.addItemDecoration(new BottomSpaceItemDecoration(24));
        presenter = new BalanceOffersPresenter();
        presenter.attachView(this);
        presenter.getOffers();

        add_btn.setOnClickListener(v -> {
            presenter.addToWallet(new AddBalanceModel(adapter.getSelectedPackage().getId(), adapter.getSelectedPackage().getAmount(), true));
        });

    }

    @Override
    public void displayOffers(ArrayList<BalanceOfferModel> offersList) {
        this.balanceOfferList = offersList;
        adapter = new BalanceOffersAdapter(balanceOfferList);
        balance_rec.setAdapter(adapter);
    }

    @Override
    public void addBalanceRes(HashMap<String, String> result) {
        Toast.makeText(this, getString(R.string.add_successfully), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
