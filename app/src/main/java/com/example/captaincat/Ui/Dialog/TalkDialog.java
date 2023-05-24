package com.example.captaincat.Ui.Dialog;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.captaincat.model.Talk;
import com.example.captaincat.R;

import java.util.List;

public class TalkDialog extends DialogFragment {
    Context context;
    List<Talk> list;
    TextView content;
    ImageView head;
    Button next;
    TextView pass;
    int index = 0;
    public TalkDialog(Context context, List<Talk> list){
        this.context = context;
        this.list = list;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_talk, container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        content = view.findViewById(R.id.tv_content);
        next = view.findViewById(R.id.btn_next);
        pass = view.findViewById(R.id.tv_pass);
        initContent();
        next.setOnClickListener(v -> {
            if (index<list.size()-1)
            {
                index++;
            initContent();
            }
        });
        pass.setOnClickListener(v ->
        {
            dismiss();
        });

    }
    public void initContent(){
        content.setText(list.get(index).getContent());
        Bitmap bitmap =
                BitmapFactory.decodeResource(getContext().
                        getResources(),list.get(index).getTalkerID());
        head.setImageBitmap(bitmap);
    }

    public void show(FragmentManager fragmentManager, String s) {
        this.show(fragmentManager,s);
    }
}
