package com.salary.plan.service;

import com.salary.category.entity.Category;
import com.salary.category.repository.CategoryQueryRepository;
import com.salary.consumption.dto.MonthlyCategoryConsumptionDto;
import com.salary.consumption.dto.MonthlyPlanSetDto;
import com.salary.consumption.dto.TargetAmountDto;
import com.salary.consumption.repository.ConsumptionQueryRepository;
import com.salary.member.entity.Member;
import com.salary.plan.dto.MonthlyPlanDto;
import com.salary.plan.entity.GoalManagement;
import com.salary.plan.entity.Plan;
import com.salary.plan.repository.GoalManagementQueryRepository;
import com.salary.plan.repository.GoalManagementRepository;
import com.salary.plan.repository.PlanQueryRepository;
import com.salary.plan.repository.PlanRepository;
import com.salary.common.util.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final GoalManagementRepository goalManagementRepository;
    private final GoalManagementQueryRepository goalManagementQueryRepository;
    private final PlanRepository planRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final ConsumptionQueryRepository consumptionQueryRepository;
    private final PlanQueryRepository planQueryRepository;

    public void setTargetAmount(Member member, TargetAmountDto targetAmountDto){
        GoalManagement goalManagement = goalManagementQueryRepository.getGoalInfo(member, targetAmountDto.targetDate());
        if(goalManagement == null){
            goalManagementRepository.save(new GoalManagement(member, targetAmountDto));
            return;
        }
        goalManagement.setTargetAmount(targetAmountDto.targetAmount());
        goalManagementRepository.save(goalManagement);
    }

    public List<MonthlyPlanDto> getMonthlyPlan(Member member, String baseDate){
        List<MonthlyPlanDto> monthlyPlans = planQueryRepository.getMonthlyPlan(member, baseDate);
        if(monthlyPlans.isEmpty()){
            return monthlyPlans;
        }

        LocalDate prevDate = DateUtil.getTargetDate(baseDate, member.getResetDay());
        LocalDate nextDate = DateUtil.getNextMonthDate(prevDate);

        List<MonthlyCategoryConsumptionDto> monthlyCategoryConsumptions
                = consumptionQueryRepository.getMonthlyCategoryConsumption(member, prevDate, nextDate);

        setConsumptionAmount(monthlyPlans, monthlyCategoryConsumptions);
        return monthlyPlans;
    }

    private void setConsumptionAmount(List<MonthlyPlanDto> monthlyPlans, List<MonthlyCategoryConsumptionDto> monthlyCategoryConsumptions){
        Map<String, Long> consumptionMap = monthlyCategoryConsumptions.stream()
                .collect(Collectors.toMap(MonthlyCategoryConsumptionDto::categoryName, MonthlyCategoryConsumptionDto::amount));

        monthlyPlans.forEach(monthlyPlan ->
                monthlyPlan.setConsumptionAmount(
                        consumptionMap.getOrDefault(monthlyPlan.getCategoryName(), 0L)
                )
        );
    }

    public void setTargetConsumptionPlan(Member member, List<MonthlyPlanSetDto> monthlyPlanSetDtoList){
        List<Plan> planList = setPlan(monthlyPlanSetDtoList, member);
        planRepository.saveAll(planList);
    }

    private List<Plan> setPlan(List<MonthlyPlanSetDto> monthlyPlanSetDtoList, Member member){
        List<Plan> planList = new ArrayList<>();
        for(MonthlyPlanSetDto monthlyPlanSetDto : monthlyPlanSetDtoList){
            Category category = categoryQueryRepository.getCategory(monthlyPlanSetDto.categoryName());
            if(category == null) throw new RuntimeException();

            Plan plan = planQueryRepository.getPlan(monthlyPlanSetDto, category, member);
            if(plan == null) {
                planList.add(new Plan(monthlyPlanSetDto, member, category));
            }else{
                plan.modify(monthlyPlanSetDto);
                planList.add(plan);
            }
        }
        return planList;
    }
}
