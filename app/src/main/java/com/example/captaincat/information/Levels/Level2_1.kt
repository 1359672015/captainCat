package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class Level2_1(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var index = 0
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[2] = LinkedList()
        reserveArmy[0].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 330, -1, Orc2(context), 1))
        reserveArmy[1].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 230, -1, Orc2(context), 1))
        reserveArmy[1].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 230, -1, Orc2(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc2(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc2(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc2(context), 1))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (reserveArmy[index].peek() != null) {
            if (index == 0) {
                return reserveArmy[index].poll()
            } else if (index == 1 && clock % 3 == 0) {
                return reserveArmy[index].poll()
            } else if (index == 2 && clock % 20 == 0) return reserveArmy[index].poll()
        }
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report == 1 || report == 3) {
            index++
        }
        return if (report == 6) {
         UserLevelsModel.unlockFire(1, context)
            true
        } else false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 15 + ProbabilityUtil.getNumberLess(6)),
                intArrayOf(1, 5 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(2, 1 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(3, 1 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(4, 1)
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
            get() = "二、众矢之的 \n第一节 更大的飞碟"
        @JvmStatic
        val story: String
            get() = "比起普通的敌人士兵，双人飞碟更加难以击败。卡托舰长必须加强攻势，突破敌人的防线。"
    }

    init {
        super.level = 3
        background = R.drawable.universe//.bgf
        initArmy()
    }
}