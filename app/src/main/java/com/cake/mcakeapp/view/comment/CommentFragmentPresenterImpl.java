package com.cake.mcakeapp.view.comment;

import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;

import java.util.ArrayList;

public class CommentFragmentPresenterImpl implements CommentFragmentPresenter {

    private CommentFragmentVu mView;

    private AuthHandler authHandler;

    private FireStoreHandler fireStoreHandler;

    public CommentFragmentPresenterImpl(CommentFragmentVu mView) {
        this.mView = mView;
        authHandler = new AuthHandlerImpl();
        fireStoreHandler = new FireStoreHandlerImpl();

    }

    @Override
    public void onAddCommentButtonClickListener() {


        if (!authHandler.getCurrentUser()){

            mView.goToSignUpPage();

            return;
        }

        mView.goToWriteCommentActivity();

    }

    @Override
    public void onLoadData() {
        fireStoreHandler.getCommentData(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<CommentData>>() {
            @Override
            public void onSuccessful(ArrayList<CommentData> data) {
                if (data == null || data.isEmpty()){
                    return;
                }
                mView.showCommentList(data);
            }

            @Override
            public void onFail(String message) {
                mView.showToast(message);
            }
        });
    }


}
