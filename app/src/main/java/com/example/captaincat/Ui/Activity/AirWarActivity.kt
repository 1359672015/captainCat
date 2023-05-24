package com.example.captaincat.Ui.Activity

import android.app.AlertDialog
import android.view.KeyEvent
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyApplication.Companion.musicUtil
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.R
import com.example.captaincat.Ui.Dialog.CommonDialog
import com.example.captaincat.Utils.HandlerUtil
import com.example.captaincat.Utils.media.MusicUtil.Companion.BATTLE
import com.example.captaincat.Utils.media.MusicUtil.Companion.SHOOT_BIU
import com.example.captaincat.Utils.message.PostBoy
import com.example.captaincat.Widget.JoystickView
import com.example.captaincat.api.ReadyOrPetModel.Companion.feedPet
import com.example.captaincat.api.TimerModel.Companion.uploadGame
import com.example.captaincat.api.UserBagModel.Companion.userGetRewars
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.databinding.ActivityAirwarBinding
import com.example.captaincat.event.WarParams
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Enemies.OrcFire
import com.example.captaincat.flyingObjects.Mine.Controller
import com.example.captaincat.flyingObjects.Mine.MeFighter
import com.example.captaincat.flyingObjects.Mine.MyFire
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Levels.BaseLevel
import com.example.captaincat.information.Tables.LevelTables
import com.example.lamstest.Common.toastLong

class AirWarActivity : BaseActivity<ActivityAirwarBinding>() {
    var frame: FrameLayout? = null
    var controller: Controller? = null
    lateinit var stick : JoystickView
    lateinit var meFighter: MeFighter
    var clockTime = 20
    lateinit var level: BaseLevel
    private var startTime: Long = 0
    private var endTime: Long = 0
    var hit = 0f
    var shoot = 0f
    var clock = 0
    var mFires : MutableList<MyFire?> = mutableListOf()
    var orcFires: MutableList<OrcFire?> =  mutableListOf()
    var enemyList: MutableList<Enemy> = mutableListOf()
    private var fireChosen: String = "012"
    var goThread = true;
    var gameThread = Thread {
        while (goThread) {
            try {
                Thread.sleep(clockTime.toLong())
            } catch (e: Exception) {
            }
            if (isGoingOn) //判断是否暂停
                handlerUtil.runMethod()
        }
    }
    /** 初始化布局 */
    override fun initView() {
        val intent = intent
        fireChosen = intent.getStringExtra(Config.CHOSEN_FIRES)?:"012"
        context = this@AirWarActivity
        setContentView(R.layout.activity_airwar)
        frame = findViewById(R.id.riverdell)
        //var iv= findViewById<ImageView>(R.id.ivBackground)
        //Glide.with(this).load(R.drawable.red_stars).into(iv)
        initMine()
    }

    override fun initData() {
        val levelIndex = intent.getIntExtra(Config.CHOSEN_LEVEL, 1)
        val type = intent.getIntExtra(Config.MAIN_OR_CHALLENGE, 1)
        initGame(levelIndex, type)
        gameThread.start()
    }

    private fun initGame(levelIndex: Int, type: Int) {
        if (type == Config.MAIN)
            level = LevelTables.getMainLevel(levelIndex, context)
        else if (type == Config.CHALLENGE)
            level = LevelTables.getChallengeLevel(levelIndex, context)
        //frame!!.setBackgroundResource(BaseLevel.background)
        startTime = System.currentTimeMillis()
        isGoingOn = true
        musicUtil.play(BATTLE)
        stick = JoystickView(context)
//        frame!!.addView(stick.apply {
//            pX= 0.0F
//            pY =0.0F
//        })

    }

    private fun initMine() {
        meFighter =  MeFighter(context, 0, Config.SCREENHEI / 2 - 580, fireChosen,{
                controller!!.getHurt(it)
                if (controller!!.blood <= 0) {
                    controller!!.setISGoingOn(false)
                    gameOver()
                }
        }, {pckg,i->
            if (pckg.haveFire(i)) {
                shoot++
                musicUtil.play(SHOOT_BIU)
                val bullet = pckg.getFire(i)
                mFires.add(bullet)
                frame!!.addView(bullet)
            }
        })
        controller = Controller(context, meFighter)
        //全是0: PostBoy.showToaster(context,controller.getHeight()+","+controller.getMeasuredHeight()+","+controller.getMinimumHeight());
        controller!!.setxy(0, Config.SCREENHEI / 2 - 280)
        frame!!.addView(meFighter)
        frame!!.addView(controller)
    }



    private val handlerUtil = HandlerUtil { runEveryThing() }
    /** 游行运作单元 */
    fun runEveryThing() {
        clock++
        PostBoy.logD("Clock", clock.toString() + "")
        moveMyFires()
        moveEnemies()
        moveOrcFires()
        getMyFire()
        getMyLife()
    }//每秒

    /** 我方补血\回蓝 */
    private fun getMyLife() {
            if (clock % (1000 / clockTime) == 0) {//每秒
                controller!!.addLife(WarParams.bringAddLife)
                if( WarParams.fireTime>0 ) {
                    WarParams.fireTime--
                    controller!!.refreshStatus()
                    if(WarParams.fireTime==0){
                        meFighter.refreshColor()
                    }
                }

            }
        if (clock % (4000 / clockTime) == 0){
            controller!!.magic.run{
                if(this.value!!<100)
                postValue(controller!!.magic.value!!+1)
            }
        }
    }

