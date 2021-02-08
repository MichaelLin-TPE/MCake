package com.cake.mcakeapp.view.comment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.GlideEngine;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.write_comment.WriteCommentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CommentFragment extends Fragment implements CommentFragmentVu{


    private CommentFragmentPresenter presenter;

    private FloatingActionButton btnAdd;

    private Context context;

    private FragmentActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity)context;
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
        btnAdd = view.findViewById(R.id.comment_btn_add_comment);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddCommentButtonClickListener();
            }
        });
    }

    @Override
    public void showPictureSelector() {
        //圖片選擇器
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(3)
                .compress(true)
                .enableCrop(true)
                .hideBottomControls(false)
                .showCropFrame(false)
                .freeStyleCropEnabled(true)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        onCatchPhotoResult(result);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


    private void onCatchPhotoResult(List<LocalMedia> result) {
        ArrayList<byte[]> photoArray = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            File file = new File(result.get(i).getCutPath());
            Uri uri = Uri.fromFile(file);
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] byteArray = stream.toByteArray();
                if (byteArray.length != 0) {
                    photoArray.add(byteArray);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (photoArray.size() != 0){
            MichaelLog.i("照片選了 "+photoArray.size()+" 張");
        }
    }

    @Override
    public void goToWriteCommentActivity() {
        Intent it = new Intent(context, WriteCommentActivity.class);
        startActivity(it);
        Tools.startActivityInAnim(fragmentActivity);
    }

}