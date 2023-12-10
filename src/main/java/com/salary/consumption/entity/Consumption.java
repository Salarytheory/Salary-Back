package com.salary.consumption.entity;

import com.salary.category.entity.Category;
import com.salary.consumption.dto.ConsumptionRecordDto;
import com.salary.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "consumption")
@NoArgsConstructor
@ToString
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private long amount;
    private String description;

    @Column(name = "used_at")
    private LocalDate usedAt;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Consumption(Member member, ConsumptionRecordDto consumptionRecordDto, Category category){
        this.member = member;
        this.category = category;
        this.name = consumptionRecordDto.name();
        this.amount = consumptionRecordDto.spentAmount();
        this.usedAt = consumptionRecordDto.usedAt();
        this.grade = Grade.valueOf(consumptionRecordDto.grade());
    }

    public void update(ConsumptionRecordDto consumptionRecordDto, Category category){
        this.category = category;
        this.name = consumptionRecordDto.name();
        this.amount = consumptionRecordDto.spentAmount();
        this.usedAt = consumptionRecordDto.usedAt();
        this.grade = Grade.valueOf(consumptionRecordDto.grade());
    }
}
