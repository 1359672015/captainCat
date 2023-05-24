package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.MyGame
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.R

abstract class BaseLevel(var context: Context) {
    open var reward //奖励表: <概率,奖励物品id>
            : Map<Int, Int>? = null

    open var level = 17
    var winInfo = "战斗胜利！"
    var loseInfo = "战斗失败！"
    var fireToUnlock = 0
    private fun unLockFire(): Boolean {
        return false
    }

    open fun postReport(news: Int): Boolean {
        if (news == 999) {
            if (MyGame.user.level == level) {
                MyGame.user.level = level + 1
            }
        }
        return false
    }

    abstract fun initArmy()
    abstract fun getNextOrc(clock: Int): Enemy?
    open val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 50 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(1, 5),
                intArrayOf(2, 5)
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
        @JvmField
         var background = R.drawable.bg
        @JvmField
        var name = " "
        @JvmField
        var story = " "
    }
}