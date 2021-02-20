package com.cake.mcakeapp.view.write_comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.DpConvertTool;
import com.cake.mcakeapp.tool.GlideEngine;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.widget.ProgressDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WriteCommentActivity extends AppCompatActivity implements WriteCommentActivityVu {

    private WriteCommentActivityPresenter presenter;

    private ImageView ivStar1,ivStar2,ivStar3,ivStar4,ivStar5;

    private ImageView ivAddPhoto;

    private TextView tvAddPhotoTitle , tvPhotoTitle,tvFinish,tvReSelectPhoto;

    private EditText edComment;

    public static final int ONE_STAR = 1;

    public static final int TWO_STAR = 2;

    public static final int THREE_STAR = 3;

    public static final int FOUR_STAR = 4;

    public static final int FIVE_STAR = 5;

    private RecyclerView rvPhoto;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        initPresenter();
        initView();

    }


    private void initView() {
        ImageView ivBack = findViewById(R.id.write_comment_action_bar_back);

        tvReSelectPhoto = findViewById(R.id.write_comment_re_select_photo);

        ivAddPhoto = findViewById(R.id.write_comment_add_photo);

        tvAddPhotoTitle = findViewById(R.id.write_comment_add_photo_title);

        rvPhoto = findViewById(R.id.write_comment_recycler_view);

        tvPhotoTitle = findViewById(R.id.write_comment_photo_title);

        tvFinish = findViewById(R.id.write_comment_action_bar_finish);

        edComment = findViewById(R.id.write_comment_edit);

        tvReSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onShowSelectPhotoView();
            }
        });

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUploadCommentClickListener(edComment.getText().toString());
            }
        });

        ivAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onShowSelectPhotoView();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButtonClickListener();
            }
        });

        ivStar1  = findViewById(R.id.write_comment_star_1);
        ivStar2  = findViewById(R.id.write_comment_star_2);
        ivStar3  = findViewById(R.id.write_comment_star_3);
        ivStar4  = findViewById(R.id.write_comment_star_4);
        ivStar5  = findViewById(R.id.write_comment_star_5);

        ivStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarButtonClickListener(ONE_STAR);
            }
        });
        ivStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarButtonClickListener(TWO_STAR);
            }
        });
        ivStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarButtonClickListener(THREE_STAR);
            }
        });
        ivStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarButtonClickListener(FOUR_STAR);
            }
        });
        ivStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStarButtonClickListener(FIVE_STAR);
            }
        });



    }

    private void initPresenter() {
        presenter = new WriteCommentActivityPresenterImpl(this);
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void changeOneStar() {
        ivStar1.setImageResource(R.drawable.star_full);
        ivStar2.setImageResource(R.drawable.star_empty);
        ivStar3.setImageResource(R.drawable.star_empty);
        ivStar4.setImageResource(R.drawable.star_empty);
        ivStar5.setImageResource(R.drawable.star_empty);
    }

    @Override
    public void changeTwoStar() {
        ivStar1.setImageResource(R.drawable.star_full);
        ivStar2.setImageResource(R.drawable.star_full);
        ivStar3.setImageResource(R.drawable.star_empty);
        ivStar4.setImageResource(R.drawable.star_empty);
        ivStar5.setImageResource(R.drawable.star_empty);
    }

    @Override
    public void changeThreeStar() {
        ivStar1.setImageResource(R.drawable.star_full);
        ivStar2.setImageResource(R.drawable.star_full);
        ivStar3.setImageResource(R.drawable.star_full);
        ivStar4.setImageResource(R.drawable.star_empty);
        ivStar5.setImageResource(R.drawable.star_empty);
    }

    @Override
    public void changeFourStar() {
        ivStar1.setImageResource(R.drawable.star_full);
        ivStar2.setImageResource(R.drawable.star_full);
        ivStar3.setImageResource(R.drawable.star_full);
        ivStar4.setImageResource(R.drawable.star_full);
        ivStar5.setImageResource(R.drawable.star_empty);
    }

    @Override
    public void changeFiveStar() {
        ivStar1.setImageResource(R.drawable.star_full);
        ivStar2.setImageResource(R.drawable.star_full);
        ivStar3.setImageResource(R.drawable.star_full);
        ivStar4.setImageResource(R.drawable.star_full);
        ivStar5.setImageResource(R.drawable.star_full);
    }

    @Override
    public void showSelectPhotoView() {
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

    @Override
    public void showPhotoView(boolean isShow) {
        rvPhoto.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvPhotoTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvReSelectPhoto.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddPhotoButton(boolean isShow) {
        ivAddPhoto.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        tvAddPhotoTitle.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showAllPhotoView(ArrayList<byte[]> photoArray) {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) edComment.getLayoutParams();
        params.topToBottom = R.id.write_comment_recycler_view;
        params.topMargin = DpConvertTool.getInstance().getDb(10);
        edComment.setLayoutParams(params);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvPhoto.setLayoutManager(manager);

        PhotoAdapter adapter = new PhotoAdapter();
        adapter.setImageArray(photoArray);
        rvPhoto.setAdapter(adapter);

    }

    @Override
    public String getCommentEmpty() {
        return getString(R.string.comment_empty);
    }

    @Override
    public void showToast(String commentEmpty) {
        Toast.makeText(this,commentEmpty,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(getString(R.string.title_upload_photo));
        progressDialog.show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public void dismissProgressDialog() {

        if (progressDialog == null){
            return;
        }

        progressDialog.dismiss();
    }

    @Override
    public void finishPage() {
        finish();
    }

    private void onCatchPhotoResult(List<LocalMedia> result) {
        ArrayList<byte[]> photoArray = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            File file = new File(result.get(i).getCutPath());
            Uri uri = Uri.fromFile(file);
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
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

            presenter.onCatchPhotos(photoArray);
        }
    }
}