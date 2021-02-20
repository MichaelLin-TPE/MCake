package com.cake.mcakeapp.view.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cake.mcakeapp.R;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailActivityVu{

    private ProductDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initPresenter();

    }

    private void initPresenter() {
        presenter = new ProductDetailPresenterImpl(this);
    }
}