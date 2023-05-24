package com.example.captaincat.Ui.Dialog;

import static com.example.captaincat.information.Config.Config.REWARD;
import static com.example.captaincat.information.Config.Config.TAKES;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.captaincat.R;
import com.example.captaincat.Ui.Adapter.BaseAdapter;
import com.example.captaincat.databinding.DialogBaseBinding;

import java.util.List;

public class MenuDialog extends Dialog {
    DialogBaseBinding b;
    List<DialogItem> list;
    String title;
    Context context;
    int type;
    View.OnClickListener confirmListener;
    private static MenuDialog instance;
    private MenuDialog(Context context, List<DialogItem> list, String title, int type, View.OnClickListener listener){
        super(context);
        b = DialogBaseBinding.inflate(getLayoutInflater());
        View inflate = LayoutInflater.from(context).
                inflate(R.layout.dialog_base,b.getRoot());
        this.setContentView(inflate);
        this.context = context;
        this.list = list;
        this.title = title;
        this.confirmListener = listener;
        this.type = type;

            initView();

    }

    private void initView() {
        if(type==REWARD||type == TAKES){
            if (REWARD==type)
            this.setCancelable(false);
            b.tvTitle.setVisibility(View.VISIBLE);
            b.btnYes.setVisibility(View.VISIBLE);
            b.tvTitle.setText(title);
            b.btnYes.setOnClickListener(confirmListener);
            b.llAll.setBackground(context.getDrawable(R.drawable.gradient_5));
        }
        if (list!=null){
            b.rvBtn.setAdapter(new BaseAdapter(context,list));
            b.rvBtn.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    static public void showDialog(Context context, List<DialogItem> list, String title, int type, View.OnClickListener listener){
        instance = new MenuDialog(context,list,title,type,listener);
        instance.show();
    }

    public static MenuDialog getInstance() {
        return instance;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        instance = null;
    }
}
