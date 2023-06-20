package com.df4j.base.util;

import com.df4j.base.exception.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void isLeapYear() {
        // 判断是闰年
        Assertions.assertTrue(DateUtils.isLeapYear(4));
        Assertions.assertTrue(DateUtils.isLeapYear(8));
        Assertions.assertTrue(DateUtils.isLeapYear(-1));
        Assertions.assertTrue(DateUtils.isLeapYear(-5));
        Assertions.assertTrue(DateUtils.isLeapYear(-9));
        Assertions.assertTrue(DateUtils.isLeapYear(2000));
        Assertions.assertTrue(DateUtils.isLeapYear(2004));
        Assertions.assertTrue(DateUtils.isLeapYear(1600));
        Assertions.assertTrue(DateUtils.isLeapYear(2024));

        // 判断不是闰年
        Assertions.assertFalse(DateUtils.isLeapYear(1700));
        Assertions.assertFalse(DateUtils.isLeapYear(2100));
        Assertions.assertFalse(DateUtils.isLeapYear(1));
        Assertions.assertFalse(DateUtils.isLeapYear(2));
        Assertions.assertFalse(DateUtils.isLeapYear(3));
        Assertions.assertFalse(DateUtils.isLeapYear(2021));
        Assertions.assertFalse(DateUtils.isLeapYear(2022));
        Assertions.assertFalse(DateUtils.isLeapYear(2023));
        Assertions.assertFalse(DateUtils.isLeapYear(-2));
        Assertions.assertFalse(DateUtils.isLeapYear(-3));
        Assertions.assertFalse(DateUtils.isLeapYear(-4));
        Assertions.assertFalse(DateUtils.isLeapYear(-101));

        Assertions.assertThrowsExactly(BaseException.class, () -> DateUtils.isLeapYear(0));

    }

    @Test
    void isValidDate() {

        Assertions.assertTrue(DateUtils.isValidDate(20230331));
        Assertions.assertTrue(DateUtils.isValidDate(20230228));
        Assertions.assertTrue(DateUtils.isValidDate(20230430));


        Assertions.assertFalse(DateUtils.isValidDate(20230229));
        Assertions.assertFalse(DateUtils.isValidDate(20230431));
        Assertions.assertFalse(DateUtils.isValidDate(20230000));
    }
}