package com.cake.mcakeapp.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.MenuData;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private ArrayList<MenuData> menuDataArrayList;

    private OnNavigationMenuItemClickListener itemClickListener;

    public void setOnNavigationMenuItemClickListener(OnNavigationMenuItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setMenuDataArrayList(ArrayList<MenuData> menuDataArrayList) {
        this.menuDataArrayList = menuDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        MenuData data = menuDataArrayList.get(position);
        holder.ivIcon.setImageResource(data.getImageId());
        holder.tvTitle.setText(data.getTitle());
        holder.itemArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onMenuItemClick(holder.tvTitle.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon;

        private TextView tvTitle;

        private ConstraintLayout itemArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.menu_item_icon);
            tvTitle = itemView.findViewById(R.id.menu_item_title);
            itemArea = itemView.findViewById(R.id.menu_item_area);
        }
    }

    public interface OnNavigationMenuItemClickListener{
        void onMenuItemClick(String title);
    }


}
