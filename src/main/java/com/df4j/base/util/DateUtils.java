package com.df4j.base.util;

import com.df4j.base.exception.BaseException;

public class DateUtils {


    /**
     * 判断公历年份是不是闰年
     *
     * @param year 输入的年份
     * @return 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year > 0) {
            // 4 8 12 ...
            return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
        } else if (year < 0) {
            // -1 -5 -9 ...
            int tmp = year + 1;
            return tmp % 400 == 0 || (tmp % 100 != 0 && tmp % 4 == 0);
        } else {
            throw new BaseException("invalid year: " + year);
        }
    }

    /**
     * 校验是否为有效日期
     *
     * @param date 输入的日期
     * @return 是否为有效日期
     */
    public static boolean isValidDate(int date) {
        int day = date % 100,
                mon = (date - day) / 100 % 100,
                year = (date - day - mon * 100) / 10000;
        if (mon > 12 || mon == 0 || day == 0 || year == 0) {
            return false;
        }
        if (mon == 2) {
            // 二月闰年29天，其他小于28
            return day <= 28 || (isLeapYear(year) && day == 29);
        } else {
            // 其他  1，3，5，7，8，10，12为31天，其他为30天。
            return day <= 30 || (day == 31 && (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12));
        }
    }
}
