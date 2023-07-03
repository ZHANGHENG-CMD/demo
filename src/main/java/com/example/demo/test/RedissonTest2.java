package com.example.demo.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RedissonTest2 {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse("9999-02-11 08:46:56");
        System.out.println("date："+parse.getTime());
        Timestamp timestamp = new Timestamp(1682053648427L);
        System.out.println("timestamp: "+timestamp);
        Date date1 = new Date();
        System.out.println("date1 "+date1.getTime());
        float l=(float) (date1.getTime()-timestamp.getTime())/(1000*3600*24);
        ///321321
        System.out.println("天数间隔："+l);
        System.out.println(l>60);

    }
}
