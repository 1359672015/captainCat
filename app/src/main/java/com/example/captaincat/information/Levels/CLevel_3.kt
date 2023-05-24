package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config

import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class CLevel_3(context: Context?) : BaseLevel(context!!) {
    override var level = 0
    var reserveArmy  = Array<LinkedList<Enemy>>(1) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        for (i in 0..1) {
            for (j in 0..5) {
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
        for (i in 0..3) {
            for (j in 0..5) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 460 + i * 90,
                        -1,
                        Orc3(context),
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
                intArrayOf(0, 200 + ProbabilityUtil.getNumberLess(100)),
                intArrayOf(2, 10),
                intArrayOf(5, 10),
                intArrayOf(6, 1),
                intArrayOf(7, 1 + ProbabilityUtil.getNumberLess(4))
            )
            return ListTurner.arrayToThings(a)
        }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report >= 36) {
         UserLevelsModel.unlockFire(3, context)
            return true
        }
        return false
    }

    companion object {
   val name  = " [困难] 困难难度"
        
        @JvmStatic
        val story: String
            get() = "困难级难度,推荐等级:13级"
    }

    init {
        initArmy()
        background = R.drawable.universe//.bga
    }
}