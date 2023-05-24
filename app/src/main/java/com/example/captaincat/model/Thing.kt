package com.example.captaincat.model

class Thing {
    var id = 0
    var name: String = ""
    var price = 0
    var desc = ""
    var type = 0
    var picUrl: String = ""
    var num = 0
    override fun toString(): String {
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", picUrl='" + picUrl + '\'' +
                ", num=" + num +
                '}'
    }
}