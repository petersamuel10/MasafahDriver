package com.vavisa.masafahdriver.tap_shipment;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.fragments.BaseFragment;
import com.vavisa.masafahdriver.tap_order.OrderAdapter;
import com.vavisa.masafahdriver.tap_order.OrderModel;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;

public class MyShipmentsFragment extends BaseFragment {

    private View fragment;
    private RecyclerView myShipmentListView;
    private List<OrderModel> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_my_shipments, container, false);
            Toolbar toolbar = fragment.findViewById(R.id.orders_toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            getActivity().setTitle("");
         //   final ScrollView scrollView = fragment.findViewById(R.id.scrollView);

           /* scrollView.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });*/

            orderList = new ArrayList<>();

            orderList.add(new OrderModel());
            orderList.add(new OrderModel());
            orderList.add(new OrderModel());

            myShipmentListView = fragment.findViewById(R.id.my_shipment_list);
            myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myShipmentListView.setAdapter(new MyShipmentAdapter(this, orderList));
            myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        return fragment;
    }

}
