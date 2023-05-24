package com.example.captaincat.Ui.Activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.captaincat.MyGame
import com.example.captaincat.Ui.Adapter.OrcAdapter
import com.example.captaincat.Ui.Dialog.DialogItem
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.databinding.ActivityBookBinding

class BookActivity : AppCompatActivity() {
    var b: ActivityBookBinding? = null
    var context: Context = this@BookActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityBookBinding.inflate(layoutInflater)
        setContentView(b!!.root)
        var list: List<DialogItem?>? = ArrayList()
        list = ListTurner.orcsToDialogItem(MyGame.user.level, context)
        b!!.rv.adapter = OrcAdapter(context, list)
        b!!.rv.layoutManager = LinearLayoutManager(context)
        b!!.ivBack.setOnClickListener { v: View? -> finish() }
    }
}