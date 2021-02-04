package com.cake.mcakeapp.view.login;

import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.error.ErrorData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;

import java.util.ArrayList;

public class LoginFragmentPresenterImpl implements LoginFragmentPresenter {

    private LoginFragmentVu mView;

    private FireStoreHandler fireStoreHandler;

    private AuthHandler authHandler;

    private ArrayList<UserData> userDataList;

    public LoginFragmentPresenterImpl(LoginFragmentVu mView) {
        fireStoreHandler = new FireStoreHandlerImpl();
        authHandler = new AuthHandlerImpl();
        userDataList = new ArrayList<>();
        this.mView = mView;
    }

    @Override
    public void onGoogleLoginClickListener() {
        mView.showProgressDialog();
        mView.startToGoogleLogin();
    }

    @Override
    public void onGoogleLoginSuccessful() {
        mView.dismissProgressDialog();
        mView.showToast(mView.getLoginSuccessful());
        String name = "";
        for (UserData data : userDataList){
            if (data.getEmail().equals(authHandler.getCurrentUserEmail())){
                name = data.getName();
                break;
            }
        }

        mView.goToProductListPage(name);
    }

    @Override
    public void onCreateUserData() {
        mView.dismissProgressDialog();
        mView.goToCompleteUserDataPage();
    }

    @Override
    public void onLoadAllUserData() {
        fireStoreHandler.getUserList(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>>() {
            @Override
            public void onSuccessful(ArrayList<UserData> data) {
                userDataList.addAll(data);
            }

            @Override
            public void onFail(String message) {
                mView.showToast(message);
            }
        });
    }

    @Override
    public void onRegisterButtonClickListener() {
        mView.goToRegisterPage();
    }

    @Override
    public void onLoginButtonClickListener(String account, String password) {
        if (account.isEmpty()){
            mView.showToast(mView.getAccountError());
            return;
        }
        if (password.isEmpty()){
            mView.showToast(mView.getPasswordError());
            return;
        }

        mView.showProgressDialog();

        authHandler.doAccountAndPasswordLogin(account,password,userLoginListener);
    }

    private AuthHandler.OnUserLoginListener userLoginListener = new AuthHandler.OnUserLoginListener() {
        @Override
        public void onSuccessful() {
            mView.dismissProgressDialog();

            String name = "";
            for (UserData data : userDataList){
                if (data.getEmail().equals(authHandler.getCurrentUserEmail())){
                    name = data.getName();
                    break;
                }
            }
            mView.goToProductListPage(name);
        }

        @Override
        public void onFail(String message) {
            mView.dismissProgressDialog();
            mView.showToast(ErrorData.getErrorMessage(message));
            MichaelLog.i("登入失敗："+message);
        }
    };


}
