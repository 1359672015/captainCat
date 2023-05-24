package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.message.PostBoy
import java.util.*

/*一场战役的信息*/
class Level2_3(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[2] = LinkedList()
        reserveArmy[0].add(Enemy(context, -300, -Config.SCREENHEI / 2 + 110, 1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, -260, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, -220, -Config.SCREENHEI / 2 + 150, 1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, -180, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 300, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 260, -Config.SCREENHEI / 2 + 160, 1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 220, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 180, -Config.SCREENHEI / 2 + 190, 1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 130, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[2].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 150, -1, Orc3(context), 1))
        reserveArmy[2].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 120, 1, Orc3(context), 1))
        reserveArmy[2].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 150, -1, Orc3(context), 1))
        reserveArmy[2].add(Enemy(context, 300, -Config.SCREENHEI / 2 + 120, 1, Orc3(context), 1))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 30 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1 && clock % 50 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 2 && reserveArmy[index].peek() != null) return reserveArmy[2].poll()
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        PostBoy.logD("now the report:$report")
        PostBoy.logD("now the index:$index")
        if (report == 4 || report == 9) {
            index++
        }
        if (report == 13) {
         UserLevelsModel.unlockFire(2, context)
            return true
        }
        return false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 90 + ProbabilityUtil.getNumberLess(12)),
                intArrayOf(1, 3 + ProbabilityUtil.getNumberLess(4)),
                intArrayOf(2, 7 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(3, 4 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(4, 2 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(5, 1 + ProbabilityUtil.getNumberLess(2))
            )
            return ListTurner.arrayToThings(a)
        }


    companion object {
        @JvmStatic
        val name: String
            get() = "二、众矢之的\n第三节 决心"
        @JvmStatic
        val story: String
            get() = "卡托下定决心铲除的盘旋在故乡上空的敌机。不论他们有多少人，都不允许他们再祸害猫猫星的树木。" +
                    "\n本关卡将会解锁新技能"
    }

    init {
        super.level = 5
        background = R.drawable.universe//.bgc
        initArmy()
    }
}