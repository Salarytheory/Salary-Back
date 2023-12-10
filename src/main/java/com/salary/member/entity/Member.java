package com.salary.member.entity;

import com.salary.global.entity.BaseTimeEntity;
import com.salary.member.dto.SocialAuthInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

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
    }
}
