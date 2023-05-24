package com.example.captaincat.Utils.netwoek;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class NetWorkUtil {

   static public boolean  checkNetWork(Activity activity){
        ConnectivityManager manager;
        manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo()!=null)
            return manager.getActiveNetworkInfo().isAvailable();
        else return false;
    }
}
