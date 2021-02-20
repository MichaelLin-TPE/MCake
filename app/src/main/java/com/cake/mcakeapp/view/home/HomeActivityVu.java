package com.cake.mcakeapp.view.home;

import com.cake.mcakeapp.data.MenuData;

import java.util.ArrayList;

public interface HomeActivityVu {
    void openDrawer();

    void closeDrawer();

    void setNavigationRecyclerView(ArrayList<MenuData> menuList);

    String getProduct();

    String getOrder();

    String getCart();

    String getContact();

    String getMember();

    void goToRegisterPage();

    String getRegister();

    void setActionBarTitle(String title);

    void setUserEmpty();

    void setUserName(String name);

    void goToLoginPage();

    void showLoginButtonAndRegisterButton(boolean isShow);

    void showProductPage();

    void showOrderFragment();

    void showCartFragment();

    void showContactUsFragment();

    void showMemberCenterFragment();

    String getComment();

    void showCommentPage();

    void setCartCount(int cartCount);

    void showCartCount(boolean isShow);
}
