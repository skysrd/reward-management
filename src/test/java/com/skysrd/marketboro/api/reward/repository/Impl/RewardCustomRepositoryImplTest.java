package com.skysrd.marketboro.api.reward.repository.Impl;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.member.repository.MemberRepository;
import com.skysrd.marketboro.api.reward.domain.Reward;
import com.skysrd.marketboro.api.reward.repository.RewardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RewardCustomRepositoryImplTest {

    Member PREFIX_MEMBER_01 = Member.createBuilder()
            .id(1L)
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
            .member(PREFIX_MEMBER_01)
            .initialPrice(50)
            .build();

    Reward PREFIX_REWARD_04 = Reward.createBuilder()
            .id(4L)
            .member(PREFIX_MEMBER_01)
            .initialPrice(1)
            .build();

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findBalanceSummaryByMember() {
        //given


        memberRepository.save(PREFIX_MEMBER_01);

        List<Reward> rewardList = new ArrayList<>();
        rewardList.add(PREFIX_REWARD_01);
        rewardList.add(PREFIX_REWARD_02);

        rewardRepository.saveAll(rewardList);

        //when
        Integer balanceSummary = rewardRepository.findBalanceSummaryByMember(PREFIX_MEMBER_01);

        //then
        assertEquals(balanceSummary, PREFIX_REWARD_01.getBalance()+ PREFIX_REWARD_02.getBalance());
    }

    @Test
    void findAvailableRewards() {
        //given
        Member member = Member.createBuilder()
                .id(1L)
                .build();

        memberRepository.save(member);

        List<Reward> rewardList = new ArrayList<>();

        rewardList.add(PREFIX_REWARD_01);
        rewardList.add(PREFIX_REWARD_02);
        rewardList.add(PREFIX_REWARD_03);
        rewardList.add(PREFIX_REWARD_04);

        rewardRepository.saveAll(rewardList);

        //when
        List<Reward> availableRewardList = rewardRepository.findGtZeroRewards(member);

        //then
        assertEquals(2, availableRewardList.size());
        assertEquals(PREFIX_REWARD_01.getId(), availableRewardList.get(0).getId());
        assertEquals(PREFIX_REWARD_01.getBalance(), availableRewardList.get(0).getBalance());
        assertEquals(PREFIX_REWARD_04.getId(), availableRewardList.get(1).getId());
        assertEquals(PREFIX_REWARD_04.getBalance(), availableRewardList.get(1).getBalance());
    }

    @Test
    void findGoeZeroRewards() {
        //given
        Member member = Member.createBuilder()
                .id(1L)
                .build();

        memberRepository.save(member);

        List<Reward> rewardList = new ArrayList<>();

        rewardList.add(PREFIX_REWARD_01);
        rewardList.add(PREFIX_REWARD_02);
        rewardList.add(PREFIX_REWARD_03);
        rewardList.add(PREFIX_REWARD_04);

        rewardRepository.saveAll(rewardList);

        //when
        List<Reward> availableRewardList = rewardRepository.findGoeZeroRewards(member);

        //then
        assertEquals(3, availableRewardList.size());
        assertEquals(PREFIX_REWARD_04.getId(), availableRewardList.get(0).getId());
        assertEquals(PREFIX_REWARD_04.getBalance(), availableRewardList.get(0).getBalance());
        assertEquals(PREFIX_REWARD_03.getId(), availableRewardList.get(1).getId());
        assertEquals(PREFIX_REWARD_03.getBalance(), availableRewardList.get(1).getBalance());
        assertEquals(PREFIX_REWARD_01.getId(), availableRewardList.get(2).getId());
        assertEquals(PREFIX_REWARD_01.getBalance(), availableRewardList.get(2).getBalance());
    }
}