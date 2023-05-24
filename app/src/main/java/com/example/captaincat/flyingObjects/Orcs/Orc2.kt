package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc2(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 350 + ProbabilityUtil.getNumberLess(180)
        killShoot["Pro"] = 50
        killShoot["Num"] = 3
        killShoot["ID"] = 2
        val normalShoot =
            arrayOf(intArrayOf(190 + ProbabilityUtil.getNumberLess(100), 2)) //102个clock放一个1号炮火
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o2
        super.id = 2
        super.back_Speed = -14
        super.name = "双兵战舰"
        super.introduce = "由两个奥克士兵驾驶同一艘战舰，作战能力更强。"
        super.favour = "喜欢协作掠夺其它星球上的宝贵资源，十分贪婪，尤其喜欢连根带走其它星球上的大型植物。"
        super.maxLife = 160
        super.speed = intArrayOf(6, 14)
        super.lowestPlace = 620 //距离下边框位置
        super.highestPlace = 400 //距离上边框位置
        setShoot()
    }
}