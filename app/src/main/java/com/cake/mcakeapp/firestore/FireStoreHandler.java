package com.cake.mcakeapp.firestore;

import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.data.UserData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface FireStoreHandler {


    void createNewUserAccount(String name, String phone, String email, String address, String sex, String uuid, OnCatchFireStoreResultListener<String> createUserDataListener);

    void getUserList(OnCatchFireStoreResultListener<ArrayList<UserData>> getUserListListener);

    void setAllUserList(ArrayList<UserData> userDataList);

    void getCommentData(OnCatchFireStoreResultListener<ArrayList<CommentData>> onCatchCommentDataListener);

    void saveCommentData(ArrayList<CommentData> allCommentList, OnCatchFireStoreResultListener<String> onSaveCommentListener);

    void setProductList(String json);

    void getProductList(OnCatchFireStoreResultListener<ArrayList<ProductData>> getProductListListener);

    void addFavoriteProduct(ProductData data);

    void catchOriginalFavoriteData(OnCatchFireStoreResultListener<ArrayList<ProductData>> getFavoriteListListener);

    ArrayList<ProductData> getFavoriteList();

    void addCartProduct(ProductData data);

    void catchOriginalCartData(OnCatchFireStoreResultListener<ArrayList<ProductData>> getCartListListener);

    ArrayList<ProductData> getCartList();

    void catchRealTimeCartData(OnCatchFireStoreResultListener<ArrayList<ProductData>> arrayListOnCatchFireStoreResultListener);

    interface OnCatchFireStoreResultListener<T>{
        void onSuccessful(T data);
        void onFail(String message);
    }

}
