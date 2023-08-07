package com.skysrd.marketboro.api.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc.perform(
                get("/api/reward/sum?memberId=1")
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }

    @Test
    @DisplayName("적립금 사용")
    void spendReward() throws Exception {
        mockMvc.perform(
                get("/api/reward/sum?memberId=1")
        ).andExpect(
                status().isOk()
        ).andDo(print());
    }
}