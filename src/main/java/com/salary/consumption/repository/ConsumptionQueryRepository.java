package com.salary.consumption.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.consumption.dto.*;
import com.salary.consumption.entity.Consumption;
import com.salary.consumption.entity.Grade;
import com.salary.member.entity.Member;
import com.salary.member.entity.QMember;
import com.salary.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.salary.consumption.entity.QConsumption.consumption;
import static com.salary.category.entity.QCategory.category;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsumptionQueryRepository {
    private final JPAQueryFactory queryFactory;

    public long getTotalSpentAmount(Member member, String baseDate){
        LocalDate start = DateUtil.getTargetDate(baseDate, member.getResetDay());
        LocalDate end = DateUtil.getNextMonthDate(start);

        Long totalAmount = queryFactory.select(consumption.amount.sum().coalesce(0L))
                .from(consumption)
                .leftJoin(QMember.member)
                .on(consumption.member.id.eq(QMember.member.id))
                .where(QMember.member.eq(member)
                        .and(consumption.usedAt.between(start, end)))
                .fetchOne();
        return totalAmount == null ? 0 : totalAmount;
    }

    public StupidConsumptionCurrentSituationDto getStupidConsumptionCurrentSituation(Member member, String baseDate){
        LocalDate start = DateUtil.getTargetDate(baseDate, member.getResetDay());
        LocalDate end = DateUtil.getNextMonthDate(start);

        return queryFactory.select(new QStupidConsumptionCurrentSituationDto(
                JPAExpressions.select(consumption.amount.sum().coalesce(0L).as("greatAmount"))
                        .from(consumption)
                        .where(consumption.member.eq(member)
                                .and(consumption.grade.eq(Grade.GREAT))
                                .and(consumption.usedAt.between(start, end))),
                JPAExpressions.select(consumption.amount.sum().coalesce(0L).as("stupidAmount"))
                        .from(consumption)
                        .where(consumption.member.eq(member)
                                .and(consumption.grade.eq(Grade.STUPID))
                                .and(consumption.usedAt.between(start, end)))
                ))
                .from(consumption)
                .limit(1)
                .fetchOne();
    }

    public List<ConsumptionHistoryDto> getMonthlyConsumptionHistory(Member member, String baseDate, Pageable pageable){
        LocalDate start = DateUtil.getTargetDate(baseDate, member.getResetDay());
        LocalDate end = DateUtil.getNextMonthDate(start);

        return queryFactory.select(new QConsumptionHistoryDto(
                    consumption.id.as("consumptionId"), consumption.name, consumption.amount.as("spentAmount"),
                    category.name.as("categoryName"), consumption.usedAt, consumption.grade
                ))
                .from(consumption)
                .join(category).on(consumption.category.eq(category))
                .where(consumption.member.eq(member)
                        .and(consumption.usedAt.between(start, end)))
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .orderBy(consumption.usedAt.desc(), consumption.grade.asc(), consumption.id.desc())
                .fetch();
    }

    public Long getMonthlyConsumptionHistoryCount(Member member, String baseDate, Pageable pageable){
        LocalDate start = DateUtil.getTargetDate(baseDate, member.getResetDay());
        LocalDate end = DateUtil.getNextMonthDate(start);

        return queryFactory.select(consumption.count())
                .from(consumption)
                .join(category).on(consumption.category.eq(category))
                .where(consumption.member.eq(member)
                        .and(consumption.usedAt.between(start, end)))
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetchFirst();
    }

    public ConsumptionHistoryDto getConsumptionHistory(Long id){
        return queryFactory.select(new QConsumptionHistoryDto(
                        consumption.id.as("consumptionId"), consumption.name, consumption.amount.as("spentAmount"),
                        category.name.as("categoryName"), consumption.usedAt, consumption.grade
                ))
                .from(consumption)
                .join(category).on(consumption.category.eq(category))
                .where(consumption.id.eq(id))
                .fetchFirst();
    }

    public List<MonthlyCategoryConsumptionDto> getMonthlyCategoryConsumption(Member member, LocalDate prevDate, LocalDate nextDate){
        return queryFactory.select(new QMonthlyCategoryConsumptionDto(consumption.category.name.as("categoryName"),
                        consumption.amount.sum().as("amount")))
                .from(consumption)
                .where(consumption.member.eq(member)
                        .and(consumption.usedAt.between(prevDate, nextDate)))
                .groupBy(consumption.category)
                .fetch();
    }
}
