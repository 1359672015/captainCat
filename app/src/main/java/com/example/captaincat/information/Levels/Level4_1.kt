package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.Utils.message.PostBoy
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level4_1(context: Context?) : BaseLevel(context!!) {
    var name = ""
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[2] = LinkedList()
        for (i in 0..19) reserveArmy[0].add(
            Enemy(
                context,
                -200 + ProbabilityUtil.getNumberLess(400),
                -Config.SCREENHEI / 2 + ProbabilityUtil.getNumberLess(300),
                ProbabilityUtil.正负一(),
                Orc3(context),
                1
            )
        )
        for (i in 0..9) reserveArmy[1].add(
            Enemy(
                context,
                -200 + ProbabilityUtil.getNumberLess(400),
                -Config.SCREENHEI / 2 + ProbabilityUtil.getNumberLess(300),
                ProbabilityUtil.正负一(),
                Orc4(context),
                1
            )
        )
        reserveArmy[2].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 120,
                -1,
                Orc5(context),
                999
            )
        )
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (reserveArmy[index].peek() != null) {
            if (index == 0) {
                return reserveArmy[index].poll()
            } else if (index == 1 && clock % 5 == 0) {
                return reserveArmy[index].poll()
            } else if (index == 2) return reserveArmy[2].poll()
        }
        return null
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 170 + ProbabilityUtil.getNumberLess(50)),
                intArrayOf(1, 7 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(2, 5 + ProbabilityUtil.getNumberLess(25)),
                intArrayOf(5, 8 + ProbabilityUtil.getNumberLess(15)),
                intArrayOf(6, if (ProbabilityUtil.isToHappen(50)) 1 else 0),
                intArrayOf(7, if (ProbabilityUtil.isToHappen(39)) 1 else 0),
                intArrayOf(8, if (ProbabilityUtil.isToHappen(7)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        PostBoy.logD("now the report:$report")
        PostBoy.logD("now the index:$index")
        if (report == 20 || report == 30) {
            index++
        } else if (report > 100) return true
        return false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
   val name  = "四、复仇之火!\n第一节：入关"
        
        @JvmStatic
        val story: String
            get() = "请继续前行，击败所有敌人"
    }

    init {
        super.level = 10
        initArmy()
        background = R.drawable.universe//.bg6
    }
}