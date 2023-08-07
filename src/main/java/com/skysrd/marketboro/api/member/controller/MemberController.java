package com.skysrd.marketboro.api.member.controller;

import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.common.result.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<?> createMember() {
        Long memberId = memberService.createMember();
        return ResponseHandler.generate()
                .status(HttpStatus.CREATED)
                .data(memberId)
                .build();
    }
}
