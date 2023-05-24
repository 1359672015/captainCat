package com.example.captaincat.flyingObjects.Mine

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.widget.*
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.captaincat.MyApplication.Companion.musicUtil
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.MyGame.Companion.user
import com.example.captaincat.R
import com.example.captaincat.Ui.Activity.AirWarActivity
import com.example.captaincat.Ui.Activity.AirWarActivity.Companion.isGoingOn
import com.example.captaincat.Utils.cache.MMKVUtils
import com.example.captaincat.Utils.media.MusicUtil.Companion.HURTBIU
import com.example.captaincat.Utils.message.PostBoy
import com.example.captaincat.event.WarParams
import com.example.captaincat.information.Config.Config.*
import com.example.captaincat.information.Tables.MyTables
import com.example.lamstest.Common.hide
import com.example.lamstest.Common.toastCover
import kotlinx.android.synthetic.main.controller.view.*
import java.math.BigDecimal
class Controller(context: Context, var meFighter: MeFighter) : RelativeLayout(
    context
) {

    var x = 0
    var y = 0
    var blood = user.blood
    var magic = MutableLiveData<Int>(100)
    var layout = R.layout.controller
    var left: Button? = null
    var right: Button? = null
    var rotaLeft: Button? = null
    var rotaRight: Button? = null
    var stopOrContinue: Button? = null
    lateinit var btnAddLife: TextView
    lateinit var btnShield: TextView
    lateinit var btnStrong: TextView
    var btnShoots = arrayOfNulls<ImageButton>(4)
    var tvFireNum = arrayOfNulls<TextView>(4)
    lateinit var bloodPB: ProgressBar
    lateinit var magicPB: ProgressBar
    lateinit var bloodTv: TextView
    lateinit var magicTv: TextView
    var rotaSpeed = 2f
    var fireKindNum = 0
    private fun initBlood(blood: Int) {
        this.blood = blood
        bloodTv.bringToFront()
        bloodTv.text = blood.toString()
        bloodPB.max = blood
        bloodPB.progress = bloodPB.max

        magicPB.max = 100
        magicTv.bringToFront()
        magic.observe(context as AirWarActivity){
            magicTv.text = it.toString()
            magicPB.progress = it
        }
        magicPB.progressDrawable =  context.resources.getDrawable(R.drawable.pb_pet_magic)
        bloodPB.progressDrawable =  context.resources.getDrawable(R.drawable.pb_my_life)
    }

    fun setxy(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                val new_left = (right - 1) / 2 - child.measuredWidth / 2 + x
                val new_top = (bottom - top) / 2 - child.measuredHeight / 2 + y
                child.layout(
                    new_left, new_top,
                    new_left + child.measuredWidth, new_top + child.measuredHeight
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    fun setISGoingOn(go: Boolean) {
        if (go) {
            isGoingOn = true
            stopOrContinue!!.text = "暂停"
            musicUtil.startBg()
        } else {
            isGoingOn = false
            stopOrContinue!!.text = "继续"
            musicUtil.pause()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initShootingButton() {
        fireKindNum = meFighter.firePackage .chosenFires.length

        rotaLeft = findViewById(R.id.rota_L)
        rotaRight = findViewById(R.id.rota_R)
        btnShoots[0] = findViewById(R.id.ib_fire_i)
        btnShoots[1] = findViewById(R.id.ib_fire_ii)
        btnShoots[2] = findViewById(R.id.ib_fire_iii)
        btnShoots[3] = findViewById(R.id.ib_fire_iv)
        tvFireNum[0] = findViewById(R.id.tv_fire_i)
        tvFireNum[1] = findViewById(R.id.tv_fire_ii)
        tvFireNum[2] = findViewById(R.id.tv_fire_iii)
        tvFireNum[3] = findViewById(R.id.tv_fire_iv)

        btnStrong = findViewById(R.id.btnStrong)
        btnShield = findViewById(R.id.btnShield)
        btnAddLife = findViewById(R.id.btnLifeAdd)

        jvWay.run {
            stop = {
                meFighter.xSpeed = 0.0
                meFighter.ySpeed = 0.0
            }
            actionUp = {
                meFighter.xSpeed = 0.0
                meFighter.ySpeed = 0.0
            }
            move = {cos,sin->
                meFighter.xSpeed = meFighter.speed*cos
                meFighter.ySpeed = meFighter.speed*sin
               // "(${meFighter.xSpeed},${meFighter.ySpeed})".toastCover("移动向量")
            }
        }
        for (i in 0..3) {
            if (i < fireKindNum) {
                Glide.with(context)
                    .load(
                        MyTables.FIRE_ICON[meFighter.firePackage.chosenFires[i].code - 48]
                    )
                    .into(btnShoots[i])
                if(MMKVUtils.decodeBool("QUICK_SHOOT",true))
                btnShoots[i]!!.setOnTouchListener { v, _ ->
                    if (isGoingOn) {
                        meFighter.shoot(meFighter.firePackage,i)
                    } else PostBoy.showSnack(v, "游戏已暂停")
                    false
                }
                else {
                    tvFireNum[i]!!.setOnTouchListener { v, _ ->
                        if (isGoingOn) {
                            meFighter.shoot(meFighter.firePackage,i)
                        } else PostBoy.showSnack(v, "游戏已暂停")
                        false
                    }
                }
                tvFireNum[i]!!.text = meFighter.firePackage.getFireNum(i).toString()
            } else {
                btnShoots[i]!!.hide()
                tvFireNum[i]!!.hide()
            }
        }
        if(!pet.fightAble){
            btnShield.hide()
            btnAddLife.hide()
            btnStrong.hide()
        }
        else {
        btnShield.setOnClickListener {
            if (isGoingOn) {
            if(magic.value!! >=10){
            WarParams.shiedTime+= 5 + user.level/2
                meFighter.refreshColor()
                refreshStatus()
                magic.postValue(magic.value!!-15)
            }
            else
                "魔法已用完！".toastCover()
            } else PostBoy.showSnack(it, "游戏已暂停")
        }
        btnAddLife.setOnClickListener {
            if (isGoingOn) {
            if(magic.value!! >=10){
                magic.postValue(magic.value!!-10)
                addLife(user.blood/10)
            }
            else
                "魔法已用完！".toastCover()
            } else PostBoy.showSnack(it, "游戏已暂停")
        }
        btnStrong.setOnClickListener {
            if (isGoingOn) {
            if(magic.value!! >=10){
                WarParams.fireTime += 10
                meFighter.refreshColor()
                refreshStatus()
                magic.postValue(magic.value!!-10)
            }
            else
                "魔法已用完！".toastCover()
            } else PostBoy.showSnack(it, "游戏已暂停")
        }
    }
    }
    fun refreshStatus(){
        findViewById<TextView>(R.id.tvShield).text = "护盾："+WarParams.shiedTime
        findViewById<TextView>(R.id.tvStrong).text = "强化："+WarParams.fireTime+"s"
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initMovingButton() {
        rotaLeft!!.setOnTouchListener { v, event ->
            if (isGoingOn) {
                meFighter.setShootWay((meFighter.shootWay - rotaSpeed), GO_LEFT)
                Log.d("当前角度:", "pX=" + meFighter.shootWay + "")
            }
            false
        }
        rotaRight!!.setOnTouchListener { v, event ->
            if (isGoingOn) {
                meFighter.setShootWay((meFighter.shootWay + rotaSpeed), GO_RIGHT)
                Log.d("当前角度:", "pX=" + meFighter.shootWay + "")
            }
            false
        }
    }

    fun move() {
        meFighter.settheX(meFighter.gettheX() + meFighter.xSpeed.toInt(), if(meFighter.xSpeed>0) TOUCH_RIGHT else TOUCH_LEFT)
        meFighter.settheY(meFighter.gettheY() + meFighter.ySpeed.toInt(),  if(meFighter.ySpeed>0) TOUCH_TOP else TOUCH_BOTTOM)
    }

    fun initConsole() {
        stopOrContinue = findViewById(R.id.btn_StopOrContinue)
        stopOrContinue!!.setOnClickListener{
             setISGoingOn(isGoingOn.not())
        }
        bloodPB = findViewById(R.id.pb_blood)
        bloodTv = findViewById(R.id.tv_blood)


        magicPB = findViewById(R.id.pb_magic)
        magicTv = findViewById(R.id.tv_magic)
    }

    //受到攻击
    fun getHurt(hurt: Int) {
        var hurts = hurt
        if(WarParams.shiedTime>0){
            hurts=hurt*9/10
                WarParams.shiedTime= WarParams.shiedTime -1
            if(WarParams.shiedTime==0)
                meFighter.refreshColor()
                refreshStatus()
        }
        blood -= hurts
        bloodPB.progress = blood
        bloodTv.text = blood.toString()
        musicUtil.play(HURTBIU)
    }

    //刷新炮火数量
    fun refreshFire() {
        for (i in 0 until fireKindNum) {
            var num = meFighter.firePackage.getFireNum(i)
            val b = BigDecimal(num)
            num = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            tvFireNum[i]!!.text = num.toString()
        }
    }

    fun addLife(addNum: Int) {
        if (blood + addNum >  user.blood) {
            blood =  user.blood
        } else {
            blood += addNum
        }
        bloodPB!!.progress = blood
        bloodTv!!.text = blood.toString()
    }

    init {
        inflate(context, layout, this)
        initShootingButton()
        initMovingButton()
        initConsole()
        initBlood(blood)
    }
}