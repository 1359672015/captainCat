package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc4
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.Utils.message.PostBoy
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.R
import com.example.captaincat.api.UserLevelsModel.Companion.setFireNum
import java.util.*

/*一场战役的信息*/
class Level4_3(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 200 + ProbabilityUtil.getNumberLess(100)),
                intArrayOf(4, 7 + ProbabilityUtil.getNumberLess(20)),
                intArrayOf(6, 1),
                intArrayOf(7, 3 + ProbabilityUtil.getNumberLess(3)),
                intArrayOf(8, if (ProbabilityUtil.isToHappen(60)) 1 else 0),
                intArrayOf(9, if (ProbabilityUtil.isToHappen(30)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }

    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[2] = LinkedList()
        for (i in 0..3) {
            for (j in 0..3) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc4(context),
                        1
                    )
                )
            }
        }
        for (i in 0..1) {
            for (j in 0..1) {
                reserveArmy[1].add(
                    Enemy(
                        context,
                        -200 + j * 170,
                        -Config.SCREENHEI / 2 + 20 + i * 120,
                        1,
                        Orc5(context),
                        1
                    )
                )
            }
        }
        reserveArmy[2].add(Enemy(context, 0, -Config.SCREENHEI / 2 + 240, -1, Orc6(context), 999))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 2 && reserveArmy[index].peek() != null) return reserveArmy[2].poll()
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        PostBoy.logD("now the report:$report")
        PostBoy.logD("now the index:$index")
        if (report == 16 || report == 20) {
            index++
            return false
        } else if (report > 999) {
            setFireNum(4, context)
            return true
        }
        return false
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
    const val name  = "四、复仇之火\n 第三节：过关斩将"

        @JvmStatic
        val story: String
            get() = "杀出重围之后要面对的，是紧追不舍的奥克将领。这次是天衣无缝的围堵，船长不得不和敌人决一死战。"
    }

    init {
        super.level = 12
        initArmy()
        background = R.drawable.universe//.bgb
    }
}