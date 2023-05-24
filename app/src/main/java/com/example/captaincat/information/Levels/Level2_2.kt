package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class  Level2_2(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[2] = LinkedList()
        reserveArmy[0].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 0, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 0, -Config.SCREENHEI / 2 + 120, -1, Orc2(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc3(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc3(context), 1))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 100 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1 && clock % 200 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 2 && reserveArmy[index].peek() != null) return reserveArmy[2].poll()
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        if (report == 4 || report == 10) {
            index++
        }
        return report == 12
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 65),
                intArrayOf(3, 4 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(4, 1 + ProbabilityUtil.getNumberLess(2))
            )
            return ListTurner.arrayToThings(a)
        }
    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        @JvmStatic
        val name = "二、众矢之的 \n第一节 通缉"
        @JvmStatic
        val story: String
            get() = "卡托的名字已经在外星人当中传开，成为了这些危险的家伙的眼中钉，看来卡托已经惹上大麻烦了。"
    }

    init {
        super.level = 4
        background = R.drawable.universe//.bgw
        initArmy()
    }
}