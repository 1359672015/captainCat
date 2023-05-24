package com.example.captaincat.Ui.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.captaincat.model.Level
import com.example.captaincat.R

class LevelsAdapter(var mcontext: Context, var list: List<Level>) :
    RecyclerView.Adapter<LevelsAdapter.myViewHolder>() {
    var chosenIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(
            LayoutInflater.from(mcontext).inflate(R.layout.item_levels, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: myViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.tvTitle.text = list[position].title
        Log.d("Lam-3_21", list[position].title!!)
        if (list[position].isChosen) holder.vChosen.visibility =
            View.VISIBLE else holder.vChosen.visibility = View.GONE
        holder.tvTitle.setOnClickListener { v: View? ->
            holder.vChosen.visibility = View.VISIBLE
            list[chosenIndex].isChosen = false
            notifyItemChanged(chosenIndex)
            chosenIndex = position
            list[chosenIndex].isChosen = true
            notifyItemChanged(chosenIndex)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var vChosen: View

        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            vChosen = itemView.findViewById(R.id.v_chosen)
        }
    }

    init {
        for (i in list.indices) {
            if (list[i].isChosen) chosenIndex = i
        }
        //chosenIndex = list.size()-1;
    }
}