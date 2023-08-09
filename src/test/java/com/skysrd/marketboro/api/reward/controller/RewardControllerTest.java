package com.skysrd.marketboro.api.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import com.skysrd.marketboro.api.reward.domain.entity.Reward;
import com.skysrd.marketboro.api.reward.domain.dto.RewardRequest;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RewardControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @BeforeEach
    void beforeEach() {
        Member member = Member.createBuilder()
                .id(1L)
                .build();

        memberRepository.save(member);

        rewardRepository.save(Reward.createBuilder()
                .id(1L)
                .member(member)
                .initialPrice(100)
                .build());
    }

    @Test
    @DisplayName("현재 적립금 잔액 확인")
    void getRewardSummary() throws Exception {
        mockMvc.perform(
                get("/api/reward/sum?memberId=1")
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }

    @Test
    @DisplayName("적립금 적립")
    void saveReward() throws Exception {
        RewardRequest rewardRequest = RewardRequest.createBuilder()
                        .memberId(1L)
                                .price(100)
                                        .build();

        mockMvc.perform(
                post("/api/reward/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardRequest))
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }

    @Test
    @DisplayName("적립금 사용")
    void spendReward() throws Exception {
        RewardRequest rewardRequest = RewardRequest.createBuilder()
                .memberId(1L)
                .price(100)
                .build();

        mockMvc.perform(
                post("/api/reward/spend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardRequest))
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }

    @Test
    @DisplayName("적립금 사용 롤백")
    void rollbackReward() throws Exception {
        mockMvc.perform(
                post("/api/reward/rollback?rewardId=1")
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }
}