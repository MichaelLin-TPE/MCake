package com.cake.mcakeapp.view.write_comment;

import com.cake.mcakeapp.data.AccountManager;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.firestore.UploadImageHandler;
import com.cake.mcakeapp.firestore.UploadImageHandlerImpl;

import java.util.ArrayList;


import static com.cake.mcakeapp.view.write_comment.WriteCommentActivity.FIVE_STAR;
import static com.cake.mcakeapp.view.write_comment.WriteCommentActivity.FOUR_STAR;
import static com.cake.mcakeapp.view.write_comment.WriteCommentActivity.ONE_STAR;
import static com.cake.mcakeapp.view.write_comment.WriteCommentActivity.THREE_STAR;
import static com.cake.mcakeapp.view.write_comment.WriteCommentActivity.TWO_STAR;

public class WriteCommentActivityPresenterImpl implements WriteCommentActivityPresenter {

    private WriteCommentActivityVu mView;

    private UploadImageHandler uploadImageHandler;

    private String comment;

    private ArrayList<byte[]> photoArray;

    private FireStoreHandler fireStoreHandler;

    private ArrayList<CommentData> allCommentList;

    private int starAmount;

    public WriteCommentActivityPresenterImpl(WriteCommentActivityVu mView) {
        this.mView = mView;
        uploadImageHandler = new UploadImageHandlerImpl();
        fireStoreHandler = new FireStoreHandlerImpl();

        fireStoreHandler.getCommentData(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<CommentData>>() {
            @Override
            public void onSuccessful(ArrayList<CommentData> data) {
                allCommentList = new ArrayList<>();
                if (data == null){
                    return;
                }
                allCommentList.addAll(data);
            }

            @Override
            public void onFail(String message) {
                mView.showToast(message);
            }
        });
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onStarButtonClickListener(int starAmount) {

        this.starAmount = starAmount;

        switch (starAmount) {
            case ONE_STAR:
                mView.changeOneStar();
                break;
            case TWO_STAR:
                mView.changeTwoStar();
                break;
            case THREE_STAR:
                mView.changeThreeStar();
                break;
            case FOUR_STAR:
                mView.changeFourStar();
                break;
            case FIVE_STAR:
                mView.changeFiveStar();
                break;

        }
    }

    @Override
    public void onShowSelectPhotoView() {
        mView.showSelectPhotoView();
    }

    @Override
    public void onCatchPhotos(ArrayList<byte[]> photoArray) {
        this.photoArray = photoArray;
        mView.showAddPhotoButton(false);
        mView.showPhotoView(true);
        mView.showAllPhotoView(photoArray);
    }

    @Override
    public void onUploadCommentClickListener(String comment) {

        if (comment.isEmpty()) {
            mView.showToast(mView.getCommentEmpty());
            return;
        }

        this.comment = comment;

        mView.showProgressDialog();

        uploadImageHandler.uploadImage(photoArray, uploadListener);

    }

    private UploadImageHandler.OnPhotoImageUploadListener uploadListener = new UploadImageHandler.OnPhotoImageUploadListener() {
        @Override
        public void onSuccessful(ArrayList<String> imageUrlArray) {

            CommentData data = new CommentData();
            data.setComment(comment);
            data.setStarAmount(starAmount);
            data.setPhotoUrlArray(imageUrlArray);
            data.setUuid(AccountManager.getInstance().getUuid());
            data.setTimeMillis(System.currentTimeMillis());

            if (allCommentList == null){
                allCommentList = new ArrayList<>();
            }
            allCommentList.add(data);
            fireStoreHandler.saveCommentData(allCommentList,onSaveCommentListener);

        }

        @Override
        public void onFail(String message) {
            mView.showToast(message);
        }
    };

    private FireStoreHandler.OnCatchFireStoreResultListener<String> onSaveCommentListener = new FireStoreHandler.OnCatchFireStoreResultListener<String>() {
        @Override
        public void onSuccessful(String data) {
            mView.dismissProgressDialog();
            mView.finishPage();
        }

        @Override
        public void onFail(String message) {
            mView.showToast(message);
        }
    };

}
