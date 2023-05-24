package com.example.captaincat.Ui.Adapter

import android.content.Context
import com.example.captaincat.Ui.Dialog.DialogItem
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.captaincat.R

class OrcAdapter(context: Context?, list: List<DialogItem>?) : BaseAdapter(
    context!!, list!!
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_orc, parent, false)
        )
    }
}