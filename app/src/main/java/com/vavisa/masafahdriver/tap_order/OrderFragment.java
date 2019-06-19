package com.vavisa.masafahdriver.tap_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;

public class OrderFragment extends BaseFragment {

    private View fragment;
    private RecyclerView myShipmentListView;
    private List<OrderModel> orderList;
    public RelativeLayout deliveryButtonLayout;
    public Button deliveryNow;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_orders, container, false);
            Toolbar toolbar = fragment.findViewById(R.id.orders_toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            getActivity().setTitle("");

            final ScrollView scrollView = fragment.findViewById(R.id.scrollView);
            deliveryButtonLayout = fragment.findViewById(R.id.delivery_button_layout);
            deliveryNow = deliveryButtonLayout.findViewById(R.id.deliver_now_button);

            /*scrollView.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });*/

            final ConstraintLayout locationLayout = fragment.findViewById(R.id.location_layout);

            locationLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            int height = locationLayout.getHeight();
                            RelativeLayout.LayoutParams layoutParams =
                                    (RelativeLayout.LayoutParams) locationLayout.getLayoutParams();
                            layoutParams.topMargin = -(height / 2);
                            locationLayout.setLayoutParams(layoutParams);
                        }
                    });


            orderList = new ArrayList<>();

            orderList.add(new OrderModel());
            orderList.add(new OrderModel());
            orderList.add(new OrderModel());

            myShipmentListView = fragment.findViewById(R.id.my_shipment_list);
            myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myShipmentListView.setAdapter(new OrderAdapter(this, orderList));
            myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

            deliveryNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_pay_dialog();
                }
            });


        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        return fragment;
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

}
