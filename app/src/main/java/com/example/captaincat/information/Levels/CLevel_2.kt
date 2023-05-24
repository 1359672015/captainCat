package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.MyApplication
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.model.Thing
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import java.util.*

/*一场战役的信息*/
class CLevel_2(context: Context?) : BaseLevel(context!!) {
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
                        MyApplication.appContext,
                        -270 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc2(
                            MyApplication.appContext
                        ),
                        1
                    )
                )
            }
        }
        for (i in 0..2) {
            for (j in 0..5) {
                reserveArmy[0].add(
                    Enemy(
                        MyApplication.appContext,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 460 + i * 90,
                        -1,
                        Orc4(
                            MyApplication.appContext
                        ),
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
        return report >= 48
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 90 + ProbabilityUtil.getNumberLess(100)),
                intArrayOf(1, 20 + ProbabilityUtil.getNumberLess(30)),
                intArrayOf(3, 10 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(4, 5 + ProbabilityUtil.getNumberLess(5)),
                intArrayOf(6, 1)
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
        @JvmStatic
        val name: String
            get() = " [一般] 一般难度"
        @JvmStatic
        val story: String
            get() = "一般级别难度。"
    }

    init {
        initArmy()
        background = R.drawable.universe//.bgf
    }
}