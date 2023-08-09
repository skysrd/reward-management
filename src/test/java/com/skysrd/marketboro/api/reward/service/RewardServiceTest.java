package com.skysrd.marketboro.api.reward.service;

import com.skysrd.marketboro.api.history.service.HistoryService;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.api.reward.domain.entity.Reward;
import com.skysrd.marketboro.api.reward.domain.dto.RewardRequest;
import com.skysrd.marketboro.api.reward.domain.dto.RewardResponse;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    RewardService rewardService;

    Member PREFIX_MEMBER_01 = Member.createBuilder()
            .id(1L)
            .build();

    Member PREFIX_MEMBER_02 = Member.createBuilder()
            .id(2L)
            .build();

    Reward PREFIX_REWARD_01 = Reward.createBuilder()
            .id(1L)
            .member(PREFIX_MEMBER_01)
            .initialPrice(100)
            .build();

    Reward PREFIX_REWARD_02 = Reward.createBuilder()
            .id(2L)
            .member(PREFIX_MEMBER_01)
            .initialPrice(-50)
            .build();

    Reward PREFIX_REWARD_03 = Reward.createBuilder()
            .id(3L)
            .member(PREFIX_MEMBER_02)
            .initialPrice(100)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardService = new RewardService(rewardRepository, memberService, historyService);
    }

    @Test
    @DisplayName("적립금 잔액 조회")
    void getRewardSummary() {
        //given
        given(rewardRepository.findBalanceSummaryByMember(any())).willReturn(100);

        //when
        Integer member01Summary = rewardService.getRewardSummary(1L);

        //then
        assertEquals(100, member01Summary);
    }

    @Test
    @DisplayName("적립금 적립")
    void saveReward() {
        //given
        RewardRequest rewardRequest = RewardRequest.createBuilder()
                .memberId(1L)
                .price(100)
                .build();

        Member member = Member.createBuilder()
                        .id(1L)
                        .build();

        Reward reward = Reward.createBuilder()
                        .id(1L)
                                .member(member)
                                        .initialPrice(100)
                                                .build();

        given(memberService.getMember(any())).willReturn(member);
        given(rewardRepository.save(any())).willReturn(reward);

        //when
        RewardResponse rewardResponse = rewardService.saveReward(rewardRequest);

        //then
        assertThat(PREFIX_REWARD_01.getBalance()).isEqualTo(rewardResponse.getBalance());
        assertThat(PREFIX_REWARD_01.getInitialPrice()).isEqualTo(rewardResponse.getInitialPrice());
    }

    @Test
    @DisplayName("적립금 사용")
    void spendReward() {
        //given
        RewardRequest rewardRequest = RewardRequest.createBuilder()
                .memberId(1L)
                .price(100)
                .build();

        Member member = Member.createBuilder()
                .id(1L)
                .build();

        Reward reward = Reward.createBuilder()
                .id(1L)
                .member(member)
                .initialPrice(100)
                .build();

        given(rewardRepository.findBalanceSummaryByMember(member)).willReturn(reward.getInitialPrice());
        given(memberService.getMember(any())).willReturn(member);
        given(rewardRepository.save(any())).willReturn(reward);

        //when
        RewardResponse rewardResponse = rewardService.spendReward(rewardRequest);

        //then
        assertThat(rewardResponse.getBalance()).isEqualTo(-100);
        assertThat(rewardResponse.getInitialPrice()).isEqualTo(-100);
    }

    @Test
    @DisplayName("적립금 롤백")
    void rollbackReward () {
        //given
        Member member = Member.createBuilder()
                .id(1L)
                .build();

        Reward reward = Reward.createBuilder()
                .id(1L)
                .member(member)
                .initialPrice(100)
                .build();

        Reward rollbackReward = Reward.createBuilder()
                .id(2L)
                .member(member)
                .initialPrice(-50)
                .build();

        given(rewardRepository.findBalanceSummaryByMember(member)).willReturn(reward.getBalance() - rollbackReward.getBalance());
        given(memberService.getMember(any())).willReturn(member);
        given(rewardRepository.findById(any())).willReturn(Optional.of(reward));

        Integer beforeSummary = rewardService.getRewardSummary(1L);

        //when
        given(rewardRepository.findBalanceSummaryByMember(member)).willReturn(reward.getBalance());
        given(rewardRepository.findById(any())).willReturn(Optional.of(rollbackReward));
        RewardResponse rollbackResponse = rewardService.rollbackRewardSpend(2L);
        Integer afterSummary = rewardService.getRewardSummary(1L);

        //then
        assertEquals(beforeSummary, afterSummary - rollbackResponse.getBalance());
    }
}