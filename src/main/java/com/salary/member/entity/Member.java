package com.salary.member.entity;

import com.salary.global.entity.BaseTimeEntity;
import com.salary.member.dto.SocialAuthInfoDto;
import com.salary.util.RandomNameMaker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor @ToString
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sub;
    private String name;
    private String email;

    @Column(name = "reset_day")
    private int resetDay;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "currency_unit")
    private String currencyUnit;

    @Column(name = "is_widget")
    private String isWidget;

    @Column(name = "is_canceled")
    private String isCanceled;

    public String getRole(){
        return role.getKey();
    }

    public Member(SocialAuthInfoDto socialAuthInfo){
        this.sub = socialAuthInfo.sub();
        this.email = socialAuthInfo.email();
        this.provider = Provider.valueOf(socialAuthInfo.provider());
        this.role = Role.USER;
    }

    @PrePersist
    public void prePersist() {
        this.resetDay = 1;
        this.currencyUnit = "₩";
        this.isCanceled = "Y";
        this.isWidget = "N";
        this.name = RandomNameMaker.generateRandomString(7);
    }

    public void setResetDay(int resetDay){
        this.resetDay = resetDay;
    }

    public void useWidget(){
        this.isWidget = "Y";
    }

    public void cancel(){
        this.isCanceled = "N";
    }

    public void restore(){
        this.isCanceled = "Y";
    }

    public void setCurrencyUnit(String currencyUnit){
        this.currencyUnit = currencyUnit;
    }
}
