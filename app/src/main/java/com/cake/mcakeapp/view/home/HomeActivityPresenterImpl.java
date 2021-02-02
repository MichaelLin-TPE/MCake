package com.cake.mcakeapp.view.home;

import android.graphics.Paint;

import com.cake.mcakeapp.tool.DataProvider;

import java.util.Date;

public class HomeActivityPresenterImpl implements HomeActivityPresenter{

    private HomeActivityVu mView;

    public HomeActivityPresenterImpl(HomeActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onNavigationViewClickListener() {

        mView.openDrawer();

    }

    @Override
    public void onLoadData() {
        mView.setNavigationRecyclerView(DataProvider.getMenuList());
    }

    @Override
    public void onNavigationMenuItemClickListener(String title) {
        if (title.equals(mView.getProduct())){
            return;
        }
        if (title.equals(mView.getOrder())){
            return;
        }
        if (title.equals(mView.getCart())){
            return;
        }
        if (title.equals(mView.getContact())){
            return;
        }
        if (title.equals(mView.getMember())) {

        }
    }
}
