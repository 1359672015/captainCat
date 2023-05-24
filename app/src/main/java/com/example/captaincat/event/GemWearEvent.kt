package com.example.captaincat.event

import com.example.captaincat.information.Tables.GemsTables.Companion.getGemById
import com.example.captaincat.model.Gem

class GemWearEvent(
    var iPlaceGemId :Int= 0,
    var iiPlaceGemId :Int= 0,
    ){

fun setWarParams(){
       val gem1 =  getGemById(iPlaceGemId)?: Gem()
       val gem2 =  getGemById(iiPlaceGemId)?: Gem()
       WarParams.bringPower = (gem1.bringPower+gem2.bringPower+100)/100.0F
       WarParams.bringFireAddNum = (gem1.bringFireAddNum+gem2.bringFireAddNum+100)/100.0F
       WarParams.bringFireFirst = (gem1.bringFireFirst+gem2.bringFireFirst+100)/100.0F
       WarParams.bringFireSpeed = (gem1.bringFireSpeed+gem2.bringFireSpeed+100)/100.0F
       WarParams.bringLife = (gem1.bringLife+gem2.bringLife+100)/100.0F
       WarParams.bringAddLife = gem1.bringAddLife+gem2.bringAddLife
    }
}
