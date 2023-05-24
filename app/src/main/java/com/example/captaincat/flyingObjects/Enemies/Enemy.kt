package com.example.captaincat.flyingObjects.Enemies

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ProbabilityUtil.getNumberLess
import com.example.captaincat.event.WarParams
import com.example.captaincat.flyingObjects.Orcs.Orc
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Config.Config.MAX_HEIGHT
import com.example.captaincat.information.Config.Config.MAX_WIDTH
import com.example.captaincat.information.Tables.OrcTables
import com.example.lamstest.Common.hide
import com.example.lamstest.Common.showQuick
import com.example.lamstest.Common.showSlow
import kotlinx.android.synthetic.main.o1.view.*
import java.util.*


/**
 * 敌机的图形模型
 * 包含：orc实体模型，布局，几何，碰撞检测
 */
class Enemy(context: Context, x: Int, y: Int, firstWay: Int, orc: Orc, report: Int) :
    LinearLayout(
        context
    ) {
    var orc: Orc
    var pX = 0
    var pY = 0
    var UpOrDown = 1
    var RightOrLeft = 1
    var bottomside = 0
    var topSide = 0
    var leftside = 0
    var rightside = 0
    var report = 0
    var hurtShowTime = 0
    //子弹队列仅记录子弹id,以便在子弹真正要显示之后才被赋予坐标(跟着敌机)
    var fireGun: Queue<Int> = LinkedList()
    var bloodPB: ProgressBar? = null
    private fun initStatus() {
        bloodPB = findViewById(R.id.blood)
        bloodPB!!.run {
            val progressDrawable1: Drawable =
                context.resources.getDrawable(R.drawable.progressbar)
                this.progressDrawable = progressDrawable1
            max = orc.maxLife
            progress = max
        }
    }

    private fun initPosition() {
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                //     if((pX - child.getMeasuredWidth()/2)<leftSide)
                leftside = pX - child.measuredWidth / 2
                //     if((pX + child.getMeasuredWidth()/2)>rightSide)
                rightside = pX + child.measuredWidth / 2
                //     if((pY + child.getMeasuredHeight()/2)>bottomSide)
                bottomside = pY + child.measuredHeight / 2
                topSide = pY - child.measuredHeight / 2
            }
        }
    }

    override fun onLayout(
        changed: Boolean,
        leftside: Int,
        top: Int,
        rightside: Int,
        bottomside: Int
    ) {
        Log.d("调用:", "Enemy.onLayout()")
        super.onLayout(changed, leftside, top, rightside, bottomside)
        for (i in 0 until childCount) {
            val child = getChildAt(i) // 获得第i个子视图
            if (child.visibility != GONE) {
                Log.i("MySide:", "leftside=$leftside,rightside=$rightside,bottomside=$bottomside")
                // 计算子视图的左边偏移数值
                val newLeftSide = (rightside - 1) / 2 - child.measuredWidth / 2 + pX
                // 计算子视图的上方偏移数值
                val newTop = (bottomside - top) / 2 - child.measuredHeight / 2 + pY
                // 根据最新的上下左右四周边界，重新放置该子视图
                child.layout(
                    newLeftSide, newTop,
                    newLeftSide + child.measuredWidth, newTop + child.measuredHeight
                )
            }
        }
    }

    /**
     * @移动轨迹函数
     * 可重写,如:变速,绕圈圈
     */
    fun moving(clock: Int) {
        hurtShowTime--
        if(hurtShowTime<=0)
            tvHurt.hide()
        initPosition()
        moveX(orc.getChangeSpeed(clock))
        //下移,速度大于10,除以10,小于10,走1格
        moveY(orc.speed[1])
        Log.d("Enemy_Position", positiontoString())
        addLife() //补血
    }

    fun loadFire(id: Int) {
        fireGun.add(id)
    }

    //制造炮火,并判断有无炮火
    fun makeFire(clock: Int): Boolean {
        orc.loadFire(this, clock)
        return !fireGun.isEmpty()
    }

    val nextFire: OrcFire
        get() = OrcTables.getOrcFire(context, this, fireGun.poll())

    private fun addLife() {
        orc.addLife()
        bloodPB!!.progress = orc.life
    }

    // 获得第i个子视图
    private val isTouchedSides: Int
        get() {
            for (i in 0 until childCount) {
                val child = getChildAt(i) // 获得第i个子视图
                if (child.visibility != GONE) {
                    leftside = pX - child.measuredWidth / 2
                    rightside = pX + child.measuredWidth / 2
                    bottomside = pY + child.measuredHeight / 2
                    topSide = pY - child.measuredHeight / 2
                }
            }
            return if (leftside <= -MAX_WIDTH-10 )
                Config.TOUCH_LEFT
            else if (rightside >= MAX_WIDTH+10)
                Config.TOUCH_RIGHT
            else if (bottomside >= MAX_HEIGHT - orc.lowestPlace)
                Config.TOUCH_BOTTOM
            else if (topSide <= -MAX_HEIGHT + orc.highestPlace)
                Config.TOUCH_TOP
            else Config.NOT_TOUCHED
        }

    fun getHurt(power: Int) {
        val very = ProbabilityUtil.isToHappen(10+ WarParams.veryShoot)
        var powerReal = (if(WarParams.fireTime>0) (power+power/5) else power).toInt()
        val realHurt = if(very) powerReal/2+powerReal else powerReal+getNumberLess(3)
        orc.getHurt(realHurt)
        tvHurt!!.text = "-${realHurt}"
        if(very)
            tvHurt.showSlow()
        else
            tvHurt.showQuick()
        hurtShowTime = 50
        bloodPB!!.progress = orc.maxLife
    }

    val isDead: Boolean
        get() = orc.life <= 0


    fun moveX(distance: Int) {
        if (isTouchedSides == Config.TOUCH_RIGHT) RightOrLeft =
            -1 else if (isTouchedSides == Config.TOUCH_LEFT) RightOrLeft = 1
        pX += distance * RightOrLeft
        requestLayout()
    }

    fun moveY(distance: Int) {

        if (isTouchedSides == Config.TOUCH_BOTTOM) UpOrDown =
            orc.back_Speed else if (isTouchedSides == Config.TOUCH_TOP) UpOrDown = 1
        if (distance in 1..9) pY += UpOrDown  else if (distance > 10) pY += UpOrDown * distance / 10
        requestLayout()
    }

    fun positiontoString(): String {
        return "中心位置为:P(" + pX + "," + pY + ")，" + "底部位置为:" + bottomside + "," +
                "左边位置为" + leftside + ",右边位置" + rightside
    }

    /***
     *
     * @param context 上下文
     * @param x 初始x坐标
     * @param y 初始y坐标
     * @param firstWay 初始方向
     */
    init {
        this.report = report
        inflate(context, orc.look, this)
        RightOrLeft = firstWay
        this.pX = x
        this.pY = y
        this.orc = orc
        initPosition()
        initStatus()
    }
}