package com.cake.mcakeapp.view.product;

import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;

import java.util.ArrayList;

public class ProductFragmentPresenterImpl implements ProductFragmentPresenter {

    private ProductFragmentVu mView;

    private FireStoreHandler fireStoreHandler;


    public ProductFragmentPresenterImpl(ProductFragmentVu mView) {
        this.mView = mView;
        fireStoreHandler = new FireStoreHandlerImpl();
    }

    @Override
    public void onLoadProductList() {
        fireStoreHandler.getProductList(getProductListListener);
    }

    //點愛心會做的事情
    @Override
    public void onProductHeartClickListener(ProductData data) {
        fireStoreHandler.addFavoriteProduct(data);
    }

    //點購物車會出現的事情
    @Override
    public void onProductCartClickListener(ProductData data) {
        fireStoreHandler.addCartProduct(data);
    }

    @Override
    public void onNeedToLogin() {
        mView.showNeedToLoginDialog();
    }

    @Override
    public void onRegisterButtonClickListener() {
        mView.goToRegisterPage();
    }

    @Override
    public void onLoginButtonClickListener() {
        mView.goToLoginPage();
    }

    @Override
    public void onProductItemClickListener(ProductData data) {
        mView.goToProductDetailActivity(data);
    }

    private FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>> getProductListListener = new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>>() {
        @Override
        public void onSuccessful(ArrayList<ProductData> data) {
            if (data == null){
                mView.showToast(mView.getFailToGetProductList());
                return;
            }

            //比對個人資料
            checkPersonalCartData(data);




        }

        @Override
        public void onFail(String message) {
            mView.showToast(message);
        }
    };

    private void checkPersonalCartData(ArrayList<ProductData> data) {

        fireStoreHandler.catchOriginalCartData(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>>() {
            @Override
            public void onSuccessful(ArrayList<ProductData> cartList) {
                for (ProductData cart : cartList){
                    for (ProductData product : data){
                        if (cart.getImageUrlArray().get(0).equals(product.getImageUrlArray().get(0))){
                            product.setCheckCart(cart.isCheckCart());
                        }
                    }
                }
                checkPersonalFavoriteData(data);

            }

            @Override
            public void onFail(String message) {

            }
        });

    }

    private void checkPersonalFavoriteData(ArrayList<ProductData> data) {
        fireStoreHandler.catchOriginalFavoriteData(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>>() {
            @Override
            public void onSuccessful(ArrayList<ProductData> favoriteList) {
                for (ProductData fav : favoriteList){
                    for (ProductData product : data){
                        if (fav.getImageUrlArray().get(0).equals(product.getImageUrlArray().get(0))){
                            product.setCheckHeart(fav.isCheckHeart());
                        }
                    }
                }

                mView.showProductList(data);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
