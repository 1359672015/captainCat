package com.example.captaincat.Ui.Activity

import android.content.Intent
import android.os.Handler
import android.os.Message
import androidx.viewbinding.ViewBinding
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyApplication
import com.example.captaincat.MyApplication.Companion.musicUtil
import com.example.captaincat.MyGame
import com.example.captaincat.R
import com.example.captaincat.Utils.TimeUtil
import com.example.captaincat.Utils.cache.SharePreferenceUtil
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logi
import com.example.captaincat.api.ReadyOrPetModel
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.databinding.ActivityWelcomeBinding
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Config.Config.*
import com.example.captaincat.model.User
import com.example.lamstest.Common.drawable
import com.example.lamstest.Common.logD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    private lateinit var handler: MyHandler
    var hasUser = 0

    override fun initView() {
        musicUtil.play()
        mViewBinding.rootView.background = R.drawable.changing_day_sky.drawable()
        initSky()
    }

    private fun initSky() {
        when(TimeUtil.getHourOf24().toInt()){
            in 5..7,in 17..18->{
                mViewBinding.rootView.background = R.drawable.changing_dusk_sky.drawable()
            }
            in 8..16->{
                mViewBinding.rootView.background = R.drawable.changing_day_sky.drawable()
            }
            else ->{
                mViewBinding.rootView.background = R.drawable.changing_night_sky.drawable()
            }

        }
    }
     override fun initData() {
        handler = MyHandler(this)

         SCREENWID =  windowManager.defaultDisplay.width
         SCREENHEI =  windowManager.defaultDisplay.height
         MAX_WIDTH =  windowManager.defaultDisplay.width/2
         MAX_HEIGHT =  windowManager.defaultDisplay.height/2
         HIGHEST_PLACE =  MAX_HEIGHT*60/100
         LOWEST_PLACE =   MAX_HEIGHT*5/100
         ("屏幕大小初始化完毕 : 屏宽:" +
                 "SCREENWID = $SCREENWID"+
                 "SCREENHEI = $SCREENHEI"+
                 "HIGHEST_PLACE = $HIGHEST_PLACE"+
                 "LOWEST_PLACE = $LOWEST_PLACE")
             .logD()

        val userId = SharePreferenceUtil.getUserId()
        Logi(userId)
        if (userId != null && Config.NO_USER != userId && userId.length > 2) {
            MyApplication.apiService.getUserById(userId)
                .enqueue(object : Callback<RequestType<User>> {
                    override fun onResponse(
                        call: Call<RequestType<User>>,
                        response: Response<RequestType<User>>
                    ) {
                        val status = response.body()!!.status
                        if (status == 200) {
                            val user = response.body()!!.data
                            MyGame.setTheUser(user)
                            ReadyOrPetModel.getPet(user.id){}
                            UserLevelsModel.userId = user.id.toString() + ""
                            Logd("getById:" + MyGame.user.toString())
                            handler.sendEmptyMessageDelayed(Config.TO_MAIN, 1200)
                        } else handler.sendEmptyMessageDelayed(Config.TO_LOGIN, 900)
                    }

                    override fun onFailure(call: Call<RequestType<User>>, t: Throwable) {
                        handler.sendEmptyMessageDelayed(Config.TO_LOGIN, 10)
                    }
                })
        } else handler.sendEmptyMessageDelayed(Config.TO_LOGIN, 2000)
    }

    private class MyHandler(activity: WelcomeActivity) : Handler() {
        private val activityWeakReference: WeakReference<WelcomeActivity>
        override fun handleMessage(msg: Message) {
            val activity = activityWeakReference.get()
            when (msg.what) {
                Config.TO_LOGIN -> activity!!.toLogin()
                Config.TO_MAIN -> activity!!.toMain()
            }
        }

        init {
            activityWeakReference = WeakReference(activity)
        }
    }

    private fun toMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun initViewBiding(): ViewBinding {
        return ActivityWelcomeBinding.inflate(layoutInflater)
    }
}