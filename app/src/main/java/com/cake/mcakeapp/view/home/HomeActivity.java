package com.cake.mcakeapp.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.MenuData;
import com.cake.mcakeapp.menu.MenuAdapter;
import com.cake.mcakeapp.tool.TypefaceHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeActivityVu {


    private HomeActivityPresenter presenter;

    private DrawerLayout drawerLayout;

    private RecyclerView rvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initPresenter();

        initView();

        presenter.onLoadData();

    }

    private void initView() {
        drawerLayout = findViewById(R.id.home_drawer_layout);
        rvMenu = findViewById(R.id.navigation_header_recycler_view);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        TextView tvActionTitle = findViewById(R.id.home_action_bar_title);
        //設置字型
        tvActionTitle.setTypeface(TypefaceHelper.get(this,"Bock_Personaluse.otf"));

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
}