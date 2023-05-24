package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc10(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 315
        killShoot["Pro"] = 80
        killShoot["Num"] = 65
        killShoot["ID"] = 9
        val normalShoot = arrayOf(
            intArrayOf(205 + ProbabilityUtil.getNumberLess(160), 7),
            intArrayOf(130 + ProbabilityUtil.getNumberLess(130), 8),
            intArrayOf(274 + ProbabilityUtil.getNumberLess(190), 9),
            intArrayOf(331 + ProbabilityUtil.getNumberLess(230), 9)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o10
        super.id = 9
        super.maxLife = 1000000
        super.addLife = 50
        super.back_Speed = -15
        super.name = "死灵冥古2"
        super.introduce = "假扮成普通士兵的死灵冥古2，强势归来"
        super.favour = "不可轻易挑战"
        super.speed = intArrayOf(-5, 20)
        super.lowestPlace = 620 //距离下边框位置
        super.highestPlace = 140 //距离上边框位置
        setShoot()
    }
}