package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class CLevel_1(context: Context?) : BaseLevel(context!!) {
    override var level = 0
    var reserveArmy = Array<LinkedList<Enemy>>(1) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        for (i in 0..3) {
            for (j in 0..5) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -270 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc3(context),
                        1
                    )
                )
            }
        }
        for (i in 0..1) {
            for (j in 0..5) {
                reserveArmy[0]!!.add(
                    Enemy(
                        context,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 460 + i * 90,
                        -1,
                        Orc1(context),
                        1
                    )
                )
            }
        }
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0) {
            if (reserveArmy[index]!!.peek() != null) return reserveArmy[index]!!.poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        return report == 36
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 100 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(2, 10),
                intArrayOf(3, 10),
                intArrayOf(5, 5 + ProbabilityUtil.getNumberLess(3))
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
        val name = " [简单] 简单难度"


        val story: String
            get() = "简单难度"
    }

    init {
        initArmy()
       background = R.drawable.universe//.bgb
    }
}