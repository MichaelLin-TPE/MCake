package com.cake.mcakeapp.google_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.auth.AuthHandler;
import com.cake.mcakeapp.auth.AuthHandlerImpl;
import com.cake.mcakeapp.data.UserData;
import com.cake.mcakeapp.firestore.FireStoreHandler;
import com.cake.mcakeapp.firestore.FireStoreHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

public class GoogleLoginHandlerImpl implements GoogleLoginHandler {

    private GoogleSignInOptions options;

    private GoogleSignInClient googleSignInClient;

    public static final int RC_SIGN_IN = 666;

    private AuthHandler authHandler;

    private FireStoreHandler fireStoreHandler;

    public GoogleLoginHandlerImpl() {
        fireStoreHandler = new FireStoreHandlerImpl();
        authHandler = new AuthHandlerImpl();
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(MyApplication.getInstance().getApplicationContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(MyApplication.getInstance().getApplicationContext(),options);

    }


    @Override
    public void doLogin(Context context) {
        MichaelLog.i("開始登入流程");
        if (context instanceof Activity) {
            Intent it = googleSignInClient.getSignInIntent();
            ((Activity) context).startActivityForResult(it, RC_SIGN_IN);
        } else {
            MichaelLog.i("context != null Activity");
        }
    }


    @Override
    public void onCatchLoginResult(Task<GoogleSignInAccount> task, OnGoogleLoginListener loginListener) {
        try {
            MichaelLog.i("登入成功");

            GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account == null){
                loginListener.onFail("Google account doesn't exist.");
                MichaelLog.i("Google帳號不存在");
                return;
            }
            registerFirebaseAuth(account,loginListener);

        }catch (Exception e){
            e.printStackTrace();
            loginListener.onFail("Google Login Fail.");
            MichaelLog.i("登入錯誤："+e.toString());
        }
    }

    private void registerFirebaseAuth(GoogleSignInAccount account, final OnGoogleLoginListener loginListener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        authHandler.signInWithCredential(credential, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = authHandler.getUser();
                    if (currentUser == null){
                        loginListener.onFail("User is empty.");
                        return;
                    }

                    checkUserDataExist(loginListener);

                }else {
                    task.getException().printStackTrace();
                    loginListener.onFail(task.getException().toString());
                    MichaelLog.i("google Login Firebase建置失敗");
                }
            }
        });
    }

    private void checkUserDataExist(final OnGoogleLoginListener loginListener) {
        fireStoreHandler.getUserList(new FireStoreHandler.OnCatchFireStoreResultListener<ArrayList<UserData>>() {
            @Override
            public void onSuccessful(ArrayList<UserData> userList) {

                boolean isFoundUserData = false;

                for (UserData data : userList){
                    if (data.getEmail().equals(authHandler.getUser().getEmail())){
                        isFoundUserData = true;
                        break;
                    }
                }
                if (isFoundUserData){
                    loginListener.onSuccessful();
                    return;
                }
                loginListener.onCreateUserData();

            }

            @Override
            public void onFail(String message) {
                loginListener.onFail(message);
            }
        });
    }
}
