package com.cake.mcakeapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cake.mcakeapp.R;


public class SignInAndRegisterDialog extends DialogFragment {


    private Context context;

    private OnSignInRegisterButtonClickListener signInRegisterButtonClickListener;

    public SignInAndRegisterDialog setOnSignInRegisterButtonClickListener(OnSignInRegisterButtonClickListener signInRegisterButtonClickListener){
        this.signInRegisterButtonClickListener = signInRegisterButtonClickListener;
        return this;
    }


    public static SignInAndRegisterDialog newInstance() {

        Bundle args = new Bundle();
        SignInAndRegisterDialog fragment = new SignInAndRegisterDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void initView(View view) {
        TextView tvSignIn = view.findViewById(R.id.sign_in_dialog_sign_in);
        TextView tvRegister = view.findViewById(R.id.sign_in_dialog_register);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInRegisterButtonClickListener.onLoginClick();
                dismiss();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInRegisterButtonClickListener.onRegisterClick();
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.sign_in_register_dialog_layout, null);
        Dialog dialog = new Dialog(getActivity());
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        initView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.width = DpConvertTool.getInstance().getDb(300);
//        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(wlp);
        return dialog;
    }

    public interface OnSignInRegisterButtonClickListener{
        void onRegisterClick();
        void onLoginClick();
    }

}
