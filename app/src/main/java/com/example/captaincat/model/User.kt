package com.example.captaincat.model

import com.example.captaincat.information.Tables.MyTables
import com.example.captaincat.event.WarParams

class User {
    var id //主键
            = 0
    var name //昵称
            : String =  ""
    var introduce //个签
            : String =  ""
    var firesLevel //六位数,每种炮火的等级水平
            : String = ""
    var email //邮箱
            : String =  ""
    var registerDate //注册时间
            : String =  ""
    var account //账号
            : String =  ""
    var password //密码
            : String =  ""
    var coins //金币数量
            = 0
    var level //表示目前还未通过的level数(第level关还没过)
            = 0
    var ownFireNum //拥有炮火数量
            = 0
    var speedLevel //速度等级
            = 0
    var lifeLevel = 19 //生命值等级
    var shipType = 0 //飞船种类
    var catType = 0 //猫咪角色类型
    var petId = 0 //宠物id
    fun setFightInfo(
        ownFireNum: String,
        firesLevel: String,
        bloodLevel: String,
        speedLevel: String
    ) {
        this.firesLevel = firesLevel
        this.ownFireNum = ownFireNum.toInt()
        lifeLevel = bloodLevel.toInt()
        this.speedLevel = speedLevel.toInt()
    }

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", firesLevel='" + firesLevel + '\'' +
                ", email='" + email + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", coins=" + coins +
                ", level=" + level +
                ", ownFireNum=" + ownFireNum +
                ", speedLevel=" + speedLevel +
                ", lifeLevel=" + lifeLevel +
                '}'
    }

    fun getFirePower(fireID: Int): Int {
        val basePower =
            MyTables.getFirePower(fireID, firesLevel!![fireID].code - 48)
        return (basePower* WarParams.bringPower).toInt()
    }

    fun getFireSupply(fireID: Int): Double {
        return MyTables.getSupply(fireID, firesLevel!![fireID].code - 48) * WarParams.bringFireAddNum
    }

    fun getFireFirst(fireID: Int): Double {
        return MyTables.getFirstNum(fireID, firesLevel!![fireID].code - 48) * WarParams.bringFireFirst
    }

    fun getFireSpeed(fireID: Int): Int {
        val baseSpeed = MyTables.getFireSpeed(fireID, (firesLevel!![fireID].code - 48))* WarParams.bringFireFirst
        return (baseSpeed).toInt()
    }

    val blood: Int
        get() = (MyTables.life[lifeLevel] * WarParams.bringLife).toInt()
    val moveSpeed: Int
        get() = MyTables.moveSpeed[speedLevel]


}