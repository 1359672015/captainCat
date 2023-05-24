package com.example.captaincat.information.Tables

import com.example.captaincat.model.Gem

class GemsTables {


    companion object{
        lateinit var allGems: List<Gem>
        fun getGemById(id:Int):Gem?{
            allGems.forEach {
                if(it.id==id)
            return it

            }
            return null
        }

    }
}
