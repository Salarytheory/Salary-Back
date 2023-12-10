package com.salary.consumption.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.salary.consumption.entity.Grade;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "지출월별내역 DTO")
public record ConsumptionHistoryDto(
        @Schema(description = "소비 식별자")
        Long consumptionId,

        @Schema(description = "돈 쓴 곳")
        String name,

        @Schema(description = "쓴 금액")
        long spentAmount,

        @Schema(description = "카테고리")
        String categoryName,

        @Schema(description = "지출날짜", pattern = "yyyy-mm-dd")
        LocalDate usedAt,

        @Schema(description = "소비등급 (GREAT, STUPID)")
        Grade grade
) {
        @QueryProjection
        public ConsumptionHistoryDto(Long consumptionId, String name, long spentAmount, String categoryName, LocalDate usedAt, Grade grade) {
                this.consumptionId = consumptionId;
                this.name = name;
                this.spentAmount = spentAmount;
                this.categoryName = categoryName;
                this.usedAt = usedAt;
                this.grade = grade;
        }
}
