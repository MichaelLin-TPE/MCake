package com.cake.mcakeapp.view.product;

import com.cake.mcakeapp.data.ProductData;

public interface ProductFragmentPresenter {
    void onLoadProductList();

    void onProductHeartClickListener(ProductData data);

    void onProductCartClickListener(ProductData data);

    void onNeedToLogin();

    void onRegisterButtonClickListener();

    void onLoginButtonClickListener();

    void onProductItemClickListener(ProductData data);
}
