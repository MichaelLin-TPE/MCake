package com.cake.mcakeapp.view.comment;

import com.cake.mcakeapp.data.CommentData;

import java.util.ArrayList;

public interface CommentFragmentVu {

    void goToWriteCommentActivity();

    void goToSignUpPage();

    void showToast(String message);

    void showCommentList(ArrayList<CommentData> data);

    void showPhotoDialog(String url);
}
