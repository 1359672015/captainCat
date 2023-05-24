package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.model.Thing
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.flyingObjects.Orcs.Orc5
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.Utils.message.PostBoy
import com.example.captaincat.flyingObjects.Orcs.Orc6
import com.example.captaincat.flyingObjects.Orcs.Orc7
import com.example.captaincat.R
import java.util.*

/*一场战役的信息*/
class Level4_4(context: Context?) : BaseLevel(context!!) {
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
        for (i in 0..3) {
            for (j in 0..3) {
                reserveArmy[0].add(
                    Enemy(
                        context,
                        -230 + j * 140,
                        -Config.SCREENHEI / 2 + 20 + i * 90,
                        -1,
                        Orc2(context),
                        1
                    )
                )
            }
        }
        for (j in 0..1) {
            reserveArmy[1].add(
                Enemy(
                    context,
                    -200 + j * 170,
                    -Config.SCREENHEI / 2 + 120,
                    1,
                    Orc6(context),
                    1
                )
            )
        }
        reserveArmy[2].add(Enemy(context, 0, -Config.SCREENHEI / 2 + 240, -1, Orc7(context), 999))
        for (i in 0..19) {
            reserveArmy[2].add(Enemy(context, 0, -Config.SCREENHEI / 2 + 240, -1, Orc5(context), 1))
        }
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 2 && clock % 150 == 1 && reserveArmy[index].peek() != null) return reserveArmy[2].poll()
        return null
    }

    override fun postReport(news: Int): Boolean {
        //根据兽人死伤情况,确认是否出队
        report += news
        PostBoy.logD("now the report:$report")
        PostBoy.logD("now the index:$index")
        if (report == 4 || report == 6) {
            index++
            return false
        } else if (report > 999) {
         UserLevelsModel.unlockFire(5, context)
            return true
        }
        return false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 600),
                intArrayOf(1, 20),
                intArrayOf(2, 20),
                intArrayOf(3, 20),
                intArrayOf(4, 20),
                intArrayOf(5, 20),
                intArrayOf(6, 1 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(7, 7),
                intArrayOf(8, 1),
                intArrayOf(9, if (ProbabilityUtil.isToHappen(60)) 1 else 0),
                intArrayOf(10, if (ProbabilityUtil.isToHappen(10)) 1 else 0)
            )
            return ListTurner.arrayToThings(a)
        }
    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
       var  name  = "四、复仇之火\n 第四节：强敌之怒"


        @JvmStatic
        val story: String
            get() = " 杀出重围的卡托船长，现在要面对的，是传说中的奥克女王。她是非常可怕的存在，令所有的空军闻风丧胆。\n对击杀了大量奥克人的卡托，奥克女王愤怒至极，发誓亲手宰了这位猫咪船长。" +
                    "\n胜利条件:击杀奥克女王。"
    }

    init {
        super.level = 13
        background = R.drawable.universe//.bgg
        initArmy()
    }
}