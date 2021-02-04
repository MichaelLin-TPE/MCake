package com.cake.mcakeapp.google_login;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface GoogleLoginHandler {
    void doLogin(Context context);

    void onCatchLoginResult(Task<GoogleSignInAccount> task, OnGoogleLoginListener loginListener);

    interface OnGoogleLoginListener{
        void onSuccessful();

        void onFail(String message);

        void onCreateUserData();
    }
}
