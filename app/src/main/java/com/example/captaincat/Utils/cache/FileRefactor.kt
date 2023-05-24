package com.example.captaincat.Utils.cache

import com.example.lamstest.Common.toast

class FileRefactor {
/**
 * 映射本地名和展示名的工具类,数据较小,存储方式用MMKV
 * 为在保证数据正确的情况下,减少不必要的读写耗时,规定以下:
 * 1. 初始化：app打开后及时初始化,从本地MMKV拿出来放内存
 * 2. 增删改：内存上修改->再存储到本地
 * 3. 查、搜: 无需动用本地MMKV,直接在内存的全局变量中查
 * */
    companion object {
        const val tag = "NAME_REFACTOR"
        //整个表信息化为字符串,用于与MMKV打交道
        private var allInfo: String = ""
        //整个表信息以map形式存放,方便读写
        private var mapAllInfo: MutableMap<String,String> = mutableMapOf()

    /** 初始化内存数据,需要在app启动后及时进行*/
        fun initialize(){
                allInfo = getAllInfo()
                mapAllInfo = getWholeMap()
        }
    /**根据本地名获取展示名*/
        fun getShowName(localName: String): String{
            return mapAllInfo[localName]?:localName
        }

    /**根据本地名获取展示名*/
    fun getListOfAll(): List<LocalFile>{
        var list:MutableList<LocalFile> = mutableListOf()
       mapAllInfo.keys.forEach {
            list.add(LocalFile(it, mapAllInfo[it]?:"暂无内容"))
        }
        return  list
    }

    /** 增、改数据 */
        fun save(localName: String, showName: String) {
            mapAllInfo.put(localName,showName)
            saveMap(mapAllInfo)
        }
    /**对某文件展示名进行重命名*/
        fun rename(showName1: String, showName2: String) {
            mapAllInfo.keys.forEach {
             if(mapAllInfo[it]==showName1){
                 mapAllInfo[it] = showName2
                 return
             }
            }
            saveMap(mapAllInfo)
        }
        /** 按key来删除对应记录 */
        fun deleteLocal(localName:String) {
            mapAllInfo.remove(localName)
            saveMap(mapAllInfo)
        }
        /** 按value来删除对应记录 */
        fun deleteShow(localName:String) {
            mapAllInfo.keys.forEach {
                if(mapAllInfo[it]==localName){
                    mapAllInfo.remove(it)
                    return
                }
            }
            saveMap(mapAllInfo)
        }
        // 整个表写入本地
        fun saveMap(mapAllInfo:MutableMap<String,String>) {
            val s = mapAllInfo.toString()
            s.toast()
            MMKVUtils.encode(tag, s)
        }
        private fun getWholeMap():MutableMap<String,String>{
            allInfo = getAllInfo()
            if(allInfo.length<=4)
                return mutableMapOf()
            return stringToMap(allInfo)
        }
        private fun getAllInfo(): String {
            return MMKVUtils.decodeString(tag, "")
        }

        //把map.toString出来的字符串转回map
        private fun stringToMap(str: String): MutableMap<String, String> {
            var str = str
            str = str.substring(1, str.length - 1)
            val strs = str.split(",".toRegex()).toTypedArray()
            val map:  MutableMap<String, String> = mutableMapOf()
            for (string in strs) {
                val key = string.split("=".toRegex()).toTypedArray()[0]
                val value = string.split("=".toRegex()).toTypedArray()[1]
                // 去掉头部空格
                val key1 = key.trim { it <= ' ' }
                val value1 = value.trim { it <= ' ' }
                map.put(key1, value1)
            }
            return map
        }
    }
    class LocalFile(var localName : String, var showName :String){
        override fun toString(): String {
            return "LocalFile(localName='$localName', showName='$showName')"
        }
    }
}