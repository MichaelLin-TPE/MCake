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
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cake.mcakeapp.R;
import com.cake.mcakeapp.tool.DpConvertTool;



public class SelectSexDialog extends DialogFragment {


    private Context context;

    public static SelectSexDialog newInstance() {

        Bundle args = new Bundle();

        SelectSexDialog fragment = new SelectSexDialog();
        fragment.setArguments(args);
        return fragment;
    }
    private OnVerifyAccountDialogClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public SelectSexDialog setOnDialogClickListener(OnVerifyAccountDialogClickListener listener){
        this.listener = listener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void initView(View view) {
        final RadioButton radMan = view.findViewById(R.id.select_radio_man);
        final RadioButton radGirl = view.findViewById(R.id.select_radio_girl);
        radMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    radGirl.setChecked(false);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onConfirmClick(context.getString(R.string.man));
                            dismiss();
                        }
                    },500);
                }
            }
        });

        radGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    radMan.setChecked(false);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onConfirmClick(context.getString(R.string.girl));
                            dismiss();
                        }
                    },500);

                }
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.sex_select_layout, null);
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



    public interface OnVerifyAccountDialogClickListener {
        void onConfirmClick(String sex);

    }

}
