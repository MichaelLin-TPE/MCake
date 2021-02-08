package com.cake.mcakeapp.view.write_comment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cake.mcakeapp.R;

import java.util.ArrayList;

public class WriteCommentActivity extends AppCompatActivity implements WriteCommentActivityVu {

    private WriteCommentActivityPresenter presenter;

    private ImageView ivStar1,ivStar2,ivStar3,ivStar4,ivStar5;

    private static final int ONE_STAR = 1;

    private static final int TWO_STAR = 2;

    private static final int THREE_STAR = 3;

    private static final int FOUR_STAR = 4;

    private static final int FIVE_STAR = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        initPresenter();
        initView();

    }


    private void initView() {
        ImageView ivBack = findViewById(R.id.write_comment_action_bar_back);
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
}