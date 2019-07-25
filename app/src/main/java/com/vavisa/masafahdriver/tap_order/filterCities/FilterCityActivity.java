package com.vavisa.masafahdriver.tap_order.filterCities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.login.CountryModel;

import java.util.ArrayList;
import java.util.List;

public class FilterCityActivity extends BaseActivity implements FilterCity {

    private EditText search_ed;
    private ImageView ic_close;
    private RadioGroup cities_rg;
    private FilterCityPresenter presenter;
  //  private List<String> cityNameList;
    private ArrayList<CountryModel> citiesList;
    private String from_id = null, to_id = null;
    private int from_selected_pos, to_selected_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter_city);

        search_ed = findViewById(R.id.search_ed);
        ic_close = findViewById(R.id.close_icon);
        cities_rg = findViewById(R.id.cities_rg);

        presenter = new FilterCityPresenter();
        presenter.attachView(this);
        presenter.getMyCities();

        cities_rg.setOnCheckedChangeListener((group, checkedId) -> {

            showMessage(String.valueOf(checkedId));
        });
    }

    @Override
    public void displayMyCities(ArrayList<CountryModel> citiesList) {

        this.citiesList = citiesList;
        //cityNameList.add(getString(R.string.all));
        for (int i = 0; i < citiesList.size(); i++) {
           // cityNameList.add(citiesList.get(i).getName());
            RadioButton rb = new RadioButton(this);
            rb.setTextColor(Color.BLACK);
            rb.setText(citiesList.get(i).getName());
            rb.setId(citiesList.get(i).getId());
            cities_rg.addView(rb);
        }

    }
}
