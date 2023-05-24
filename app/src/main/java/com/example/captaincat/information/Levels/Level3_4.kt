package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level3_4(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..2) {
            for (j in 0..2) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -230 + j * 160,
                        -Config.SCREENHEI / 2 + 20 + i * 99,
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

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 180 + ProbabilityUtil.getNumberLess(44)),
                intArrayOf(3, 7 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(4, 10 + ProbabilityUtil.getNumberLess(15)),
                intArrayOf(5, 10 + ProbabilityUtil.getNumberLess(15)),
                intArrayOf(6, if (ProbabilityUtil.isToHappen(40)) 1 else 0),
                intArrayOf(7, if (ProbabilityUtil.isToHappen(18)) 1 else 0),
                intArrayOf(8, if (ProbabilityUtil.isToHappen(4)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        return if (report == 9) true else false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        @JvmStatic
        val name: String
            get() = "三、坚韧不催 \n第四节:黑夜"
        @JvmStatic
        val story: String
            get() = "击碎了梦境，却发现更可怕的现实正在眼前。九个奥克暗影一齐出现在眼前，可怕的噩梦似乎就要拉开序幕。" +
                    "\n胜利条件: 击败所有敌人。"
    }

    init {
        super.level = 9
        background = R.drawable.universe//.bgw
        initArmy()
    }
}