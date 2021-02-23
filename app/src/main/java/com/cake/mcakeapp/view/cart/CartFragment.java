package com.cake.mcakeapp.view.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.detail.ProductDetailActivity;

import java.util.ArrayList;

import static com.cake.mcakeapp.view.product.ProductFragment.PRODUCT_DATA;


public class CartFragment extends Fragment implements CartFragmentVu {

    private CartFragmentPresenter presenter;

    private RecyclerView rvCartList;

    private FragmentActivity fragmentActivity;

    private ProgressBar progressBar;

    private CartAdapter adapter;

    private ImageView ivLogo;

    private TextView tvNoData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        presenter = new CartFragmentPresenterImpl(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onLoadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        rvCartList = view.findViewById(R.id.cart_recycler_view);
        rvCartList.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        progressBar = view.findViewById(R.id.cart_progress);
        ivLogo = view.findViewById(R.id.cart_logo);
        tvNoData = view.findViewById(R.id.cart_no_data);
    }

    @Override
    public void showCartList(ArrayList<ProductData> data) {
        adapter = new CartAdapter();
        adapter.setCartList(data);
        rvCartList.setAdapter(adapter);
        adapter.setOnCartItemClickListener(new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onRemoveClick(ProductData data,int position) {
                MichaelLog.i("點擊移除 index : "+position);
                presenter.onButtonRemoveClickListener(data,position);
            }

            @Override
            public void onItemClick(ProductData data) {
                presenter.onCartItemClickListener(data);
            }

            @Override
            public void onCheckOutClick(ArrayList<ProductData> checkOutList) {

            }
        });
    }

    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshCartView(int position, ArrayList<ProductData> allCartList) {
        adapter.setCartList(allCartList);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,adapter.getItemCount());
    }

    @Override
    public void showNoCartData(boolean isShow) {
        ivLogo.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void clearView(ArrayList<ProductData> allCartList) {
        adapter.setCartList(allCartList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goToDetailPage(ProductData data) {
        Intent it = new Intent(fragmentActivity, ProductDetailActivity.class);
        it.putExtra(PRODUCT_DATA,data);
        startActivity(it);
        Tools.startActivityInAnim(fragmentActivity);
    }
}