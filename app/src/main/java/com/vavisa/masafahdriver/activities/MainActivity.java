package com.vavisa.masafahdriver.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.tap_order.OrderFragment;
import com.vavisa.masafahdriver.tap_profile.profile.ProfileFragment;
import com.vavisa.masafahdriver.tap_shipment.MyShipmentsFragment;

import static com.vavisa.masafahdriver.util.FragmentUtil.getFragmentBackStackEntryAt;
import static com.vavisa.masafahdriver.util.FragmentUtil.switchFragment;

public class MainActivity extends BaseActivity {
    public static BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment orderFragment = new OrderFragment();
        switchFragment(orderFragment, "orders");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            menuItem -> {

                Fragment fragment = null;

                int backStackEntryAt;

                switch (menuItem.getItemId()) {
                    case R.id.navigation_orders:
                        backStackEntryAt = getFragmentBackStackEntryAt("orders");

                        if (backStackEntryAt >= 0) {
                            getSupportFragmentManager()
                                    .popBackStack(backStackEntryAt + 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            if (!"orders"
                                    .equals(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryAt(
                                                            getSupportFragmentManager().getBackStackEntryCount() - 1)
                                                    .getName())) {

                                getSupportFragmentManager()
                                        .popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragment = new OrderFragment();
                                switchFragment(fragment, "orders");
                            }
                        }
                        return true;

                    case R.id.navigation_my_shipments:
                        backStackEntryAt =
                                getFragmentBackStackEntryAt("myShipments");

                        if (backStackEntryAt > 0) {
                            getSupportFragmentManager()
                                    .popBackStack(backStackEntryAt + 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            if (!"myShipments"
                                    .equals(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryAt(
                                                            getSupportFragmentManager().getBackStackEntryCount() - 1)
                                                    .getName())) {

                                getSupportFragmentManager()
                                        .popBackStack(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragment = new MyShipmentsFragment();
                                switchFragment(fragment, "myShipments");
                            }
                        }
                        return true;

                    case R.id.navigation_profile:
                        backStackEntryAt =
                                getFragmentBackStackEntryAt("profile");

                        if (backStackEntryAt > 0) {
                            getSupportFragmentManager()
                                    .popBackStack(backStackEntryAt + 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            if (!"profile"
                                    .equals(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryAt(
                                                            getSupportFragmentManager().getBackStackEntryCount() - 1)
                                                    .getName())) {
                                fragment = new ProfileFragment();
                                switchFragment(fragment, "profile");
                            }
                        }

                        return true;
                }

                return false;
            };

    private void switchFragment(Fragment fragment, String fragmentName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentName);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    private int getFragmentBackStackEntryAt(String fragmentTagName) {
        for (int entry = 0; entry < getSupportFragmentManager().getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(
                    getSupportFragmentManager().getBackStackEntryAt(entry).getName())) {
                return entry;
            }
        }
        return -1;
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

        // super.onBackPressed();
    }
}
