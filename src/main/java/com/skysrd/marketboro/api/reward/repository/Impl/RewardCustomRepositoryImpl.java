package com.skysrd.marketboro.api.reward.repository.Impl;

import com.skysrd.marketboro.api.member.domain.entity.Member;
import com.skysrd.marketboro.api.reward.domain.entity.Reward;
import com.skysrd.marketboro.api.reward.repository.RewardCustomRepository;
import com.skysrd.marketboro.common.support.Querydsl4RepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.skysrd.marketboro.api.reward.domain.QReward.reward;

public class RewardCustomRepositoryImpl extends Querydsl4RepositorySupport implements RewardCustomRepository {

    public RewardCustomRepositoryImpl() {
        super(Reward.class);
    }

    @Override
    public Integer findBalanceSummaryByMember(Member member1) {
        return select(
                reward.balance.sum())
                .from(reward)
                .where(reward.createdDate.after(LocalDateTime.now().minusYears(1))
                    .and(reward.member.id.eq(member1.getId()))
                    .and(reward.balance.gt(0)))
                .fetchOne();
    }

    @Override
    public List<Reward> findGtZeroRewards(Member member) {
        return selectFrom(reward)
                .where(reward.createdDate.after(LocalDateTime.now().minusYears(1))
                    .and(reward.member.id.eq(member.getId())
                    .and(reward.balance.gt(0)))
                )
                .orderBy(reward.id.asc())
                .fetch();
    }

    @Override
    public List<Reward> findGoeZeroRewards(Member member) {
        return selectFrom(reward)
                .where(reward.createdDate.after(LocalDateTime.now().minusYears(1))
                        .and(reward.member.id.eq(member.getId())
                        .and(reward.balance.goe(0)))
                )
                .orderBy(reward.id.desc())
                .fetch();
    }
}
