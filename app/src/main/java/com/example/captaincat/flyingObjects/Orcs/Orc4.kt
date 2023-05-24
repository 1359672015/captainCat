package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.ProbabilityUtil

class Orc4(context: Context?) : Orc(context!!) {
    private fun setShoot() {
        val killShoot: MutableMap<String, Int> = HashMap()
        killShoot["Clock"] = 300+ProbabilityUtil.getNumberLess(200)
        killShoot["Pro"] = 40
        killShoot["Num"] = 3
        killShoot["ID"] = 3
        val normalShoot = arrayOf(
            intArrayOf(299 + ProbabilityUtil.getNumberLess(300), 3),
            intArrayOf(399 + ProbabilityUtil.getNumberLess(300), 4)
        )
        setShootTable(normalShoot, killShoot)
    }

    init {
        super.look = R.layout.o4
        super.id = 4
        super.back_Speed = -18
        //super.name="噩梦·奥克暗影";
        //super.introduce="神秘的奥克暗影，据说是远古的奥克星的亡灵所化。具有惊人的毁灭能力，如果看见他的影子，千万不要眨眼！";
        //super.favour="具有难以预测的运行轨迹，就好像根本打不中的鬼魂。他的炮火，被成为“空军的厄运”";
        super.name = "杀戮者·奥拉佐一"
        super.introduce = "奥拉佐一是舰队的统帅，常常带领数千名空军士兵和兽人去侵略其它的星球。他有个习惯: 绝不留活口。"
        super.favour = "行事谨慎的奥拉佐一不会轻易轻敌,更喜欢和对方战舰保持一定的距离。富有作战经验的他非常擅长躲避炮弹。"
        super.maxLife = 450
        super.speed = intArrayOf(7, 9)
        super.lowestPlace = 650 //距离下边框位置
        super.highestPlace = 380 //距离上边框位置
        setShoot()
    }
}