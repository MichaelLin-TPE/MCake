package com.cake.mcakeapp.view.product;

import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;

import java.util.ArrayList;

public class ProductFragmentPresenterImpl implements ProductFragmentPresenter {

    private ProductFragmentVu mView;

    private FireStoreHandler fireStoreHandler;

    private ArrayList<ProductData> favoriteList , cartList;

    public ProductFragmentPresenterImpl(ProductFragmentVu mView) {
        this.mView = mView;
        fireStoreHandler = new FireStoreHandlerImpl();
        fireStoreHandler.catchOriginalFavoriteData();
        fireStoreHandler.catchOriginalCartData();
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

    private FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>> getProductListListener = new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>>() {
        @Override
        public void onSuccessful(ArrayList<ProductData> data) {
            if (data == null){
                mView.showToast(mView.getFailToGetProductList());
                return;
            }


            favoriteList = fireStoreHandler.getFavoriteList();
            cartList = fireStoreHandler.getCartList();


            //比對個人資料
            for (ProductData cart : cartList){
                for (ProductData product : data){
                    if (cart.getImageUrlArray().get(0).equals(product.getImageUrlArray().get(0))){
                        product.setCheckCart(cart.isCheckCart());
                    }
                }
            }

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
            mView.showToast(message);
        }
    };
}
