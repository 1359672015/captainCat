package com.example.captaincat.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

   static public String getTime(){
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("ddHHmm");
        String s = df1.format(date);
        return s;
    }
    static public String getDay(){
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("YYYYMMdd");
        String s = df1.format(date);
        return s;
    }
    static public String getTime(String form){
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat(form);
        String s = df1.format(date);
        return s;
    }
    static public String getHourOf24(){
       return getTime().substring(2,4);
    }
    static public void main(String[] args){

        System.out.println(System.currentTimeMillis());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());


    }

}
