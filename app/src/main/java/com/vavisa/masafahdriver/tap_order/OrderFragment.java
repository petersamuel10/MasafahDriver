package com.vavisa.masafahdriver.tap_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.PaymentResult;
import com.vavisa.masafahdriver.activities.ShipmentModel;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;

public class OrderFragment extends BaseFragment implements OrdersViews {

    private View fragment;
    private RecyclerView order_rec;
    private NestedScrollView scrollView;
    public RelativeLayout deliveryButtonLayout;
    private ImageView noShipmentsImage;
    private TextView noShipmentsMessage;
    public Button deliveryNow;
    private OrderPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_orders, container, false);
            initViews();

            presenter = new OrderPresenter();
            presenter.attachView(this);
            presenter.getShipment();

            deliveryNow.setOnClickListener(v -> show_pay_dialog());
        }

        return fragment;
    }

    private void initViews() {

        scrollView = fragment.findViewById(R.id.scrollView);
        deliveryButtonLayout = fragment.findViewById(R.id.delivery_button_layout);
        deliveryNow = deliveryButtonLayout.findViewById(R.id.deliver_now_button);
        noShipmentsImage = fragment.findViewById(R.id.no_shipments_image);
        noShipmentsMessage = fragment.findViewById(R.id.no_shipments_message);

        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_UP));

        final ConstraintLayout locationLayout = fragment.findViewById(R.id.location_layout);

        locationLayout.post(() -> {
            int height = locationLayout.getHeight();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) locationLayout.getLayoutParams();
            layoutParams.topMargin = -(height / 2);
            locationLayout.setLayoutParams(layoutParams);
        });

        order_rec = fragment.findViewById(R.id.my_shipment_list);
        order_rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        order_rec.addItemDecoration(new BottomSpaceItemDecoration(50));

    }

    private void show_pay_dialog() {

        final DialogPlus dialogPlus =
                DialogPlus.newDialog(getActivity())
                        .setGravity(Gravity.BOTTOM)
                        .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                        .setContentHolder(new ViewHolder(R.layout.payment_pop_up))
                        .create();

        ImageView close_ic = (ImageView) dialogPlus.findViewById(R.id.close_icon);
        close_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
            }
        });
        TextView amount = (TextView) dialogPlus.findViewById(R.id.amount);
        RecyclerView ship_rec = (RecyclerView) dialogPlus.findViewById(R.id.shipment_list);

        ArrayList<PaymentListModel> paymentList = new ArrayList<>();
        paymentList.add(new PaymentListModel());
        paymentList.add(new PaymentListModel());
        paymentList.add(new PaymentListModel());
        paymentList.add(new PaymentListModel());

        ship_rec.setAdapter(new PaymentListAdapter(paymentList));

        Button pay_online_btn = (Button) dialogPlus.findViewById(R.id.pay_now_button);
        pay_online_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PaymentResult.class));
            }
        });


        dialogPlus.show();
    }

    @Override
    public void displayShipment(ArrayList<ShipmentModel> orderList) {

        if (orderList.size() == 0) {
            noShipmentsMessage.setVisibility(View.VISIBLE);
            noShipmentsImage.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else
            order_rec.setAdapter(new OrderAdapter(this, orderList));
    }
}
