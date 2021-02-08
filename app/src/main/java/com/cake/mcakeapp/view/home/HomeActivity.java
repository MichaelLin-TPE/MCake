package com.cake.mcakeapp.view.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.MenuData;
import com.cake.mcakeapp.menu.MenuAdapter;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.tool.TypefaceHelper;
import com.cake.mcakeapp.view.cart.CartFragment;
import com.cake.mcakeapp.view.comment.CommentFragment;
import com.cake.mcakeapp.view.contact.ContactFragment;
import com.cake.mcakeapp.view.login.LoginFragment;
import com.cake.mcakeapp.view.member.MemberFragment;
import com.cake.mcakeapp.view.order.OrderFragment;
import com.cake.mcakeapp.view.product.ProductFragment;
import com.cake.mcakeapp.view.register.RegisterFragment;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Locale;

import static com.cake.mcakeapp.view.register.RegisterFragment.REGISTER;

public class HomeActivity extends AppCompatActivity implements HomeActivityVu {


    private HomeActivityPresenter presenter;

    private DrawerLayout drawerLayout;

    private RecyclerView rvMenu;

    private TextView tvActionBarTitle, tvUserName , tvRegister,tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initPresenter();

        initView();

        presenter.onLoadData();
        presenter.onCheckUserExist();
        presenter.onLoadProductPage();
    }
    private void initView() {
        tvUserName = findViewById(R.id.navigation_header_name);
        drawerLayout = findViewById(R.id.home_drawer_layout);
        rvMenu = findViewById(R.id.navigation_header_recycler_view);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        tvActionBarTitle = findViewById(R.id.home_action_bar_title);
        tvRegister = findViewById(R.id.navigation_header_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClickListener();
            }
        });

        tvLogin = findViewById(R.id.navigation_header_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginButtonClickListener();
            }
        });


        //設置字型
        tvActionBarTitle.setTypeface(TypefaceHelper.get(this, "Bock_Personaluse.otf"));

        ImageView ivMenu = findViewById(R.id.home_action_bar_menu);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNavigationViewClickListener();
            }
        });
    }

    private void initPresenter() {
        presenter = new HomeActivityPresenterImpl(this);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void setNavigationRecyclerView(ArrayList<MenuData> menuList) {
        MenuAdapter adapter = new MenuAdapter();
        adapter.setMenuDataArrayList(menuList);
        rvMenu.setAdapter(adapter);
        adapter.setOnNavigationMenuItemClickListener(new MenuAdapter.OnNavigationMenuItemClickListener() {
            @Override
            public void onMenuItemClick(String title) {
                presenter.onNavigationMenuItemClickListener(title);
            }
        });
    }

    @Override
    public String getProduct() {
        return getString(R.string.product_list);
    }

    @Override
    public String getOrder() {
        return getString(R.string.search_order);
    }

    @Override
    public String getCart() {
        return getString(R.string.shopping_cart);
    }

    @Override
    public String getContact() {
        return getString(R.string.contact_us);
    }

    @Override
    public String getMember() {
        return getString(R.string.memeber_center);
    }

    @Override
    public void goToRegisterPage() {

        RegisterFragment registerFragment = RegisterFragment.newInstance(REGISTER);

        Tools.replace(R.id.home_frame_layout, getSupportFragmentManager(), registerFragment, false, RegisterFragment.newInstance(REGISTER).getClass().getSimpleName());

        registerFragment.setOnUserIsLoginListener(new RegisterFragment.OnUserIsLoginListener() {
            @Override
            public void onLogin(String name) {
                presenter.onCheckUserExist();
            }
        });
    }

    @Override
    public String getRegister() {
        return getString(R.string.title_register);
    }

    @Override
    public void setActionBarTitle(String title) {
        tvActionBarTitle.setText(title);
    }

    @Override
    public void setUserEmpty() {
        tvUserName.setText(getString(R.string.user_welcome));
    }

    @Override
    public void setUserName(String name) {
        tvUserName.setText(String.format(Locale.getDefault(), "%s %s", name, getString(R.string.welcome_back)));
    }

    @Override
    public void goToLoginPage() {

        LoginFragment loginFragment = LoginFragment.newInstance();

        Tools.replace(R.id.home_frame_layout, getSupportFragmentManager(), loginFragment, false, LoginFragment.newInstance().getClass().getSimpleName());
        loginFragment.setOnUserIsLoginListener(new RegisterFragment.OnUserIsLoginListener() {
            @Override
            public void onLogin(String name) {
                presenter.onCheckUserExist();
            }
        });


    }

    @Override
    public void showLoginButtonAndRegisterButton(boolean isShow) {
        tvLogin.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvRegister.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProductPage() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), ProductFragment.newInstance(),false,ProductFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void showOrderFragment() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), OrderFragment.newInstance(),false,OrderFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void showCartFragment() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), CartFragment.newInstance(),false,CartFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void showContactUsFragment() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), ContactFragment.newInstance(),false,ContactFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void showMemberCenterFragment() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), MemberFragment.newInstance(),false,MemberFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public String getComment() {
        return getString(R.string.comment);
    }

    @Override
    public void showCommentPage() {
        Tools.replace(R.id.home_frame_layout,getSupportFragmentManager(), CommentFragment.newInstance(),false,CommentFragment.newInstance().getClass().getSimpleName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MichaelLog.i("HomeActivity 有收到資料 : "+requestCode);
        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}