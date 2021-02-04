package com.cake.mcakeapp.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.google_login.GoogleLoginHandler;
import com.cake.mcakeapp.google_login.GoogleLoginHandlerImpl;
import com.cake.mcakeapp.tool.MichaelLog;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.product.ProductFragment;
import com.cake.mcakeapp.view.register.RegisterFragment;
import com.cake.mcakeapp.widget.ProgressDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import static com.cake.mcakeapp.google_login.GoogleLoginHandlerImpl.RC_SIGN_IN;
import static com.cake.mcakeapp.view.register.RegisterFragment.COMPLETE_USER_DATA;
import static com.cake.mcakeapp.view.register.RegisterFragment.REGISTER;


public class LoginFragment extends Fragment implements LoginFragmentVu{



    private LoginFragmentPresenter presenter;


    private SignInButton googleLoginButton;

    private Context context;

    private ProgressDialog progressDialog;

    private GoogleLoginHandler googleLoginHandler;

    private FragmentActivity fragmentActivity;

    private RegisterFragment.OnUserIsLoginListener userIsLoginListener;

    public LoginFragment setOnUserIsLoginListener(RegisterFragment.OnUserIsLoginListener isLoginListener){
        this.userIsLoginListener = isLoginListener;
        return this;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleLoginHandler = new GoogleLoginHandlerImpl();
        initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onLoadAllUserData();
    }

    private void initPresenter() {
        presenter = new LoginFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        googleLoginButton = view.findViewById(R.id.login_google);
        //設置深色模式
        googleLoginButton.setColorScheme(SignInButton.COLOR_DARK);
        //設定Google登入按鈕的字串
        Tools.setGooglePlusButtonText(googleLoginButton,context.getString(R.string.user_gmail_login));

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onGoogleLoginClickListener();
            }
        });

        final EditText edAccount = view.findViewById(R.id.login_edit_account);
        final EditText edPassword = view.findViewById(R.id.login_edit_password);


        Button btnLogin = view.findViewById(R.id.login_btn);
        Button btnRegister = view.findViewById(R.id.login_btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = edAccount.getText().toString();
                String password = edPassword.getText().toString();

                presenter.onLoginButtonClickListener(account,password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonClickListener();
            }
        });


    }


    @Override
    public void startToGoogleLogin() {
        googleLoginHandler.doLogin(getActivity());
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.newInstance(context.getString(R.string.logining));
        progressDialog.show(fragmentActivity.getSupportFragmentManager(),"dialog");
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void goToProductListPage(String name) {
        //傳給Activity有使用者登入
        userIsLoginListener.onLogin(name);
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(),ProductFragment.newInstance(),false,ProductFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public void goToCompleteUserDataPage() {
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(), RegisterFragment.newInstance(COMPLETE_USER_DATA),false,RegisterFragment.newInstance(COMPLETE_USER_DATA).getClass().getSimpleName());
    }

    @Override
    public String getLoginSuccessful() {
        return context.getString(R.string.login_success) ;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToRegisterPage() {
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(),RegisterFragment.newInstance(REGISTER),false,RegisterFragment.newInstance(REGISTER).getClass().getSimpleName());
    }

    @Override
    public String getAccountError() {
        return context.getString(R.string.account_error);
    }

    @Override
    public String getPasswordError() {
        return context.getString(R.string.password_error);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MichaelLog.i("Google Login onActivityResult");
        if (requestCode == RC_SIGN_IN && data != null){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleLoginHandler.onCatchLoginResult(task,loginListener);
        }
    }

    private GoogleLoginHandler.OnGoogleLoginListener loginListener = new GoogleLoginHandler.OnGoogleLoginListener() {
        @Override
        public void onSuccessful() {
            presenter.onGoogleLoginSuccessful();
        }

        @Override
        public void onFail(String message) {
            Toast.makeText(fragmentActivity,message,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreateUserData() {
            presenter.onCreateUserData();
        }
    };
}