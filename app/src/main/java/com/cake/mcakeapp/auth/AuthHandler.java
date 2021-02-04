package com.cake.mcakeapp.auth;

import com.cake.mcakeapp.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public interface AuthHandler {
    void registerAccount(String email, String password , OnCreateNewAccountListener createNewAccountListener);

    boolean getCurrentUser();

    String getCurrentUserEmail();

    void signInWithCredential(AuthCredential credential, OnCompleteListener<AuthResult> authResultOnCompleteListener);

    FirebaseUser getUser();

    void doAccountAndPasswordLogin(String account, String password, OnUserLoginListener userLoginListener);


    interface OnCreateNewAccountListener{
        void onSuccessful();
        void onFail(String message);
    }
    
    interface OnUserLoginListener{
        void onSuccessful();
        void onFail(String message);
    }
    
}
