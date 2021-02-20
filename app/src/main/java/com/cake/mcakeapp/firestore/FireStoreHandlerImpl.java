package com.cake.mcakeapp.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.CommentData;
import com.cake.mcakeapp.data.ProductData;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.tool.JsonHelper;
import com.cake.mcakeapp.tool.MichaelLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

    public static final String PRODUCT = "product";

    public static final String PRODUCT_LIST = "product_list";

    public static final String FAVORITE_DATA = "favorite";

    public static final String CART = "cart";

    private AuthHandler authHandler;

    private ArrayList<ProductData> allFavoriteList , cartList;

    public FireStoreHandlerImpl() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        authHandler = new AuthHandlerImpl();
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

    @Override
    public void setProductList(String json) {

        Map<String,String> map = new HashMap<>();
        map.put("json",json);

        firebaseFirestore.collection(PRODUCT).document(PRODUCT_LIST)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            MichaelLog.i("上傳商品資訊成功");
                        }
                    }
                });
    }

    @Override
    public void getProductList(OnCatchFireStoreResultListener<ArrayList<ProductData>> getProductListListener) {
        DocumentReference documentReference = firebaseFirestore.collection(PRODUCT).document(PRODUCT_LIST);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    getProductListListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_product_list));
                    return;
                }

                if (value != null && value.exists()){
                    String json = (String)value.get("json");

                    if (json == null){
                        getProductListListener.onFail(MyApplication.getInstance().getApplicationContext().getString(R.string.fail_to_get_product_list));
                        return;
                    }

                    ArrayList<ProductData> commentList = JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<ProductData>>(){}.getType());

                    if (commentList == null){
                        getProductListListener.onSuccessful(null);
                        return;
                    }
                    getProductListListener.onSuccessful(commentList);
                    MichaelLog.i("取得資料了");
                }
            }
        });
    }

    @Override
    public void addFavoriteProduct(ProductData data) {

        if (!data.isCheckHeart()){

            for (ProductData fav : allFavoriteList){
                if (fav.getImageUrlArray().get(0).equals(data.getImageUrlArray().get(0))){
                    MichaelLog.i("刪除我的最愛");
                    allFavoriteList.remove(fav);
                    break;
                }
            }
        }else {
            allFavoriteList.add(data);
        }
        String json = JsonHelper.getGson().toJson(allFavoriteList);
        Map<String,String> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(FAVORITE_DATA).document(authHandler.getCurrentUserEmail())
                .set(map);
    }

    @Override
    public void catchOriginalFavoriteData(OnCatchFireStoreResultListener<ArrayList<ProductData>> getFavoriteListListener) {
        allFavoriteList = new ArrayList<>();

        if (!authHandler.getCurrentUser()){
            getFavoriteListListener.onSuccessful(allFavoriteList);
            return;
        }


        DocumentReference documentReference = firebaseFirestore.collection(FAVORITE_DATA).document(authHandler.getCurrentUserEmail());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    getFavoriteListListener.onSuccessful(allFavoriteList);
                    return;
                }

                if (value != null && value.exists()){
                    String json = (String)value.get("json");

                    if (json == null){
                        getFavoriteListListener.onSuccessful(allFavoriteList);
                        return;
                    }

                    allFavoriteList = JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<ProductData>>(){}.getType());

                    if (allFavoriteList == null || allFavoriteList.isEmpty()){
                        getFavoriteListListener.onSuccessful(allFavoriteList);
                        return;
                    }
                    MichaelLog.i("取得我的最愛資料");
                    getFavoriteListListener.onSuccessful(allFavoriteList);
                } else {
                    getFavoriteListListener.onSuccessful(allFavoriteList);
                }
            }
        });
    }

    @Override
    public ArrayList<ProductData> getFavoriteList() {
        return allFavoriteList;
    }

    @Override
    public void addCartProduct(ProductData data) {

        if (!data.isCheckCart()){
            for (ProductData fav : cartList){
                if (fav.getImageUrlArray().get(0).equals(data.getImageUrlArray().get(0))){
                    MichaelLog.i("刪除我的購物車");
                    cartList.remove(fav);
                    break;
                }
            }
        }else {
            cartList.add(data);
        }

        String json = JsonHelper.getGson().toJson(cartList);
        Map<String,String> map = new HashMap<>();
        map.put("json",json);

        firebaseFirestore.collection(CART).document(authHandler.getCurrentUserEmail()).set(map);
    }

    @Override
    public void catchOriginalCartData(OnCatchFireStoreResultListener<ArrayList<ProductData>> getCartListListener) {
        cartList = new ArrayList<>();

        if (!authHandler.getCurrentUser()){
            getCartListListener.onSuccessful(cartList);
            return;
        }

        DocumentReference documentReference = firebaseFirestore.collection(CART).document(authHandler.getCurrentUserEmail());

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    getCartListListener.onSuccessful(cartList);
                    return;
                }

                if (value != null && value.exists()){
                    String json = (String)value.get("json");

                    if (json == null){
                        getCartListListener.onSuccessful(cartList);
                        return;
                    }

                    cartList = JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<ProductData>>(){}.getType());

                    if (cartList == null || cartList.isEmpty()){
                        getCartListListener.onSuccessful(cartList);
                        return;
                    }
                    getCartListListener.onSuccessful(cartList);
                    MichaelLog.i("取得我的購物車資料");
                }else {
                    getCartListListener.onSuccessful(cartList);
                }
            }
        });
    }

    @Override
    public ArrayList<ProductData> getCartList() {
        return cartList;
    }
}
