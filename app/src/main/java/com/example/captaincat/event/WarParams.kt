package com.example.captaincat.event

class WarParams {
    //全都是百分数的分子
    companion object{
        var veryShoot = 10
        var bringPower:Float = 0.0F
        var bringFireAddNum:Float = 0.0F
        var bringFireFirst:Float = 0.0F
        var bringFireSpeed:Float = 0.0F
        var bringLife:Float = 0.0F
        var bringAddLife:Int = 0


        var shiedTime = 0
        var fireTime = 0
        var magic = 0
        fun refreshParams(){
            shiedTime = 0
            fireTime = 0
            magic = 0
        }
    }
}