package com.example.captaincat.information.Tables;

import static com.example.captaincat.information.Tables.ObjectTable.getThing;

import com.example.captaincat.model.Thing;

import java.util.ArrayList;
import java.util.List;

public class FireTakesTable {
    static boolean inited = false;
    static final int[][][] shi = {
            {{1,3},{2,3}},
            {{1,15},{2,15},{3,10},{4,2}},
            {{1,40},{2,40},{4,20},{5,20}},
            {{1,188},{3,122},{5,99},{6,1},{7,1}},
            {{901,1},{1,99}},
            };

            static final int[][][] jian= {
            {{1,15},{2,15},{3,10}},
            {{1,66},{2,55},{3,33},{4,33},{5,19}},
            {{1,99},{2,88},{3,45},{4,20},{5,30},{6,1}},
            {{1,199},{2,98},{3,50},{6,3},{7,2}},
            {{902,1},{1,99},{17,1}},
            };
            static final int[][][] dao= {//火石
            {{1,35},{2,35},{5,10}},
            {{1,88},{2,66},{3,33},{4,33},{5,44}},
            {{1,122},{2,111},{4,99},{5,88},{6,1},{7,2}},
            {{1,199},{4,99},{2,99},{3,99},{6,3},{7,7},{8,2}},
            {{900,1},{11,66},{17,1}},
            };
            static final int[][][] huo= {//气功
            {{2,55},{5,60},{6,1}},
            {{1,60},{2,44},{3,44},{4,44},{5,44}},
            {{2,111},{4,99},{5,88},{6,2},{7,6},{8,4}},
            {{1,229},{4,149},{2,119},{3,129},{6,4},{7,18},{8,9}},
            {{903,1},{13,99},{17,1}},
            };
            static final int[][][] qi= {//核武器
            {{2,55},{4,88},{7,5},{6,2}},
            {{2,99},{4,99},{6,2},{7,18},{8,5}},
            {{2,111},{4,99},{5,88},{6,2},{7,28},{8,10},{9,1}},
            {{1,277},{4,177},{6,7},{7,77},{8,17},{9,7},{10,2}},
            {{902,1},{905,1},{13,99},{17,1}},
            };
            static final int[][][] zhan= {//核气功
            {{7,16},{5,66},{4,66},{8,16},{3,66},{6,6}},
            {{7,28},{5,88},{4,88},{8,8},{3,88},{6,8},{8,8},{9,8}},
            {{7,33},{5,118},{4,128},{8,12},{3,128},{6,8},{8,18},{9,18},{10,1}},
            {{7,43},{5,199},{4,199},{8,19},{3,199},{6,10},{8,45},{9,45},{10,5}},
            {{901,1},{903,1},{904,99},{17,1}},
            };

            static final int[][][] life = {
                    {{1,15},{2,15},{3,15}},//2
                    {{1,30},{2,30},{3,30},{4,10},{5,15}},//3
                    {{1,40},{2,40},{3,40},{4,30},{5,20}},//4
                    {{1,40},{2,40},{3,40},{4,30},{5,20},{6,1}},//5
                    {{2,50},{3,40},{4,40},{5,28},{6,1}},//6
                    {{2,50},{3,40},{4,40},{5,33},{6,1}},//7
                    {{2,50},{3,50},{4,50},{5,35},{6,1},{7,1}},//8
                    {{6,2},{4,60}},//9
                    {{6,2},{4,70},{7,10}},//10
                    {{7,20}},//11
                    {{4,99},{6,3},{7,15}},//12
                    {{6,3},{7,50},{8,5},{9,1},{3,55},{5,44}},//13
                    {{6,5},{3,144},{4,144},{5,14},{8,10},{7,46},{9,3}},//14
                    {{6,10},{3,199},{4,199},{8,21},{9,5},{10,1}},//15
                    {{11,60},{904,1},{905,1}},//16
                    {{903,1},{1005,1}},//17
            };

    static final int[][][] speed = {
            {{1,20},{2,20},{3,15}},//2
            {{1,30},{2,30},{3,20},{4,15},{5,15}},//3
            {{1,40},{2,40},{3,30},{4,30},{5,30}},//4
            {{4,99},{5,99},{6,3},{8,1}},//5
            {{6,10},{8,3},{9,3},{10,1}},//6
    };
    public  static List<Thing> getNeeds(int id , int toLevel){
        toLevel-=2;
        int[][] a = getArray(id)[toLevel];
        ArrayList<Thing> list = new ArrayList<>();
        for(int i = 0 ;i < a.length;i++){
            Thing thing = getThing(a[i][0]);
            thing.setNum(a[i][1]);
                list.add(thing);
        }
        return list;

    }
    public  static List<Thing> getUpLevelSpeedNeeds(int toLevel){
        toLevel-=2;
        int[][] a = speed[toLevel];
        ArrayList<Thing> list = new ArrayList<>();
        for(int i = 0 ;i < a.length;i++){
            Thing thing = getThing(a[i][0]);
            thing.setNum(a[i][1]);
            list.add(thing);
        }
        return list;

    }
    public  static List<Thing> getUpLevelLifeNeeds(int toLevel){
        toLevel-=2;
        int[][] a = life[toLevel];
        ArrayList<Thing> list = new ArrayList<>();
        for(int i = 0 ;i < a.length;i++){
            Thing thing = getThing(a[i][0]);
            thing.setNum(a[i][1]);
            list.add(thing);
        }
        return list;
    }


    public static int[][][] getArray(int id){
            switch (id){
                case 0:return shi;
                case 1:return jian;
                case 2:return dao;
                case 3:return huo;
                case 4:return qi;
                case 5:return zhan;
                default:return shi;
            }
    }

}


