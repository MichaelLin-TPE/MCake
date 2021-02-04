package com.cake.mcakeapp.auth;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AuthHandlerImpl implements AuthHandler {


    private FirebaseAuth auth;

    private OnCreateNewAccountListener accountListener;

    public AuthHandlerImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void registerAccount(String email, String password, final OnCreateNewAccountListener accountListener) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(MyApplication.getInstance().getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            accountListener.onSuccessful();
                        }else {
                            accountListener.onFail(task.getException().toString());
                        }
                    }
                });
    }

    @Override
    public boolean getCurrentUser() {

        return auth.getCurrentUser() != null;
    }

    @Override
    public String getCurrentUserEmail() {

        if (auth.getCurrentUser() != null){
            return auth.getCurrentUser().getEmail();
        }else {
            return "";
        }
    }

    @Override
    public void signInWithCredential(AuthCredential credential, final OnCompleteListener<AuthResult> authResultOnCompleteListener) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authResultOnCompleteListener.onComplete(task);
            }
        });
    }

    @Override
    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    @Override
    public void doAccountAndPasswordLogin(String account, String password, final OnUserLoginListener userLoginListener) {
        auth.signInWithEmailAndPassword(account,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userLoginListener.onSuccessful();
                        }else {
                            userLoginListener.onFail(task.getException().toString());
                        }
                    }
                });
    }


}
