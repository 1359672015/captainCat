package com.example.captaincat.Ui.Dialog;

import android.view.View;

public class DialogItem {
    String text;
   View.OnClickListener listener;
   String iconUrl = "";
   int iconLayout = 0;
    public DialogItem(String text,String iconUrl, View.OnClickListener listener){
        this.text = text;
        this.listener = listener;
        this.iconUrl = iconUrl;
    }
    public DialogItem(String text,int iconUrl, View.OnClickListener listener){
        this.text = text;
        this.listener = listener;
        this.iconLayout = iconUrl;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return iconUrl;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public int getIconLayout() {
        return iconLayout;
    }
}