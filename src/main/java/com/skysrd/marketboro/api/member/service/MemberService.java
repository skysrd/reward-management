package com.skysrd.marketboro.api.member.service;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import com.skysrd.marketboro.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember() {
        Member member = Member.createBuilder().build();
        return member.getId();
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ApiException.builder()
                        .errorMessage("Member Not Found")
                        .errorCode("MEMBER_NOT_FOUND")
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }
}
