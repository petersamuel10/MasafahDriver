package com.vavisa.masafahdriver.tap_profile.wallet.BalanceOffers;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView add_custom_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        Toolbar toolbar = findViewById(R.id.buy_points_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        balance_rec = findViewById(R.id.balance_rec);
        add_custom_tag = findViewById(R.id.custom_amount_txt);
        add_btn = findViewById(R.id.add_balance_btn);
        balance_rec.addItemDecoration(new BottomSpaceItemDecoration(24));
        presenter = new BalanceOffersPresenter();
        presenter.attachView(this);
        presenter.getOffers();

        add_custom_tag.setOnClickListener(v -> showCustomAmountAlert());
        add_btn.setOnClickListener(v -> {
            presenter.addToWallet(new AddBalanceModel(adapter.getSelectedPackage().getId(), adapter.getSelectedPackage().getAmount(), true));
        });

    }

    private void showCustomAmountAlert() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = LayoutInflater.from(this).inflate(R.layout.item_custom_balance, null);
        dialog.setView(view);
        dialog.setTitle(R.string.add_custom_amount);

        EditText amount_ed = view.findViewById(R.id.custom_amount_ed);

        dialog.setPositiveButton(R.string.add, (dialog12, which) -> {

            if (TextUtils.isEmpty(amount_ed.getText().toString()))
                Toast.makeText(BalanceOffersActivity.this, R.string.please_enter_your_amount, Toast.LENGTH_SHORT).show();
            else {
                presenter.addToWallet(new AddBalanceModel("", amount_ed.getText().toString(), false));
            }

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());

        dialog.create().show();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
