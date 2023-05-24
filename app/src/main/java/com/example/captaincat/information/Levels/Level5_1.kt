package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level5_1(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..19) {
            reserveArmy[0].add(
                Enemy(
                    context,
                    -200 + ProbabilityUtil.getNumberLess(400),
                    -Config.SCREENHEI / 2 + 100 + ProbabilityUtil.getNumberLess(500),
                    ProbabilityUtil.正负一(),
                    Orc4(context),
                    1
                )
            )
        }
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 160,
                -1,
                Orc6(context),
                999
            )
        )
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (clock == 9000) report = 1000
        if (index == 0 && clock % 60 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 150 + ProbabilityUtil.getNumberLess(500)),
                intArrayOf(2, 2 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(3, 2 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(6, 1 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(8, 0 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(9, if (ProbabilityUtil.isToHappen(30)) 1 else 0),
                intArrayOf(10, if (ProbabilityUtil.isToHappen(5)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun postReport(news: Int): Boolean {
        report += news
        return if (report > 7000) true else false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        var name  = "五、银河之怒\n 第一节：刺客来也"


        @JvmStatic
        val story: String
            get() = "卡托杀死了奥克女王，敌人基地必将拉响警报。要想成功潜入敌人基地中心，必须把所有发现自己的奥克士兵将都消灭。"
    }

    init {
        super.level = 14
        background = R.drawable.universe//.bgg
        initArmy()
    }
}