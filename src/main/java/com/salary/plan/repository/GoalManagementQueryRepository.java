package com.salary.plan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salary.member.entity.Member;
import com.salary.plan.entity.GoalManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.salary.plan.entity.QGoalManagement.goalManagement;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoalManagementQueryRepository {
    private final JPAQueryFactory queryFactory;

    public GoalManagement getGoalInfo(Member member, String startTargetMonth){
        return queryFactory.select(goalManagement)
                .from(goalManagement)
                .where(goalManagement.member.eq(member)
                        .and(goalManagement.startTargetMonth.eq(startTargetMonth)))
                .fetchOne();
    }
}
