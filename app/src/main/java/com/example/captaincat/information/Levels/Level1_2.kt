package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.flyingObjects.Orcs.Orc3
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class Level1_2(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var index = 0 //队伍数目
    var report = 0

    /** 加载敌军队伍  */
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[0].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 500, -1, Orc1(context), 1))
        reserveArmy[0].add(Enemy(context, 100, -Config.SCREENHEI / 2 + 520, 1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 10, -Config.SCREENHEI / 2 + 220, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, 10, -Config.SCREENHEI / 2 + 220, -1, Orc1(context), 1))
        reserveArmy[1].add(Enemy(context, -50, -Config.SCREENHEI / 2 + 220, 1, Orc3(context), 1))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 20 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1 && clock % 50 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    /**可以判断已经死了多少敌人，以此方式来让队伍下标增加
     * 如：确保已经前两条队伍的敌人都死了，则report达到了8，则可以队伍下标++ */
    override fun postReport(news: Int): Boolean {
        super.postReport(news)
        //根据敌人死伤情况,确认是否出队
        report += news
        if (report == 2) {
            reserveArmy[index].clear()
            index++
        }
        return if (report == 5) true else false
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 30 + ProbabilityUtil.getNumberLess(8)),
                intArrayOf(1, 1 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(2, 1 + ProbabilityUtil.getNumberLess(2)),
                intArrayOf(3, 1 + ProbabilityUtil.getNumberLess(2))
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
        @JvmStatic
        val name: String
            get() = "一、猫咪舰长\n 第二节 勇敢"
        @JvmStatic
        val story: String
            get() = "几艘外星士兵的飞碟被击垮。这个消息很快会传开——奥克头目会下令活捉敢反抗敌人的舰长。" +
                    "接下来找上来的敌人还会越来越多。"
    }

    init {
        super.level = 2
        initArmy()
        background = R.drawable.universe//.universe
    }
}