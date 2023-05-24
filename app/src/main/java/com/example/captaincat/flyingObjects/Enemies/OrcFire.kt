package com.example.captaincat.flyingObjects.Enemies

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.widget.RelativeLayout
import com.example.captaincat.R
import com.example.captaincat.flyingObjects.Mine.MeFighter
import com.example.captaincat.information.Config.Config

/**
 * 敌人炮火基类
 */
class OrcFire(
    context: Context,
    var x: Int = 0,
    var y: Int = 0,
    var power: Int = 5,
    var speed: Int = 12,
    var layout: Int = R.layout.of1,
    addSpeed: Int
) : RelativeLayout(
    context
) {
    var addSpeed = 0
    var bulletLL: RelativeLayout? = null
    private fun initView(layout: Int) {
        inflate(context, layout, this)
        bulletLL = findViewById(R.id.bulletll)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
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
        }
    }

    fun moving() {
        speed += addSpeed
        settheY(gettheY() + speed)
    }

    val isTouchedSides: Int
        get() = if (x <= -Config.SCREENWID / 2) Config.TOUCH_LEFT else if (x >= Config.SCREENWID / 2) Config.TOUCH_RIGHT else if (y >= Config.SCREENHEI / 2) Config.TOUCH_BOTTOM else Config.NOT_TOUCHED

    fun isTouchMe(meFighter: MeFighter): Boolean {
        //Log.d("大炮位置", meFighter.gettheX()+","+ meFighter.mWidth+","+ meFighter.gettheY());
        var left = 0
        var right = 0
        var head = 0
        var bottom = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                left = x - child.measuredWidth / 2
                right = x + child.measuredWidth / 2
                head = y + child.measuredHeight / 2
                bottom = y - child.measuredHeight / 2
            }
            //顶点达到大炮位置并且80％的核心部分触碰到大炮
            if (head > meFighter.gettheY()-meFighter.mWidth /2&&
                    y<meFighter.gettheY()+meFighter.mWidth /2) {
                if (!(right < meFighter.gettheX() - meFighter.mWidth / 2
                            || left > meFighter.gettheX() + meFighter.mWidth / 2)
                ) {
                    meFighter.getHurt(power)
                    return true
                } else if (head > Config.SCREENHEI / 2 - meFighter.gettheY() / 2) {
                    return true
                }
            }
        }
        return false
    }

    fun gettheX(): Int {
        return x
    }

    fun gettheY(): Int {
        return y
    }

    fun settheX(x: Int) {
        this.x = x
        requestLayout()
    }

    fun settheY(y: Int) {
        this.y = y
        requestLayout()
    }



    init {
        this.x = x
        this.y = y
        this.addSpeed = addSpeed
        Log.d("子弹位置", "pX=$x,pY$y")
        initView(layout)
    }
}