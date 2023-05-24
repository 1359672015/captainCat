package com.example.captaincat.Ui.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.captaincat.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public abstract class BaseDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int style= R.style.dialogStyle;
        if (getStyle()!=0){
            style=getStyle();
        }
        AvoidLeakDialog dialog = new AvoidLeakDialog(requireActivity(), style);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(getLayoutView());
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.getAttributes().windowAnimations=R.style.DialogAnimationFadeInOut;
        lp.gravity = getGravity(); // 中间显示
        lp.width=settingWidth();
        lp.height=settingHeight();
        window.setAttributes(lp);
        initView(dialog);

        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    protected int getStyle(){
        return 0;
    }

    protected abstract void  initView(Dialog dialog);
    protected abstract View getLayoutView();
    protected abstract int getGravity();

    protected int settingHeight(){
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
    protected int settingWidth(){
        return QMUIDisplayHelper.getScreenWidth(requireActivity());
    }


}
