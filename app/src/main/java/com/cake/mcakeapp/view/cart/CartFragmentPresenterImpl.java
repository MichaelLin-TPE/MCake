package com.cake.mcakeapp.view.cart;

import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;

import java.util.ArrayList;

public class CartFragmentPresenterImpl implements CartFragmentPresenter {

    private CartFragmentVu mView;

    private FireStoreHandler fireStoreHandler;

    private ArrayList<ProductData> allCartList;

    public CartFragmentPresenterImpl(CartFragmentVu mView) {
        this.mView = mView;
        fireStoreHandler = new FireStoreHandlerImpl();
        allCartList = new ArrayList<>();
    }

    @Override
    public void onLoadData() {

        mView.showProgress(true);

        fireStoreHandler.catchOriginalCartData(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<ProductData>>() {
            @Override
            public void onSuccessful(ArrayList<ProductData> data) {

                if (data == null || data.isEmpty()){

                    mView.showProgress(false);
                    mView.showNoCartData(true);

                    return;
                }
                mView.showNoCartData(false);

                allCartList.addAll(data);
                mView.showProgress(false);
                mView.showCartList(data);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    @Override
    public void onButtonRemoveClickListener(ProductData data, int position) {
        data.setCheckCart(false);
        fireStoreHandler.addCartProduct(data);

        int index = 0;

        for (ProductData pro : allCartList){
            if (pro.getImageUrlArray().get(0).equals(data.getImageUrlArray().get(0))){
                break;
            }
            index ++;
        }

        allCartList.remove(index);
        MichaelLog.i("allCartList size : "+allCartList.size());
        if (allCartList.isEmpty()){
            mView.clearView(allCartList);
            mView.showNoCartData(true);
            return;
        }

        mView.refreshCartView(position,allCartList);
    }
}
