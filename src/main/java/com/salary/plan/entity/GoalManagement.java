package com.salary.plan.entity;

import com.salary.consumption.dto.TargetAmountDto;
import com.salary.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name = "goal_management")
@NoArgsConstructor
@ToString
public class GoalManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_date")
    private String targetDate;

    @Column(name = "target_amount")
    private long targetAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public GoalManagement(Member member, TargetAmountDto targetAmountDto){
        this.member = member;
        this.targetDate = targetAmountDto.targetDate();
        this.targetAmount = targetAmountDto.targetAmount();
    }
}
