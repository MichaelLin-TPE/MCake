package com.cake.mcakeapp.view.login;

public interface LoginFragmentPresenter {
    void onGoogleLoginClickListener();

    void onGoogleLoginSuccessful();

    void onCreateUserData();

    void onLoadAllUserData();

    void onRegisterButtonClickListener();

    void onLoginButtonClickListener(String account, String password);
}
