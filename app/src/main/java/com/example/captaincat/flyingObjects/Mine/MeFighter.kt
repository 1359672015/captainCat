package com.example.captaincat.flyingObjects.Mine

import android.content.Context
import android.graphics.Canvas
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.captaincat.MyGame
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.R
import com.example.captaincat.event.WarParams
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Config.Config.*
import com.example.captaincat.information.Tables.CharacterConfig
import com.example.lamstest.Common.drawable
import com.example.lamstest.Common.hide
import com.example.lamstest.Common.logD
import com.example.lamstest.Common.show

class MeFighter(context: Context,
                  var x: Int,
                  var y: Int,
                  fireChosen: String?,
                  var getHurt:(Int)->Unit = {_->},
                  var shoot:(FirePackage,Int)->Unit = {_,_->}
                ) : LinearLayout(context) {
    var layout = R.layout.myconnon
    //移动速度
    var speed = MyGame.user.moveSpeed
    //射击角度
    var shootWay = 90f
    //炮口🗡
    lateinit  var sword: TextView
    lateinit var ivPet: ImageView
    //宽度
    var mWidth = 0
    var mWidthInit = false

    lateinit var firePackage: FirePackage

    var xSpeed = 0.0
    var ySpeed = 0.0
    private fun initView() {
        inflate(context, layout, this)
        sword = findViewById(R.id.sword)
        ivPet = findViewById(R.id.ivElf)
        if(pet.fightAble){
            ivPet.show()
            Glide.with(context).load(CharacterConfig.elves[pet.look]).into(ivPet)
        }
        else
            ivPet.hide()
    }
    fun refreshColor(){
        var fire: Boolean = (WarParams.fireTime > 0)
        var shield:Boolean = (WarParams.shiedTime > 0)
        findViewById<LinearLayout>(R.id.mycat).background =
            when{
                fire&&shield->R.drawable.gradient_round3_super.drawable()
                fire->R.drawable.gradient_round3_shield.drawable()
                shield->R.drawable.gradient_round3_fire.drawable()
                else-> R.drawable.aa_gradient1.drawable()
            }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                if(!mWidthInit){
                    if(child.measuredWidth>mWidth)
                    mWidth = child.measuredWidth
                }
                // 计算子视图的左边偏移数值
                val new_left = (right - 1) / 2 - child.measuredWidth / 2 + x
                // 计算子视图的上方偏移数值
                val new_top = (bottom - top) / 2 - child.measuredHeight / 2 + y
                // 根据最新的上下左右四周边界，重新放置该子视图
                child.layout(
                    new_left, new_top,
                    new_left + child.measuredWidth, new_top + child.measuredHeight
                )
            }
            mWidthInit = true
        }
    }
    fun setShootWay(rotation: Float, way: Int) {
        if (rotation < 15 && way == Config.GO_LEFT) return else if (rotation > 165 && way == Config.GO_RIGHT) return else {
            sword!!.rotation = rotation + 45
            shootWay = rotation
        }
    }

    fun gettheX(): Int {
        return x
    }

    fun gettheY(): Int {
        return y
    }

    fun settheX(x: Int, way: Int) {
        if (!isTouchedSides.contains(way.toString())) {
            this.x = x
            requestLayout()
        }
    }
    fun settheY(y: Int, way: Int) {
        if (!isTouchedSides.contains(way.toString())) {
            this.y = y
            requestLayout()
        }
    }
    // 获得第i个子视图
    val isTouchedSides: String
        get() {
            var left = 0
            var right = 0
            var top = 0
            var bottom = 1000

            val sb = StringBuilder("")
            for (i in 0 until childCount) {
                val child = getChildAt(i) // 获得第i个子视图
                if (child.visibility != GONE) {
                    if (x - child.measuredWidth / 2 < left) left = x - child.measuredWidth / 2
                    if (x + child.measuredWidth / 2 > right) right = x + child.measuredWidth / 2
                    if (y + child.measuredHeight / 2 > top) top = y + child.measuredHeight / 2
                    if (y - child.measuredWidth / 2 < bottom) bottom = y - child.measuredHeight / 2
                    "(pX,pY) =($x,$y) , bottom = $bottom , LOWEST_PLACE = $LOWEST_PLACE".logD("当前位置")
                }

                 if (left <= - SCREENWID / 2 + 10)
                    sb.append( TOUCH_LEFT)
                    if (right >=  SCREENWID / 2 - 10)
                    sb.append(TOUCH_RIGHT)
                  if (top >=  HIGHEST_PLACE)
                      sb.append(TOUCH_TOP)
                  if (bottom <=  LOWEST_PLACE)
                      sb.append(TOUCH_BOTTOM)
            }
            return sb.toString()
        }

    init {
        this.x = x
        this.y = y
        initView()
        firePackage = FirePackage(context, fireChosen!!, this)
    }
}