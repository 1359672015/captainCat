package com.example.captaincat.Ui.Dialog

import android.app.Dialog
import android.content.Context
import com.example.captaincat.Ui.Dialog.StoryDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.captaincat.R

class StoryDialog(
    context: Context,
   var title: String = "",
   var sContent: String = "",
   var clicker:()->Unit = {},

) : Dialog(context) {
    var inflate: View


    init {
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_storyhtml, null)
        this.setContentView(inflate)
        val content = inflate.findViewById<TextView>(R.id.content)
        val title1 = inflate.findViewById<TextView>(R.id.title1)
        content.text = sContent
        title1.text = title
        findViewById<View>(R.id.go).setOnClickListener{
            clicker.invoke()
        }

    }
}