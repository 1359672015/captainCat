package com.example.captaincat.Ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.captaincat.model.Thing
import com.example.captaincat.R
import com.example.captaincat.Ui.Adapter.ObjAdapter.myHolder

open class ObjAdapter(var context: Context, var bagList: List<Thing>,val isFood:Boolean = false ,var click:(Thing)->Unit={_->}) :
    RecyclerView.Adapter<myHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        return if(isFood)
            myHolder(LayoutInflater.from(context).inflate(R.layout.item_food, parent, false))
        else
            myHolder(LayoutInflater.from(context).inflate(R.layout.item_objicon, parent, false))
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.name.text = bagList[position].name + ""
        holder.num.text =   "拥有："+bagList[position].num.toString()
          Glide.with(context).load(bagList[position].picUrl).into(holder.icon);
        holder.itemView.setOnClickListener {
            click.invoke(bagList[position])
        }
    }

    override fun getItemCount(): Int {
        return bagList.size
    }

    inner class myHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @JvmField
        var name: TextView
        @JvmField
        var num: TextView
        var icon: ImageView

        init {
            name = itemView.findViewById(R.id.tv_name)
            num = itemView.findViewById(R.id.tv_number)
            icon = itemView.findViewById(R.id.iv_icon)
        }
    }
}