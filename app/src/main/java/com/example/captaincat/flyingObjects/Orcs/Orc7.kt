package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc7(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        killShoot["Clock"] = 200+ProbabilityUtil.getNumberLess(200)
        killShoot["Pro"] = 50
        killShoot["Num"] = 12
        killShoot["ID"] = 6
        val normalShoot = arrayOf(
            intArrayOf(20 + ProbabilityUtil.getNumberLess(190), 4),
            intArrayOf(172 + ProbabilityUtil.getNumberLess(230), 6),
            intArrayOf(281 + ProbabilityUtil.getNumberLess(230), 8)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o7
        super.id = 7
        super.name = "奥克女皇·古尔丝"
        super.introduce = "她，就是奥克星最至高无上的女王，掌管着那个星球上的一切。如果能葬身于她的炮火，你绝对已经算是不凡的英雄了。战胜她？" +
                "“离她越远越好。”———这是幸存的银河战士给你的建议。"
        super.favour = "她会给你你绝不想遭遇到的可怕攻击。如果你不幸被生气的古尔丝瞄准，其实已经没有必要躲避了。"
        super.maxLife = 7500
        super.addLife = 5
        super.back_Speed = -31
        super.speed = intArrayOf(10, 14)
        super.lowestPlace = 700 //距离下边框位置
        super.highestPlace = 200 //距离上边框位置
        setShoot()
    }
}