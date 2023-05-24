package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.information.Config.Config
import com.example.captaincat.model.Thing
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc1
import com.example.captaincat.R
import com.example.captaincat.Utils.ListTurner
import com.example.captaincat.Utils.ProbabilityUtil
import java.util.*

/*一场战役的信息*/
class Level1_1(context: Context?) : BaseLevel(context!!) {
    var reserveArmy  = Array<LinkedList<Enemy>>(2) { _ -> LinkedList() }
    var index = 0 //队伍数目
    var report = 0

    /** 加载敌军队伍  */
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[1] = LinkedList()
        reserveArmy[0].add(
            Enemy(
                context, -200, -Config.SCREENHEI / 2 + 120, -1,
                Orc1(context), 1
            )
        )
        reserveArmy[1].add(
            Enemy(
                context, -200, -Config.SCREENHEI / 2 + 120, 1,
                Orc1(context), 99
            )
        )
    }

    /**
     * 有敌人在准备队列,则出队.取余20是为了延时避免一次性全部弹出。
     * index代表目前在第几条队伍
     * 一条队伍出完之后必须准确得到判断。
     */
    override fun getNextOrc(clock: Int): Enemy? {
        if (index == 0 && clock % 10 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        } else if (index == 1 && clock % 10 == 0) {
            if (reserveArmy[index].peek() != null) return reserveArmy[index].poll()
        }
        return null
    }

    /**可以判断已经死了多少敌人，以此方式来让队伍下标增加
     * 如：确保已经前两条队伍的敌人都死了，则report达到了8，则可以队伍下标++ */
    override fun postReport(news: Int): Boolean {
        //根据敌人死伤情况,确认是否出队
        report += news
        reserveArmy[index].clear()
        index++
        return report == 100
    }

    override val rewards: List<Thing>
        get() {
            val a = arrayOf(
                intArrayOf(0, 15 + ProbabilityUtil.getNumberLess(10)),
                intArrayOf(2, 1 + ProbabilityUtil.getNumberLess(2))
            )
            return ListTurner.arrayToThings(a)
        }

    companion object {
        @JvmStatic
        val name: String
            get() = "一、猫咪舰长\n 第一节 舰长归来"
        @JvmStatic
        val story: String
            get() = ("邪恶的外星人——奥克人乘坐着巨大的太空母舰降临到猫猫星球上空，试图掠夺猫猫星球上美丽的绿色植物。\n" +
                    "勇敢的喵星人组建起了伟大的反侵略战争。战争的序幕就此拉开。\n" +
                    "可不料敌人的舰队坚韧不催。一年后，猫猫军团伤亡惨重，大规模战役已经不再有希望。\n" +
                    "但坚强的猫猫星人并不会被轻易击溃，反抗的战争并不会因此结束。来自不同地方的猫猫英雄们纷纷驾驶着自己的太空战舰，与敌人展开战斗。\n" +
                    "卡托是一位伟大的猫咪舰长，而朱利安是卡托的得力侦察官、助手。\n" +
                    "你需要在游戏中扮演卡托船长，驾驶飞船奋力反抗奥克的强大舰队……\n" +
                    "点击控制台上的攻击按钮，攻击外星人的飞碟，击败所有敌人，赢得战斗的胜利。").trimIndent()
    }

    init {
        super.level = 1
       background = R.drawable.universe//.bgr
        initArmy()
    }
}