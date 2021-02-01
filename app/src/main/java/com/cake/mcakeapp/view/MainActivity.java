package com.cake.mcakeapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.view.home.HomeActivity;

public class MainActivity extends MCakeBaseActivity {

    //進版圖頁面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(it);
                startActivityInAnim(MainActivity.this,1);
                finish();
            }
        },3000);

    }

    public static void startActivityInAnim(Activity activity, int type) {
        if (type == 1) {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (type == 2) {
            activity.overridePendingTransition(R.anim.bottom_in, 0);
        }
    }
}