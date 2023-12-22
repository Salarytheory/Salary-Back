package com.salary.plan.entity;

import com.salary.category.entity.Category;
import com.salary.consumption.dto.MonthlyPlanSetDto;
import com.salary.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name = "plan",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "plan_UN",
                        columnNames = {"base_date", "category_id", "member_id"}
                )
        })
@NoArgsConstructor
@ToString
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_amount")
    private long targetAmount;

    @Column(name = "base_date")
    private String baseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Plan(MonthlyPlanSetDto monthlyPlanSetDto, Member member, Category category){
        this.targetAmount = monthlyPlanSetDto.targetAmount();
        this.baseDate = monthlyPlanSetDto.baseDate();
        this.category = category;
        this.member = member;
    }
}
