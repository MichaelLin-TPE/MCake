package com.cake.mcakeapp.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.tool.JsonHelper;
import com.cake.mcakeapp.tool.MichaelLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireStoreHandlerImpl implements FireStoreHandler {

    private FirebaseFirestore firebaseFirestore;

    private ArrayList<UserData> userDataArrayList;

    public static final String USER = "users";

    public static final String USER_LIST = "user_list";

    public static final String COMMENT = "comment";

    public static final String COMMENT_LIST = "comment_list";

    public FireStoreHandlerImpl() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void createNewUserAccount(String name, String phone, String email, String address, String sex, String uuid, final OnCatchFireStoreResultListener<String> createUserDataListener) {
        UserData data = new UserData();
        data.setName(name);
        data.setPhone(phone);
        data.setEmail(email);
        data.setAddress(address);
        data.setSex(sex);
        data.setUuid(uuid);
        data.setPhotoUrl("");
        userDataArrayList.add(data);
        String userJson = JsonHelper.getGson().toJson(userDataArrayList);
        Map<String,String> map = new HashMap<>();
        map.put("userJson",userJson);
        firebaseFirestore.collection(USER)
                .document(USER_LIST)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            createUserDataListener.onSuccessful(MyApplication.getInstance().getApplicationContext().getString(R.string.create_data_successful));

                        }else {
                            createUserDataListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_create_data));
                        }
                    }
                });
    }

    @Override
    public void getUserList(final OnCatchFireStoreResultListener<ArrayList<UserData>> getUserListListener) {
        DocumentReference documentReference = firebaseFirestore.collection(USER).document(USER_LIST);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    getUserListListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_user_list));
                    return;
                }

                if (value != null && value.exists()){
                    String json = (String)value.get("userJson");

                    if (json == null){
                        getUserListListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_user_list));
                        return;
                    }

                    ArrayList<UserData> userDataList = JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<UserData>>(){}.getType());

                    if (userDataList == null){
                        getUserListListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_user_list));
                        return;
                    }
                    getUserListListener.onSuccessful(userDataList);
                    MichaelLog.i("取得資料了");
                }

            }
        });
    }

    @Override
    public void setAllUserList(ArrayList<UserData> userDataList) {
        this.userDataArrayList = userDataList;
    }

    @Override
    public void getCommentData(OnCatchFireStoreResultListener<ArrayList<CommentData>> onCatchCommentDataListener) {
        DocumentReference documentReference = firebaseFirestore.collection(COMMENT).document(COMMENT_LIST);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    onCatchCommentDataListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_comment_list));
                    return;
                }

                if (value != null && value.exists()){
                    String json = (String)value.get("json");

                    if (json == null){
                        onCatchCommentDataListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_comment_list));
                        return;
                    }

                    ArrayList<CommentData> commentList = JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<CommentData>>(){}.getType());

                    if (commentList == null){
                        onCatchCommentDataListener.onSuccessful(null);
                        return;
                    }
                    onCatchCommentDataListener.onSuccessful(commentList);
                    MichaelLog.i("取得資料了");
                }
            }
        });
    }

    @Override
    public void saveCommentData(ArrayList<CommentData> allCommentList, OnCatchFireStoreResultListener<String> onSaveCommentListener) {
        String json = JsonHelper.getGson().toJson(allCommentList);
        Map<String,String> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(COMMENT).document(COMMENT_LIST)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            onSaveCommentListener.onSuccessful(MyApplication.getInstance().getApplicationContext().getString(R.string.create_data_successful));
                        }else {
                            onSaveCommentListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_create_data));
                        }
                    }
                });
    }
}
