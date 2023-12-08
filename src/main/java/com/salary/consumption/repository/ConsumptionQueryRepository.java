package com.salary.consumption.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.consumption.dto.QStupidConsumptionCurrentSituationDto;
import com.salary.consumption.dto.StupidConsumptionCurrentSituationDto;
import com.salary.consumption.entity.Grade;
import com.salary.member.entity.Member;
import com.salary.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.salary.consumption.entity.QConsumption.consumption;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsumptionQueryRepository {
    private final JPAQueryFactory queryFactory;

    public long getTotalSpentAmount(Member member){
        Long totalAmount = queryFactory.select(consumption.amount.sum().coalesce(0L))
                .from(consumption)
                .leftJoin(QMember.member)
                .on(consumption.member.id.eq(QMember.member.id))
                .where(QMember.member.eq(member))
                .groupBy(consumption.member)
                .fetchOne();
        return totalAmount == null ? 0 : totalAmount;
    }

    public StupidConsumptionCurrentSituationDto getStupidConsumptionCurrentSituation(Member member){
        return queryFactory.select(new QStupidConsumptionCurrentSituationDto(
                JPAExpressions.select(consumption.amount.sum().coalesce(0L).as("greatAmount"))
                        .from(consumption)
                        .where(consumption.member.eq(member)
                                .and(consumption.grade.eq(Grade.GREAT))),
                JPAExpressions.select(consumption.amount.sum().coalesce(0L).as("stupidAmount"))
                        .from(consumption)
                        .where(consumption.member.eq(member)
                                .and(consumption.grade.eq(Grade.STUPID)))
                ))
                .from(consumption)
                .limit(1)
                .fetchOne();
    }


}
