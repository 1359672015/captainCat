package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc3(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 400+ProbabilityUtil.getNumberLess(200)
        killShoot["Pro"] = 40
        killShoot["Num"] = 3
        killShoot["ID"] = 2
        val normalShoot = arrayOf(
            intArrayOf(368 + ProbabilityUtil.getNumberLess(280), 2),
            intArrayOf(450 + ProbabilityUtil.getNumberLess(360), 3)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.id = 3
        super.look = R.layout.o3
        super.name = "残暴奥克兽人"
        super.introduce = "体型庞大，嗜血的奥克兽人，在喜欢大量食用绿色植物的同时，也完全不会拒绝把任何出现在它前面的外星动物吃掉。"
        super.favour = "由于视力不佳，智力较低，更喜欢近离目标更近地进行狂轰滥炸，难以稳定地驾驶飞船。"
        super.maxLife = 220
        super.addLife = 0
        super.back_Speed = -10
        super.speed = intArrayOf(5, 7)
        super.lowestPlace = 620
        super.highestPlace = 200
        setShoot()
    }
}