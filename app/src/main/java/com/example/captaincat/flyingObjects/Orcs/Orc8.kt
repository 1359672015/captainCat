package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc8(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 395
        killShoot["Pro"] = 70
        killShoot["Num"] = 15
        killShoot["ID"] = 8
        val normalShoot = arrayOf(
            intArrayOf(15 + ProbabilityUtil.getNumberLess(65), 5),
            intArrayOf(130 + ProbabilityUtil.getNumberLess(130), 4),
            intArrayOf(205 + ProbabilityUtil.getNumberLess(160), 6),
            intArrayOf(274 + ProbabilityUtil.getNumberLess(190), 8),
            intArrayOf(331 + ProbabilityUtil.getNumberLess(230), 9)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o8
        super.id = 8
        super.maxLife = 36000
        super.addLife = 10
        super.back_Speed = -40
        //super.name="奥克单于";
        //super.introduce="奥克国的国王，好战、好掠夺，梦想着称霸中原以及中亚、西亚地区。企图等待时机攻下大唐。";
        //super.favour="身为奥克单于，已经身经百战，知道怎么指挥大军，也完全有能力解决闯入的刺客。\n必杀技:群狼之咒,奥克之怒,";
        super.name = "冥古"
        super.introduce = "冥古，是奥克的恶灵。是奥克文明的邪恶之魂，引导奥克奥克去奴役银河系。" +
                "在古尔丝之魂彻底灭亡之后，冥古的灵魂被暗影卡巫释放出来，作为奥克最后的力量。" +
                "只有击败冥古，才能真正打败邪恶的奥克。"
        super.favour = "尽你所能吧。"
        super.speed = intArrayOf(9, 16)
        super.lowestPlace = 620 //距离下边框位置
        super.highestPlace = 140 //距离上边框位置
        setShoot()
    }
}