    /** 我方炮火移动 */
    private fun moveMyFires() {
        controller!!.move()
        var i = 0
        while(i<mFires.size) {
            if(!isGoingOn)
            {
                return
            }
            if (mFires[i]!!.isTouchedSides != Config.NOT_TOUCHED) { //碰到了边框
                frame!!.removeView(mFires[i]) //移除布局
                mFires[i] = null //释放炮火的空间
                mFires.removeAt(i) //移除队列
            } else {
                mFires[i]!!.moving()
                if (mFires[i]!!.isTouchEnemy(enemyList)) { //击中了敌人
                    hit++ //击中统计+1
                    frame!!.removeView(mFires[i]) //移除布局
                    mFires[i] = null //释放炮火的内存空间
                    mFires.removeAt(i) //移出队列
                }
            }
            i++
        }
    }

    /** 敌方炮火移动 */
    private fun moveOrcFires() {
        var i = 0
        while(i<orcFires.size){
            if(!isGoingOn)
            {
                return
            }
            if (orcFires[i]!!.isTouchedSides != Config.NOT_TOUCHED) {
                //敌人子弹到底了
                frame!!.removeView(orcFires[i])
                orcFires[i] = null
                orcFires.removeAt(i)
            } else if (orcFires[i]!!.isTouchMe(controller!!.meFighter)) { //敌人子弹击中我
                frame!!.removeView(orcFires[i])
                orcFires[i] = null
                orcFires.removeAt(i)
            } else orcFires[i]!!.moving() //保持移动
            i++
        }
    }

    /** 敌人移动 */
    private fun moveEnemies() {
        if (true) {
            val e = level.getNextOrc(clock)
            if (e != null) {
                enemyList.add(e)
                frame!!.addView(e)
            }
        }
        for (enemy in enemyList) {
            if(!isGoingOn)
            {
                return
            }
            enemy.moving(clock)
            //敌人炮火队列不为空且距离上次发射已经3个周期
            if (enemy.makeFire(clock) && clock % 3 == 0) {
                val orcFire = enemy.nextFire
                orcFires.add(orcFire)
                frame!!.addView(orcFire)
                orcFire.bringToFront()
            }
            if (enemy.isDead) {
                if (level.postReport(enemy.report)) showWinInfo()
                frame!!.removeView(enemy)
                enemyList.remove(enemy)
                break
            }
        }
    }/*补充弹药每1/3秒补充补给,要将3传入*/

    /** 我方弹药补给 */
    private fun getMyFire(){
            val giveTime = 300
            /*补充弹药每1/3秒补充补给,要将3传入*/
            if (clock % (giveTime / clockTime) == 0) {
                meFighter.firePackage.makeFire(1000.0 / giveTime)
            controller?.refreshFire()
            }
        }

    /**战斗结束,弹出胜利信息 */
    private fun showWinInfo() {
        UserLevelsModel.userWinLevel(level.level, context)
        feedPet(pet.id,-4,1)
        CommonDialog.Builder().setCharacter(R.drawable.car_julian)
            .setTitle("战斗胜利！")
            .setContent("长官，恭喜获得战斗胜利!\n${upLoadGameInfo()}")
            .setRightText("领取战利品")
            .setRightOnclickListener {
                userGetRewars(context, level,this)
                it.dismiss()
            }.show(supportFragmentManager)
            controller!!.setISGoingOn(false)
    }

    /**战斗结束,弹出失败信息 */
    private fun gameOver() {
        controller!!.setISGoingOn(false)
      if(!this.isFinishing)
      {
          feedPet(pet.id,-4,-5)
          CommonDialog.Builder().setCharacter(R.drawable.car_julian)
              .setTitle("战斗失败")
              .setContent("长官，飞船已遭到严重破坏，快撤退吧!\n${upLoadGameInfo()}")
              .setRightOnclickListener {
                  goThread = false
                  it.dismiss()
                  finish()
          }.show(supportFragmentManager)
      }
    else {
        "gameOver,活动正在销毁".toastLong()
      }
    }

    fun upLoadGameInfo(): String {
        if(shoot<1)
            shoot = 1.0F
        val hitPro = hit * 100 / shoot
        endTime = System.currentTimeMillis()
        val minute = (endTime - startTime).toInt() / 60000
        uploadGame(hit.toInt(),shoot.toInt(),(endTime - startTime).toInt()/1000)
        return "命中率:${if(hitPro.toString().length>6)hitPro.toString().substring(0,6) else hitPro}% \n游戏时间:${minute + 1}分钟 ".trimIndent()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quit()
        }
        if (keyCode == KeyEvent.KEYCODE_0) {
            quit()
        }
        return false
    }

    override fun onStop() {
        super.onStop()
        controller!!.setISGoingOn(false)
    }

    private fun quit() {
        val diaBuilder = AlertDialog.Builder(context)
        diaBuilder.setTitle("确定退出战斗吗?")
        diaBuilder.setPositiveButton("有事先走") { _, _ ->
                goThread = false
            finish()
        }
        diaBuilder.setNegativeButton("继续战斗") { dialog, _ ->
            isGoingOn = true
            dialog.dismiss()
        }
        diaBuilder.show()
        controller!!.setISGoingOn(false)
    }
    companion object{
        var isGoingOn = true
    }

    override fun onDestroy() {
        orcFires  = mutableListOf()
        mFires = mutableListOf()
       goThread = false
        enemyList = mutableListOf()
        super.onDestroy()
    }

    override fun initViewBiding(): ViewBinding {
        return ActivityAirwarBinding.inflate(layoutInflater)
    }

}