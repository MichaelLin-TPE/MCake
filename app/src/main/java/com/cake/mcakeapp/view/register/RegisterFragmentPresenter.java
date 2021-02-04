package com.cake.mcakeapp.view.register;

public interface RegisterFragmentPresenter {
    void onSelectSexButtonClickListener();

    void onSelectSexConfirmClickListener(String sex);

    void onCreateAccountButtonClickListener(String name, String phone, String email, String address, String password, String passwordConfirm, String sex);

    void onLoadUserList();

    void onCatchMode(int type);
}
