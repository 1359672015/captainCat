package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.api.UserLevelsModel.Companion.setFireNum
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class Level3_2(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        for (i in 0..7) {
            reserveArmy[0].add(
                Enemy(
                    context,
                    -200,
                    -Config.SCREENHEI / 2 + 120,
                    -1,
                    Orc4(context),
                    1
                )
            )
        }
        for (i in 0..3) reserveArmy[1].add(
            Enemy(
                context,
                -200 + i * 15,
                -Config.SCREENHEI / 2 + 120,
                -1,
                Orc4(context),
                1
            )
        )
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 320 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report == 8) {
            index++
        } else if (report == 12) {
            setFireNum(3, context)
            return true
        }
        return false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 140 + ProbabilityUtil.getNumberLess(12)),
                intArrayOf(3, 5 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(4, 6 + ProbabilityUtil.getNumberLess(8)),
                intArrayOf(5, 6 + ProbabilityUtil.getNumberLess(8)),
                intArrayOf(6, if (ProbabilityUtil.isToHappen(25)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }
    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        @JvmStatic
        val name: String
            get() = "三、坚韧不催 \n第二节 杀出重围 "
        @JvmStatic
        val story: String
            get() = "胜利条件：打败所有敌机，获得胜利。"
    }

    init {
        super.level = 7
        initArmy()
        background = R.drawable.universe//.bgw
    }
}