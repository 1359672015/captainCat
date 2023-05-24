package com.example.captaincat.Utils.message

import android.widget.Toast
import com.example.captaincat.MyApplication.Companion.appContext
object ToastUtils {
    fun toastLong(text: String?) {
        Toast.makeText(appContext, text, Toast.LENGTH_LONG).show()
    }

    fun toast(text: String?) = Toast.makeText(appContext, text, Toast.LENGTH_SHORT).show()

    private var toast :Toast? = null

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
}