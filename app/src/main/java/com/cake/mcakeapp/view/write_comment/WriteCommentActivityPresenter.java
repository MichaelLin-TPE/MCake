package com.cake.mcakeapp.view.write_comment;

import java.util.ArrayList;

public interface WriteCommentActivityPresenter {
    void onBackButtonClickListener();

    void onStarButtonClickListener(int starAmount);

    void onShowSelectPhotoView();

    void onCatchPhotos(ArrayList<byte[]> photoArray);

    void onUploadCommentClickListener(String comment);
}
