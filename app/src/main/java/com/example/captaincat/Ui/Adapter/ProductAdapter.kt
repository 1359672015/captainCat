package com.example.captaincat.Ui.Adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd
import com.example.captaincat.model.Thing
import com.example.captaincat.Ui.Dialog.BuyDialog

class ProductAdapter(context: Context?, bagList: List<Thing>?,val bought:()->Unit={}) : ObjAdapter(
    context!!, bagList!!
) {
    override fun onBindViewHolder(holder: ObjAdapter.myHolder, position: Int) {
        holder.name.text = bagList[position].name + ""
        holder.num.text = "价格：" + bagList[position].price + ""
        Logd(bagList[position].name)
          Glide.with(context).load(bagList[position].picUrl).into(holder.icon);
        holder.itemView.setOnClickListener {
            BuyDialog.showDialog(
                context,
                bagList[position],bought
            )
        }
    }
}