package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.flyingObjects.Orcs.Orc7
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level5_3(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..3) {
            for (j in 0..3) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -270 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc6(context),
                        1
                    )
                )
            }
        }
        reserveArmy[0].add(Enemy(context, 140, -Config.SCREENHEI / 2 + 300, -1, Orc7(context), 99))
        reserveArmy[0].add(Enemy(context, -140, -Config.SCREENHEI / 2 + 300, 1, Orc7(context), 99))
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
        return if (report >= 200) true else false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 880 + ProbabilityUtil.getNumberLess(80)),
                intArrayOf(2, 50),
                intArrayOf(6, 2),
                intArrayOf(7, 5 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(8, 4),
                intArrayOf(9, 1),
                intArrayOf(10, if (ProbabilityUtil.isToHappen(20)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }
    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {

        val name = "五、银河之怒\n 第三节：斩尽春风不肯归"
        @JvmStatic
        val story: String
            get() = "冲过基地，击垮所有敌人！"
    }

    init {
        super.level = 16
        background = R.drawable.universe//.bgb
        initArmy()
    }
}