package com.cake.mcakeapp.view.detail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.ProductData;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter{

    private ProductData data;

    private static final int IMAGE = 0;

    private static final int INFORMATION = 1;

    private Context context;

    private Activity activity;

    public DetailAdapter() {
        context = MyApplication.getInstance().getApplicationContext();
    }

    public void setProductData(ProductData data) {
        this.data = data;
    }

    public void setContext(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;

        if (viewType == IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_banner_item_layout,parent,false);
            return new ImageViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_information_item_layout,parent,false);

        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder){
            DetailImageAdapter adapter = new DetailImageAdapter(data.getImageUrlArray());

            ((ImageViewHolder) holder).banner.addBannerLifecycleObserver((LifecycleOwner)activity).setAdapter(adapter).setIndicator(new CircleIndicator(context));
            ((ImageViewHolder) holder).banner.setPageTransformer(new ZoomOutPageTransformer());
        }

        if (holder instanceof InformationViewHolder){

            if (data.isCheckHeart()){
                ((InformationViewHolder) holder).ivHeart.setImageResource(R.drawable.heart_full);
            }else {
                ((InformationViewHolder) holder).ivHeart.setImageResource(R.drawable.heart_empty);
            }
            ((InformationViewHolder) holder).tvName.setText(String.format(Locale.getDefault(),"%s : %s",context.getString(R.string.product_name),data.getName()));

            ((InformationViewHolder) holder).tvDescription.setText(String.format(Locale.getDefault(),"%s : %s",context.getString(R.string.product_description),data.getDescription()));
            ((InformationViewHolder) holder).tvMaterial.setText(String.format(Locale.getDefault(),"%s : %s",context.getString(R.string.product_material),data.getMaterialDescription()));
            ((InformationViewHolder) holder).tvWorkMainDay.setText(String.format(Locale.getDefault(),"%s : %d",context.getString(R.string.product_working_main_day),data.getWorkMainDay()));
            ((InformationViewHolder) holder).tvPrice.setText(String.format(Locale.getDefault(),"$%d",data.getCurrentPrice()));
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return IMAGE;
        }

        return INFORMATION;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private Banner banner;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.detail_item_banner);
        }
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName , tvMaterial , tvDescription,tvWorkMainDay,tvPrice;

        private ImageView ivHeart;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.detail_information_name);
            tvMaterial = itemView.findViewById(R.id.detail_information_material);
            tvDescription = itemView.findViewById(R.id.detail_information_description);
            tvWorkMainDay = itemView.findViewById(R.id.detail_information_work_main_day);
            tvPrice = itemView.findViewById(R.id.detail_information_price);
            ivHeart = itemView.findViewById(R.id.detail_information_heart);

        }
    }
}
