package com.salary.plan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.category.entity.Category;
import com.salary.consumption.dto.MonthlyPlanSetDto;
import com.salary.member.entity.Member;
import com.salary.plan.dto.MonthlyPlanDto;
import com.salary.plan.dto.QMonthlyPlanDto;
import com.salary.plan.entity.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.salary.consumption.entity.QConsumption.consumption;
import static com.salary.plan.entity.QPlan.plan;
import static com.salary.category.entity.QCategory.category;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<MonthlyPlanDto> getMonthlyPlan(Member member, String baseDate){
        return queryFactory.select(new QMonthlyPlanDto(
                        plan.id, category.name.as("categoryName"), plan.targetAmount))
                .from(plan)
                .join(category).on(plan.category.eq(category))
                .where(plan.member.eq(member)
                        .and(plan.baseDate.eq(baseDate)))
                .fetch();
    }

    public Plan getPlan(MonthlyPlanSetDto monthlyPlanSetDto, Category category, Member member){
        return queryFactory.select(plan)
                .from(plan)
                .where(plan.category.eq(category)
                        .and(plan.baseDate.eq(monthlyPlanSetDto.baseDate()))
                        .and(plan.member.eq(member)))
                .fetchOne();
    }
}
