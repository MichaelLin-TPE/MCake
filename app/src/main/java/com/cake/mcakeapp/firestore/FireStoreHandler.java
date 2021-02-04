package com.cake.mcakeapp.firestore;

import com.cake.mcakeapp.data.UserData;

import java.util.ArrayList;

public interface FireStoreHandler {


    void createNewUserAccount(String name, String phone, String email, String address, String sex, String uuid, OnCatchFireStoreResultListener<String> createUserDataListener);

    void getUserList(OnCatchFireStoreResultListener<ArrayList<UserData>> getUserListListener);

    void setAllUserList(ArrayList<UserData> userDataList);

    interface OnCatchFireStoreResultListener<T>{
        void onSuccessful(T data);
        void onFail(String message);
    }

}
