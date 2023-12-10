package com.salary.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

public class DateTest {
    @Test
    void getMonthly(){
        String baseDt = "2023-12";
        String[] baseDtArr = baseDt.split("-");

        LocalDate a = LocalDate.of(Integer.parseInt(baseDtArr[0]), Integer.parseInt(baseDtArr[1]), 1);

        YearMonth yearMonth = YearMonth.of(Integer.parseInt(baseDtArr[0]), Integer.parseInt(baseDtArr[1]));

        int lastDay = yearMonth.lengthOfMonth();
    }

    @Test
    void targetDateTest(){
        String yyyyMM = "2024-02";
        int resetDay = 30;
        String[] cutDate = yyyyMM.split("-");
        LocalDate start = LocalDate.of(Integer.parseInt(cutDate[0]), Integer.parseInt(cutDate[1]), resetDay);
        LocalDate end = start.plusMonths(1).minusDays(1);

        System.out.println(start + " // " + end);
    }
}
