package com.cake.mcakeapp.view.detail;

import com.cake.mcakeapp.data.ProductData;

public interface ProductDetailPresenter {
    void onBackButtonClickListener();

    void onCatchData(ProductData data);
}
