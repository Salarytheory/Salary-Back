package com.salary.consumption.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "스튜핏 소비 현황 정보 dto")
public class StupidConsumptionCurrentSituationDto{
        @Schema(description = "그레잇소비금액")
        private final long greatAmount;
        @Schema(description = "스튜핏소비금액")
        private final long stupidAmount;
        @Schema(description = "그레잇소비비율")
        private long greatRatio;
        @Schema(description = "스튜핏소비비율")
        private long stupidRatio;

        @QueryProjection
        public StupidConsumptionCurrentSituationDto(long greatAmount, long stupidAmount) {
                this.greatAmount = greatAmount;
                this.stupidAmount = stupidAmount;
                calculateRatio();
        }

        public void calculateRatio(){
                long totalAmount = greatAmount + stupidAmount;
                this.greatRatio = Math.round(((double) greatAmount / totalAmount) * 100L);
                this.stupidRatio = Math.round(((double) stupidAmount / totalAmount) * 100L);
        }
}
