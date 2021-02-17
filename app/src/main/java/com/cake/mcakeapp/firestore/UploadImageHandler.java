package com.cake.mcakeapp.firestore;

import java.util.ArrayList;

public interface UploadImageHandler {


    void uploadImage(ArrayList<byte[]> photoArray,OnPhotoImageUploadListener uploadListener);

    interface OnPhotoImageUploadListener{
        void onSuccessful(ArrayList<String> imageUrlArray);
        void onFail(String message);
    }

}
