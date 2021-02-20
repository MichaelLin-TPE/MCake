package com.cake.mcakeapp.view.product;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.JsonHelper;
import com.cake.mcakeapp.widget.SignInAndRegisterDialog;

import java.util.ArrayList;


public class ProductFragment extends Fragment implements ProductFragmentVu {

    private ProductFragmentPresenter presenter;

    private RecyclerView rvProductList;

    private FragmentActivity fragmentActivity;

    private Context context;


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
        ProductAdapter adapter = new ProductAdapter();
        adapter.setProductList(data);
        rvProductList.setAdapter(adapter);

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
        });
    }

    @Override
    public void showNeedToLoginDialog() {
        SignInAndRegisterDialog signInAndRegisterDialog = SignInAndRegisterDialog.newInstance();
        signInAndRegisterDialog.setOnSignInRegisterButtonClickListener(new SignInAndRegisterDialog.OnSignInRegisterButtonClickListener() {
            @Override
            public void onRegisterClick() {
                
            }

            @Override
            public void onLoginClick() {

            }
        });
        signInAndRegisterDialog.show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }
}