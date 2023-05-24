package com.example.captaincat.Ui.Activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyGame
import com.example.captaincat.MyGame.Companion.gemWearEvent
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.MyGame.Companion.petData
import com.example.captaincat.R
import com.example.captaincat.Ui.Adapter.FireAdapter
import com.example.captaincat.Ui.Adapter.LevelsAdapter
import com.example.captaincat.Ui.Dialog.StoryDialog
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastMsg
import com.example.captaincat.api.TimerModel.Companion.getGame
import com.example.captaincat.databinding.ActivityChooseFiresBinding
import com.example.captaincat.event.WarParams
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Tables.LevelTables
import com.example.captaincat.information.Tables.MyTables
import com.example.lamstest.Common.hide
import com.example.lamstest.Common.toast

class ChoosingActivity : BaseActivity<ActivityChooseFiresBinding>() {
    var rvLevels: RecyclerView? = null
    lateinit var rvFires: RecyclerView
    lateinit var fireAdapter: FireAdapter

    var levelsAdapter: LevelsAdapter? = null
    var type = 1
    override fun initView() {
        WarParams.refreshParams()
        type = intent.extras!!.getInt("TYPE")
        rvFires = mViewBinding.rvFires
        rvLevels = mViewBinding.rvLevels
        initLevels()
        initFiresIcon()
        findViewById<View>(R.id.btn_start).setOnClickListener(
            View.OnClickListener {
                val fires = fireAdapter.chosenFires
                if (fires.isEmpty()) {
                    ToastMsg(context, "请至少选择一种技能!")
                    return@OnClickListener
                }
                val level = levelsAdapter!!.chosenIndex + 1
                var title: String? = ""
                var story: String? = ""
                if (type == Config.MAIN) {
                    title = LevelTables.getMainLevelName(level)
                    story = LevelTables.getMainLevelStory(level)
                } else if (type == Config.CHALLENGE) {
                    title = LevelTables.getChallengeLevelName(level)
                    story = LevelTables.getChallengeLevelStory(level)
                }
                StoryDialog(
                    context, title!!, story!!
                ).apply{
                    this.clicker = {
                        val intent = Intent(context, AirWarActivity::class.java)
                        intent.putExtra(Config.CHOSEN_FIRES, fires)
                        intent.putExtra(Config.CHOSEN_LEVEL, level)
                        intent.putExtra(Config.MAIN_OR_CHALLENGE, type)
                        getGame{
                            if(it)
                        startActivity(intent)
                            else
                                "今日游戏时间已超过两小时，休息一下，明天再来吧！".toast()
                        }
                        dismiss()
                    }
                }.show()
            })
    }

    private fun initLevels() {
        val num = MyGame.user.level
        val list = if (type == 1) LevelTables.getMainLevelList(num) //所能选的最大level=当前用户level
        else LevelTables.getChallengeLevelList()
        levelsAdapter = LevelsAdapter(context, list)
        rvLevels!!.adapter = levelsAdapter
        rvLevels!!.layoutManager = LinearLayoutManager(context)
        rvLevels!!.scrollToPosition(list.size - 1)
    }

    private fun initFiresIcon() {
        val list = MyTables.getChoosingFire()
        fireAdapter = FireAdapter(context, list)
        rvFires.adapter = fireAdapter
        val lm = LinearLayoutManager(context)
        lm.orientation = RecyclerView.HORIZONTAL
        rvFires.layoutManager = lm
    }

    override fun initViewBiding(): ViewBinding {
        return ActivityChooseFiresBinding.inflate(layoutInflater)
    }

    override fun initData() {
        gemWearEvent.setWarParams()
        mViewBinding.cbWithPet.run{
            if(pet.id!=-1)
            petData.observe(this@ChoosingActivity){
                if(it==null){
                    hide()
                }
                else if(!it.fightAble){
                    text = "宠物${it.status}，无法作战"
                }
                else{
                    text = "宠物已准备作战"
                }
            }
            else this.hide()
        }
    }
}