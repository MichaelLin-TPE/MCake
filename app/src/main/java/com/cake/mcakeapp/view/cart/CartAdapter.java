package com.cake.mcakeapp.view.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.tool.ImageHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter {

    private static final int TOTAL = 1;

    private static final int DATA = 0;

    private ArrayList<ProductData> cartList;

    private OnCartItemClickListener cartItemClickListener;

    public void setOnCartItemClickListener(OnCartItemClickListener cartItemClickListener){
        this.cartItemClickListener = cartItemClickListener;
    }

    public void setCartList(ArrayList<ProductData> cartList) {
        this.cartList = cartList;
    }

    @Override
    public int getItemViewType(int position) {

        if (cartList == null || cartList.isEmpty()){
            return TOTAL;
        }

        if (position >= 0 && position < cartList.size()) {
            return DATA;
        }
        return TOTAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_data_item_layout, parent, false);
            return new CartDataViewHolder(view);
        }

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_item_layout, parent, false);

        return new TotalPriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CartDataViewHolder) {
            ProductData data = cartList.get(position);
            ImageHelper.getInstance().setImageResource(((CartDataViewHolder) holder).ivPhoto, data.getImageUrlArray().get(0));
            ((CartDataViewHolder) holder).tvPrice.setText(String.format(Locale.getDefault(), "$%d", data.getCurrentPrice()));
            ((CartDataViewHolder) holder).tvName.setText(data.getName());
            ((CartDataViewHolder) holder).tvDesc.setText(data.getDescription());
            ((CartDataViewHolder) holder).btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemClickListener.onRemoveClick(data,position);
                }
            });
            ((CartDataViewHolder) holder).itemArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemClickListener.onItemClick(data);
                }
            });

        }

        if (holder instanceof TotalPriceViewHolder) {
            int totalPrice = 0;
            for (ProductData pro : cartList){
                totalPrice += pro.getCurrentPrice();
            }

            ((TotalPriceViewHolder) holder).tvPrice.setText(String.format(Locale.getDefault(),"$%d",totalPrice));
            ((TotalPriceViewHolder) holder).btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemClickListener.onCheckOutClick(cartList);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if (cartList == null || cartList.isEmpty()) {
            return 0;
        }

        return cartList.size() + 1;
    }

    public static class CartDataViewHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView ivPhoto;

        private final TextView tvName;
        private final TextView tvDesc;
        private final TextView tvPrice;
        private final ConstraintLayout itemArea;
        private final Button btnRemove;


        public CartDataViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.cart_item_photo);
            tvName = itemView.findViewById(R.id.cart_item_title);
            tvDesc = itemView.findViewById(R.id.cart_item_description);
            tvPrice = itemView.findViewById(R.id.cart_item_price);
            btnRemove = itemView.findViewById(R.id.cart_item_remove);
            itemArea = itemView.findViewById(R.id.cart_item_area);

        }
    }

    public static class TotalPriceViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvPrice;

        private final Button btnCheck;

        public TotalPriceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPrice = itemView.findViewById(R.id.total_price);
            btnCheck = itemView.findViewById(R.id.total_btn_check);

        }
    }

    public interface OnCartItemClickListener{
        void onRemoveClick(ProductData data,int position);

        void onItemClick(ProductData data);

        void onCheckOutClick(ArrayList<ProductData> checkOutList);
    }


}
