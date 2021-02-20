package com.cake.mcakeapp.view.comment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.tool.GlideEngine;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.login.LoginFragment;
import com.cake.mcakeapp.view.write_comment.WriteCommentActivity;
import com.cake.mcakeapp.widget.PhotoDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CommentFragment extends Fragment implements CommentFragmentVu {


    private CommentFragmentPresenter presenter;

    private FloatingActionButton btnAdd;

    private Context context;

    private FragmentActivity fragmentActivity;

    private RecyclerView rvComment;

    private Handler handler = new Handler(Looper.getMainLooper());

    private ArrayList<CommentData> commentDataList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();
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
        presenter = new CommentFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        initView(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initView(View view) {

        rvComment = view.findViewById(R.id.comment_recycler_view);

        rvComment.setLayoutManager(new LinearLayoutManager(fragmentActivity));

        btnAdd = view.findViewById(R.id.comment_btn_add_comment);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddCommentButtonClickListener();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.onLoadData();


    }

    @Override
    public void goToWriteCommentActivity() {
        Intent it = new Intent(context, WriteCommentActivity.class);
        startActivity(it);
        Tools.startActivityInAnim(fragmentActivity);
    }

    @Override
    public void goToSignUpPage() {
        Tools.replace(R.id.home_frame_layout, fragmentActivity.getSupportFragmentManager(), LoginFragment.newInstance(), false, LoginFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(fragmentActivity, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCommentList(ArrayList<CommentData> commentDataList) {
        this.commentDataList = commentDataList;
        handler.post(showCommentView);


    }

    @Override
    public void showPhotoDialog(String url) {
        PhotoDialog photoDialog = PhotoDialog.newInstance(url);
        photoDialog.show(fragmentActivity.getSupportFragmentManager(), "dialog");
    }

    private Runnable showCommentView = new Runnable() {
        @Override
        public void run() {
            CommentAdapter adapter = new CommentAdapter();
            adapter.setCommentList(commentDataList);
            rvComment.setAdapter(adapter);

            adapter.setOnPhotoClickListener(new CommentPhotoAdapter.OnPhotoClickListener() {
                @Override
                public void onClickPhoto(String url) {
                    presenter.onPhotoClickListener(url);
                }
            });
        }
    };

}