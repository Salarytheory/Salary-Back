package com.salary.plan.repository;

import com.salary.member.entity.Member;
import com.salary.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PlanRepository extends JpaRepository<Plan, Long> {
    void deleteByMember(Member member);
}
