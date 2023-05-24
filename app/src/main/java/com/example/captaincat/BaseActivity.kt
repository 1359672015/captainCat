package com.example.captaincat

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T:ViewBinding> : AppCompatActivity() {
    lateinit var context : Context
    lateinit var mViewBinding :T
    lateinit var vbClass:Class<ViewBinding>
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         supportActionBar?.hide()
        //  //requestWindowFeature(Window.FEATURE_NO_TITLE)
        mViewBinding = initViewBiding() as T//此处必须给mViewBinding赋值
             //= vbClass.inflate(layoutInflater)
        window.run{
            setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
            addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) //禁止设备自动锁屏
        }
         setContentView(mViewBinding.root)
         context = this@BaseActivity

         initView()
         initData()

    }
    abstract fun initViewBiding():ViewBinding

    abstract fun initView()

    abstract fun initData()
}