package com.vavisa.masafahdriver.tap_order.invoice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.tap_order.invoice.payment_page.PaymentPage;
import com.vavisa.masafahdriver.tap_order.invoice.payment_page.PaymentResult;

import java.util.ArrayList;

public class InvoiceActivity extends BaseActivity implements InvoiceViews {

    private RecyclerView shipment_rec;
    private TextView total_amount_txt, total_shipment_txt,
            free_delivery_tag, free_delivery_txt,
            wallet_amount_tag, wallet_amount_txt,
            pay_card_tag, pay_card_txt;

    private Button pay_btn;
    String total_amount, free_delivery_amount, wallet_amount, card_amount;

    private InvoicePresenter presenter;
    private InvoiceModel paymentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        initViews();
        bindData();

        presenter = new InvoicePresenter();
        presenter.attachView(this);

        pay_btn.setOnClickListener(v -> {
            presenter.payOrder(paymentModel.getOrder_id());
        });
    }

    @SuppressLint("SetTextI18n")
    private void bindData() {

        ArrayList<ShipmentModel> shipmentList = getIntent().getParcelableArrayListExtra("shipmentList");
        paymentModel = getIntent().getParcelableExtra("paymentModel");
        total_amount = paymentModel.getTotal_amount() + getString(R.string.kd);
        free_delivery_amount = paymentModel.getFree_deliveries_used();
        wallet_amount = paymentModel.getWallet_amount_used();
        card_amount = paymentModel.getCard_amount();

        shipment_rec.setAdapter(new ShipmentAdapter(shipmentList));
        total_amount_txt.setText(total_amount);
        total_shipment_txt.setText(String.valueOf(shipmentList.size()));
        if (free_delivery_amount.equals("0")) {
            free_delivery_tag.setVisibility(View.GONE);
            free_delivery_txt.setVisibility(View.GONE);
        } else
            free_delivery_txt.setText(free_delivery_amount);

        if (wallet_amount.equals("0")) {
            wallet_amount_tag.setVisibility(View.GONE);
            wallet_amount_txt.setVisibility(View.GONE);
        } else
            wallet_amount_txt.setText(wallet_amount.concat(getString(R.string.kd)));

        if (card_amount.equals("0")) {
            pay_card_tag.setVisibility(View.GONE);
            pay_card_txt.setVisibility(View.GONE);
            pay_btn.setText(getString(R.string.confirm));
        } else {
            pay_card_txt.setText(card_amount.concat(getString(R.string.kd)));
            pay_btn.setText((getText(R.string.total_pay_by_card)) + card_amount.concat(getString(R.string.kd) + " )"));
        }
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.invoice_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        shipment_rec = findViewById(R.id.shipment_rec);
        total_amount_txt = findViewById(R.id.total_amount);
        total_shipment_txt = findViewById(R.id.total_shipment);
        free_delivery_tag = findViewById(R.id.free_delivery_tag);
        free_delivery_txt = findViewById(R.id.free_delivery);
        wallet_amount_tag = findViewById(R.id.wallet_tag);
        wallet_amount_txt = findViewById(R.id.wallet);
        pay_card_tag = findViewById(R.id.card_tag);
        pay_card_txt = findViewById(R.id.card);

        pay_btn = findViewById(R.id.pay_btn);

        shipment_rec.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void displayPaymentMethod(PaidModel paidModel) {
        if (paidModel.getMessage().equals(getString(R.string.redirect_to_payment))) {
            String[] paymentMethodItems = new String[paidModel.getPaymentMethod().size()];
            for (int i = 0; i < paidModel.getPaymentMethod().size(); i++) {
                paymentMethodItems[i] = paidModel.getPaymentMethod().get(i).getPaymentMethodName();
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.select_payment_method);

            alert.setSingleChoiceItems(paymentMethodItems, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    dialog.dismiss();
                    Intent intent = new Intent(InvoiceActivity.this, PaymentPage.class);
                    intent.putExtra("payment_url", paidModel.getPaymentMethod().get(position).getPaymentMethodUrl());
                    startActivity(intent);
                }
            });

            alert.create().show();

        } else {
            Intent intent = new Intent(new Intent(this, PaymentResult.class));
            intent.putExtra("status", "-1");
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
