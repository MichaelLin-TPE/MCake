package com.cake.mcakeapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cake.mcakeapp.R;


public class ProgressDialog extends DialogFragment {


    private Context context;

    public static ProgressDialog newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        ProgressDialog fragment = new ProgressDialog();
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
        TextView tvTitle = view.findViewById(R.id.progress_title);
        tvTitle.setText(getArguments().getString("title",""));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.progress_dialog_layout, null);
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

}
