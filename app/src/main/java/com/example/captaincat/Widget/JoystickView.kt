package com.example.captaincat.Widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.captaincat.R
import com.example.captaincat.databinding.LayoutSlideViewBinding

//拖动操作杆
open class JoystickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val mViewBinding: LayoutSlideViewBinding by viewBinding(CreateMethod.BIND)
    var isSlideOperating = true
        set(value) {
            mViewBinding.mCircleViewByImage.setSlideOperating(value)

        }
     var clickLockView: (View) -> Unit = {}
     var forwardMove: (Unit) -> Unit = {}
     var backMove: (Unit) -> Unit = {}
     var move: (Double,Double) -> Unit = {_,_->}
     var stop: ( ) -> Unit = {  }
      var leftMove: (Unit) -> Unit = {}
      var rightMove: (Unit) -> Unit = {}
      var centerMove: (Unit) -> Unit = {}
      var centerClick: (Unit) -> Unit = {}
      var actionUp: (Unit) -> Unit = {}
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!isSlideOperating) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (!isSlideOperating) {
            super.onTouchEvent(event)
        } else {
            false
        }
    }

    init {
        inflate(context, R.layout.layout_slide_view, this)
        mViewBinding.mCircleViewByImage.run {
            setCallback(object : CircleViewByImage.ActionCallback {
                override fun forwardMove() {
                    forwardMove.invoke(Unit)
                }

                override fun move(x: Double, y: Double) {
                    move.invoke(x,y)
                }


                override fun backMove() {
                    backMove.invoke(Unit)
                }

                override fun leftMove() {
                    leftMove.invoke(Unit)
                }

                override fun rightMove() {
                    rightMove.invoke(Unit)
                }

                override fun centerMove() {
                    centerMove.invoke(Unit)
                }

                override fun centerClick() {
                    centerClick.invoke(Unit)
                }

                override fun actionUp() {
                    actionUp.invoke(Unit)
                }
            })
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
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
                    new_left.toInt(),
                    new_top.toInt(),
                    (new_left + child.measuredWidth).toInt(),
                    (new_top + child.measuredHeight).toInt()
                )
            }
        }
    }
}