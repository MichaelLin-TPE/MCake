package com.cake.mcakeapp.view.detail;

import com.cake.mcakeapp.data.ProductData;

public interface ProductDetailActivityVu {
    void finishPage();

    void showActionBarTitle(ProductData data);

    void showProductData(ProductData data);
}
