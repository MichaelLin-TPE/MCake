package com.cake.mcakeapp.view.register;

import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.view.product.ProductFragmentPresenter;
import com.cake.mcakeapp.view.product.ProductFragmentVu;

import java.util.ArrayList;
import java.util.UUID;

import static com.cake.mcakeapp.view.register.RegisterFragment.COMPLETE_USER_DATA;
import static com.cake.mcakeapp.view.register.RegisterFragment.REGISTER;

public class RegisterFragmentPresenterImpl implements RegisterFragmentPresenter {

    private RegisterFragmentVu mView;

    private AuthHandler authHandler;

    private String name, phone, email, address, password, sex;

    private ArrayList<UserData> userDataList;

    private FireStoreHandler fireStoreHandler;

    private int type;

    public RegisterFragmentPresenterImpl(RegisterFragmentVu mView) {
        this.mView = mView;
        fireStoreHandler = new FireStoreHandlerImpl();
        authHandler = new AuthHandlerImpl();
    }

    @Override
    public void onSelectSexButtonClickListener() {
        mView.showSexSelectDialog();
    }

    @Override
    public void onSelectSexConfirmClickListener(String sex) {
        mView.setSexResult(sex);
    }

    @Override
    public void onCreateAccountButtonClickListener(String name, String phone, String email, String address, String password, String passwordConfirm, String sex) {

        switch (type){
            case REGISTER:
                doRegisterAccount(name,phone,email,address,password,passwordConfirm,sex);
                break;
            case COMPLETE_USER_DATA:
                doCompleteUserData(name,phone,address,sex);
                break;
        }



    }

    private void doCompleteUserData(String name, String phone, String address, String sex) {

        if (name.isEmpty()) {
            mView.showToast(mView.getNameError());
            return;
        }
        if (phone.length() != 10) {
            mView.showToast(mView.getPhoneError());
            return;
        }
        if (address.isEmpty()) {
            mView.showToast(mView.getAddressError());
            return;
        }

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.sex = sex;

        mView.showProgressDialog(mView.getCreating());
        fireStoreHandler.createNewUserAccount(name,phone,authHandler.getUser().getEmail(),address,sex,UUID.randomUUID().toString(),createUserDataListener);
    }




    private void doRegisterAccount(String name, String phone, String email, String address, String password, String passwordConfirm, String sex) {
        if (name.isEmpty()) {
            mView.showToast(mView.getNameError());
            return;
        }
        if (phone.length() != 10) {
            mView.showToast(mView.getPhoneError());
            return;
        }
        if (email.isEmpty() || !email.contains("@")) {
            mView.showToast(mView.getEmailError());
            return;
        }
        if (address.isEmpty()) {
            mView.showToast(mView.getAddressError());
            return;
        }
        if (password.isEmpty() || passwordConfirm.isEmpty()) {
            mView.showToast(mView.getPasswordError());
            return;
        }

        if (!password.equals(passwordConfirm)) {
            mView.showToast(mView.getPasswordAreNotTheSame());
            return;
        }
        boolean isFoundSameEmail = false;
        if (!userDataList.isEmpty()){
            for (UserData data : userDataList){
                if (data.getEmail().equals(email)){
                    isFoundSameEmail = true;
                }
            }
        }
        if (isFoundSameEmail){
            mView.showToast(mView.getIsSameEmail());
            return;
        }


        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
        this.sex = sex;

        mView.showProgressDialog(mView.getCreating());
        authHandler.registerAccount(email, password, registerAccountCallBackListener);
    }

    @Override
    public void onLoadUserList() {
        fireStoreHandler.getUserList(getUserListListener);
    }

    @Override
    public void onCatchMode(int type) {
        this.type = type;
        switch (type){
            case REGISTER:
                mView.showAccountAndPassword(true);
                mView.setTitle(mView.getRegisterAccount());
                mView.setCreateButtonText(mView.getRegisterAccount());
                break;
            case COMPLETE_USER_DATA:
                mView.showAccountAndPassword(false);
                mView.setCreateButtonText(mView.getCompleteUserData());
                mView.setTitle(mView.getCompleteRegister());
                break;
        }
    }

    private FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>> getUserListListener = new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>>() {
        @Override
        public void onSuccessful(ArrayList<UserData> data) {
            userDataList = new ArrayList<>();
            userDataList.addAll(data);
            fireStoreHandler.setAllUserList(userDataList);
            MichaelLog.i("達到使用者資料了 userDataList size : "+userDataList.size());
        }

        @Override
        public void onFail(String message) {
            MichaelLog.i("拿不到使用者清單");
        }
    };

    //註冊帳號成功與否在這
    private AuthHandler.OnCreateNewAccountListener registerAccountCallBackListener = new AuthHandler.OnCreateNewAccountListener() {
        @Override
        public void onSuccessful() { ;
            String uuid = UUID.randomUUID().toString();
            //登入成功在FireStore建立資料
            fireStoreHandler.createNewUserAccount(name,phone,email,address,sex,uuid,createUserDataListener);

        }

        @Override
        public void onFail(String message) {
            mView.dismissProgressDialog();
            mView.showToast(message);
        }
    };

    private FireStoreHandler.OnCatchFireStoreResultListener<String> createUserDataListener = new FireStoreHandler.OnCatchFireStoreResultListener<String>() {
        @Override
        public void onSuccessful(String data) {
            mView.dismissProgressDialog();
            if (authHandler.getCurrentUser()){
                mView.showToast(data);
                mView.setUserEmail(name);
                mView.goToProductListPage();
            }else {
                mView.showToast(mView.getLoginError());
            }
        }

        @Override
        public void onFail(String message) {
            mView.dismissProgressDialog();
            mView.showToast(message);
        }
    };

}
