package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level4_2(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 50 + ProbabilityUtil.getNumberLess(280)),
                intArrayOf(4, 10 + ProbabilityUtil.getNumberLess(15)),
                intArrayOf(5, 10 + ProbabilityUtil.getNumberLess(15)),
                intArrayOf(7, if (ProbabilityUtil.isToHappen(60)) 1 else 0),
                intArrayOf(8, if (ProbabilityUtil.isToHappen(30)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..39) {
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
            reserveArmy[0].add(
                Enemy(
                    context,
                    -200 + ProbabilityUtil.getNumberLess(400),
                    -Config.SCREENHEI / 2 + 100 + ProbabilityUtil.getNumberLess(500),
                    ProbabilityUtil.正负一(),
                    Orc5(context),
                    1
                )
            )
        }
        reserveArmy[0].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 120,
                -1,
                Orc2(context),
                999
            )
        )
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (clock >= 7700) report = 1000
        if (index == 0 && clock % 160 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        report += news
        return if (report > 999) {
         UserLevelsModel.unlockFire(4, context)
            true
        } else false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        @JvmStatic
        val name: String
            get() = "四、复仇之火\n 第二节：围剿"
        @JvmStatic
        val story: String
            get() = "对卡托的通缉在整个奥克星球传开。卡托在路上遭到了众多士兵的围剿，寡不敌众，如果可以，尽可能躲避并逃跑。\n" +
                    "胜利条件：成攻存活三分钟后，击杀任意敌人。"
    }

    init {
        super.level = 11
        initArmy()
        background = R.drawable.universe//.bgr
    }
}