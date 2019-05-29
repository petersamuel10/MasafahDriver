package com.vavisa.masafahdriver.tap_profile.balance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.util.BottomSpaceItemDecoration;

import java.util.ArrayList;

public class AddBalance extends AppCompatActivity {

    RecyclerView balance_rec;
    Button add_btn;
    AddBalanceAdapter adapter;
    ArrayList<BalanceModel> balanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        balance_rec = findViewById(R.id.balance_rec);
        add_btn = findViewById(R.id.add_balance_btn);

        balanceList = new ArrayList<>();
        balanceList.add(new BalanceModel("20.000 KD" , "1 free delivery"));
        balanceList.add(new BalanceModel("50.000 KD" , "3 free delivery(s)"));
        balanceList.add(new BalanceModel("100.000 KD" , "5 free delivery(s)"));

        balance_rec.addItemDecoration(new BottomSpaceItemDecoration(24));

        balance_rec.setAdapter(new AddBalanceAdapter(balanceList));


    }
}
