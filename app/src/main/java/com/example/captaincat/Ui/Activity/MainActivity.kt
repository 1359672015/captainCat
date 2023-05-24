package com.example.captaincat.Ui.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyApplication.Companion.musicUtil
import com.example.captaincat.MyGame
import com.example.captaincat.R
import com.example.captaincat.Ui.Dialog.DialogItem
import com.example.captaincat.Ui.Dialog.MenuDialog
import com.example.captaincat.Utils.TimeUtil
import com.example.captaincat.Utils.media.MusicUtil.Companion.TREE_HOUSE
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd
import com.example.captaincat.api.ReadyOrPetModel.Companion.getGems
import com.example.captaincat.api.UserBagModel.Companion.initThings
import com.example.captaincat.api.UserLevelsModel.Companion.refreshUser
import com.example.captaincat.databinding.MainMapBinding
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Tables.CharacterConfig
import com.example.lamstest.Common.drawable
import com.example.lamstest.Common.shake
import com.example.lamstest.Common.toast

class MainActivity :BaseActivity<MainMapBinding>() {
    override fun initView() {
        Logd("MainAty:" + MyGame.user.toString())
        initListener() //初始化点击事件
        initUserInfo()
        initSky()
    }

    override fun initViewBiding(): ViewBinding {
        return MainMapBinding.inflate(layoutInflater)
    }


    override fun initData() {

    }

    private fun initSky() {
        when(TimeUtil.getHourOf24().toInt()){
             in 5..7,in 17..18->{
                mViewBinding.rootView.background = R.drawable.changing_dusk_sky.drawable()
                mViewBinding.ivBG.background = R.drawable.bg3.drawable()
            }
            in 8..16->{
                mViewBinding.rootView.background = R.drawable.changing_day_sky.drawable()
                mViewBinding.ivBG.background = R.drawable.bg.drawable()
            }
            else ->{
                mViewBinding.rootView.background = R.drawable.changing_night_sky.drawable()
                mViewBinding.ivBG.background = R.drawable.bg2.drawable()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    fun initUserInfo() {
        mViewBinding.tvHead.text = "${MyGame.user.name}\nLv.${MyGame.user.level}"
        initThings()
        getGems{}
    }

    fun initListener() {
        Logd("Main:initListener" + MyGame.user.toString())
        mViewBinding.ivEShip.setOnClickListener {
            val list: MutableList<DialogItem> = ArrayList()
            list.add(DialogItem("🛸主线模式", null, View.OnClickListener {
                turnTo(ChoosingActivity::class.java, MAIN_LEVEL)
            }))
            list.add(DialogItem("🛰挑战模式", null) {
                turnTo(ChoosingActivity::class.java, CHALLENGE_LEVEL)
            })
            MenuDialog.showDialog(context, list, null, Config.MENU, null)
        }
        mViewBinding.ivStore.setOnClickListener {
            val list: MutableList<DialogItem> = ArrayList()
            list.add(DialogItem("🏠 商店", null) {
                turnTo(StoreActivity::class.java, 0)
            })
            list.add(DialogItem("🎒 背包", null) {
                turnTo(BagActivity::class.java, 0)
            })
            MenuDialog.showDialog(context, list, null, Config.MENU, null)

        }
        mViewBinding.ivIron.setOnClickListener {
            val list: MutableList<DialogItem> = ArrayList()
            list.add(DialogItem("🛠 修炼所", null) {
                turnTo(PracticeActivity::class.java, 0)
            })
            list.add(DialogItem("💎 装备", null) {
                turnTo(BackUpActivity::class.java, 0)
            })
            MenuDialog.showDialog(context, list, null, Config.MENU, null)

        }
        mViewBinding.rlJulian.setOnClickListener {
            turnTo(BookActivity::class.java, 0)
        }
        mViewBinding.llMine.setOnClickListener {
            turnTo(MyinfoActivity::class.java, 0)
        }
        mViewBinding.ivMore.setOnClickListener {
            val list: MutableList<DialogItem> = ArrayList()
            list.add(DialogItem("📧 意见反馈", null) { "该功能尚未开发".toast() })
            list.add(DialogItem("⚙ 游戏设置", null) { "该功能尚未开发".toast() })
            list.add(DialogItem("📌 退出账号", null, View.OnClickListener {
                turnTo(LoginActivity::class.java, 0)
                finish()
            }))
            list.add(DialogItem("👣 退出游戏", null) { finish() })
            MenuDialog.showDialog(context, list, null, Config.MENU, null)
        }
        mViewBinding.ivEgg.run{
            setOnClickListener {
                if(MyGame.user.level>6){
                    if(MyGame.pet.id==-1) 
                        turnTo(GetPetActivity::class.java, 0)
                    else turnTo(PetActivity::class.java, 0) 
                }
                else "宠物功能6级后开放".toast()
            }
            if(MyGame.user.level>6){
                if(MyGame.pet.id==-1){ 
                    Glide.with(context).load(R.mipmap.pic_egg).into(this)
                    this.shake()
                }
                else  Glide.with(context).load(CharacterConfig.elves[MyGame.pet.look]).into(this)
              
            }
        }
    }

    private fun turnTo(activityClass: Class<*>?, extra: Int) {
        refreshUser{
            val intent = Intent(context, activityClass)
            intent.putExtra(type, extra)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quit()
        }
        return false
    }

    private fun quit() {
        val diaBuilder = AlertDialog.Builder(context)
        diaBuilder.setTitle("确定退出游戏吗?")
        diaBuilder.setPositiveButton("确定") { dialog, which ->
            System.exit(0)
            //finish();
        }
        diaBuilder.setNegativeButton("继续游戏") { dialog, which -> dialog.dismiss() }
        diaBuilder.show()
    }

    override fun onResume() {
        super.onResume()
        refreshUser{
            initUserInfo()
        }
    }
    override fun onRestart() {
        super.onRestart()
        musicUtil.play(TREE_HOUSE)
    }
    companion object {
        private const val MAIN_LEVEL = 1
        private const val CHALLENGE_LEVEL = 2
        const val type = "TYPE"
        fun backToMain(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}