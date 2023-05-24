package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level5_2(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(1) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..4) {
            for (j in 0..5) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -270 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc4(context),
                        1
                    )
                )
            }
        }
        for (i in 0..2) {
            for (j in 0..5) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 460 + i * 90,
                        -1,
                        Orc5(context),
                        1
                    )
                )
            }
        }
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        return if (report == 48) true else false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 600 + ProbabilityUtil.getNumberLess(200)),
                intArrayOf(5, 10),
                intArrayOf(4, 10),
                intArrayOf(7, 2 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(8, 2),
                intArrayOf(9, if (ProbabilityUtil.isToHappen(70)) 1 else 0),
                intArrayOf(10, if (ProbabilityUtil.isToHappen(15)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }
    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        var name  = "五、银河之怒\n 第二节：万夫莫开"


        @JvmStatic
        val story: String
            get() = "暴露了行踪，又一次遭到了围剿。\n击杀所有敌人。"
    }

    init {
        super.level = 15
        initArmy()
        background = R.drawable.universe//.bg7
    }
}