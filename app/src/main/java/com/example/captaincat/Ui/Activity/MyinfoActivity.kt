package com.example.captaincat.Ui.Activity

import com.example.captaincat.api.UserLevelsModel.Companion.refreshUser
import com.example.captaincat.api.UserLevelsModel.Companion.changeInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.captaincat.MyGame
import com.example.captaincat.model.User
import com.example.captaincat.databinding.ActivityMyinfoBinding

class MyinfoActivity : AppCompatActivity() {
    var b: ActivityMyinfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //InitViewUtil.initView(this)
        //getSupportActionBar().hide();
        b = ActivityMyinfoBinding.inflate(layoutInflater)
        setContentView(b!!.root)
        refreshUser()
        val user = MyGame.user
        setUserInfo(user)
        initListener()
    }

    private fun initListener() {
        b!!.llAll.setOnClickListener { v: View? ->
            MyGame.user.name = b!!.etName.text.toString()
            MyGame.user.introduce = b!!.etIntroduce.text.toString()
            setUserInfo(MyGame.user)
            b!!.etName.visibility = View.GONE
            b!!.tvName.visibility = View.VISIBLE
            b!!.etIntroduce.visibility = View.GONE
            b!!.tvIntroduce.visibility = View.VISIBLE
        }
        b!!.tvName.setOnClickListener { v: View? ->
            b!!.etName.visibility = View.VISIBLE
            b!!.tvName.visibility = View.GONE
        }
        b!!.tvIntroduce.setOnClickListener { v: View? ->
            b!!.etIntroduce.visibility = View.VISIBLE
            b!!.tvIntroduce.visibility = View.GONE
        }
        b!!.ivBack.setOnClickListener { v: View? ->
            changeInfo(b!!.etName.text.toString(), b!!.etIntroduce.text.toString())
            //MainActivity.getInstance().initUserInfo();
            finish()
        }
    }

    fun setUserInfo(user: User) {
        b!!.tvName.text = """
               昵称 : ${user.name}
               等级 : ${user.level}
               账号 : ${user.account}
               """.trimIndent()
        b!!.etName.setText(MyGame.user.name)
        b!!.etIntroduce.setText(MyGame.user.introduce)
        b!!.tvIntroduce.text = "介绍 : " + MyGame.user.introduce
        b!!.tvFightInfo.text = """
               生命值 : ${user.blood}
               移动速度 : ${user.speedLevel}
               技能槽数目 : ${user.ownFireNum}
               """.trimIndent()
    }
}