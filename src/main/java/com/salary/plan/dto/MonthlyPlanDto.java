package com.salary.plan.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "월별소비계획 현황 dto")
public class MonthlyPlanDto {
        @Schema(description = "식별자")
        private final Long id;
        @Schema(description = "카테고리 이름")
        private final String categoryName;
        @Schema(description = "목표금액")
        private final long targetAmount;
        @Schema(description = "지출금액")
        private long consumptionAmount;
        @Schema(description = "남은금액")
        private long remainAmount;

        @QueryProjection
        public MonthlyPlanDto(Long id, String categoryName, long targetAmount) {
                this.id = id;
                this.categoryName = categoryName;
                this.targetAmount = targetAmount;
        }

        public void setConsumptionAmount(long consumptionAmount){
                this.consumptionAmount = consumptionAmount;
                this.remainAmount = targetAmount - consumptionAmount;
        }
}
