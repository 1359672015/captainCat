package com.example.captaincat.model

data class Gem(
    var id: Int = 0,
    var name: String = "stone",
    
    var introduce: String = "",
    var picUrl: String = "",

    var bringPower: Int = 0,
    var bringFireAddNum: Int = 0,
    var bringFireFirst: Int = 0,
    var bringFireSpeed: Int = 0,
    var bringLife: Int = 0,
    var bringAddLife: Int = 0
){
    var gaindesc: String = ""
            get(){
                val builder = StringBuilder()
                bringPower.run{
                    if(this!=0)
                        builder.append("所有炮火威力 + $this%\n")
                }
                bringFireAddNum.run{
                    if(this!=0)
                        builder.append("所有炮火补给 + $this%\n")
                }
                bringFireFirst.run{
                    if(this!=0)
                        builder.append("所有炮火初始值 + $this%\n")
                }
                bringFireSpeed .run{
                    if(this!=0)
                        builder.append("所有炮火速度 + $this%\n")
                }
                bringLife.run{
                    if(this!=0)
                        builder.append("基础生命值 + $this%\n")
                }
                bringAddLife.run{
                    if(this!=0)
                        builder.append("每秒回血 + $this \n")
                }
                return builder.toString()
            }
}
