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
}
