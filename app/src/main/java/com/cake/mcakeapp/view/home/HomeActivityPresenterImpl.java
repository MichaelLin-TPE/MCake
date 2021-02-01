package com.cake.mcakeapp.view.home;

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
}
