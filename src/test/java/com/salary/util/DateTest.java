//package com.salary.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//
//public class DateTest {
//    @Test
//    void getMonthly(){
//        String baseDt = "2023-12";
//        String[] baseDtArr = baseDt.split("-");
//
//        LocalDate a = LocalDate.of(Integer.parseInt(baseDtArr[0]), Integer.parseInt(baseDtArr[1]), 1);
//
//        YearMonth yearMonth = YearMonth.of(Integer.parseInt(baseDtArr[0]), Integer.parseInt(baseDtArr[1]));
//
//        int lastDay = yearMonth.lengthOfMonth();
//    }
//
//    @Test
//    void targetDateTest(){
//        String yyyyMM = "2024-01";
//        int resetDay = 31;
//
//        LocalDate start;
//        String[] cutDate = yyyyMM.split("-");
//        int year = Integer.parseInt(cutDate[0]);
//        int month = Integer.parseInt(cutDate[1]);
//
//        if(isValidDay(year, month, resetDay)){
//            start = LocalDate.of(year, month, resetDay);
//        }
//        resetDay = getMonthLastDay(year, month);
//        start =  LocalDate.of(year, month, resetDay);
//
//        LocalDate end = start.plusMonths(1).minusDays(1);
//        System.out.println(start + " // " + end);
//    }
//
//    private boolean isValidDay(int year, int month, int resetDay){
//        YearMonth yearMonth = YearMonth.of(year, month);
//        int lastDayOfMonth = yearMonth.lengthOfMonth();
//        return resetDay <= lastDayOfMonth;
//    }
//
//    private int getMonthLastDay(int year, int month){
//        YearMonth yearMonth = YearMonth.of(year, month);
//        return yearMonth.lengthOfMonth();
//    }
//}
