package com.cake.mcakeapp.view.home;

import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.AccountManager;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.DataProvider;
import com.cake.mcakeapp.tool.JsonHelper;
import com.cake.mcakeapp.tool.MichaelLog;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;


public class HomeActivityPresenterImpl implements HomeActivityPresenter{

    private HomeActivityVu mView;

    private AuthHandler authHandler;

    private FireStoreHandler fireStoreHandler;

    public HomeActivityPresenterImpl(HomeActivityVu mView) {
        authHandler = new AuthHandlerImpl();
        fireStoreHandler = new FireStoreHandlerImpl();
        fireStoreHandler.catchOriginalCartData();
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
        mView.closeDrawer();
        if (title.equals(mView.getProduct())){

            mView.showProductPage();
            return;
        }
        if (title.equals(mView.getOrder())){
            mView.showOrderFragment();
            return;
        }
        if (title.equals(mView.getCart())){
            mView.showCartFragment();
            return;
        }
        if (title.equals(mView.getContact())){
            mView.showContactUsFragment();
            return;
        }
        if (title.equals(mView.getMember())) {
            mView.showMemberCenterFragment();
        }
        if (title.equals(mView.getComment())){
            mView.showCommentPage();
        }
    }

    @Override
    public void onRegisterButtonClickListener() {
        mView.closeDrawer();
        mView.goToRegisterPage();
    }

    @Override
    public void onCheckUserExist() {
        if (authHandler.getCurrentUser()){
            fireStoreHandler.getUserList(getUserListListener);
            MichaelLog.i("有使用者");
            mView.showLoginButtonAndRegisterButton(false);
        }else {
            MichaelLog.i("無使用者");
            mView.setUserEmpty();
            mView.showLoginButtonAndRegisterButton(true);
        }
    }

    @Override
    public void onLoginButtonClickListener() {
        mView.closeDrawer();
        mView.goToLoginPage();
    }

    @Override
    public void onLoadProductPage() {
        mView.showProductPage();
    }

    @Override
    public void onCheckUserCartAmount() {

        ArrayList<ProductData> cartList = fireStoreHandler.getCartList();

        if (cartList == null || cartList.isEmpty()){

        }
    }

    private FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>> getUserListListener = new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>>() {
        @Override
        public void onSuccessful(ArrayList<UserData> data) {
            String userName = "",uuid = "";
            for (UserData userData : data){
                if (userData.getEmail().equals(authHandler.getCurrentUserEmail())){
                    userName = userData.getName();
                    uuid = userData.getUuid();
                    break;
                }
            }
            mView.setUserName(userName);


            //儲存必要資訊
            String json = JsonHelper.getGson().toJson(data);
            AccountManager.getInstance().setUserListJson(json);
            AccountManager.getInstance().setUserUUID(uuid);
        }

        @Override
        public void onFail(String message) {
            MichaelLog.i("取不到使用者資料");
        }
    };
}
