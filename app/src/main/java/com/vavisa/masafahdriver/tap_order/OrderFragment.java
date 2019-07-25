package com.vavisa.masafahdriver.tap_order;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.common_model.ShipmentModel;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.tap_order.invoice.InvoiceActivity;
import com.vavisa.masafahdriver.tap_order.invoice.InvoiceModel;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderFragment extends BaseFragment implements OrdersViews {

    private View fragment;
    private SwipeRefreshLayout sw;
    private RecyclerView order_rec;
    private NestedScrollView scrollView;
    public RelativeLayout deliveryButtonLayout, no_shipment_layout;
    private TextView from_location_name, to_location_name;
    private ImageView ic_swap;
    public Button deliveryNow;
    private OrderPresenter presenter;
    private OrderAdapter adapter;
    private String from_id = null, to_id = null;
    private ArrayList<CountryModel> allCitiesList, cityNameList;
    private int from_selected_pos, to_selected_pos;
    private EditText search_ed;
    private ImageView ic_clear;
    private RadioGroup cities_rg;

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
            presenter.getShipment(from_id, to_id);
            presenter.getMyCities();

            ic_swap.setOnClickListener(v -> {
                try {
                    if (!from_id.equals(to_id)) {
                        String temp_str = from_id;
                        from_id = to_id;
                        to_id = temp_str;
                        from_location_name.setText(cityNameList.get(from_selected_pos).getName());
                        to_location_name.setText(cityNameList.get(to_selected_pos).getName());
                        presenter.getShipment(from_id, to_id);
                    }
                } catch (Exception e) {
                }
            });
            deliveryNow.setOnClickListener(v -> {

                List<String> shipment_ids = new ArrayList<>();
                for (ShipmentModel shipment : adapter.getSelectedShipment()) {
                    shipment_ids.add(shipment.getId());
                }

                HashMap<String, List<String>> shipment_hashMap = new HashMap<>();
                shipment_hashMap.put("shipment_ids", shipment_ids);
                presenter.acceptShipment(shipment_hashMap);
            });
            sw.setOnRefreshListener(() -> {
                sw.setRefreshing(false);
                presenter.getShipment(from_id, to_id);
            });

            from_location_name.setOnClickListener(v -> showCityAlert(true, to_selected_pos));
            to_location_name.setOnClickListener(v -> showCityAlert(false, to_selected_pos));

        }

        return fragment;
    }

    private void showCityAlert(boolean isFrom, int selectedPosition) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.select_city);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_select_city_alert, null);
        alert.setView(view);
        alert.create();

        search_ed = view.findViewById(R.id.search_ed);
        ic_clear = view.findViewById(R.id.ic_clear);
        cities_rg = view.findViewById(R.id.cities_rg);
        getListOfCities(allCitiesList);

        AlertDialog alertDialog = alert.create();

        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityNameList = new ArrayList<>();
                for (CountryModel city : allCitiesList) {
                    if (city.getName().toLowerCase().startsWith(String.valueOf(s)))
                        cityNameList.add(city);
                }
                getListOfCities(cityNameList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ic_clear.setOnClickListener(v -> {
            search_ed.setText("");
            cities_rg.removeAllViews();
            getListOfCities(allCitiesList);
        });

        cities_rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            if (isFrom) {
                from_id = (checkedId == 0)? null:String.valueOf(checkedId);
                from_location_name.setText(radioButton.getText());
            } else {
                to_id = (checkedId == 0)? null:String.valueOf(checkedId);
                to_location_name.setText(radioButton.getText());
            }
            presenter.getShipment(from_id,to_id);
            alertDialog.dismiss();
        });

        alertDialog.show();

        /*alert.setSingleChoiceItems(this.cityNameList, selectedPosition, (dialog, position) -> {
            dialog.dismiss();
            if (isFrom) {
                if (position == 0)
                    from_id = null;
                else
                    from_id = String.valueOf(citiesList.get(position - 1).getId());
                from_location_name.setText(this.cityNameList[position]);
                from_selected_pos = position;
            } else {
                if (position == 0)
                    to_id = null;
                else
                    to_id = String.valueOf(citiesList.get(position - 1).getId());
                to_location_name.setText(this.cityNameList[position]);
                to_selected_pos = position;
            }
            presenter.getShipment(from_id, to_id);
        });*/


    }

    private void getListOfCities(ArrayList<CountryModel> cities_list) {
        cities_rg.removeAllViews();
        for (int i = 0; i < cities_list.size(); i++) {
            RadioButton rb = new RadioButton(getContext());
            rb.setTextColor(Color.BLACK);
            rb.setText(cities_list.get(i).getName());
            rb.setId(cities_list.get(i).getId());
            rb.setTextSize(16);
            cities_rg.addView(rb);
        }
    }

    private void initViews() {

        scrollView = fragment.findViewById(R.id.scrollView);
        sw = fragment.findViewById(R.id.sw);
        from_location_name = fragment.findViewById(R.id.from_location_name);
        to_location_name = fragment.findViewById(R.id.to_location_name);
        ic_swap = fragment.findViewById(R.id.ic_swap);
        deliveryButtonLayout = fragment.findViewById(R.id.delivery_button_layout);
        deliveryNow = deliveryButtonLayout.findViewById(R.id.deliver_now_button);
        no_shipment_layout = fragment.findViewById(R.id.no_shipments_layout);

        sw.setColorSchemeResources(R.color.colorPrimary);
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

    @Override
    public void displayShipment(ArrayList<ShipmentModel> orderList) {

        // to hide button after filter
        deliveryButtonLayout.setVisibility(View.GONE);
        if (orderList.size() == 0) {
            no_shipment_layout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            no_shipment_layout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            adapter = new OrderAdapter(this, orderList);
            order_rec.setAdapter(adapter);
        }
    }

    @Override
    public void payment(InvoiceModel paymentModel) {

        Intent intent = new Intent(getContext(), InvoiceActivity.class);
        intent.putExtra("shipmentList", adapter.getSelectedShipment());
        intent.putExtra("paymentModel", paymentModel);
        getActivity().startActivity(intent);
    }

    @Override
    public void displayMyCities(ArrayList<CountryModel> citiesList) {
        citiesList.add(0, new CountryModel(0, getString(R.string.all)));
        this.allCitiesList = citiesList;
        this.cityNameList = citiesList;
        from_location_name.setText(citiesList.get(0).getName());
        to_location_name.setText(citiesList.get(0).getName());
    }

}
