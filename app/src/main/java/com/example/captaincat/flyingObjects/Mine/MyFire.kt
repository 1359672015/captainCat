package com.example.captaincat.flyingObjects.Mine

import android.content.Context
import android.widget.LinearLayout
import android.graphics.Canvas
import android.util.Log
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.R

class MyFire(/*上下文*/
    context: Context, meFighter: MeFighter
) : LinearLayout(context) {
    /*位置*/
    var x = 0
    var y = 0

    /*对应样式地址*/
    var layout = 0

    /*威力*/
    var power = 10

    /*速度*/
    var speed = 25

    /*运动角度*/
    var shootWay = 90f

    /*对应布局地址*/
    var bulletLL: LinearLayout? = null

    /**
     * @子类设置属性
     * 威力
     * 速度
     * 布局
     */
    fun setInfo(power: Int, speed: Int, layout: Int) {
        this.power = power
        this.speed = speed
        initView(layout)
    }

    private fun initView(layout: Int) {
        inflate(context, layout, this)
        bulletLL = findViewById(R.id.bulletll)
        bulletLL!!.setRotation(shootWay - 90)
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

    /**
     * @移动轨迹函数
     * 可重写
     * 默认：直线
     * 可重写种类：增减速、抛物线、重力下坠、回力
     */
    fun moving() {
        val Π = Math.PI
        val rotatuion = shootWay / 180.0 * Π
        val deltaX = (Math.cos(rotatuion) * speed).toInt()
        val deltaY = (Math.sin(rotatuion) * speed).toInt()
        Log.d("三角函数计算结果", "Δx=$deltaX,Δy=$deltaY.射击角度是:$shootWay,即:$rotatuion")
        settheY(gettheY() - deltaY)
        settheX(gettheX() - deltaX)
    }

    /**
     * @碰撞壁垒检测
     */
    val isTouchedSides: Int
        get() = if (x <= -Config.SCREENWID / 2) Config.TOUCH_LEFT else if (x >= Config.SCREENWID / 2) Config.TOUCH_RIGHT else if (y <= -Config.SCREENHEI / 2) Config.TOUCH_TOP else Config.NOT_TOUCHED

    /**
     * @碰撞敌机检测
     * 扣除生命值的动作已在此函数中执行
     */
    fun isTouchEnemy(enemyList: List<Enemy>?): Boolean {
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                left = x - child.measuredWidth / 2
                right = x + child.measuredWidth / 2
                top = y - child.measuredHeight / 2
                bottom = y + child.measuredHeight / 2
            }
        }
        return if (enemyList != null) {
            for (enemy in enemyList) {
                val e_width = enemy.rightside - enemy.leftside
                //Log.i("敌人位置b", enemy.positiontoString())
                return if (top > enemy.bottomside || bottom < enemy.topSide) continue else if (right < enemy.leftside || left > enemy.rightside) continue else {
                    Log.d(
                        "碰到了敌人",
                        "子弹左侧:" + left + ",敌人左侧:" + enemy.leftside + ",敌人右侧:" + enemy.rightside
                    )
                    enemy.getHurt(power)
                    true
                }
            }
            false
        } else false
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
        x = meFighter.gettheX()
        y = meFighter.gettheY() - 50
        shootWay = meFighter.shootWay
    }
}