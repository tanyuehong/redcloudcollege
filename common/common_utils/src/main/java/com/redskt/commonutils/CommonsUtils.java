package com.redskt.commonutils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonsUtils {
    // 获取格式话
    public static String getFormatDateString() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return  date.format(formatter);
    }

    // 获取毫秒时间戳
    public static String getTimeMMString() {
        long timeMap = System.currentTimeMillis();
        return Long.toString(timeMap).substring(6);
    }
}
