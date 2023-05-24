package com.example.captaincat.Ui.Dialog

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import android.widget.EditText
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import com.example.captaincat.model.Gem
import com.example.captaincat.R
import com.example.captaincat.databinding.DialogBuyBinding
import com.example.lamstest.Common.hide

class WearDialog(
    context: Context,
    val gem: Gem,
    val isWeared: Boolean,
    val clickWear: () -> Unit
) : Dialog(context, true, null) {

    var b: DialogBuyBinding
    var tvPrice: TextView
    var etNum: EditText
    private fun initData() {
        b.tvName.text = gem.name
        b.tvPrice.hide()
        Glide.with(context).load(gem.picUrl).into(b.ivIcon)
    }

    private fun initListener() {
        b.etNum.hide()
        b.btnLess.hide()
        b.btnMore.hide()
        b.tvIntro.text = gem.introduce+"\n\n"+(if(gem.gaindesc.length>0)"[属性加成]:\n" else "") +gem.gaindesc
        b.btnConfirm.run{
            this.text = if(isWeared) "确定" else "装备"
            setOnClickListener { v: View? ->
                dismiss()
                clickWear.invoke()
            }
        }
    }

    companion object {
        private var dialog: WearDialog? = null
        fun showDialog(context: Context, gem: Gem ,isWeared :Boolean = false,onWear:()->Unit) {
            dialog = WearDialog(context, gem  ,isWeared, onWear)
            dialog!!.show()
        }
    }

    init {
        b = DialogBuyBinding.inflate(layoutInflater)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_buy, b.root)
        tvPrice = b.tvPrice
        etNum = b.etNum
        b.llAll.visibility = View.VISIBLE
        this.setContentView(inflate)
        initData()
        initListener()
    }
}