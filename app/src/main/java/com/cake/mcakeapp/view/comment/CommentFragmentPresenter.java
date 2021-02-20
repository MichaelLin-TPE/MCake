package com.cake.mcakeapp.view.comment;

import java.util.ArrayList;

public interface CommentFragmentPresenter {
    void onAddCommentButtonClickListener();

    void onLoadData();

    void onPhotoClickListener(String url);
}
