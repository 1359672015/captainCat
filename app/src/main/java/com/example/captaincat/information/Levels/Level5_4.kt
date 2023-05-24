package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.flyingObjects.Orcs.Orc8
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level5_4(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        for (j in 0..19) {
            reserveArmy[0].add(
                Enemy(
                    context,
                    -270 + ProbabilityUtil.getNumberLess(540),
                    -Config.SCREENHEI / 2 + 20 + ProbabilityUtil.getNumberLess(500),
                    -1,
                    Orc6(context),
                    1
                )
            )
        }
        reserveArmy[1].add(
            Enemy(
                context,
                -140,
                -Config.SCREENHEI / 2 + 300,
                1,
                Orc8(context),
                10000
            )
        )
        for (j in 0..69) {
            reserveArmy[1].add(
                Enemy(
                    context,
                    -270 + ProbabilityUtil.getNumberLess(540),
                    -Config.SCREENHEI / 2 + 20 + ProbabilityUtil.getNumberLess(500),
                    -1,
                    Orc4(context),
                    1
                )
            )
            reserveArmy[1].add(
                Enemy(
                    context,
                    -270 + ProbabilityUtil.getNumberLess(540),
                    -Config.SCREENHEI / 2 + 20 + ProbabilityUtil.getNumberLess(500),
                    -1,
                    Orc5(context),
                    1
                )
            )
        }
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 120 == 0) {
            if (reserveArmy[0].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1 && clock % 60 == 0) {
            if (reserveArmy[1].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 1800),
                intArrayOf(4, 60),
                intArrayOf(5, 60),
                intArrayOf(6, 8),
                intArrayOf(7, 15),
                intArrayOf(8, 4),
                intArrayOf(9, 3),
                intArrayOf(10, 1)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report == 20) index++
        return if (report > 10000) true else false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
   val name  = "五、银河之怒\n 第四节：终结"
        
        @JvmStatic
        val story: String
            get() = "他是奥克邪恶的灵魂，是这个可怕的生生不息的力量源泉。他就是冥古。" +
                    "\n杀死冥古，彻底粉碎奥克星的黑暗之心！"
    }

    init {
        initArmy()
        background = R.drawable.universe//.bgg
    }
}