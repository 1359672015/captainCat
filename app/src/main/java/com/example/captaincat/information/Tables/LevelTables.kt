package com.example.captaincat.information.Tables

import android.content.Context
import android.util.Log
import com.example.captaincat.information.Levels.*
import com.example.captaincat.model.Level

object LevelTables {
    @JvmStatic
    fun getMainLevel(id: Int, context: Context?): BaseLevel {
        return when (id) {
            1 -> Level1_1(context)
            2 -> Level1_2(context)
            3 -> Level2_1(context)
            4 -> Level2_2(context)
            5 -> Level2_3(context)
            6 -> Level3_1(context)
            7 -> Level3_2(context)
            8 -> Level3_3(context)
            9 -> Level3_4(context)
            10 -> Level4_1(context)
            11 -> Level4_2(context)
            12 -> Level4_3(context)
            13 -> Level4_4(context)
            14 -> Level5_1(context)
            15 -> Level5_2(context)
            16 -> Level5_3(context)
            17 -> Level5_4(context)
            18 -> Level6_1(context)
            else -> Level1_1(context)
        }
    }

    @JvmStatic
    fun getMainLevelName(id: Int): String {
        return when (id) {
            1 -> Level1_1.name
            2 -> Level1_2.name
            3 -> Level2_1.name
            4 -> Level2_2.name
            5 -> Level2_3.name
            6 -> Level3_1.name
            7 -> Level3_2.name
            8 -> Level3_3.name
            9 -> Level3_4.name
            10 -> Level4_1.name
            11 -> Level4_2.name
            12 -> Level4_3.name 
            13 -> Level4_4.name 
            14 -> Level5_1.name 
            15 -> Level5_2.name 
            16 -> Level5_3.name 
            17 -> Level5_4.name 
            18 -> Level6_1.name
            else -> Level1_1.name
        }
    }

    @JvmStatic
    fun getMainLevelStory(id: Int): String {
        return when (id) {
            1 -> Level1_1.story
            2 -> Level1_2.story
            3 -> Level2_1.story
            4 -> Level2_2.story
            5 -> Level2_3.story
            6 -> Level3_1.story
            7 -> Level3_2.story
            8 -> Level3_3.story
            9 -> Level3_4.story
            10 -> Level4_1.story
            11 -> Level4_2.story
            12 -> Level4_3.story
            13 -> Level4_4.story
            14 -> Level5_1.story
            15 -> Level5_2.story
            16 -> Level5_3.story
            17 -> Level5_4.story
            18 -> Level6_1.story
            else -> Level1_1.story
        }
    }

    @JvmStatic
    fun getChallengeLevel(id: Int, context: Context?): BaseLevel {
        return when (id) {
            1 -> CLevel_1(context)
            2 -> CLevel_2(context)
            3 -> CLevel_3(context)
            4 -> CLevel_4(context)
            5 -> CLevel_5(context)
            6 -> CLevel_6(context)
            7 -> CLevel_7(context)
            8 -> CLevel_8(context)
            else -> CLevel_6(context)
        }
    }

    @JvmStatic
    fun getChallengeLevelStory(id: Int): String {
        return when (id) {
            1 -> CLevel_1.story 
            2 -> CLevel_2.story
            3 -> CLevel_3.story
            4 -> CLevel_4.story
            5 -> CLevel_5.story
            6 -> CLevel_6.story
            7 -> CLevel_7.story
            8 -> CLevel_8.story
            else -> CLevel_1.story
        }
    }

    @JvmStatic
    fun getChallengeLevelName(id: Int): String {
        return when (id) {
            1 -> CLevel_1.name
            2 -> CLevel_2.name
            3 -> CLevel_3.name
            4 -> CLevel_4.name
            5 -> CLevel_5.name
            6 -> CLevel_6.name
            7 -> CLevel_7.name
            8 -> CLevel_8.name
            else -> CLevel_1.name
        }
    }

    @JvmStatic
    fun getMainLevelList(num: Int): List<Level> {
        val list: MutableList<Level> = ArrayList()
        for (i in 1..num) {
            val l = Level()
            l.isChosen = i == num
            l.title = getMainLevelName(i)
            Log.d("lam", l.title)
            list.add(l)
        }
        return list
    }

    @JvmStatic
    fun getChallengeLevelList(): List<Level> {
        val list: MutableList<Level> = ArrayList()
        for (i in 1..8) {
            val l = Level()
            l.isChosen = i == 1
            l.title = getChallengeLevelName(i)
            list.add(l)
        }
        return list
    }
}