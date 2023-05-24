package com.example.captaincat.information.Tables;

import android.util.Log;

import com.example.captaincat.MyGame;
import com.example.captaincat.model.FireIcon;
import com.example.captaincat.R;

import java.util.ArrayList;
import java.util.List;

public class MyTables {
/**
 * 技能:每种最高:5
 * life最高:15
 * speed最高:6
 * ownFire最高:4
 * */
    /**等级-每秒补给表
     * [炮火id][等级]
     * 0级作为放崩溃指标,并为了贴近数字使用习惯
     * */
    static public String[] fireNames = {
            "便便",
            "石头",
            "火石头",
            "气功",
            "核弹",
            "核气功"
    };
    static public double[][]
            supply ={
            { 0, 1.2, 1.9, 2.5, 3.1, 4.2, 5.1},//便便
            { 0, 1.0, 1.6, 2.1, 2.9, 3.5, 4.6},//石头
            { 0, 0.8, 1.4, 2.0, 2.5, 3.4, 4.4},//火石头
            { 0, 0.7, 1.3, 1.9, 2.5, 3.3, 4.3},//气功
            { 0, 0.9, 1.4, 2.0, 2.6, 3.2, 4.2},//核弹
            { 0, 1.0, 1.4, 2.1, 2.7, 3.4, 4.1},//核气功
            }  ;

    static public double[][]
            firstNum={
            {1 , 7,  13, 20, 38, 59, 90},//便便
            {1 , 7,  11, 17, 34, 57, 88},//石头
            {1 , 6,  12, 17, 35, 45, 86},//火石头
            {1 , 6,  10, 15, 32, 46, 86},//气功
            {1 , 10, 15, 20, 35, 42, 83},//核弹
            {1 , 7 , 12, 18, 34, 42, 85},//核气功
    };
    static public int[][]
            firesPower={
            {1, 7 , 12, 19, 27, 39 , 69 }, //便便
            {1, 9 , 15, 23, 35, 48 , 76 }, //石头
            {1, 14, 20, 29, 49, 66 , 90 }, //火石头
            {1, 20, 26, 38, 53, 77 , 105 }, //气功
            {1, 35, 48, 68, 108,137, 178 },//核弹
            {1, 63, 88, 117,150,199, 281 },//核气功
    };
    static public int[][]
            firesSpeed= {
            {1, 16, 18 , 22, 28, 36, 46},//便便
            {1, 16, 20 , 26, 34, 45, 52},//石头
            {1, 16, 19 , 24, 30, 40, 48},//火石头
            {1, 16, 19 , 24, 32, 39, 50},//气功
            {1, 19, 23 , 27, 33, 40, 50},//核弹
            {1, 18, 22 , 26, 33, 41, 51},//核气功
            };

        static public int[] moveSpeed = {8,8,11,13,15,17,18};
        static public int[] life = {0,
                70,     100,    150,    230,
                320,    450,    600,    750,    1100,
                1500,   1900,   2300,   2800,   3400,
                4200,   5300,   6500 };

         static  public int[] fireLayout = {
                 R.layout.mf1,
                 R.layout.mf2,
                 R.layout.mf3,
                 R.layout.mf4,
                 R.layout.mf5,
                 R.layout.mf6
         };
    static  public int[] FIRE_ICON = {
            R.drawable.icon_shit,
            R.drawable.ic_1,
            R.drawable.icon_fire,
            R.drawable.icon_qigong,
            R.drawable.icon_core,
            R.drawable.icon_catombomb
    };

    public static int[] getFireLayout() {
        return fireLayout;
    }

    public static String[] getFireNames() {
        return fireNames;
    }
    public static String getFireName(int id){
        return fireNames[id];
    }
    public static double getSupply(int id, int level ) {
        return supply[id][level];
    }

    public static int getFirePower(int id, int level ) {
        return firesPower[id][level];
    }
    public static double getFirstNum(int id, int level ) {
        return firstNum[id][level];
    }
    public static int getFireSpeed(int id, int level ) {
        return firesSpeed[id][level];
    }

   public static List<FireIcon> getChoosingFire(){
       String levels = MyGame.user.getFiresLevel();
       List<FireIcon> list = new ArrayList<>();
       for(int i = 0 ; i< levels.length();i++){
           if(levels.charAt(i)-48!=0){
               Log.d("Lam3_22",i+fireNames[i]+FIRE_ICON[i]+(levels.charAt(i)-48));
           FireIcon fireIcon = new FireIcon(i,fireNames[i],FIRE_ICON[i],levels.charAt(i)-48);
           list.add(fireIcon);
           }
       }
       return list;
    }

}
