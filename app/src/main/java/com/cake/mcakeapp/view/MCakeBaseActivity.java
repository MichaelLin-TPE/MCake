package com.cake.mcakeapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.cake.mcakeapp.R;


/**
 * 有相同邏輯的東西會坐在這裡
 */
public class MCakeBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_cake_base);






    }
}