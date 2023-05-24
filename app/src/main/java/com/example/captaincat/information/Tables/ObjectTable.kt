package com.example.captaincat.information.Tables

import com.example.captaincat.api.UserBagModel.Companion.getBagThingNum
import com.example.captaincat.model.Thing
import com.example.captaincat.model.UpLevelInfo
import com.example.captaincat.Ui.Dialog.DialogItem
import java.lang.StringBuilder
import java.util.ArrayList

class ObjectTable {

companion object{

    //包括0：金币
    @JvmStatic
      var allThing: List<Thing> = mutableListOf()

    @JvmStatic
    fun getThing(id: Int): Thing {
        allThing.forEach{
            if(it.id == id)
                return it
        }
        return Thing().apply {
            name = "未知"
        }
    }

    val thingsString: String
        get() {
            val s = StringBuilder("")
            for (thing in allThing) {
                s.append(
                    """$thing""".trimIndent()
                )
            }
            return s.toString()
        }
    /*物品列表转换成弹窗子项以显示*/
    @JvmStatic
    fun turnThingsIntoDiaItem(list: List<Thing>): List<DialogItem> {
        val dialogItemList: MutableList<DialogItem> = ArrayList()
        for (thing in list) {
            dialogItemList.add(DialogItem(thing.name + " × " + thing.num, thing.picUrl, null))
        }
        return dialogItemList
    }
    /*确认修炼界面校验物品拥有情况*/
    fun turnIntoDiaItemForPra(list: List<Thing>): UpLevelInfo {
        val dialogItemList: MutableList<DialogItem> = ArrayList()
        var enough = true
        for (thing in list) {
            var material = "(拥有)"
            val num = getBagThingNum(thing.id)
            if (thing.num > num) {
                enough = false
                material = "(材料不足)"
            }
            dialogItemList.add(
                DialogItem(
                    thing.name + " × " + thing.num + material,
                    thing.picUrl,
                    null
                )
            )
        }
        return UpLevelInfo(dialogItemList, enough)
    }
}

}