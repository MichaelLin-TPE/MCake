package com.cake.mcakeapp.view.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.AccountManager;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.tool.ImageHelper;
import com.cake.mcakeapp.view.write_comment.PhotoAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.utils.L;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private ArrayList<CommentData> commentList;

    public void setCommentList(ArrayList<CommentData> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentData data = commentList.get(position);
        holder.tvComment.setText(data.getComment());
        holder.tvTime.setText(String.format(Locale.getDefault(),"%s %s",new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.TAIWAN).format(new Date(data.getTimeMillis())) , "上傳"));
        holder.tvStarAmount.setText(data.getStarAmount()+"");

        catchUserData(holder,data);

        CommentPhotoAdapter adapter = new CommentPhotoAdapter();
        adapter.setPhotoUrlArray(data.getPhotoUrlArray());
        holder.recyclerView.setAdapter(adapter);

    }

    private void catchUserData(ViewHolder holder, CommentData data) {
        ArrayList<UserData> userData = AccountManager.getInstance().getUserList();
        for (UserData user : userData){
            if (user.getUuid().equals(data.getUuid())){
                holder.tvName.setText(user.getName());
                if (user.getPhotoUrl() == null || user.getPhotoUrl().isEmpty()){
                    break;
                }
                ImageHelper.getInstance().setImageResource(holder.tvPhoto,user.getPhotoUrl());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView tvPhoto;

        private TextView tvName, tvComment, tvStarAmount, tvTime;

        private RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhoto = itemView.findViewById(R.id.comment_item_user_photo);
            tvName = itemView.findViewById(R.id.comment_item_user_name);
            tvComment = itemView.findViewById(R.id.comment_item_comment_content);
            tvStarAmount = itemView.findViewById(R.id.comment_item_star_amount);
            tvTime = itemView.findViewById(R.id.comment_item_time);
            recyclerView = itemView.findViewById(R.id.comment_item_recycler_view);


            LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getInstance().getApplicationContext());
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
        }
    }
}
