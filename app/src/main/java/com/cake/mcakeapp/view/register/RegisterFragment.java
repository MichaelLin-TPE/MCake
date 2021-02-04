package com.cake.mcakeapp.view.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.Tools;
import com.cake.mcakeapp.view.product.ProductFragment;
import com.cake.mcakeapp.widget.ProgressDialog;
import com.cake.mcakeapp.widget.SelectSexDialog;


public class RegisterFragment extends Fragment implements RegisterFragmentVu{


    private RegisterFragmentPresenter presenter;


    private EditText edName,edPhone,edEmail,edAddress,edPassword,edPasswordConfirm;

    private TextView tvSex,tvEmail,tvPassword,tvPasswordConfirm,tvTitle;

    private Button btnCreate,btnSelectSex;

    private FragmentActivity fragmentActivity;

    public static final int REGISTER = 0;

    public static final int COMPLETE_USER_DATA = 1;

    private ProgressDialog progressDialog;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    public static RegisterFragment newInstance(int type) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    private static OnUserIsLoginListener loginListener;

    public RegisterFragment setOnUserIsLoginListener(OnUserIsLoginListener listener){
        loginListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new RegisterFragmentPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.register_title);
        tvEmail = view.findViewById(R.id.register_email);
        tvPassword = view.findViewById(R.id.register_password);
        tvPasswordConfirm = view.findViewById(R.id.register_password_confirm);
        edName = view.findViewById(R.id.register_edit_name);
        edPhone = view.findViewById(R.id.register_edit_phone);
        edEmail = view.findViewById(R.id.register_edit_email);
        edAddress = view.findViewById(R.id.register_edit_address);
        edPassword = view.findViewById(R.id.register_edit_password);
        edPasswordConfirm = view.findViewById(R.id.register_edit_password_confirm);
        tvSex = view.findViewById(R.id.register_sex_result);
        btnCreate = view.findViewById(R.id.register_register);
        btnSelectSex = view.findViewById(R.id.register_btn_select_sex);

        btnSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSelectSexButtonClickListener();
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edName.getText().toString();
                String phone = edPhone.getText().toString();
                String email = edEmail.getText().toString();
                String address = edAddress.getText().toString();
                String password = edPassword.getText().toString();
                String passwordConfirm = edPasswordConfirm.getText().toString();
                String sex = tvSex.getText().toString();

                presenter.onCreateAccountButtonClickListener(name,phone,email,address,password,passwordConfirm,sex);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() == null){
            return;
        }
        int type = getArguments().getInt("type",0);

        presenter.onCatchMode(type);
        presenter.onLoadUserList();

    }

    @Override
    public void showSexSelectDialog() {
        SelectSexDialog.newInstance().setOnDialogClickListener(new SelectSexDialog.OnVerifyAccountDialogClickListener() {
            @Override
            public void onConfirmClick(String sex) {
                presenter.onSelectSexConfirmClickListener(sex);
            }
        }).show(getFragmentManager(),"dialog");
    }

    @Override
    public void setSexResult(String sex) {
        tvSex.setText(sex);
    }

    @Override
    public String getNameError() {
        return context.getString(R.string.name_error);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public String getPhoneError() {
        return context.getString(R.string.phone_error);
    }

    @Override
    public String getEmailError() {
        return context.getString(R.string.email_error);
    }

    @Override
    public String getAddressError() {
        return context.getString(R.string.address_error);
    }

    @Override
    public String getPasswordError() {
        return context.getString(R.string.password_error);
    }

    @Override
    public String getPasswordAreNotTheSame() {
        return context.getString(R.string.password_not_the_same);
    }

    @Override
    public String getCreating() {
        return context.getString(R.string.creating);
    }

    @Override
    public void showProgressDialog(String title) {

        progressDialog = ProgressDialog.newInstance(title);
        progressDialog.show(getFragmentManager(),"dialog");

    }

    @Override
    public void dismissProgressDialog() {

        if (progressDialog == null){
            return;
        }

        progressDialog.dismiss();
    }

    @Override
    public void setUserEmail(String name) {
        //這邊會傳給Activity
        loginListener.onLogin(name);
    }

    @Override
    public String getLoginError() {
        return context.getString(R.string.login_error);
    }

    @Override
    public void goToProductListPage() {
        Tools.replace(R.id.home_frame_layout,fragmentActivity.getSupportFragmentManager(), ProductFragment.newInstance(),false,ProductFragment.newInstance().getClass().getSimpleName());
    }

    @Override
    public String getIsSameEmail() {
        return context.getString(R.string.already_has_same_email);
    }

    @Override
    public void showAccountAndPassword(boolean isShow) {
        edEmail.setVisibility(isShow ? View.VISIBLE : View.GONE);
        edPasswordConfirm.setVisibility(isShow ? View.VISIBLE : View.GONE);
        edPassword.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvEmail.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvPasswordConfirm.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvPassword.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public String getRegisterAccount() {
        return context.getString(R.string.title_register);
    }

    @Override
    public void setCreateButtonText(String content) {
        btnCreate.setText(content);
    }

    @Override
    public String getCompleteUserData() {
        return context.getString(R.string.complete_user_data);
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public String getCompleteRegister() {
        return context.getString(R.string.complete_register);
    }


    public interface OnUserIsLoginListener{
        void onLogin(String name);
    }

}