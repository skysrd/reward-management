package com.skysrd.marketboro.api.history.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.history.repository.HistoryRepository;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HistoryControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        Member member = Member.createBuilder()
                        .id(1L)
                                .build();

        memberRepository.save(member);

        historyRepository.save(History.createBuilder()
                .id(1L)
                .member(member)
                .rewardType(RewardType.SAVE)
                .price(100)
                .build());

        historyRepository.save(History.createBuilder()
                .id(2L)
                .member(member)
                .rewardType(RewardType.SPEND)
                .price(50)
                .build());
    }

    @Test
    @DisplayName("적립금 적립 내역 확인")
    void getSaveHistory() throws Exception {
        mockMvc.perform(get("/api/history?memberId=1&rewardType=SAVE"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("적립금 사용 내역 확인")
    void getSpendHistory() throws Exception {
        mockMvc.perform(get("/api/history?memberId=1&rewardType=SPEND"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}