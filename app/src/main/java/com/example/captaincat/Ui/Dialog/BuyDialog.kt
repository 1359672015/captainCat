package com.example.captaincat.Ui.Dialog

import android.app.Dialog
import android.content.Context
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastMsg
import com.example.captaincat.model.Thing
import android.widget.TextView
import android.widget.EditText
import com.bumptech.glide.Glide
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import com.example.captaincat.MyGame
import android.view.LayoutInflater
import android.view.View
import com.example.captaincat.api.UserBagModel.Companion.userBuyThing
import com.example.captaincat.R
import com.example.captaincat.databinding.DialogBuyBinding

class BuyDialog private constructor(context: Context, thing: Thing,val bought: () -> Unit) : Dialog(context, true, null) {
    var thing: Thing
    var b: DialogBuyBinding
    var tvPrice: TextView
    var etNum: EditText
    var isEdit = false
    private fun initData() {
        b.tvName.text = thing.name
        b.tvPrice.text = "总价:" + thing.price + "金币"
        Glide.with(context).load(thing.picUrl).into(b.ivIcon)
    }

    private fun initListener() {
        b.etNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                Log.d("输入的内容为", s.toString().trim { it <= ' ' })
                if (s == null || s.length < 1) thing.num = 1 else thing.num =
                    Integer.valueOf(s.toString())
                //etNum.setText(thing.getNum()+"");
                tvPrice.text = "总价:" + thing.num * thing.price + "金币"
            }
        })
        b.btnLess.setOnClickListener { v: View? ->
            if (thing.num > 1) {
                thing.num = thing.num - 1
                etNum.setText(thing.num.toString() + "")
                tvPrice.text = "总价:" + thing.num * thing.price + "金币"
            }
        }
        b.btnMore.setOnClickListener { v: View? ->
            thing.num = thing.num + 1
            etNum.setText(thing.num.toString() + "")
            tvPrice.text = "总价:" + thing.num * thing.price + "金币"
        }
        b.tvIntro.text = thing.desc
        b.btnConfirm.setOnClickListener { v: View? ->
            if (MyGame.user.coins < thing.price * thing.num) ToastMsg(
                context,
                "金币不足！"
            ) else {
                 userBuyThing(thing, context)

                dismiss()
            }
        }
    }

    companion object {
        private var dialog: BuyDialog? = null
        fun showDialog(context: Context, thing: Thing, bought: () -> Unit) {
            dialog = BuyDialog(context, thing, bought)
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
        this.thing = thing
        thing.num = 1
        initData()
        initListener()
    }
}