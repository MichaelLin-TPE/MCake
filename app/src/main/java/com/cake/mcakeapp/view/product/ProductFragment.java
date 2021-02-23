package com.cake.mcakeapp.view.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.detail.ProductDetailActivity;
import com.cake.mcakeapp.view.login.LoginFragment;
import com.cake.mcakeapp.view.register.RegisterFragment;
import com.cake.mcakeapp.widget.SignInAndRegisterDialog;

import java.util.ArrayList;


public class ProductFragment extends Fragment implements ProductFragmentVu {

    private ProductFragmentPresenter presenter;

    private RecyclerView rvProductList;

    private FragmentActivity fragmentActivity;

    private ProgressBar progressBar;

    public static final String PRODUCT_DATA = "product_data";

    private Context context;

    private ProductAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new ProductFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        initView(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initView(View view) {
        rvProductList = view.findViewById(R.id.product_recycler_view);
        rvProductList.setLayoutManager(new GridLayoutManager(context,2));
        progressBar = view.findViewById(R.id.product_progress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onLoadProductList();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public String getFailToGetProductList() {
        return context.getString(R.string.fail_to_get_product_list);
    }

    @Override
    public void showProductList(ArrayList<ProductData> data) {
        MichaelLog.i("顯示畫面");
        adapter = new ProductAdapter();
        adapter.setProductList(data);
        adapter.setHasStableIds(true);
        ((SimpleItemAnimator)rvProductList.getItemAnimator()).setSupportsChangeAnimations(false);
        rvProductList.setAdapter(adapter);
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0,data.size());
        rvProductList.setRecycledViewPool(pool);
        adapter.setOnProductIconClickListener(new ProductAdapter.OnProductIconClickListener() {
            @Override
            public void onClickHeart(ProductData data) {
                presenter.onProductHeartClickListener(data);
            }

            @Override
            public void onClickCart(ProductData data) {
                presenter.onProductCartClickListener(data);
            }

            @Override
            public void onNeedLogin() {
                presenter.onNeedToLogin();
            }

            @Override
            public void onItemClick(ProductData data) {
                presenter.onProductItemClickListener(data);
            }
        });
    }

    @Override
    public void showNeedToLoginDialog() {
        SignInAndRegisterDialog signInAndRegisterDialog = SignInAndRegisterDialog.newInstance();
        signInAndRegisterDialog.setOnSignInRegisterButtonClickListener(new SignInAndRegisterDialog.OnSignInRegisterButtonClickListener() {
            @Override
            public void onRegisterClick() {
                presenter.onRegisterButtonClickListener();
            }

            @Override
            public void onLoginClick() {
                presenter.onLoginButtonClickListener();
            }
        });
        signInAndRegisterDialog.show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void goToRegisterPage() {
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(), RegisterFragment.newInstance(RegisterFragment.REGISTER),false,RegisterFragment.newInstance(RegisterFragment.REGISTER).getClass().getSimpleName());
    }

    @Override
    public void goToLoginPage() {
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(), LoginFragment.newInstance(),false,LoginFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void goToProductDetailActivity(ProductData data) {
        Intent it = new Intent(context, ProductDetailActivity.class);
        it.putExtra(PRODUCT_DATA,data);
        startActivity(it);
    }

    @Override
    public void refreshProductPage(ArrayList<ProductData> allProductDataList, int index) {
        if (adapter == null){
            MichaelLog.i("adapter is null 不能刷新畫面");
            return;
        }
        MichaelLog.i("刷新畫面");
        adapter.setProductList(allProductDataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}