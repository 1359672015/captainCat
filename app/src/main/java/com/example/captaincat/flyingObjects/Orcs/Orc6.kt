package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc6(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        killShoot["Clock"] = 390+ProbabilityUtil.getNumberLess(200)
        killShoot["Pro"] = 50
        killShoot["Num"] = 5
        killShoot["ID"] = 5
        val normalShoot = arrayOf(
            intArrayOf(30 + ProbabilityUtil.getNumberLess(400), 2),
            intArrayOf(142 + ProbabilityUtil.getNumberLess(420), 5),
            intArrayOf(302 + ProbabilityUtil.getNumberLess(420), 6),
            intArrayOf(471 + ProbabilityUtil.getNumberLess(600), 7)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o6
        super.id = 6
        //super.name="奥克将领";
        //super.introduce="一个奥克营队的领导者，指挥着军队南征北战，自身更是勇猛善战。\n必杀技: 飞枪 , 投石";
        //super.favour="狡猾奸诈,极难被打败。有一定的回血能力。";
        super.name = "嗜血·卡巫"
        super.introduce = "卡巫，传说中的奥克不死女巫。残暴的变异兽人就是她所制造。在奥克，女巫的地位非常高。"
        super.favour = "她所召唤的紫光烈焰更让人闻风丧胆，不少星球的最后的保卫者就因不敌卡巫而败给了奥克。"
        super.maxLife = 2000
        super.addLife = 3
        super.back_Speed = -30
        super.speed = intArrayOf(6, 17)
        super.lowestPlace = 660 //距离下边框位置
        super.highestPlace = 150 //距离上边框位置
        setShoot()
    }
}