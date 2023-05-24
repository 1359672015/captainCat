package com.example.captaincat.Ui.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.captaincat.R
import com.example.captaincat.Ui.Adapter.BaseAdapter.BaseViewHolder
import com.example.captaincat.Ui.Dialog.DialogItem
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd

open class BaseAdapter(var context: Context, var list: List<DialogItem>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_dialog, parent, false)
         )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val icon = list[position].icon
        val layout = list[position].iconLayout
        if (icon != null && icon.length > 2) {
            Glide.with(context).load(icon).into(holder.ivIcon);
        } else if (layout != 0) {
            holder.ivIcon.setImageBitmap(BitmapFactory.decodeResource(context.resources, layout))
        } else holder.ivIcon.visibility = View.GONE
        holder.tvText.text = list[position].text
        holder.itemView.setOnClickListener(list[position].listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvText: TextView
        var ivIcon: ImageView

        init {
            tvText = itemView.findViewById(R.id.tv_text)
            ivIcon = itemView.findViewById(R.id.iv_icon)
        }
    }

    init {
        Logd(list.toString())
    }
}