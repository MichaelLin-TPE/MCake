package com.cake.mcakeapp.view.write_comment;

public class WriteCommentActivityPresenterImpl implements WriteCommentActivityPresenter {

    private WriteCommentActivityVu mView;

    public WriteCommentActivityPresenterImpl(WriteCommentActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onStarButtonClickListener(int starAmount) {

    }
}
