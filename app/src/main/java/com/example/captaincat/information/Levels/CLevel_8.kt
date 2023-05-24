package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc10
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import java.util.*

/*一场战役的信息*/
class CLevel_8(context: Context?) : BaseLevel(context!!) {
    override var level = 0
    var reserveArmy = Array<LinkedList<Enemy>>(1) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()

            reserveArmy[0].add(
                Enemy(
                    context,
                    -270 +   140,
                    -Config.SCREENHEI / 2 + 20,
                    -1,
                    Orc10(context),
                    99
                )
            )

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
        return report == 99
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0,5200 + ProbabilityUtil.getNumberLess(1600)),
                intArrayOf(5, 5 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(6, 1 + ProbabilityUtil.getNumberLess(5)),
                intArrayOf(7, 2 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(8, 1 + ProbabilityUtil.getNumberLess(4)),
                intArrayOf(9, 2 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(10, 1)
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
   val name  = " [人生plus] 人生难度plus"
        
        @JvmStatic
        val story: String
            get() = "人生级难度plus,推荐等级:暂无"
    }

    init {
        initArmy()
    }
}