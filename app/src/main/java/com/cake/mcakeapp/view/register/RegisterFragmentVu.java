package com.cake.mcakeapp.view.register;

public interface RegisterFragmentVu {
    void showSexSelectDialog();

    void setSexResult(String sex);

    String getNameError();

    void showToast(String nameError);

    String getPhoneError();

    String getEmailError();

    String getAddressError();

    String getPasswordError();

    String getPasswordAreNotTheSame();

    String getCreating();

    void showProgressDialog(String creating);

    void dismissProgressDialog();

    void setUserEmail(String name);

    String getLoginError();

    void goToProductListPage();

    String getIsSameEmail();

    void showAccountAndPassword(boolean isShow);

    String getRegisterAccount();

    void setCreateButtonText(String content);

    String getCompleteUserData();

    void setTitle(String title);

    String getCompleteRegister();
}
