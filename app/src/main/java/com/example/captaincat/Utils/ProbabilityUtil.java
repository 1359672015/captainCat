package com.example.captaincat.Utils;

import java.util.Random;

public class ProbabilityUtil {


   static public boolean isToHappen(int probability){
        Random random = new Random();
        int a = random.nextInt(100);
        if(a<=probability)
            return true;
        else  return false;
    }

    static public int getNumberLess(int num){
       Random random = new Random();
       int a = random.nextInt(num);
       return a;
    }
    static public int 正负一(){
        Random random = new Random();
        int a = random.nextInt(10);
        if(a<5)
        return -1;
        else return 1;
    }
}
