package com.smic.cf.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Description 日期工具类
 * @ClassName DateUtil
 * @Author 蔡明涛
 * @date 2020.03.01 10:57
 */
public class DateUtils {

    /**
     * @Description 将传入的时间戳转化成String类型的日期
     * @Param [time]
     * @return java.lang.String
     * @Author 蔡明涛
     * @Date 2020.03.01 11:12
     **/
    public static String getDate(long time){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return timeFormatter.format(dateTime);
    }

    /**
     * @Description 获取指定日期格式的当前时间
     * @Param []
     * @return java.lang.String
     * @Author 蔡明涛
     * @Date 2020.03.01 11:36
     **/
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当天日期
     * @param []
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020.03.01 17:37
     **/
    public static String getCurrentDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
//    public static void main(String[] args) {
//        System.out.println(getDate(1580350061000L));
//    }

}
