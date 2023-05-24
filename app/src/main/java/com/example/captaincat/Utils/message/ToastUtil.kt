package com.example.captaincat.Utils.message

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.captaincat.MyApplication
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.Utils.TimeUtil
import com.google.android.material.snackbar.Snackbar

class ToastUtil {
    companion object{
        var ErrorMessage = "网络或服务器错误"

        var toast :Toast? = null
        //会按规定时间等待toast消息队列

        fun ToastMsg(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }


        fun ToastLong(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }


        fun ToastError(context: Context?) {
            Toast.makeText(context, ErrorMessage, Toast.LENGTH_SHORT).show()
        }

        /**
         * 显示Toast,会立刻覆盖原来的toast消息
         */

        fun showText(context: Context?, text: String?) {
            if (toast == null) {
                toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
            }
            toast!!.setText(text)
            toast!!.show()
        }


        fun Logd(message: String?) {
            Log.d("Lam" + TimeUtil.getTime("MM_dd"), message!!)
        }


        fun Logi(message: String?) {
            Log.i("Lam" + TimeUtil.getTime("MM_dd"), message!!)
        }


        fun toast(text: String?) {
            Toast.makeText(MyApplication.appContext, text, Toast.LENGTH_SHORT).show()
        }


        /**
         * 显示Toast,会立刻覆盖原来的toast消息
         */

        fun toastCover(text: String?) {
            if (toast == null) {
                toast = Toast(appContext)
                toast = Toast.makeText(appContext,"", Toast.LENGTH_SHORT)
            }
            toast.run {
                this!!.setText(text)
                this.show()
            }

        }
        fun showSnack(view: View?, message: String?) {
            Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        fun showDialog(message: String?, onConfirm: View.OnClickListener?) {}
        fun netWorkError() {}fun toastLong(text: String?) {
            Toast.makeText(MyApplication.appContext, text, Toast.LENGTH_LONG).show()
        }

    }

}