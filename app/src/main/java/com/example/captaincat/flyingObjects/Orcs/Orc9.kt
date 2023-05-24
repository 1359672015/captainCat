package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc9(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 395
        killShoot["Pro"] = 80
        killShoot["Num"] = 35
        killShoot["ID"] = 8
        val normalShoot = arrayOf(
            intArrayOf(205 + ProbabilityUtil.getNumberLess(160), 6),
            intArrayOf(130 + ProbabilityUtil.getNumberLess(130), 7),
            intArrayOf(274 + ProbabilityUtil.getNumberLess(190), 8),
            intArrayOf(331 + ProbabilityUtil.getNumberLess(230), 9)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o9
        super.id = 9
        super.maxLife = 100000
        super.addLife = 30
        super.back_Speed = -20
        super.name = "死灵冥古"
        super.introduce = "死灵冥古，强势归来"
        super.favour = "无法战胜。"
        super.speed = intArrayOf(-5, 30)
        super.lowestPlace = 620 //距离下边框位置
        super.highestPlace = 140 //距离上边框位置
        setShoot()
    }
}