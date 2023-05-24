package com.example.captaincat.model

import com.example.captaincat.Utils.TimeUtil

class Pet(var name:String ="宠物",
          var look  :Int = 0,
          var id :Int = -1,
          var uid :Int = 0,
          var energy:Int = 100,
          var mood :Int = 100,
          ){

    var status:String=""
    get() {
        if(TimeUtil.getHourOf24().toInt()  <0){
            return "睡觉中..zzz"
        }
        else
            return when{
             energy<10&&  mood<10 -> "濒临死亡"
             energy<50&&  mood<40 -> "状态十分糟糕 "
             energy<60&&  mood<60 -> "状态不太好 "
             energy<30 -> "十分饥饿"
             energy<50 -> "饥饿"
             mood<30 -> "心情十分低落"
             mood<50 -> "心情低落"
             energy>75&&   mood>75 -> "开心、健康"
             energy>60&&   mood>60 -> "嘴馋"
            else -> "有点饥饿"
        }
    }
    var fightAble :Boolean= false
    get() {
        return this.status=="开心、健康"
                ||this.status=="嘴馋"
    }
}