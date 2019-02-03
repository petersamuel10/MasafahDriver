package com.vavisa.masafahdriver.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vavisa.masafahdriver.R;


public class FragmentUtil {

    public static void switchFragment(
            FragmentManager fragmentManager, Fragment fragment, String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, fragmentName);
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commit();
    }

    public static int getFragmentBackStackEntryAt(
            FragmentManager fragmentManager, String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return entry;
            }
        }
        return -1;
    }
}
