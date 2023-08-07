package com.skysrd.marketboro.api.reward.service;

import com.skysrd.marketboro.api.history.service.HistoryService;
import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.service.MemberService;
import com.skysrd.marketboro.api.reward.domain.Reward;
import com.skysrd.marketboro.api.reward.domain.dto.RewardRequest;
import com.skysrd.marketboro.api.reward.domain.dto.RewardResponse;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @InjectMocks
    private RewardService rewardService;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private HistoryService historyService;

    Member FIXTURE_MEMBER_01 = Member.createBuilder()
            .id(1L)
            .build();

    Member FIXTURE_MEMBER_02 = Member.createBuilder()
            .id(2L)
            .build();

    Reward FIXTURE_REWARD_01 = Reward.createBuilder()
            .id(1L)
            .member(FIXTURE_MEMBER_01)
            .initialPrice(100)
            .build();

    Reward FIXTURE_REWARD_02 = Reward.createBuilder()
            .id(2L)
            .member(FIXTURE_MEMBER_01)
            .initialPrice(-50)
            .build();

    Reward FIXTURE_REWARD_03 = Reward.createBuilder()
            .id(3L)
            .member(FIXTURE_MEMBER_02)
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
        given(rewardRepository.findById(any())).willReturn(Optional.ofNullable(FIXTURE_REWARD_01));

        //when
        Integer rewardSummary = rewardService.getRewardSummary(FIXTURE_REWARD_01.getMember().getId());

        //then
        assertThat(rewardSummary).isEqualTo(FIXTURE_REWARD_01.getInitialPrice());

    }

    @Test
    @DisplayName("적립금 적립")
    void saveReward() {
        //given
        RewardRequest rewardRequest = RewardRequest.createBuilder()
                .memberId(FIXTURE_MEMBER_01.getId())
                .price(100)
                .build();

        List<Reward> rewardList = new ArrayList<>();
        rewardList.add(FIXTURE_REWARD_01);

        given(rewardRepository.findByMember(any())).willReturn(rewardList);

        //when
        RewardResponse rewardResponse = rewardService.saveReward(rewardRequest);

        //then
        assertThat(rewardResponse.getId()).isEqualTo(FIXTURE_REWARD_01.getId());
        assertThat(rewardResponse.getBalance()).isEqualTo(FIXTURE_REWARD_01.getBalance());
        assertThat(rewardResponse.getInitialPrice()).isEqualTo(FIXTURE_REWARD_01.getInitialPrice());
    }

    @Test
    @DisplayName("적립금 사용")
    void spendReward() {
    }
}