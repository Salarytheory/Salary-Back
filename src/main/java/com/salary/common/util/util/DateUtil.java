package com.salary.common.util.util;

import java.time.LocalDate;
import java.time.YearMonth;

public class DateUtil {
    public static LocalDate getTargetDate(String yyyyMM, int resetDay){
        String[] cutDate = yyyyMM.split("-");
        int year = Integer.parseInt(cutDate[0]);
        int month = Integer.parseInt(cutDate[1]);

        if(isValidDay(year, month, resetDay)){
            return LocalDate.of(year, month, resetDay);
        }
        resetDay = getMonthLastDay(year, month);
        return LocalDate.of(year, month, resetDay);
    }

    private static boolean isValidDay(int year, int month, int resetDay){
        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        return resetDay <= lastDayOfMonth;
    }

    private static int getMonthLastDay(int year, int month){
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    public static LocalDate getNextMonthDate(LocalDate prev){
        return prev.plusMonths(1).minusDays(1);
    }
}
