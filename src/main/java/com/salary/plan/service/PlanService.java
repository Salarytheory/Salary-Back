package com.salary.plan.service;

import com.salary.category.entity.Category;
import com.salary.category.repository.CategoryQueryRepository;
import com.salary.consumption.dto.MonthlyPlanSetDto;
import com.salary.consumption.dto.TargetAmountDto;
import com.salary.member.entity.Member;
import com.salary.plan.dto.MonthlyPlanDto;
import com.salary.plan.entity.GoalManagement;
import com.salary.plan.entity.Plan;
import com.salary.plan.repository.GoalManagementRepository;
import com.salary.plan.repository.PlanQueryRepository;
import com.salary.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final GoalManagementRepository goalManagementRepository;
    private final PlanRepository planRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final PlanQueryRepository planQueryRepository;

    public void setTargetAmount(Member member, TargetAmountDto targetAmountDto){
        goalManagementRepository.save(new GoalManagement(member, targetAmountDto));
    }

    public List<MonthlyPlanDto> getMonthlyPlan(Member member, String baseDate){
        return planQueryRepository.getMonthlyPlan(member, baseDate);
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
