package com.skysrd.marketboro.api.history.service;

import com.skysrd.marketboro.api.history.domain.dto.HistoryResponse;
import com.skysrd.marketboro.api.history.domain.dto.HistorySearchRequest;
import com.skysrd.marketboro.api.history.domain.entity.History;
import com.skysrd.marketboro.api.history.domain.enums.RewardType;
import com.skysrd.marketboro.api.history.repository.HistoryRepository;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.api.reward.domain.Reward;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private MemberRepository memberRepository;

    Member PREFIX_MEMBER_01 = Member.createBuilder()
            .id(1L)
            .build();

    History PREFIX_HISTORY_01 = History.createBuilder()
            .id(1L)
            .member(PREFIX_MEMBER_01)
            .price(100)
            .rewardType(RewardType.SAVE)
            .build();

    History PREFIX_HISTORY_02 = History.createBuilder()
            .id(2L)
            .member(PREFIX_MEMBER_01)
            .price(50)
            .rewardType(RewardType.SPEND)
            .build();

    History PREFIX_HISTORY_03 = History.createBuilder()
            .id(3L)
            .member(PREFIX_MEMBER_01)
            .price(50)
            .rewardType(RewardType.SAVE)
            .build();

    History PREFIX_HISTORY_04 = History.createBuilder()
            .id(4L)
            .member(PREFIX_MEMBER_01)
            .price(1)
            .rewardType(RewardType.SAVE)
            .build();

    @Test
    @DisplayName("Save 히스토리 보기")
    void getSaveHistory() {
        //given
        List<History> saveHistoryList = new ArrayList<>();
        saveHistoryList.add(PREFIX_HISTORY_01);
        saveHistoryList.add(PREFIX_HISTORY_03);
        saveHistoryList.add(PREFIX_HISTORY_04);

        Page<History> saveHistoryPage = new PageImpl<>(saveHistoryList);

        List<History> spendHistoryList = new ArrayList<>();
        spendHistoryList.add(PREFIX_HISTORY_02);

        Page<History> spendHistoryPage = new PageImpl<>(spendHistoryList);

        given(memberRepository.findById(any())).willReturn(java.util.Optional.ofNullable(PREFIX_MEMBER_01));
        given(historyRepository.findPageByRewardTypeAndMember(any(), any(), any())).willReturn(saveHistoryPage);

        //when
        Page<HistoryResponse> saveHistoryResponsePage = historyService.getHistory(
                HistorySearchRequest.createBuilder()
                        .memberId(1L)
                        .rewardType(RewardType.SAVE)
                        .build()
        );

        //then
        assertEquals(PREFIX_HISTORY_01.getId(), saveHistoryResponsePage.getContent().get(0).getId());
        assertEquals(PREFIX_HISTORY_01.getPrice(), saveHistoryResponsePage.getContent().get(0).getPrice());

        assertEquals(PREFIX_HISTORY_03.getId(), saveHistoryResponsePage.getContent().get(1).getId());
        assertEquals(PREFIX_HISTORY_03.getPrice(), saveHistoryResponsePage.getContent().get(1).getPrice());

        assertEquals(PREFIX_HISTORY_04.getId(), saveHistoryResponsePage.getContent().get(2).getId());
        assertEquals(PREFIX_HISTORY_04.getPrice(), saveHistoryResponsePage.getContent().get(2).getPrice());
    }

    @Test
    @DisplayName("Spend 히스토리 보기")
    void getSpendHistory() {
        //given
        List<History> spendHistoryList = new ArrayList<>();
        spendHistoryList.add(PREFIX_HISTORY_02);

        Page<History> spendHistoryPage = new PageImpl<>(spendHistoryList);

        given(memberRepository.findById(any())).willReturn(java.util.Optional.ofNullable(PREFIX_MEMBER_01));
        given(historyRepository.findPageByRewardTypeAndMember(any(), any(), any())).willReturn(spendHistoryPage);

        //when
        Page<HistoryResponse> spendHistoryResponsePage = historyService.getHistory(
                HistorySearchRequest.createBuilder()
                        .memberId(1L)
                        .rewardType(RewardType.SAVE)
                        .build()
        );

        //then
        assertEquals(PREFIX_HISTORY_02.getId(), spendHistoryResponsePage.getContent().get(0).getId());
        assertEquals(PREFIX_HISTORY_02.getPrice(), spendHistoryResponsePage.getContent().get(0).getPrice());
    }
}