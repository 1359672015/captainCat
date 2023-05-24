package com.example.captaincat.Utils.cache

import com.example.captaincat.MyApplication.Companion.appContext


import android.os.Parcelable
import com.tencent.mmkv.MMKV

object MMKVUtils {

    private fun getMMKV():MMKV{
        if (MMKV.defaultMMKV()==null){
            MMKV.initialize(appContext)
        }
        return MMKV.defaultMMKV()!!
    }
    fun decodeBool(key: String, default: Boolean = false): Boolean {
        return getMMKV().decodeBool(key, default)
    }

    fun decodeDouble(key: String, default: Double = 0.0): Double {
        return getMMKV().decodeDouble(key, default)
    }

    fun decodeFloat(key: String, default: Float = 0F): Float {
        return getMMKV().decodeFloat(key, default)
    }

    fun decodeInt(key: String, default: Int = 0): Int {
        return getMMKV().decodeInt(key, default)
    }

    fun decodeLong(key: String, default: Long = 0): Long {
        return getMMKV().decodeLong(key, default)
    }

    fun decodeString(key: String, default: String = ""): String {
        return getMMKV().decodeString(key, default) ?: ""
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>): T? {
        return MMKV.defaultMMKV()!!.decodeParcelable(key, tClass)
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>,default:T): T? {
        return MMKV.defaultMMKV()!!.decodeParcelable(key, tClass,default)
    }

    fun <T> encode(key: String, value: T) {
        when (value) {
            is String -> {
                getMMKV().encode(key, value)
            }
            is Boolean -> {
                getMMKV().encode(key, value)
            }
            is Double -> {
                getMMKV().encode(key, value)
            }
            is Float -> {
                getMMKV().encode(key, value)
            }
            is Int -> {
                getMMKV().encode(key, value)
            }
            is Long -> {
                getMMKV().encode(key, value)
            }
            is Parcelable -> {
                getMMKV().encode(key, value)
            }
        }
    }

    fun removeValueForKey(key: String){
        getMMKV().removeValueForKey(key)
    }

}