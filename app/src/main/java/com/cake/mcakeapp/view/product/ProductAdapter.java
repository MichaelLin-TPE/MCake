package com.cake.mcakeapp.view.product;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.tool.ImageHelper;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<ProductData> productList;

    private OnProductIconClickListener iconClickListener;

    private AuthHandler authHandler;

    public ProductAdapter(){
        authHandler = new AuthHandlerImpl();
    }

    public void setOnProductIconClickListener(OnProductIconClickListener iconClickListener){
        this.iconClickListener = iconClickListener;
    }

    public void setProductList(ArrayList<ProductData> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductData data = productList.get(position);

        ImageHelper.getInstance().setImageResource(holder.ivPhoto,data.getImageUrlArray().get(0));
        holder.tvTitle.setText(data.getName());
        holder.tvState.setText("現正熱賣中");
        holder.tvPrice.setText(String.format(Locale.getDefault(),"$%s",data.getCurrentPrice()));


        if (data.isCheckHeart()){
            holder.ivHeart.setImageResource(R.drawable.heart_full);
        }else {
            holder.ivHeart.setImageResource(R.drawable.heart_empty);
        }

        if (data.isCheckCart()){
            holder.ivCart.setImageResource(R.drawable.cart_full);
        }else {
            holder.ivCart.setImageResource(R.drawable.cart_empty);
        }


        holder.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!authHandler.getCurrentUser()){

                    iconClickListener.onNeedLogin();

                    return;
                }


                data.setCheckHeart(!data.isCheckHeart());

                iconClickListener.onClickHeart(data);

                notifyItemChanged(position);
            }
        });

        holder.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!authHandler.getCurrentUser()){

                    iconClickListener.onNeedLogin();

                    return;
                }

                data.setCheckCart(!data.isCheckCart());

                iconClickListener.onClickCart(data);

                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivPhoto;

        private ImageView ivHeart,ivCart;

        private TextView tvTitle,tvState,tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.product_item_photo);
            ivHeart = itemView.findViewById(R.id.product_item_heart);
            ivCart = itemView.findViewById(R.id.product_item_cart);
            tvTitle = itemView.findViewById(R.id.product_item_title);
            tvState = itemView.findViewById(R.id.product_item_state);
            tvPrice = itemView.findViewById(R.id.product_item_price);

        }
    }

    public interface OnProductIconClickListener{
        void onClickHeart(ProductData data);
        void onClickCart(ProductData data);
        void onNeedLogin();
    }
}
