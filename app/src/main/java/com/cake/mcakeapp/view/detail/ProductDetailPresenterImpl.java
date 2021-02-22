package com.cake.mcakeapp.view.detail;

import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.tool.MichaelLog;

public class ProductDetailPresenterImpl implements ProductDetailPresenter {

    private ProductDetailActivityVu mView;

    public ProductDetailPresenterImpl(ProductDetailActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onBackButtonClickListener() {
        mView.finishPage();
    }

    @Override
    public void onCatchData(ProductData data) {

        if (data == null){
            MichaelLog.i("ProductData is null");
            return;
        }

        mView.showActionBarTitle(data);
        mView.showProductData(data);
    }
}
