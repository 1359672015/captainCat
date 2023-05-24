package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc5(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 300+ProbabilityUtil.getNumberLess(300)
        killShoot["Pro"] = 20
        killShoot["Num"] = 4
        killShoot["ID"] = 4
        val normalShoot = arrayOf(
            intArrayOf(267 + ProbabilityUtil.getNumberLess(300), 4),
            intArrayOf(345 + ProbabilityUtil.getNumberLess(300), 5)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o5
        super.id = 5
        super.name = "噩梦·奥克暗影"
        super.introduce = "神秘的奥克暗影，据说是远古的奥克星的亡灵所化。具有惊人的毁灭能力，如果看见他的影子，千万不要眨眼！"
        super.favour = "具有难以预测的运行轨迹，就好像根本打不中的鬼魂。他的炮火，被成为“空军的厄运”"
        super.maxLife = 700
        super.speed = intArrayOf(15, 21)
        super.back_Speed = -35
        super.lowestPlace = 660 //距离下边框位置
        super.highestPlace = 180 //距离上边框位置
        setShoot()
    }
}