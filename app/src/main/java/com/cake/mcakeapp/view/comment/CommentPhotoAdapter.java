package com.cake.mcakeapp.view.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.ImageHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CommentPhotoAdapter extends RecyclerView.Adapter<CommentPhotoAdapter.ViewHolder> {

    private ArrayList<String> photoUrlArray;

    public void setPhotoUrlArray(ArrayList<String> photoUrlArray) {
        this.photoUrlArray = photoUrlArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_photo_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = photoUrlArray.get(position);
        ImageHelper.getInstance().setImageResource(holder.tvPhoto,url);
    }

    @Override
    public int getItemCount() {
        return photoUrlArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView tvPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPhoto = itemView.findViewById(R.id.photo_item_image);

        }
    }
}
