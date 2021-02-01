package com.cake.mcakeapp;

import com.cake.mcakeapp.data.ProductData;

public interface FirebaseHandler {


    void onCatchAllProductList(OnCatchDataFromFirebase<ProductData> listener);


    interface OnCatchDataFromFirebase<T>{
        void onSuccessful(T data);

        void onFail(String errorMessage);
    }


}
