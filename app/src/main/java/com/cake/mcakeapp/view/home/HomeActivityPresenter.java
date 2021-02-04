package com.cake.mcakeapp.view.home;

public interface HomeActivityPresenter {
    void onNavigationViewClickListener();

    void onLoadData();

    void onNavigationMenuItemClickListener(String title);

    void onRegisterButtonClickListener();

    void onCheckUserExist();

    void onLoginButtonClickListener();

    void onLoadProductPage();
}
