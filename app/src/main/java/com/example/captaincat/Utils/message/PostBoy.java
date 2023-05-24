package com.example.captaincat.Utils.message;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class PostBoy {
    Context context;
    static public void ToastOut(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    static public void showSnack(View view,String message){
         Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
    }
    static public void logE(String tag,String content){
        Log.e(tag,content);
    }
    static public void logE(String content){
        Log.e("Cats Vs Alien",content);
    }
    static public void logD(String tag,String content){
        Log.d(tag,content);
    }
    static public void logD(String content){
        Log.d("LamLog",content);
    }
    static public void logI(String tag,String content){
        Log.i(tag,content);
    }
    static public void logI(String content){
        Log.i("Cats Vs Alien",content);
    }

}
