package com.salary.consumption.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.salary.category.entity.Category;

public record MonthlyCategoryConsumptionDto(
        String categoryName,
        long amount
) {
    @QueryProjection
    public MonthlyCategoryConsumptionDto(String categoryName, long amount) {
        this.categoryName = categoryName;
        this.amount = amount;
    }
}
