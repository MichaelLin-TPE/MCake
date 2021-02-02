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
}
