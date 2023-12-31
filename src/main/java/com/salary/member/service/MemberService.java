package com.salary.member.service;

import com.salary.member.dto.SocialAuthInfoDto;
import com.salary.member.entity.Member;
import com.salary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StateManager stateManager;

    public Optional<Member> getMemberInfo(String sub){
        return memberRepository.findBySub(sub);
    }

    public Member socialLogin(String state, SocialAuthInfoDto socialAuthInfo){
        boolean isValid = stateManager.isExists(state);
        if(isValid){
            Member member = memberRepository.findBySub(socialAuthInfo.sub()).orElse(null);
            if(member == null) member = memberRepository.save(new Member(socialAuthInfo));

            if("N".equals(member.getIsCanceled())){
                member.restore();
                memberRepository.save(member);
            }
            return member;
        }
        throw new IllegalArgumentException();
    }

    public void setResetDay(Member member, int resetDay){
        member.setResetDay(resetDay);
        memberRepository.save(member);
    }

    public void setCurrencyUnit(Member member, String currencyUnit){
        member.setCurrencyUnit(currencyUnit);
        memberRepository.save(member);
    }

    public void cancel(Member member){
        member.cancel();
        memberRepository.save(member);
    }
}
