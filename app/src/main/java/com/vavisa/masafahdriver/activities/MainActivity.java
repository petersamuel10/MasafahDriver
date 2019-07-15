package com.vavisa.masafahdriver.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.tap_my_shipment.MyShipmentsFragment;
import com.vavisa.masafahdriver.tap_order.OrderFragment;
import com.vavisa.masafahdriver.tap_profile.profile.ProfileFragment;
import com.vavisa.masafahdriver.util.Constants;

import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends BaseActivity {

    private BottomNavigationView navigationView;
    private HashMap<String, Stack<Fragment>> mStack;
    private String mCurrentTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mStack = new HashMap<String, Stack<Fragment>>();
        mStack.put(Constants.TAB_ORDER, new Stack<Fragment>());
        mStack.put(Constants.TAB_SHIPMENT, new Stack<Fragment>());
        mStack.put(Constants.TAB_PROFILE, new Stack<Fragment>());

        navigationView.setSelectedItemId(R.id.navigation_orders);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.navigation_orders:
                selectTab(Constants.TAB_ORDER);
                return true;
            case R.id.navigation_my_shipments:
                selectTab(Constants.TAB_SHIPMENT);
                return true;
            case R.id.navigation_profile:
                selectTab(Constants.TAB_PROFILE);
                return true;
        }
        return false;
    };

    private void selectTab(String tabId) {

        // if press twice in the same tab
        if (mCurrentTab == tabId) {

            Fragment fragment = mStack.get(mCurrentTab).elementAt(0);
            mStack.get(tabId).clear();
            pushFragments(tabId, fragment, true);

        } else {
            mCurrentTab = tabId;

            if (mStack.get(tabId).size() == 0) {

                /*
                 *    First time this tab is selected. So add first fragment of that tab.
                 *    Dont need animation, so that argument is false.
                 *    We are adding a new fragment which is not present in stack. So add to stack is true.
                 */

                switch (tabId) {
                    case "tab_order":
                        pushFragments(tabId, new OrderFragment(), true);
                        break;
                    case "tab_shipment":
                        pushFragments(tabId, new MyShipmentsFragment(), true);
                        break;
                    case "tab_profile":
                        pushFragments(tabId, new ProfileFragment(), true);
                        break;
                }

            } else {


                //   We are switching tabs, and target tab is already has atleast one fragment.
                //    No need of animation, no need of stack pushing. Just show the target fragment

                pushFragments(tabId, mStack.get(tabId).lastElement(), false);
            }
        }
    }

    public void pushFragments(String tag, Fragment fragment, boolean shouldAdd) {
        if (shouldAdd)
            mStack.get(tag).push(fragment);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_layout_content, fragment);
        ft.commit();
    }

    private void popFragments() {
        /*
         *    Select the second last fragment in current tab's stack..
         *    which will be shown after the fragment transaction given below
         */

        Fragment fragment = mStack.get(mCurrentTab).elementAt(mStack.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
        mStack.get(mCurrentTab).pop();

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_layout_content, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        if (mStack.get(mCurrentTab).size() == 1) {
            if (mCurrentTab == Constants.TAB_ORDER) {
                // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
                finish();
                return;
            } else {
                navigationView.setSelectedItemId(R.id.home);
            }
        } else
            // Goto previous fragment in navigation stack of this tab
            popFragments();
    }
}
