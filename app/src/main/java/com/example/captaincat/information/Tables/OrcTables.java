package com.example.captaincat.information.Tables;

import android.content.Context;

import com.example.captaincat.flyingObjects.Enemies.Enemy;
import com.example.captaincat.flyingObjects.Enemies.OrcFire;
import com.example.captaincat.R;

public class OrcTables {
    //11种敌军炮火
   static public String[] fireName =
            {"石块","钢弹","炸弹",
             "炮击","雷轰,","暗影之咒",
            "破碎飞碟","火流星","毁灭之火"};
   static  public int[] fireLayout =
           { R.layout.of1,R.layout.of2,R.layout.of3,
             R.layout.of4,R.layout.of5,R.layout.of6,
             R.layout.of7,R.layout.of8,R.layout.of9,
           };


    static public int[] firePower = {3 ,7 ,15, 20, 34, 45, 55, 65, 85};
    static public int[] fireSpeed =    {4 ,5 ,5, 6, 7, 6, -17, -8, -24};
    static public int[] fireAddSpeed = {0 ,0 ,0, 0, 0, 1, 1,  2,  1};



    static public OrcFire getOrcFire(Context context , Enemy enemy, int id){
        id--;
        return new OrcFire(context,enemy.getPX(),enemy.getPY(),
                OrcTables.firePower[id], OrcTables.fireSpeed[id], fireLayout[id],fireAddSpeed[id]);
    }

}
