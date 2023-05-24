package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.message.PostBoy
import java.util.*

/*一场战役的信息*/
class Level3_1(context: Context?) : BaseLevel(context!!) {
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
        reserveArmy[0].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, -100, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 200, -Config.SCREENHEI / 2 + 120, -1, Orc1(context), 1))
        for (i in 0..2) reserveArmy[1].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 120,
                -1,
                Orc4(context),
                1
            )
        )
        for (i in 0..2) reserveArmy[2].add(
            Enemy(
                context,
                -100,
                -Config.SCREENHEI / 2 + 120,
                -1,
                Orc4(context),
                100
            )
        )
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (reserveArmy[index].peek() != null) if (index == 0) {
            return reserveArmy[index].poll()
        } else if (index == 1 && clock % 80 == 0) {
            return reserveArmy[index].poll()
        } else if (index == 2) return reserveArmy[2].poll()
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        PostBoy.logD("now the report:$report")
        PostBoy.logD("now the index:$index")
        if (report == 4 || report == 7) {
            index++
        } else if (report > 300) {
            return true
        }
        return false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 100 + ProbabilityUtil.getNumberLess(32)),
                intArrayOf(3, 6 + ProbabilityUtil.getNumberLess(4)),
                intArrayOf(4, 3 + ProbabilityUtil.getNumberLess(4)),
                intArrayOf(5, 3 + ProbabilityUtil.getNumberLess(4))
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
            get() = "三、坚韧不催 \n第一节 潜行"
        @JvmStatic
        val story: String
            get() = "卡托和朱利安已经进入敌人基地的外围，根据朱利安的侦察，基地外围有重兵把守。为了保证行踪不被发现，必须速战速决解决掉这些边境的士兵。"
    }

    init {
        super.level = 6
        background = R.drawable.universe//.bga
        initArmy()
    }
}