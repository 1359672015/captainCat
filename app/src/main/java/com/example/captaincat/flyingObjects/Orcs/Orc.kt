package com.example.captaincat.flyingObjects.Orcs

import android.content.Context
import com.example.captaincat.R
import com.example.captaincat.Utils.InterfaceLoadFire
import com.example.captaincat.Utils.ProbabilityUtil
import com.example.captaincat.flyingObjects.Enemies.Enemy
import java.util.*

/**
 * 敌机的实体模型
 * 包含：属性，简介，图片
 * 以及: 射击习惯 + 移动方式
 */
open class Orc(var context: Context) {
    //id
    var id = 0
    //生命值
    var life = 0
    //最大生命值
    var maxLife = 0
    //名称
    var name: String = ""
    //介绍
    var introduce: String = ""
    //战斗特点
    var favour: String = ""
    //速度区间
    var speed: IntArray = intArrayOf(0,100)
    //返回时速度
    var back_Speed = -10
    //变速周期
    var changeSpeedTime = 60
    //变速结果
    var changeSpeed = 1
    //一次回血
    var addLife = 0
    //射击表:子弹id和射击周期
    var random = Random()
    var lowestPlace = 620 //距离下边框位置
    var highestPlace = 200 //距离上边框位置

    //触发连击之后,每个周期会消耗一发
    var waitForShoot = 0
    var loadFire: InterfaceLoadFire? = null
    var killShoot: Map<String, Int> = HashMap()
    var normalShoot: Array<IntArray>?= null
    fun setShootTable(normalShoot: Array<IntArray>, killShoot: Map<String, Int>) {
        this.normalShoot = normalShoot
        this.killShoot = killShoot
        life = maxLife
    }

    fun loadFire(enemy: Enemy, clock: Int) {
        if (waitForShoot > 0) {
            // enemy.loadFire(OrcTables.getOrcFire(context, enemy, killShoot.get("ID")));
            enemy.loadFire(killShoot["ID"]!!)
            waitForShoot--
            return  //连击过程,暂停普通上膛
        } else if (clock % killShoot["Clock"]!! == 0) {
            if (ProbabilityUtil.isToHappen(killShoot["Pro"]!!)) //触发连击,接下来num次再loadFire时都会成功制造;
                waitForShoot = killShoot["Num"]!!
        }
        for (i in normalShoot!!.indices) {
            if (clock % normalShoot!![i][0] == 0) // enemy.loadFire(OrcTables.getOrcFire(context, enemy, normalShoot[i][1]));
                enemy.loadFire(normalShoot!![i][1])
        }
    }

     var look: Int =R.drawable.orc_orc

    fun getChangeSpeed(clock: Int): Int {
        //从二元数组所圈定的区间当中随机选取一个整数作为速度
        if (clock % changeSpeedTime == 0) {
            changeSpeed = random.nextInt(speed[1] - speed[0]) + speed[0]
        }
        return changeSpeed
    }

    fun addLife() {
        if (life < maxLife - addLife) life += addLife else life = maxLife
    }

    fun getHurt(hurt: Int) {
        life -= hurt
    }

}