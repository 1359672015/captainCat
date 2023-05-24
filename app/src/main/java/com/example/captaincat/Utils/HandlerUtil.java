package com.example.captaincat.Utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class HandlerUtil{
/** 2-10创建
 * 子线程内更新Ui的工具*/
    InterfaceWithTheMethod anInterfaceWithTheMethod;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            int whatEverTheMessageIs = message.what;

                anInterfaceWithTheMethod.theMethod();
        }

    };
    public HandlerUtil(InterfaceWithTheMethod interfaceWithTheMethod){
            this.anInterfaceWithTheMethod = interfaceWithTheMethod;

    }
    public void runMethod(){
        Message message  = new Message();
        message.what = 1;
            handler.sendMessage(message);
    }
}
