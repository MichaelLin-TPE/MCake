package com.cake.mcakeapp.view.login;

public interface LoginFragmentVu {
    void startToGoogleLogin();

    void showProgressDialog();

    void dismissProgressDialog();

    void goToProductListPage(String name);

    void goToCompleteUserDataPage();

    String getLoginSuccessful();

    void showToast(String message);

    void goToRegisterPage();

    String getAccountError();

    String getPasswordError();
}
