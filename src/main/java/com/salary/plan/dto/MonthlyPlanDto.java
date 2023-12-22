package com.salary.plan.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "월별소비계획 현황 dto")
public record MonthlyPlanDto(
        @Schema(description = "카테고리 이름")
        String categoryName,
        @Schema(description = "목표금액")
        long targetAmount,
        @Schema(description = "지출금액")
        long consumptionAmount,
        @Schema(description = "남은금액")
        long remainAmount
) {
        @QueryProjection
        public MonthlyPlanDto(String categoryName, long targetAmount, long consumptionAmount, long remainAmount) {
                this.categoryName = categoryName;
                this.targetAmount = targetAmount;
                this.consumptionAmount = consumptionAmount;
                this.remainAmount = remainAmount;
        }
}
