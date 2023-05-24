package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class Level3_3(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        for (i in 0..3) {
            for (j in 0..3) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -200 + j * 130,
                        -Config.SCREENHEI / 2 + 20 + i * 80,
                        -1,
                        Orc4(context),
                        1
                    )
                )
            }
        }
        reserveArmy[1].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 120, -1, Orc5(context), 100))
        reserveArmy[1].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 120, 1, Orc5(context), 100))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report == 16) {
            index++
        } else if (report >= 100) {
         UserLevelsModel.unlockFire(3, context)
            return true
        }
        return false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 160 + ProbabilityUtil.getNumberLess(22)),
                intArrayOf(3, 7 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(5, 10 + ProbabilityUtil.getNumberLess(11)),
                intArrayOf(6, if (ProbabilityUtil.isToHappen(35)) 1 else 0),
                intArrayOf(7, if (ProbabilityUtil.isToHappen(8)) 1 else 0)
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
            get() = "三、坚韧不催 \n第三节 梦境"
        @JvmStatic
        val story: String
            get() = "闯入母舰边界的卡托船长似乎已被敏锐的奥克士兵发现。边界内存在着强大的魔力，使得卡托头晕目眩。" +
                    "在这奇怪的幻境，有可怕的奥克暗影出现，如鬼魅一般闪现飘忽，难以击中。" +
                    "\n胜利条件: 杀死一个奥克暗影，击碎梦境，获得胜利。"
    }

    init {
        super.level = 8
        background = R.drawable.universe//.bgb
        initArmy()
    }
}