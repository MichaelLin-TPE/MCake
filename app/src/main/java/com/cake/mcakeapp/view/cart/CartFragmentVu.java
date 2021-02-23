package com.cake.mcakeapp.view.cart;

import com.cake.mcakeapp.data.ProductData;

import java.util.ArrayList;

public interface CartFragmentVu {
    void showCartList(ArrayList<ProductData> data);

    void showProgress(boolean isShow);

    void refreshCartView(int position, ArrayList<ProductData> allCartList);

    void showNoCartData(boolean isShow);

    void clearView(ArrayList<ProductData> allCartList);

    void goToDetailPage(ProductData data);
}
