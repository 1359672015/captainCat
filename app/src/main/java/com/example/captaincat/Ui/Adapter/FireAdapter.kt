package com.example.captaincat.Ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.MyGame
import com.example.captaincat.R
import com.example.captaincat.Utils.message.ToastUtil.Companion.showText
import com.example.captaincat.model.FireIcon
class FireAdapter(var context: Context, var list: List<FireIcon>) :
    RecyclerView.Adapter<FireAdapter.myViewHolder>() {
    var chosen = BooleanArray(6)
    var chosenNum = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_fireicon, parent, false)
        )
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.level.text = list[position].level.toString() + ""
        holder.name.text = list[position].name
        Glide.with(appContext).load(list[position].icon).into(holder.icon)
        holder.icon.setOnClickListener { v: View? ->
            if (chosen[position]) {
                chosen[position] = false
                chosenNum--
                holder.vChosen.visibility = View.GONE
            } else {
                if (chosenNum == MyGame.user.ownFireNum) {
                    showText(appContext, "您目前至多选择" + MyGame.user.ownFireNum + "个技能!")
                    return@setOnClickListener
                }
                chosen[position] = true
                chosenNum++
                holder.vChosen.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    val chosenFires: String
        get() {
            val stringBuilder = StringBuilder("")
            for (i in 0..5) {
                if (chosen[i]) {
                    val c = (i + 48).toChar()
                    stringBuilder.append(c)
                }
            }
            return stringBuilder.toString()
        }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var level: TextView
        var name: TextView
        var icon: ImageView
        var vChosen: View
        init {
            level = itemView.findViewById(R.id.tv_level)
            icon = itemView.findViewById(R.id.iv_icon)
            name = itemView.findViewById(R.id.tv_name)
            vChosen = itemView.findViewById(R.id.v_chosen)
        }
    }
}