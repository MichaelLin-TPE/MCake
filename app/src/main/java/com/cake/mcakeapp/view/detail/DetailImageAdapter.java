package com.cake.mcakeapp.view.detail;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.tool.ImageHelper;

import java.util.List;

public class DetailImageAdapter extends com.youth.banner.adapter.BannerAdapter<String, DetailImageAdapter.ViewHolder> {

    private List<String> imageList;

    public DetailImageAdapter(List<String> imageUrList) {
        super(imageUrList);
        this.imageList = imageUrList;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindView(ViewHolder holder, String data, int position, int size) {

        String url = imageList.get(position);

        ImageHelper.getInstance().setImageResource(holder.imageView,url);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
