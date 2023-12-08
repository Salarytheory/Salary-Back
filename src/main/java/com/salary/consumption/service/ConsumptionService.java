package com.salary.consumption.service;

import com.salary.category.entity.Category;
import com.salary.category.repository.CategoryQueryRepository;
import com.salary.consumption.dto.ConsumptionRecordDto;
import com.salary.consumption.dto.ConsumptionSummaryDto;
import com.salary.consumption.dto.StupidConsumptionCurrentSituationDto;
import com.salary.consumption.entity.Consumption;
import com.salary.consumption.repository.ConsumptionQueryRepository;
import com.salary.consumption.repository.ConsumptionRepository;
import com.salary.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionQueryRepository consumptionQueryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    public ConsumptionSummaryDto getSummary(Member member){
        long targetAmount = member.getTargetAmount();
        long totalSpentAmount = consumptionQueryRepository.getTotalSpentAmount(member);
        return new ConsumptionSummaryDto(targetAmount, totalSpentAmount, targetAmount - totalSpentAmount);
    }

    public StupidConsumptionCurrentSituationDto getStupidConsumptionCurrentSituation(Member member){
        return consumptionQueryRepository.getStupidConsumptionCurrentSituation(member);
    }

    public void record(Member member, ConsumptionRecordDto consumptionRecordDto){
        Category category = categoryQueryRepository.getCategory(consumptionRecordDto.categoryName());
        if(category == null){
            log.info("TODO 커스텀 카테고리 저장");
            throw new RuntimeException();
        }
        consumptionRepository.save(new Consumption(member, consumptionRecordDto, category));
    }

    public void getMonthlyConsumptionHistory(Member member, String baseDate){

    }
}
