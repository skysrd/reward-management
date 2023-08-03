package com.skysrd.marketboro.api.member.repository;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
