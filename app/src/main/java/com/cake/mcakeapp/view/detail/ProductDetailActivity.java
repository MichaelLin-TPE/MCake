package com.cake.mcakeapp.view.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import static com.cake.mcakeapp.view.product.ProductFragment.PRODUCT_DATA;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailActivityVu{

    private ProductDetailPresenter presenter;

    private TextView tvActionBarTitle;

    private RecyclerView recyclerView;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initPresenter();
        initView();


        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        ProductData data = (ProductData) bundle.getSerializable(PRODUCT_DATA);

        presenter.onCatchData(data);

    }

    private void initView() {

        tvActionBarTitle = findViewById(R.id.detail_action_bar_title);
        recyclerView = findViewById(R.id.detail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView ivBack = findViewById(R.id.detail_action_bar_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButtonClickListener();
            }
        });

    }

    private void initPresenter() {
        presenter = new ProductDetailPresenterImpl(this);
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public void showActionBarTitle(ProductData data) {
        tvActionBarTitle.setText(data.getName());
    }

    @Override
    public void showProductData(ProductData data) {
        DetailAdapter adapter = new DetailAdapter();
        adapter.setProductData(data);
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);
    }


}