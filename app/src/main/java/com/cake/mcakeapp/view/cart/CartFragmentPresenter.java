package com.cake.mcakeapp.view.cart;

import com.cake.mcakeapp.data.ProductData;

public interface CartFragmentPresenter {
    void onLoadData();

    void onButtonRemoveClickListener(ProductData data,int position);
}
