package com.example.captaincat.flyingObjects.Mine

import android.content.Context
import android.util.Log
import com.example.captaincat.MyGame
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Tables.MyTables

/** 业务逻辑:
 * 基础补给表(补给/初始值于炮火等级的关系)
 */
class FirePackage(var context: Context, chosenFires: String, var meFighter: MeFighter) {
    var fireIds: IntArray

    //补给: 每个时间单位增加的炮火数，下标顺序递增
    var fireSupply = DoubleArray(6)

    //每种炮火数量值，下标顺序递增
    var fireNum = DoubleArray(6)
    var fireSpeed = IntArray(6)
    var firePower = IntArray(6)
    var fireLayout = MyTables.getFireLayout()!!
    var chosenFires = "012"

    /**
     * 点击控制台中第i个炮弹,获取实际炮弹的数量是否大于 1。
     */
    fun haveFire(i: Int): Boolean {
        var i = i
        i = fireIds[i]
        return fireNum[i] >= 1
    }

    /*形参i表示按了第几个炮火的按钮*/
    fun getFire(i: Int): MyFire {
        //将在控制台中的位置转换为在实际炮火表中的位置
        var i = i
        i = fireIds[i]
        fireNum[i]--
        val myFire = MyFire(context, meFighter)
        myFire.setInfo(firePower[i], fireSpeed[i], fireLayout[i])
        return myFire
    }

    /**
     * @炮火补给
     */
    fun makeFire(divide: Double) {
        for (i in fireIds.indices) {
            fireNum[fireIds[i]] += fireSupply[fireIds[i]] / divide
            if(fireNum[fireIds[i]]>Config.maxFireNum)fireNum[fireIds[i]] = Config.maxFireNum
            Log.i("补给", (fireSupply[fireIds[i]] / divide).toString() )
        }
    }

    /**
     * 炮弹数量
     */
    fun getFireNum(i: Int): Double {
        var i = i
        i = fireIds[i]
        return fireNum[i]
    }

    init {
        this.chosenFires = chosenFires
        fireIds = IntArray(chosenFires.length)
        for (i in fireIds.indices) fireIds[i] = chosenFires[i].code - 48
        //为炮火数量赋初始值
        for (i in 0..5) fireNum[i] = MyGame.user.getFireFirst(i)
        //炮火每周期加成
        for (i in 0..5) fireSupply[i] = MyGame.user.getFireSupply(i)
        for (i in 0..5) fireSpeed[i] = MyGame.user.getFireSpeed(i)
        for (i in 0..5) firePower[i] = MyGame.user.getFirePower(i)
    }
}