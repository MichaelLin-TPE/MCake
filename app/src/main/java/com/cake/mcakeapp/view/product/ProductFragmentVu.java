package com.cake.mcakeapp.view.product;

import com.cake.mcakeapp.data.ProductData;

import java.util.ArrayList;

public interface ProductFragmentVu {
    void showToast(String message);

    String getFailToGetProductList();

    void showProductList(ArrayList<ProductData> data);

    void showNeedToLoginDialog();
}
