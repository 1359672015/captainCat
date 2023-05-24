package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil.getNumberLess

class Orc1(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        //每300个clock有60%的概率10连击4号导弹.
        killShoot["Clock"] = 280+ getNumberLess(180)
        killShoot["Pro"] = 20
        killShoot["Num"] = 1
        killShoot["ID"] = 1
        val normalShoot = arrayOf(intArrayOf(245 + getNumberLess(100), 1))
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.id = 1
        super.back_Speed = -1
        super.look = R.layout.o1
        super.id = 1
        super.name = "奥克士兵"
        super.introduce = "服从于领者命令的奥克星士兵,梦想可以击退被侵略星球的保卫者,成为军队的领导者。"
        super.favour = "一心只想往前冲，奋力发射石块，攻击一切阻挡他们前进的飞船！"
        super.maxLife = 90
        super.speed = intArrayOf(3, 4)
        super.lowestPlace = 620 //距离下边框位置
        super.highestPlace = 500 //距离上边框位置
        setShoot()
    }
}