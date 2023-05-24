package com.example.captaincat.Ui.Dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.example.captaincat.R
import com.example.captaincat.databinding.DialogOrcBinding
import com.example.captaincat.flyingObjects.Orcs.Orc

@SuppressLint("SetTextI18n")
class OrcDialog(context: Context, orc: Orc) : Dialog(context) {
    var b: DialogOrcBinding

    companion object {
        var dialog: OrcDialog? = null
        @JvmStatic
        fun showOrcInfo(context: Context, orc: Orc) {
            dialog = OrcDialog(context, orc)
            dialog!!.show()
        }
    }

    init {
        b = DialogOrcBinding.inflate(layoutInflater)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_orc, b.root)
        setContentView(inflate)
        b.llAll.visibility = View.VISIBLE
        b.tvName.text = "${orc.name}\n等级：${orc.id * 3}\n最高速度：${orc.speed[1]}".trimIndent()
        b.tvIntro.text = "【 介 绍 】：" + orc.introduce
        b.tvFavour.text = "【战斗特点】：" + orc.favour
        Glide.with(context).load(orc.look).into(b.ivOrc)
    }
}