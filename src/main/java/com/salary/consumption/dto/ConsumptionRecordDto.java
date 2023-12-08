package com.salary.consumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "쓴 돈 입력 DTO")
public record ConsumptionRecordDto(
        @Schema(description = "돈 쓴 곳")
        String name,
        @Schema(description = "쓴 금액")
        long spentAmount,
        @Schema(description = "카테고리")
        String categoryName,
        @Schema(description = "지출날짜", pattern = "yyyy-mm-dd")
        LocalDate usedAt,
        @Schema(description = "소비등급 (GREAT, STUPID)")
        String grade
) {
}
