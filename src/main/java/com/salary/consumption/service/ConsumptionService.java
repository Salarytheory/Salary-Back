package com.salary.consumption.service;

import com.salary.category.entity.Category;
import com.salary.category.repository.CategoryQueryRepository;
import com.salary.consumption.dto.ConsumptionRecordDto;
import com.salary.consumption.dto.ConsumptionSummaryDto;
import com.salary.consumption.dto.StupidConsumptionCurrentSituationDto;
import com.salary.consumption.dto.TargetAmountDto;
import com.salary.consumption.entity.Consumption;
import com.salary.consumption.repository.ConsumptionQueryRepository;
import com.salary.consumption.repository.ConsumptionRepository;
import com.salary.member.entity.Member;
import com.salary.plan.entity.GoalManagement;
import com.salary.plan.repository.GoalManagementQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionQueryRepository consumptionQueryRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final GoalManagementQueryRepository goalManagementQueryRepository;

    public ConsumptionSummaryDto getSummary(Member member, String baseDate){
        GoalManagement goalManagement = goalManagementQueryRepository.getGoalInfo(member, baseDate);
//        long targetAmount = member.getTargetAmount();
        long targetAmount = 0;
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
