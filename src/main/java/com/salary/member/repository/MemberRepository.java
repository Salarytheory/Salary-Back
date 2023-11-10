package com.salary.member.repository;

import com.salary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySub(String sub);
    boolean existsBySub(String sub);
}
