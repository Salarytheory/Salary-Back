package com.salary.consumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "월간 카테고리별 목표소비금액 등록용 dto (List 형식으로 제출)")
public record MonthlyPlanSetDto(
        @Schema(description = "카테고리 이름")
        String categoryName,
        @Schema(description = "목표금액")
        long targetAmount,
        @Schema(description = "기준연월", pattern = "yyyy-mm")
        String baseDate
) {
}
