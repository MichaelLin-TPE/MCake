package com.cake.mcakeapp.tool;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cake.mcakeapp.R;
import com.google.android.gms.common.SignInButton;

public class Tools {

    public static void replace(int container, FragmentManager fm, Fragment nextFragment, boolean needBackStack, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fm.findFragmentByTag(nextFragment.getClass().getSimpleName());
        if (f != null) {
            ft.remove(f);
        }
        ft.replace(container, nextFragment);
        if (needBackStack) {
            ft.addToBackStack(tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commitAllowingStateLoss();
    }

    public static void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    public static void startActivityInAnim(Activity activity) {

        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
