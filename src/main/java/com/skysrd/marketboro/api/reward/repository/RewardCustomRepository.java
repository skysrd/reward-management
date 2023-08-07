package com.skysrd.marketboro.api.reward.repository;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.reward.domain.Reward;

import java.util.List;

public interface RewardCustomRepository {
    Integer findBalanceSummaryByMember(Member member);

    List<Reward> findGtZeroRewards(Member member);

    List<Reward> findGoeZeroRewards(Member member);
}
