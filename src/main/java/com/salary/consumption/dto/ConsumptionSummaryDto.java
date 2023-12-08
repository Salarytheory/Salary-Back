package com.salary.consumption.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지출현황내역 (계획금액, 사용금액, 남은금액 DTO)")
public record ConsumptionSummaryDto(
        @Schema(description = "목표금액")
        long targetAmount,
        @Schema(description = "사용한금액")
        long spentAmount,
        @Schema(description = "남은금액")
        long remainingAmount
) {
}
