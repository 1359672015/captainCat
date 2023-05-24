package com.example.captaincat.model

class ItemPractice(
    var id: Int = 0,
    var name: String = "",
    var level: Int = 0,
    var iconUrl: Int = 0,
    var info: String = "",
    var nextInfo: String = "",
    var isMax: Boolean = false,
    var needs: List<Thing>? = mutableListOf()
) {
    override fun toString(): String {
        return "ItemPractice{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", info='" + info + '\'' +
                ", id=" + id +
                ", nextInfo='" + nextInfo + '\'' +
                ", isMax=" + isMax +
                '}'
    }
}