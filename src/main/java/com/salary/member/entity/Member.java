package com.salary.member.entity;

import com.salary.member.dto.SocialAuthInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor @ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sub;
    private String name;
    private String email;

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
}
