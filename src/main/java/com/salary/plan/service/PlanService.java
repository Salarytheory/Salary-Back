package com.salary.plan.service;

import com.salary.consumption.dto.TargetAmountDto;
import com.salary.member.entity.Member;
import com.salary.plan.entity.GoalManagement;
import com.salary.plan.repository.GoalManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final GoalManagementRepository goalManagementRepository;

    public void setTargetAmount(Member member, TargetAmountDto targetAmountDto){
        goalManagementRepository.save(new GoalManagement(member, targetAmountDto));
    }
}
