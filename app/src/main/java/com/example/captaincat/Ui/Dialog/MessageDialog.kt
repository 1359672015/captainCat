package com.example.captaincat.Ui.Dialog

import android.app.Dialog
import android.content.Context
import com.example.captaincat.Ui.Dialog.MessageDialog
import android.view.LayoutInflater
import android.view.View
import com.example.captaincat.R
import com.example.captaincat.databinding.DialogMessageBinding

class MessageDialog(
    context: Context,
    var title: String?,
    var sContent: String?,
    var btnText :String = "确定",
    cancleAble:Boolean = false,
    var clicker:(MessageDialog)->Unit = {},

) : Dialog(context) {
    init {
        var b = DialogMessageBinding.inflate(layoutInflater)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_message, b.root)
        this.setContentView(inflate)
        if (title != null) {
            b.llAll.visibility = View.VISIBLE
            b.tvMessage.text = sContent
            b.tvTitle.text = title
            b.btnConfirm.text = btnText
            b.btnConfirm.setOnClickListener {
                clicker.invoke(this)
            }
        }
        this.setCancelable(cancleAble)
    }
}