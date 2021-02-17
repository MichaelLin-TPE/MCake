package com.cake.mcakeapp.view.write_comment;

import java.util.ArrayList;

public interface WriteCommentActivityVu {
    void closePage();

    void changeOneStar();

    void changeTwoStar();

    void changeThreeStar();

    void changeFourStar();

    void changeFiveStar();

    void showSelectPhotoView();

    void showPhotoView(boolean isShow);

    void showAddPhotoButton(boolean isShow);

    void showAllPhotoView(ArrayList<byte[]> photoArray);

    String getCommentEmpty();

    void showToast(String commentEmpty);

    void showProgressDialog();

    void dismissProgressDialog();

    void finishPage();
}
