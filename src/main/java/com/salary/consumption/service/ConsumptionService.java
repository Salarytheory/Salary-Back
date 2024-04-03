package com.salary.consumption.service;

import com.salary.category.entity.Category;
import com.salary.category.repository.CategoryQueryRepository;
import com.salary.consumption.dto.*;
import com.salary.consumption.entity.Consumption;
import com.salary.consumption.repository.ConsumptionQueryRepository;
import com.salary.consumption.repository.ConsumptionRepository;
import com.salary.member.entity.Member;
import com.salary.member.repository.MemberRepository;
import com.salary.plan.entity.GoalManagement;
import com.salary.plan.repository.GoalManagementQueryRepository;
import com.salary.common.util.util.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionQueryRepository consumptionQueryRepository;
    private final MemberRepository memberRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final GoalManagementQueryRepository goalManagementQueryRepository;

    public ConsumptionSummaryDto getSummary(Member member, String baseDate){
        long targetAmount = 0;
        GoalManagement goalManagement = goalManagementQueryRepository.getGoalInfo(member, baseDate);
        if(goalManagement != null){
            targetAmount = goalManagement.getTargetAmount();
        }
        long totalSpentAmount = consumptionQueryRepository.getTotalSpentAmount(member, baseDate);
        return new ConsumptionSummaryDto(targetAmount, totalSpentAmount, targetAmount - totalSpentAmount);
    }

    public StupidConsumptionCurrentSituationDto getStupidConsumptionCurrentSituation(Member member, String baseDate, String isWidget){
        StupidConsumptionCurrentSituationDto stupidConsumptionCurrentSituation
                = consumptionQueryRepository.getStupidConsumptionCurrentSituation(member, baseDate);
        if("Y".equals(isWidget)){
            member.useWidget();
            memberRepository.save(member);
        }
        return consumptionQueryRepository.getStupidConsumptionCurrentSituation(member, baseDate);
    }

    public void record(Member member, ConsumptionRecordDto consumptionRecordDto){
        Category category = categoryQueryRepository.getCategory(consumptionRecordDto.categoryName());
        if(category == null){
            log.info("TODO 커스텀 카테고리 저장");
            throw new RuntimeException();
        }
        consumptionRepository.save(new Consumption(member, consumptionRecordDto, category));
    }

    public PaginationDto<List<ConsumptionHistoryDto>> getMonthlyConsumptionHistory(Member member, String baseDate, Pageable pageable){
        List<ConsumptionHistoryDto> result = consumptionQueryRepository.getMonthlyConsumptionHistory(member, baseDate, pageable);
        long count = consumptionQueryRepository.getMonthlyConsumptionHistoryCount(member, baseDate);
        return new PaginationDto<>(result, count);

    }

    public ConsumptionHistoryDto getConsumptionHistory(Long id){
        return consumptionQueryRepository.getConsumptionHistory(id);
    }

    public void modify(Long id, ConsumptionRecordDto consumptionRecordDto){
        Category category = categoryQueryRepository.getCategory(consumptionRecordDto.categoryName());
        if(category == null){
            log.info("TODO 커스텀 카테고리 저장");
            throw new RuntimeException();
        }
        Consumption consumption = consumptionRepository.findById(id).orElseThrow();
        consumption.update(consumptionRecordDto, category);
        consumptionRepository.save(consumption);
    }

    public void remove(Long id){
        consumptionRepository.deleteById(id);
    }
}
