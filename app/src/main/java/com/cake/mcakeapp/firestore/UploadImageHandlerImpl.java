package com.cake.mcakeapp.firestore;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.MichaelLog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class UploadImageHandlerImpl implements UploadImageHandler {

    private FirebaseStorage storage;

    private StorageReference storageReference;

    private StorageReference uploadReference;

    private ArrayList<byte[]> photoArray;

    private ArrayList<String> photoUrlArray;

    private int photoCount = 0;

    public UploadImageHandlerImpl() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        photoUrlArray = new ArrayList<>();
    }

    private OnPhotoImageUploadListener uploadListener;


    @Override
    public void uploadImage(ArrayList<byte[]> photoArray, OnPhotoImageUploadListener uploadListener) {

        this.uploadListener = uploadListener;
        this.photoArray = photoArray;

        uploadPhoto();



    }

    private void uploadPhoto() {

        String photoPath = "comment/"+ UUID.randomUUID().toString()+".jpg";

        uploadReference = storageReference.child(photoPath);

        if (photoCount < photoArray.size()){
            UploadTask uploadTask = uploadReference.putBytes(photoArray.get(photoCount));
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    catchPhotoUrl();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.upload_fail)+" error : "+e.toString());
                }
            });
        }else {
            uploadListener.onSuccessful(photoUrlArray);
        }
    }

    private void catchPhotoUrl() {
        uploadReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String downloadUrl = uri.toString();
                photoUrlArray.add(downloadUrl);
                photoCount ++;
                uploadPhoto();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_photo_url));
            }
        });
    }


}
