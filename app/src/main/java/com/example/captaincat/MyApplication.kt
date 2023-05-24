 package com.example.captaincat

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.example.captaincat.api.UserBagModel
import com.example.captaincat.api.core.ApiService
import com.example.captaincat.api.core.RetrofitService
import com.example.captaincat.Ui.Activity.AirWarActivity
import com.example.captaincat.Ui.Activity.AirWarActivity.Companion.isGoingOn
import com.example.captaincat.Utils.cache.FileRefactor
import com.example.captaincat.Utils.cache.SharePreferenceUtil
import com.example.captaincat.Utils.media.MusicUtil
import com.example.lamstest.Common.logD
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.tencent.mmkv.MMKV

 class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
        QMUISwipeBackActivityManager.init(this)
        MMKV.initialize(appContext)
        FileRefactor.initialize()
        musicUtil = MusicUtil()
        musicUtil.init()
        SharePreferenceUtil.init(appContext, "Jamrave")
        RetrofitService.iniService()//注册retrofit
        apiService = RetrofitService.getInstance().apiService
        UserBagModel.initThings()
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }
    companion object{
       lateinit var appContext : Context
       lateinit var instance : MyApplication
       lateinit var musicUtil: MusicUtil
       lateinit var apiService: ApiService
    }

     /**
      * 当前Acitity个数
      */
     private var activityAcount = 0
     private var activityTotal = 0
     var appIsForeground = true
     /**
      * Activity 生命周期监听，用于监控app前后台状态切换
      */
     var activityLifecycleCallbacks: ActivityLifecycleCallbacks =
         object : ActivityLifecycleCallbacks {
             override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                 activityTotal++
                 "活动数量:$activityTotal".logD()
             }
             override fun onActivityStarted(activity: Activity) {
                 if (activityAcount == 0) {
                     if(activity is AirWarActivity){
                         if(isGoingOn)
                     musicUtil.startBg()
                     }
                     else
                     musicUtil.startBg()

                     "app回到了前台".logD()
                     appIsForeground = true
                     // if(FinishActivityManager.getManager().isActivityExists(CourseDetailActivity::class.java))
                     //如果课程活动还在任务栈里(即当前状态为在课堂内),则发起进入课堂消息
                 }
                 activityAcount++
             }
             override fun onActivityResumed(activity: Activity) {}
             override fun onActivityPaused(activity: Activity) {}
             override fun onActivityStopped(activity: Activity) {
                 activityAcount--
                 if (activityAcount == 0) {
                     "app被放到了后台".logD()
                     musicUtil.pause()
                     appIsForeground = false
                 }
             }
             override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
             override fun onActivityDestroyed(activity: Activity) {
                 activityTotal--
                 "活动数量:$activityTotal".logD()
             }
         }
}