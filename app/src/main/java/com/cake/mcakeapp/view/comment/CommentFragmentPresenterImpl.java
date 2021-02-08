package com.cake.mcakeapp.view.comment;

import java.util.ArrayList;

public class CommentFragmentPresenterImpl implements CommentFragmentPresenter {

    private CommentFragmentVu mView;

    public CommentFragmentPresenterImpl(CommentFragmentVu mView) {
        this.mView = mView;
    }

    @Override
    public void onAddCommentButtonClickListener() {

        mView.goToWriteCommentActivity();

    }


}
