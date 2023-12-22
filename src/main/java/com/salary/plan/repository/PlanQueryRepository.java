package com.salary.plan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.member.entity.Member;
import com.salary.plan.dto.MonthlyPlanDto;
import com.salary.plan.dto.QMonthlyPlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
                category.name.as("categoryName"), plan.targetAmount,
                        consumption.amount.sum().as("consumptionAmount"),
                        plan.targetAmount.subtract(consumption.amount.sum()).as("remainAmount")
                ))
                .from(plan)
                .join(category).on(plan.category.eq(category))
                .leftJoin(consumption).on(consumption.category.eq(category))
                .where(plan.member.eq(member)
                        .and(plan.baseDate.eq(baseDate)))
                .groupBy(category.name, plan.targetAmount)
                .fetch();
    }
}
